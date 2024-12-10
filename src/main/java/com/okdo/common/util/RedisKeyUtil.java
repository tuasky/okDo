package com.okdo.common.util;

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String VERIFY_CODE = "verifyCode";

    public static String getVerifyCodeKey(String type, String receiver) {
        return VERIFY_CODE + SPLIT + type + SPLIT + receiver;
    }
}
