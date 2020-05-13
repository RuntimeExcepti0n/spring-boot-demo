package com.dragon.mybatis.service.impl;

import com.dragon.mybatis.entity.PaymentInfo;
import com.dragon.mybatis.mapper.PaymentInfoDao;
import com.dragon.mybatis.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private PaymentInfoDao paymentInfoDao;

    @Override
    public int savePaymentInfo(PaymentInfo paymentInfo) {
        return paymentInfoDao.savePaymentInfo(paymentInfo);
    }

    @Override
    public List<PaymentInfo> listPaymentInfo(PaymentInfo paymentInfo) {
        return paymentInfoDao.listPaymentInfo(paymentInfo);
    }
}
