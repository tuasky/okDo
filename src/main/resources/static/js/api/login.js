function loginApi(params) {
    return $axios({
        url: localUrl + '/user/login',
        method: 'post',
        params
    })
}

function sendCodeApi(params) {
    return $axios({
        url: localUrl + '/user/sendCode',
        method: 'get',
        params
    })
}