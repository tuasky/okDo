package com.okdo.common.core.security;

import com.okdo.domain.User;

public class HostHolder {
    private static final ThreadLocal<User> threadLocal = new ThreadLocal<User>();

    public static void set(User user) {
        threadLocal.set(user);
    }

    public static User get() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
