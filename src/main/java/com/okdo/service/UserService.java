package com.okdo.service;

import com.alibaba.fastjson2.JSONObject;
import com.okdo.common.core.R;
import com.okdo.common.core.model.LoginBody;
import com.okdo.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


public interface UserService extends IService<User> {
    public JSONObject login(LoginBody loginBody);

    public void sendCode(String type, String receiver) throws Exception;

    public void addUser(User user);

    public User getUserByEmail(String email);

    public User getUserByPhone(String phone);

    public User getUserByUid(Long uid);

}
