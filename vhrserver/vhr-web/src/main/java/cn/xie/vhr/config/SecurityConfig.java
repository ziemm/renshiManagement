package cn.xie.vhr.config;

import cn.xie.vhr.model.Hr;
import cn.xie.vhr.model.RespBean;
import cn.xie.vhr.service.HrService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: xie
 * @create: 2020-05-20 20:20
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    HrService hrService;

    @Autowired
    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();//该方法可以显式说明密码的加密方式
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //使用自定义用户认证
        auth.userDetailsService(hrService).passwordEncoder(passwordEncoder());

        //也可不写.passwordEncoder(),则默认会找到
    }

    /**
     * 配置不需要拦截的页面
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login","/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico", "/verifyCode");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .anyRequest()
//                .authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        return o;
                    }
                })
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/doLogin")
                .loginPage("/login")
                .successHandler(new AuthenticationSuccessHandler() {
                    //认证成功之后需要的操作
                    public void onAuthenticationSuccess(HttpServletRequest rep, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        //返回认证成功的实体，并且返回字符串
                        Hr hr = (Hr)auth.getPrincipal();
                        hr.setPassword(null);//返回的json字符串不需要密码
                        RespBean ok = RespBean.ok("登录成功！",hr);
                        String s = new ObjectMapper().writeValueAsString(ok);//将java对象转为Json格式对象
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest rep, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        //认证失败，列出登录失败的原因
                        RespBean respBean = RespBean.error("登录失败");
                        if(e instanceof LockedException)
                            respBean.setMsg("账户被锁定，请联系管理员！");
                        else if(e instanceof CredentialsExpiredException)
                            respBean.setMsg("密码过期，请联系管理员");
                        else if(e instanceof AccountExpiredException)
                            respBean.setMsg("账户过期，请联系管理员");
                        else if(e instanceof DisabledException)
                            respBean.setMsg("账户被禁用，请联系管理员");
                        else if(e instanceof BadCredentialsException)
                            respBean.setMsg("用户名或密码错误，请联系管理员");

                        out.write(new ObjectMapper().writeValueAsString(respBean));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .logout()//默认注销登录的url就是logout
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功！")));
                    }
                })
                .and()
                .csrf().disable() //停掉跨站请求伪造，以供postman可以测试
                //没有登录或者权限不足导致请求失败时，取消掉默认的重定向
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest eq, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        resp.setStatus(401);//访问没有认证
                        PrintWriter out = resp.getWriter();
                        RespBean respBean = RespBean.error("访问失败");
                        if(e instanceof InsufficientAuthenticationException){
                            respBean.setMsg("请求失败，请联系管理员！");
                        }
                        out.write(new ObjectMapper().writeValueAsString(respBean));
                        out.flush();
                        out.close();
                    }
                })
        ;
    }
}
