package cn.xie.vhr.controller.system;

import cn.xie.vhr.model.Hr;
import cn.xie.vhr.model.RespBean;
import cn.xie.vhr.model.Role;
import cn.xie.vhr.service.HrService;
import cn.xie.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: xie
 * @create: 2020-07-24 14:14
 **/
@RestController
@RequestMapping("/system/hr")
public class HrController {
    @Autowired
    HrService hrService;
    @Autowired
    RoleService roleService;

    @GetMapping("/")
    public List<Hr> getAllHrs(String keywords){
        return hrService.getAllHrs(keywords);
    }

    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr){
        if(hrService.updateHr(hr)==1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败");
    }

    @GetMapping("/roles")
    public List<Role>getAllRoles(){
        return roleService.getAllRoles();
    }

    @PutMapping("/role")
    public RespBean updateHrRole(Integer hrid,Integer[] rids){
        if(hrService.updateHrRole(hrid,rids)){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteHrById(@PathVariable Integer id){
        if(hrService.deleteHrById(id)==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

}
