package com.okdo.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.okdo.common.core.inform.InformEngine;
import com.okdo.common.core.model.LoginBody;
import com.okdo.common.core.redis.RedisCache;
import com.okdo.common.core.security.JwtUtil;
import com.okdo.common.util.RedisKeyUtil;
import com.okdo.common.util.NumberUtil;
import com.okdo.common.util.StringUtils;
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
    final JwtUtil jwtUtil;

    @Override
    public JSONObject login(LoginBody loginBody) {
        JSONObject jo = new JSONObject();

        User user = null;
        if ("email".equals(loginBody.getType()) || "password".equals(loginBody.getType()))
            user = getUserByEmail(loginBody.getReceiver());
        else
            user = getUserByPhone(loginBody.getReceiver());

        if ("password".equals(loginBody.getType())) {
            if (!validPassword(loginBody, user, jo))
                return jo;
        } else {
            // 邮箱或手机号登录
            if (!validVerifyCode(loginBody, jo))
                return jo;
            if (user == null) {
                // 自动创建新用户
                user = User.builder().uid(generateUid()).build();
                if ("email".equals(loginBody.getType()))
                    user.setEmail(loginBody.getReceiver());
                else user.setPhone(loginBody.getReceiver());

                addUser(user);
            }
        }

        // 生成jwt token
        String token = jwtUtil.generateToken(String.valueOf(user.getUid()));
        jo.put("token", token);
        jo.put("user", user);

        return jo;
    }

    public Long generateUid() {
        // TODO: 优化
        // 随机生成-查重加位法
        long uid = Long.parseLong(NumberUtil.getFixLengthNumber(11));
        while (true) {
            if (getUserByUid(uid) == null) {
                return uid;
            }
            uid += 1;
        }
    }

    public boolean validVerifyCode(LoginBody loginBody, JSONObject jo) {
        String verifyCodeKey = RedisKeyUtil.getVerifyCodeKey(loginBody.getType(),
                loginBody.getReceiver());
        String verifyCode = redisCache.getString(verifyCodeKey);
        if (verifyCode == null) {
            jo.put("error", "验证码不存在或已过期！");
            return false;
        }
        if (!verifyCode.equals(loginBody.getVerifyCode())) {
            jo.put("error", "验证码错误!");
            return false;
        }
        redisCache.delString(verifyCodeKey);
        return true;
    }

    public boolean validPassword(LoginBody loginBody, User user, JSONObject jo) {
        if (user == null || user.getPassword() == null) {
            jo.put("error", "您还未设置密码，请先使用验证码登录！");
            return false;
        }
        if (!user.getPassword().equals(loginBody.getPassword())) {
            jo.put("error", "密码错误！");
            return false;
        }

        return true;
    }

    @Override
    public void sendCode(String type, String receiver) throws Exception {
        String verifyCodeKey = RedisKeyUtil.getVerifyCodeKey(type, receiver);
        String verifyCode = redisCache.getString(verifyCodeKey);
        if (verifyCode == null) {
            verifyCode = NumberUtil.getNumVerifyCode(6);
            redisCache.string(RedisKeyUtil.getVerifyCodeKey(type, receiver), verifyCode,
                    5 * 60, TimeUnit.SECONDS);
        }
        try {
            if ("email".equals(type)) {
                informEngine.sendEmailHtmlMessage(
                        receiver,
                        "okDo-登录验证",
                        "/mail/verifyCode.html",
                        "email", receiver, "verifyCode", verifyCode);
            }
        } catch (Exception e) {
            log.error(String.format("send inform(%s) error", type));
        }
    }

    @Override
    public void addUser(User user) {
        this.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getEmail, email);
        return this.getOne(lqw);
    }

    @Override
    public User getUserByPhone(String phone) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone, phone);
        return this.getOne(lqw);
    }

    @Override
    public User getUserByUid(Long uid) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUid, uid);
        return this.getOne(lqw);
    }
}




