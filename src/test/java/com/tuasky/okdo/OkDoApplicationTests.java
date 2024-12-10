package com.tuasky.okdo;

import com.okdo.OkDoApplication;
import com.okdo.common.core.inform.InformEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = OkDoApplication.class)
@RunWith(SpringRunner.class)
public class OkDoApplicationTests {

    @Autowired
    private InformEngine informEngine;

    @Test
    public void contextLoads() {
    }

    @Test
    public void sendEmailTest() throws Exception {
        String email = "2403888254@qq.com";
        informEngine.sendEmailHtmlMessage(
                email,
                "okDo-登录验证",
                "/mail/verifyCode.html",
                "email", email, "verifyCode", "123456");
    }

}
