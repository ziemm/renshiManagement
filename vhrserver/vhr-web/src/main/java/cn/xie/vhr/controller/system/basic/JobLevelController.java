package cn.xie.vhr.controller.system.basic;

import cn.xie.vhr.model.Joblevel;
import cn.xie.vhr.model.RespBean;
import cn.xie.vhr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: xie
 * @create: 2020-06-10 15:47
 **/
@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {

    @Autowired
    JobLevelService jobLevelService;
    @GetMapping("/")
    public List<Joblevel> getAllJobLevels(){
        return jobLevelService.getAllJobLevels();
    }

    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody Joblevel joblevel){
        if(jobLevelService.addJobLevel(joblevel)==1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @PutMapping("/")
    public RespBean updateJobLevelById(@RequestBody Joblevel joblevel){
        if(jobLevelService.updateJobLevelById(joblevel)==1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteJobLevelById(@PathVariable Integer id){
        if(jobLevelService.deleteJobLevelById(id)==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @DeleteMapping("/")
    public RespBean deleteJobLevelsByIds(Integer[] ids){
        if(jobLevelService.deleteJobLevelsByIds(ids)==ids.length){
            return RespBean.ok("批量删除成功！");
        }
        return RespBean.error("删除失败");
    }

}
