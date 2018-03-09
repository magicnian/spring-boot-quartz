package com.magicnian.quartz.springbootquartz.rest;

import com.magicnian.quartz.springbootquartz.config.HttpConfig;
import com.magicnian.quartz.springbootquartz.exception.ServiceException;
import com.magicnian.quartz.springbootquartz.job.JDSpiderJob;
import com.magicnian.quartz.springbootquartz.response.ResponseModel;
import com.magicnian.quartz.springbootquartz.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/paramtest",method = RequestMethod.POST)
    public ResponseModel paramtest(@RequestBody HttpConfig config)throws ServiceException{
        CommonService.validParam(config);

        return new ResponseModel();
    }


}
