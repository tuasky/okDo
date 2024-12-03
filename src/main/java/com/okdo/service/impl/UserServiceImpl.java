package com.okdo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.okdo.common.core.R;
import com.okdo.common.core.model.LoginBody;
import com.okdo.mapper.UserMapper;
import com.okdo.service.UserService;
import com.okdo.domain.User;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public R<String> login(LoginBody loginBody) {
        return null;
    }

    @Override
    public R<String> sendCode(String type, String informSource) {

        return null;
    }

    @Override
    public void insertUser(User user) {

    }
}




