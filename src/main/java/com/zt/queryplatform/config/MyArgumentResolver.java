package com.zt.queryplatform.config;

import com.zt.queryplatform.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Author: linzj
 * @Date: 2019/6/20 13:48
 * @Desciption  用于初始化拦截返回实体对象
 */

@Component
public class MyArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return  methodParameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter p, ModelAndViewContainer m, NativeWebRequest nativeWebRequest, WebDataBinderFactory factory) throws Exception {

       return null;
    }
}
