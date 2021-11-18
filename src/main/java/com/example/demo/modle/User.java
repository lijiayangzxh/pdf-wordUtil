package com.example.demo.modle;

import lombok.Data;

/**
 * @author LJY
 * @version 1.0
 * @description
 * @date 2021/11/9 17:51
 */
@Data
public class User {
    private String name;
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
