package com.okdo.common.util;

import java.util.Random;
import java.util.UUID;

public class NumberUtil {

    public static String getNumVerifyCode() {
        return getNumVerifyCode(6);
    }

    public static String getNumVerifyCode(int length) {
        return getFixLengthNumber(length);
    }

    public static String getMixVerifyCode() {
        return UUID.randomUUID().toString()
                .replaceAll("-", "");
    }

    public static String getMixVerifyCode(int length) {
        return UUID.randomUUID().toString()
                .replaceAll("-", "")
                .substring(0, length);
    }

    public static String getFixLengthNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
