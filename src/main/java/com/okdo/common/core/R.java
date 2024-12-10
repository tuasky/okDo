package com.okdo.common.core;

import com.okdo.common.constant.Status;
import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private T data;
    private int code;
    private String msg;

    public static <T> R<T> restResult(T data, int code, String msg) {
        R<T> r = new R<T>();
        r.setData(data);
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> success(T data) {
        return restResult(data, Status.OK, null);
    }

    public static <T> R<T> success() {
        return restResult(null, Status.OK, null);
    }

    public static <T> R<T> success(T data, String msg) {
        return restResult(data, Status.OK, msg);
    }
}
