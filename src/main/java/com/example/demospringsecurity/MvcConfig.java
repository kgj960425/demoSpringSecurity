package com.example.demospringsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //addViewController(어떤 url 요청이 들어오면) setViewName(어떤 view 이름을 리턴할 것) 인지 --(springMvc에 view 리졸버를 사용해서)
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
        //WebMvcConfigurer.super.addViewControllers(registry);
    }
}