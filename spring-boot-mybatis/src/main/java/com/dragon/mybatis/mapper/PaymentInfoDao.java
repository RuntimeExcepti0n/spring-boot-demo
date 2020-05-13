package com.dragon.mybatis.mapper;

import com.dragon.mybatis.entity.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentInfoDao {

    int savePaymentInfo(PaymentInfo paymentInfo);

    List<PaymentInfo> listPaymentInfo(PaymentInfo paymentInfo);
}
