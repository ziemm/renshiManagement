package cn.xie.vhr.config;

import cn.xie.vhr.model.Menu;
import cn.xie.vhr.model.Role;
import cn.xie.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 根据用户传来的请求地址，分析出请求需要的角色
 * @author: xie
 * @create: 2020-06-07 15:44
 **/
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    MenuService menuService;

    //ant路径风格的匹配工具类
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        //加载出所有资源menus资源：每个角色可以访问的menu资源
        List<Menu> menus = menuService.getAllMenusWithRole();
        for (Menu menu : menus) {
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                //匹配到对应路径，查询该路径对应的资源下可有的角色
                List<Role> roles = menu.getRoles();
                String[] str = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    str[i]=roles.get(i).getName();//获取角色的英文名字
                }
                //返回需要的格式
                return SecurityConfig.createList(str);
            }
        }
        //如果没有匹配到，说明请求是非法的或者无资源或无对应权限的
        //此处的处理方法是：若没有匹配上，则登陆后可以访问
        return SecurityConfig.createList("ROLE_LOGIN"); //设置可以登录访问的标记
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
