function isCellPhone(val) {
    if (!/^1(3|4|5|6|7|8)\d{9}$/.test(val)) {
        return false
    } else {
        return true
    }
}


function checkPhone(rule, value, callback) {
    // let phoneReg = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/;
    if (value == "") {
        callback(new Error("请输入手机号"))
    } else if (!isCellPhone(value)) {//引入methods中封装的检查手机格式的方法
        callback(new Error("请输入正确的手机号!"))
    } else {
        callback()
    }
}


function validID(rule, value, callback) {
    // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
    let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
    if (value == '') {
        callback(new Error('请输入身份证号码'))
    } else if (reg.test(value)) {
        callback()
    } else {
        callback(new Error('身份证号码不正确'))
    }
}

function isEmptyStr(value) {
    return value == null || value === ''
}

function isValidEmail(value, loginContext) {
    if (isEmptyStr(value)) {
        loginContext.errorRemain = '请输入邮箱账号';
        return false;
    }
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegex.test(value)) {
        loginContext.errorRemain = '请输入正确的邮箱账号';
        return false;
    }
    return true;
}

function isValidCode(value, loginContext) {
    if (isEmptyStr(value)) {
        loginContext.errorRemain = '验证码不能为空'
        return false;
    }
    const regex = /^\d{6}$/;
    if (!regex.test(value)) {
        loginContext.errorRemain = '请输入6位数字验证码'
        return false;
    }
    return true;
}

function isValidPassword(value, loginContext) {
    if (isEmptyStr(value)) {
        loginContext.errorRemain = '密码不能为空'
        return false;
    }
    return true;
}

function isValidLoginForm (loginForm, loginContext) {
    if (loginForm.type === 'email') {
        return isValidEmail(loginForm.receiver, loginContext) &&
            isValidCode(loginForm.verifyCode, loginContext);
    }
    return isValidEmail(loginForm.receiver, loginContext) &&
        isValidPassword(loginForm.password, loginContext);
}


