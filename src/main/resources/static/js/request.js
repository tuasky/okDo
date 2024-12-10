(function (win) {
    axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
    const service = axios.create({
        baseURL: '/',
        timeout: 100000
    })

    service.interceptors.request.use(config => {
        // 是否需要设置 token
        // const isToken = (config.headers || {}).isToken === false
        // if (getToken() && !isToken) {
        //   config.headers['Authorization'] = 'Bearer ' + getToken() // 让每个请求携带自定义token
        // }
        // get请求映射params参数
        if (config.method === 'get' && config.params) {
            let url = config.url + '?';
            for (const propName of Object.keys(config.params)) {
                const value = config.params[propName];
                var part = encodeURIComponent(propName) + "=";
                if (value !== null && typeof(value) !== "undefined") {
                    if (typeof value === 'object') {
                        for (const key of Object.keys(value)) {
                            let params = propName + '[' + key + ']';
                            var subPart = encodeURIComponent(params) + "=";
                            url += subPart + encodeURIComponent(value[key]) + "&";
                        }
                    } else {
                        url += part + encodeURIComponent(value) + "&";
                    }
                }
            }
            url = url.slice(0, -1);
            console.log(url);
            config.params = {};
            config.url = url;
        }
        console.log("--请求过滤器--", config)
        return config
    }, error => {
        console.log(error)
        Promise.reject(error)
    })

    service.interceptors.response.use(res => {
            console.log('--响应拦截器--',res)

            // if (res.data.code === 0 && res.data.msg === 'NOTLOGIN') {// 返回登录页面
            //     console.log('---/backend/page/login/login.html---')
            //     localStorage.removeItem('userInfo')
            //     window.top.location.href = '/backend/page/login/login.html'
            // } else {
                return res.data
            // }
        }, error => {
            console.log('err' + error)
            let { message } = error;
            if (message === "Network Error") {
                message = "后端接口连接异常";
            }
            else if (message.includes("timeout")) {
                message = "系统接口请求超时";
            }
            else if (message.includes("Request failed with status code")) {
                message = "系统接口" + message.substr(message.length - 3) + "异常";
            }
            // window.ELEMENT.Message({
            //     message: message,
            //     type: 'error',
            //     duration: 5 * 1000
            // })
            return Promise.reject(error)
        }
    )
    win.$axios = service
})(window);
