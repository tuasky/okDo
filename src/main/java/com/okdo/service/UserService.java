package com.okdo.service;

import com.okdo.common.core.R;
import com.okdo.common.core.model.LoginBody;
import com.okdo.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;


public interface UserService extends IService<User> {
    public R<String> login(LoginBody loginBody);

    public R<String> sendCode(String type, String informSource);

    public void insertUser(User user);
}
