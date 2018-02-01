package com.magicnian.quartz.springbootquartz.rest;

import com.magicnian.quartz.springbootquartz.JDSpiderJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liunn on 2018/1/26.
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private JDSpiderJob jdSpiderJob;

    @RequestMapping("/getByHand")
    public String getByHand(){
        jdSpiderJob.spider();
        return "success";
    }


}
