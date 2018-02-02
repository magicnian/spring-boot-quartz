package com.magicnian.quartz.springbootquartz.service;

import com.magicnian.quartz.springbootquartz.annotation.ParamValidator;
import com.magicnian.quartz.springbootquartz.enums.ParamValidateMsg;
import com.magicnian.quartz.springbootquartz.enums.ResponseCode;
import com.magicnian.quartz.springbootquartz.exception.ServiceException;
import com.magicnian.quartz.springbootquartz.util.CommonUtil;
import com.magicnian.quartz.springbootquartz.util.ReflectionUtil;

import java.lang.reflect.Field;

/**
 * Created by liunn on 2018/2/2.
 */
public class CommonService {


    /**
     * 验证参数
     *
     * @param request
     * @throws ServiceException
     */
    public static void validParam(Object request) throws ServiceException {
        //根据注解验证属性
        Field[] fields = request.getClass().getDeclaredFields();
        for (Field field : fields) {
            ParamValidator validator = field.getAnnotation(ParamValidator.class);
            if (null == validator) {
                continue;
            }

            String fieldName = field.getName();
            if (!CommonUtil.isEmptyStr(validator.name())) {
                fieldName = validator.name();
            }

            if (validator.required()) {
                Object value = ReflectionUtil.getFieldValue(request, field.getName());
                if (CommonUtil.isEmptyStr(value)) {
                    throw new ServiceException(ResponseCode.ILLEGAL_ARGS.getCode(), fieldName + ParamValidateMsg.IS_REQUIRED.getMsg());
                }
            }
        }
    }
}
