package com.okdo.controller;
import com.alibaba.fastjson2.JSONObject;
import com.okdo.common.core.R;
import com.okdo.common.core.model.LoginBody;
import com.okdo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    @PostMapping("/login")
    public R<JSONObject> login(@RequestBody LoginBody loginBody) throws Exception{
        JSONObject jo = userService.login(loginBody);
        if (jo.containsKey("error")) {
            return R.error(jo.getString("error"));
        }
        return R.success(jo);
    }

    @GetMapping("/sendCode")
    public R<String> sendCode(String type, String receiver) throws Exception{
        userService.sendCode(type, receiver);
        return R.success();
    }

}
