package cn.xie.vhr.exception;

import cn.xie.vhr.model.RespBean;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 定义全局的异常处理类
 * @author: xie
 * @create: 2020-06-09 19:28
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public RespBean sqlException(SQLException e){
        if(e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.error("该数据有关联数据，请求失败");
        }
        return RespBean.error("数据库异常，操作失败");
    }
}
