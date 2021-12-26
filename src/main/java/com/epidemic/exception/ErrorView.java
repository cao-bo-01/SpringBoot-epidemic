package com.epidemic.exception;

import com.epidemic.util.MailComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class ErrorView implements ErrorViewResolver {

    //    邮件发送      项目启动 执行一次
    @Autowired
    MailComponent mailComponent;

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request,
                                         HttpStatus status, Map<String, Object> model) {

        if (status.equals(HttpStatus.NOT_FOUND)) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/resolver404");

            StringBuilder builder = new StringBuilder();

            String s1 = status.toString();
            String s2 = model.toString();
            String s3 = request.getCharacterEncoding();
            String s4 = request.getRequestURI();
            String s5 = request.getProtocol();
            String s6 = request.getRemoteAddr();


            builder.append("错误原因:" + s1 + "\n");
            builder.append("model 信息:" + s2 + "\n");
            builder.append("字符集:" + s3 + "\n");
            builder.append("请求URL:" + s4 + "\n");
            builder.append("协议和版本:" + s5 + "\n");
            builder.append("客户IP:" + s6 + "\n");

            mailComponent.send(builder.toString());
            return modelAndView;
        }
        return null;
    }
}
