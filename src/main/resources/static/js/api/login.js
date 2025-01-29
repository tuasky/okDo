function loginApi(data) {
    return $axios({
        url: '/user/login',
        method: 'post',
        data: data
    })
}

function sendCodeApi(params) {
    return $axios({
        url: '/user/sendCode',
        method: 'get',
        params
    })
}