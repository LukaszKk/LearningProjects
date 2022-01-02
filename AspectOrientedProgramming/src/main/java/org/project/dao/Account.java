package org.project.dao;

import org.springframework.stereotype.Component;

@Component
public class Account {

    public void addAccount() {
        System.out.println(getClass().getSimpleName() + ": Adding account");
    }
}
