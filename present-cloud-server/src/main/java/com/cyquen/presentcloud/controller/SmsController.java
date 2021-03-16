package com.cyquen.presentcloud.controller;

import com.alibaba.alicloud.sms.ISmsService;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.cyquen.presentcloud.entity.ResponseBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class SmsController {

    private ISmsService smsService;

    private RedisTemplate<String, String> redisTemplate;

    public SmsController(ISmsService smsService, RedisTemplate<String, String> redisTemplate) {
        this.smsService = smsService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 短信发送
     *
     * @param code 验证码
     */
    @GetMapping("/sms")
    public ResponseBean batchSendCheckCode(@RequestParam("phoneNumber") String phoneNumber) {

        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phoneNumber);
        request.setSignName("");
        request.setTemplateCode("");
        String code = StringUtils.leftPad(new Random().nextInt(10000) + "", 4, "0");
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = smsService.sendSmsRequest(request);
        } catch (ClientException e) {
            return ResponseBean.error(e.getErrMsg());
        }

        String responseCode = sendSmsResponse.getCode();
        if (responseCode.equals("OK")) {
            ValueOperations<String,String> forValue = redisTemplate.opsForValue();
            forValue.set(phoneNumber, code, 5, TimeUnit.MINUTES);
            return ResponseBean.ok("验证码发送成功");
        } else if (responseCode.equals("isv.MOBILE_NUMBER_ILLEGAL")) {
            return ResponseBean.error(phoneNumber + "为非法手机号");
        }
        return ResponseBean.error("后端出现错误，请运营人员解决");
    }
}
