package com.epidemic.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MailComponent {

    @Autowired
    private JavaMailSender mailSender;

    public void send(String content) {

        System.out.println("发送邮件");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("程序运行情况报告");

//        记录时间
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String time = dateFormat.format(date);


        message.setText("运行时间：" + time +"\n"+content);

        message.setTo("13629101894@139.com");
        message.setFrom("1972746991@qq.com");

        mailSender.send(message);


    }



}
