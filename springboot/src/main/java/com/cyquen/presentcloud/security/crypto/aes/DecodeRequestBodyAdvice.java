package com.cyquen.presentcloud.security.crypto.aes;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @author Zhengxikun
 */
@ControllerAdvice(basePackages = "com.cyquen.presentcloud.controller")
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(DecodeRequestBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        System.out.println(1);
        try {
            boolean decode = false;
            if (methodParameter.getMethod().isAnnotationPresent(SecurityParameter.class)) {
                //获取注解配置的包含和去除字段
                SecurityParameter serializedField = methodParameter.getMethodAnnotation(SecurityParameter.class);
                //入参是否需要解密
                decode = serializedField.inDecode();
            }
            if (decode) {
                logger.info("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密");
                return new MyHttpInputMessage(inputMessage);
            } else {
                return inputMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密出现异常：" + e.getMessage());
            return inputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    static class MyHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;

        public MyHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            this.body = IOUtils.toInputStream(AesUtils.decrypt(easpString(IOUtils.toString(inputMessage.getBody(), "UTF-8"))), "UTF-8");
        }

        @Override
        public InputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        /**
         * @param requestData
         * @return
         */
        public String easpString(String requestData) {
            if (requestData != null && !"".equals(requestData)) {
                String s = "{\"requestData\":";
                if (!requestData.startsWith(s)) {
                    throw new RuntimeException("参数【requestData】缺失异常！");
                } else {
                    int closeLen = requestData.length() - 1;
                    int openLen = "{\"requestData\":".length();
                    return StringUtils.substring(requestData, openLen, closeLen);
                }
            }
            return "";
        }
    }
}