package com.okdo.controller;

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
    public R<String> login(@RequestBody LoginBody loginBody) throws Exception{
        R<String> R = userService.login(loginBody);
        return null;
    }

    @PostMapping("/sendCode")
    public R<String> sendCode(@RequestParam("type") String type,
                              @RequestParam("informSource") String informSource) throws Exception{
        return userService.sendCode(type, informSource);
    }

}
