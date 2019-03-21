package com.atguigu.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot04TaskApplicationTests {

    @Autowired
    JavaMailSenderImpl mailSender;

    /**
     * 发送简单邮件
     */
    @Test
    public void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("通知-今晚开会");  //标题
        message.setText("今晚7:30开会");    //内容

        message.setTo("qw85123163@163.com");    //发送到
        message.setFrom("215302414@qq.com");    //发送人

        mailSender.send(message);
    }

    /**
     * 发送复杂邮件
     */
    @Test
    public void test02() throws Exception {
        //1、创建一个复杂的消息邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject("通知-今晚开会");   //标题

        String html = "<b style='color:red'>今天 7:30 开会</b>";

        helper.setText(html, true); //内容是否为html设置为true

        helper.setTo("qw85123163@163.com"); //发送到
        helper.setFrom("215302414@qq.com"); //发送人

        //上传文件
        helper.addAttachment("1.jpg", new File("C:\\Users\\Rinko\\Desktop\\アサちゃん002.jpg"));
        helper.addAttachment("2.jpg", new File("C:\\Users\\Rinko\\Desktop\\アサちゃん.jpg"));

        mailSender.send(mimeMessage);
    }

}
