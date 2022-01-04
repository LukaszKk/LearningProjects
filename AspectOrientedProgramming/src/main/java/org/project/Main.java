package org.project;

import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.project.config.Config;
import org.project.dao.Account;
import org.project.dao.Membership;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        // enable log4j2
        PluginManager.addPackage("org.project");

        var context = new AnnotationConfigApplicationContext(Config.class);

        var account = context.getBean("account", Account.class);
        var membershipManager = context.getBean("membership", Membership.class);

        account.addAccount();
        System.out.println("Again!");
        account.addAccount();
        membershipManager.addMember();

        account.setName("asdasd");
        account.setCode("12312312");
        String name = account.getName();
        String code = account.getCode();

        context.close();
    }
}
