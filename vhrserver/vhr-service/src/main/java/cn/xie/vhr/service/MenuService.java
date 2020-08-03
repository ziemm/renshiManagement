package cn.xie.vhr.service;

import cn.xie.vhr.mapper.MenuMapper;
import cn.xie.vhr.mapper.MenuRoleMapper;
import cn.xie.vhr.model.Hr;
import cn.xie.vhr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: xie
 * @create: 2020-06-04 21:54
 **/
@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;

    public List<Menu> getMenusByHrId() {
        //根据存储Session中的用户获取Id,使用下面的方法获得Authentication对象
         Hr hr = (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return menuMapper.getMenusByHrId(hr.getId());
    }

    //@Cacheable 此处可以配置缓存
    public List<Menu> getAllMenusWithRole(){
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.deleteByRid(rid);
        if(mids==null||mids.length==0){
            return true;
        }

        Integer result = menuRoleMapper.insertRecord(rid,mids);
        return result==mids.length;
    }
}
