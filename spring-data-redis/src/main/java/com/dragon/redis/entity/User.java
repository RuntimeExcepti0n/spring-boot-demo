package com.dragon.redis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -2503562945875315637L;

    private String name;

    private int age;

    private String address;

    private List<String> hobby;
}
