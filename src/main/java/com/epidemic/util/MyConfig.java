package com.epidemic.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class MyConfig {

//    把国际化组件放进容器
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

}
