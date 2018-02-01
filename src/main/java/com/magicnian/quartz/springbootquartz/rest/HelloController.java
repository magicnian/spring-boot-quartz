package com.magicnian.quartz.springbootquartz.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liunn on 2018/2/1.
 */
@Controller
@RequestMapping("/hello")
@Slf4j
public class HelloController {


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String hello(Model model) {
        model.addAttribute("name", "Friend");
        return "hello";
    }
}
