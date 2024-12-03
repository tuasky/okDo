package com.okdo.common.core.inform;

public interface Inform {
    class InformType {
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
    }
    class ContentType {
        public static final String TEXT = "text";
        public static final String HTML = "html";
    }
    void sendMessage(InformContext message) throws Exception;
}
