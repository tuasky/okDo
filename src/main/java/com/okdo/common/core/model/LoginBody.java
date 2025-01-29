package com.okdo.common.core.model;

import lombok.Data;

@Data
public class LoginBody {
    private String type;
    private String receiver;
    private String verifyCode;
    private String password;
}
