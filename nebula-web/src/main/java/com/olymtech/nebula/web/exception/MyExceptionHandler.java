/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.web.exception;

import com.olymtech.nebula.entity.Callback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author taoshanchang 15/11/20
 */
public class MyExceptionHandler implements HandlerExceptionResolver {

    protected Log log = LogFactory.getLog(this.getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.error("异常捕获", ex);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ex", ex);

        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            System.out.println("exception in Controller of: " + method.getBeanType());
            System.out.println("exception in method of: " + method.getMethod());
            System.out.println(method.getMethod().getReturnType());

            log.error(ex.getMessage());
            log.error(ex.getStackTrace());

            if (method.getMethod().getReturnType().getTypeName().endsWith("com.olymtech.nebula.entity.Callback")){
                PrintWriter writer = null;
                try {
                    writer = response.getWriter();
                    Callback callbak = new Callback();
                    callbak.setCallbackMsg("Error");
                    callbak.setCode(500);
                    callbak.setResponseContext(ex.getMessage());

                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(callbak);
                    writer.write(json);

                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    writer.close();
                }finally {
                    writer.close();
                }
                return null;
            }else{
                //根据不同错误转向不同页面
                if (ex instanceof BusinessException) {
                    return new ModelAndView("error/error-business", model);
                } else if (ex instanceof ParameterException) {
                    return new ModelAndView("error/error-parameter", model);
                } else {
                    return new ModelAndView("error/error", model);
                }

            }

        }
        return new ModelAndView("error/error", model);
    }

}
