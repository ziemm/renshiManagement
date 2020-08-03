package cn.xie.vhr.utils;

import cn.xie.vhr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author: xie
 * @create: 2020-07-24 14:26
 **/
public class HrUtils {
    public static Hr getCurrentHr(){
        return (Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
