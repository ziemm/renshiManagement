package cn.xie.mailserver.receiver;

import cn.xie.vhr.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;


/**
 * @author: xie
 * @create: 2020-08-03 18:40
 **/
@Component
public class MailReceiver {
    public  static final Logger logger = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailProperties mailProperties;

    //Themeleaf渲染器
    @Autowired
    TemplateEngine templateEngine;

    //接收并处理消息
    @RabbitListener(queues = "xie.email.welcome")
    public void Handler(Employee employee){
        logger.info(employee.toString());
        //收到消息，发送邮件

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);

        try {
            /*设置邮件*/
            helper.setTo(employee.getEmail());
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject("入职欢迎");
            helper.setSentDate(new Date());

            /*邮件内容设置，Themeleaf*/
            Context context  = new Context();
            context.setVariable("name",employee.getName()); //姓名
            context.setVariable("posName", employee.getPosition().getName()); //职位
            context.setVariable("jobLevelName", employee.getJobLevel().getName());//职位等级
            context.setVariable("departmentName", employee.getDepartment().getName());//部门名称

            //渲染处理
            String mail = templateEngine.process("mail", context);
            helper.setText(mail,true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("邮件发送失败！"+e.getMessage());
        }

    }
}
