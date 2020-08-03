package cn.xie.vhr.controller.system.basic;

import cn.xie.vhr.model.Menu;
import cn.xie.vhr.model.RespBean;
import cn.xie.vhr.model.Role;
import cn.xie.vhr.service.MenuService;
import cn.xie.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: xie
 * @create: 2020-06-14 21:01
 **/
@RestController
@RequestMapping("/system/basic/permiss")
public class PermissionController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }

    /**
     * 根据角色的id查询出可以访问的菜单资源id，以便对角色与资源的对应关系进行查看
     * @return
     */
    @GetMapping("/mids/{rid}")
    public List<Integer> getMidsByRid(@PathVariable Integer rid){
        return menuService.getMidsByRid(rid);
    }

    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid,Integer[] mids){
        if(menuService.updateMenuRole(rid,mids)){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败");
    }

    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        if(roleService.addRole(role)==1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("/role/{rid}")
    public RespBean deleteRoleById(@PathVariable Integer rid){
        if(roleService.deleteRoleById(rid)==1){
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败");
    }
}
