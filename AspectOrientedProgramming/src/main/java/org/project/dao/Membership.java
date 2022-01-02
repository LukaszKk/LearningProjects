package org.project.dao;

import org.springframework.stereotype.Component;

@Component
public class Membership {

    public void addMember() {
        System.out.println(getClass().getSimpleName() + ": doing something");
    }
}
