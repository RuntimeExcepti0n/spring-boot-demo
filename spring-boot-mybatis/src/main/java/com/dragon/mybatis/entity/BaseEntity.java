package com.dragon.mybatis.entity;
import com.dragon.mybatis.annotation.CreateTime;
import com.dragon.mybatis.annotation.UpdateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -8721140153430082059L;

    private String createdBy;

    private String updatedBy;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @CreateTime
    private Date createdDate;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @UpdateTime
    private Date updatedDate;


    private String isDelete = "N";

}
