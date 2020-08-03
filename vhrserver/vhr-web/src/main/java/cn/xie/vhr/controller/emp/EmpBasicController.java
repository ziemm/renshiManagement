package cn.xie.vhr.controller.emp;

import cn.xie.vhr.model.*;
import cn.xie.vhr.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author: xie
 * @create: 2020-07-27 17:13
 **/
@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    NationService nationService;

    @Autowired
    PoliticsstatusService politicsstatusService;

    @Autowired
    JobLevelService jobLevelService;

    @Autowired
    PositionService positionService;

    @Autowired
    DepartmentService departmentService;

    /**
     * 获取员工信息
     * @param page
     * @param size
     * @param employee  检索所需要的员工信息
     * @param beginDateScope 检索所需要的员工开始入职时间段
     * @return
     */
    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          Employee employee, Date[] beginDateScope){

        return employeeService.getEmployeeByPage(page,size,employee,beginDateScope);

    }
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee){
        if(employeeService.addEmp(employee)==1){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }


    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        return nationService.getAllNations();
    }

    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus(){
        return politicsstatusService.getAllPoliticsstatus();
    }

    @GetMapping("/joblevels")
    public List<Joblevel> getAllJobLevels(){
        return jobLevelService.getAllJobLevels();
    }

    @GetMapping("/positions")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    @GetMapping("/maxWorkID")
    public RespBean getMaxWorkID(){
        RespBean respBean = RespBean.build()
                .setStatus(200)
                .setObj(String.format("%08d",employeeService.maxWorkID()+1));
        return respBean;
    }

    @GetMapping("/deps")
    public List<Department> getAllDepartment(){
        return departmentService.getAllDepartments();
    }

    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        if(employeeService.updateEmp(employee)==1){
            RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmpByEid(@PathVariable Integer id){
        if(employeeService.deleteEmpByEid(id)==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
