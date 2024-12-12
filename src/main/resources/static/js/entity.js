class LoginContext {
    isLogin = false
    isShowLoginWindow= false
    isSentCode= false
    isAccept= false
    timeLeft = 60
    codeMsg = '发送验证码'
    errorRemain
    timer
}

class LoginForm {
    type = 'email'
    receiver
    verifyCode
}

class LoginUser {

}