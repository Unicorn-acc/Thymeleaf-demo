package com.baizhi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author MiracloW
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("/demo")
    public String demo(Model model){
        log.debug("demo ok");
        model.addAttribute("msg","hello thymeleaf");
        return "demo";
    }
//    在SpringMVC中进行配置，不需要为每一个访问thymeleaf模板页面单独开发一个controller请求
//    @GetMapping("/login")
//    public String login(Model model){
//        return "login";
//    }

}
