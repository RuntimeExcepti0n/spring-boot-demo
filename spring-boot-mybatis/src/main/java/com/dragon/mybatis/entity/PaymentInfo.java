package com.dragon.mybatis.entity;

import lombok.Data;

/**
 * @author RuntimeExcet0in
 * @since 2020-05-13
 */
@Data
public class PaymentInfo extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID22
     */
    private String id;

    /**
     * 单据编号
     */
    private String billNo;

    private String payeeName;

    private String payeeNum;

    private String accountName;

    private String accountNum;

    private String payWay;
}
