package cn.xie.vhr.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 判断当前用户是否具备当前角色
 * @author: xie
 * @create: 2020-06-07 17:37
 **/
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        //遍历可访问资源所需要的角色
        for (ConfigAttribute configAttribute : collection) {
            String needRole = configAttribute.getAttribute();
            if("ROLE_LOGIN".equals(needRole)){
                //若果是“ROLE_LOGIN”,说明登陆后就可访问
                if(authentication instanceof AnonymousAuthenticationToken){//若当前用户是一个匿名用户登录的实例，说明未登录
                    throw new AccessDeniedException("尚未登录，请登录");
                }else {
                    return;
                }
            }
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();//获取当前用户所有的角色
            //如果当前用户角色中，与上面collection中的某个角色匹配上，则说明具有权限
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals(needRole)){
                    return; //直接return 通过
                }
            }
        }

        //上面没有return掉，说明该用户具备的角色没有
        throw new AccessDeniedException("权限不足，请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
