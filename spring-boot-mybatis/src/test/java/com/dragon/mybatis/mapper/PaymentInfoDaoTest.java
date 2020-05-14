package com.dragon.mybatis.mapper;

import com.dragon.mybatis.MybatisApplicationTest;
import com.dragon.mybatis.Util.UUIDUtil;
import com.dragon.mybatis.entity.PaymentInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class PaymentInfoDaoTest extends MybatisApplicationTest {

    @Autowired
    PaymentInfoDao paymentInfoDao;

    @Test
    public void test(){
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setAccountName("SDfsd");
        paymentInfo.setId(UUIDUtil.getUUIDWithoutDash());
        paymentInfoDao.savePaymentInfo(paymentInfo);
    }

}
