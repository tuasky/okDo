package com.okdo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.okdo.common.core.R;
import com.okdo.common.core.inform.InformEngine;
import com.okdo.common.core.model.LoginBody;
import com.okdo.common.core.redis.RedisCache;
import com.okdo.common.util.RedisKeyUtil;
import com.okdo.common.util.VerifyCodeUtil;
import com.okdo.mapper.UserMapper;
import com.okdo.service.UserService;
import com.okdo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    final InformEngine informEngine;
    final RedisCache redisCache;

    @Override
    public R<String> login(LoginBody loginBody) {
        return null;
    }

    @Override
    public R<String> sendCode(String type, String receiver) throws Exception {
        String verifyCode = VerifyCodeUtil.getNumVerifyCode(6);
        redisCache.string(RedisKeyUtil.getVerifyCodeKey(type, receiver), verifyCode,
                5 * 60, TimeUnit.SECONDS);

        informEngine.sendEmailHtmlMessage(
                receiver,
                "okDo-登录验证",
                "/mail/verifyCode.html",
                "email", receiver, "verifyCode", verifyCode);
        return R.success();
    }

    @Override
    public void insertUser(User user) {

    }
}




