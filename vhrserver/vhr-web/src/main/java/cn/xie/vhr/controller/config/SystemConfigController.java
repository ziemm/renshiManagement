package cn.xie.vhr.controller.config;

import cn.xie.vhr.model.Menu;
import cn.xie.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统管理的controller
 * @author: xie
 * @create: 2020-06-04 21:49
 **/
@RestController
@RequestMapping("system/config")
public class SystemConfigController {

    @Autowired
    MenuService menuService;

    /**
     * 根据用户展示可以操作的菜单
     * @return
     */
    @GetMapping("/menu")
    public List<Menu> getMenusByHrId(){
        return menuService.getMenusByHrId();
    }
}
