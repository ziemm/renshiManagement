package cn.xie.vhr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xie
 * @create: 2020-05-14 22:56
 **/
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/employee/basic/hello")
    public String test1(){
        return "/employee/basic/hello";
    }

    @GetMapping("/employee/advanced/hello")
    public String test2(){
        return "/employee/advanced/hello";
    }
}
