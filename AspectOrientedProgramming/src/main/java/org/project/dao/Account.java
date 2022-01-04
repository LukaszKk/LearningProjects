package org.project.dao;

import org.springframework.stereotype.Component;

@Component
public class Account {

    private String name;
    private String code;

    public void addAccount() {
        System.out.println(getClass().getSimpleName() + ": Adding account");
    }

    public String getName() {
        System.out.println(getClass().getSimpleName() + ": Get name");
        return name;
    }

    public void setName(String name) {
        System.out.println(getClass().getSimpleName() + ": Set name");
        this.name = name;
    }

    public String getCode() {
        System.out.println(getClass().getSimpleName() + ": Get code");
        return code;
    }

    public void setCode(String code) {
        System.out.println(getClass().getSimpleName() + ": Set code");
        this.code = code;
    }
}
