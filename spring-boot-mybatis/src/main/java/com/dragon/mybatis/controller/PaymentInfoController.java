package com.dragon.mybatis.controller;

import com.alibaba.fastjson.JSON;
import com.dragon.mybatis.Util.UUIDUtil;
import com.dragon.mybatis.entity.PaymentInfo;
import com.dragon.mybatis.service.PaymentInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payInfo")
public class PaymentInfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PaymentInfoService paymentInfoService;

    @PostMapping("/save")
    public int savePayment(@RequestBody PaymentInfo paymentInfo){
        logger.info("保存收款人信息{}", JSON.toJSONString(paymentInfo));
        paymentInfo.setId(UUIDUtil.getUUIDWithoutDash());
        int i = paymentInfoService.savePaymentInfo(paymentInfo);
        return i;
    }
}
