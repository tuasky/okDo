package com.okdo.common.core.model;

import lombok.Data;

@Data
public class LoginBody {
    private String email;
    private String phone;
    private String verifyCode;
}
