package com.magicnian.quartz.springbootquartz.service;

import com.magicnian.quartz.springbootquartz.bean.Site;
import com.magicnian.quartz.springbootquartz.exception.ServiceException;
import com.magicnian.quartz.springbootquartz.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * jobservice
 * Created by liunn on 2018/2/7.
 */
@Service
@Slf4j
public class JobService {


    /**
     * 添加job
     * @param site
     * @return
     * @throws ServiceException
     */
    public ResponseModel addJob(Site site)throws ServiceException{
        //参数校验
        CommonService.validParam(site);

        return new ResponseModel();
    }
}
