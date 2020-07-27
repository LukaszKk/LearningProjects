package org.project;

public class Main {

    private static final String APP_NAME = "Spring app";

    public static void main(String[] args) {
        System.out.println(getAppName());
    }

    private static String getAppName() {
        return APP_NAME;
    }
}
