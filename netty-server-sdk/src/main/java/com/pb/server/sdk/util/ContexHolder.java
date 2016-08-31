package com.pb.server.sdk.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContexHolder {
    private static ApplicationContext context;

    static {
        context = new ClassPathXmlApplicationContext(
                "spring.xml");
    }

    public static void init() {
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> T getBean(Class<T> c) {
        return context.getBean(c);
    }
}
