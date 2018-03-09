package com.magicnian.quartz.springbootquartz.rest;

import com.magicnian.quartz.springbootquartz.bean.Site;
import com.magicnian.quartz.springbootquartz.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liunn on 2018/2/7.
 */
@RestController
@RequestMapping("/job")
@Slf4j
public class JobController {

    @RequestMapping(value = "/addJob",method = RequestMethod.POST)
    public ResponseModel addJob(@RequestBody Site site){
        return null;
    }
}
