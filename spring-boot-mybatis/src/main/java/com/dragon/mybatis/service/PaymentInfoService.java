package com.dragon.mybatis.service;

import com.dragon.mybatis.entity.PaymentInfo;

import java.util.List;


public interface PaymentInfoService {

    int savePaymentInfo(PaymentInfo paymentInfo);

    List<PaymentInfo> listPaymentInfo(PaymentInfo paymentInfo);
}
