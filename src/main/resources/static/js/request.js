(function (win) {
    axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
    const service = axios.create({
        baseURL: '/',
        timeout: 100000
    })

    service.interceptors.request.use(config => {
        config.headers['Authorization'] = getToken();
        if (config.method === 'get' && config.params) {
            let url = config.url + '?';
            for (const propName of Object.keys(config.params)) {
                const value = config.params[propName];
                let part = encodeURIComponent(propName) + "=";
                if (value !== null && typeof(value) !== "undefined") {
                    if (typeof value === 'object') {
                        for (const key of Object.keys(value)) {
                            let params = propName + '[' + key + ']';
                            let subPart = encodeURIComponent(params) + "=";
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
        console.log("--请求拦截器--", config)
        return config
    }, error => {
        console.log(error)
        Promise.reject(error)
    })

    service.interceptors.response.use(res => {
        console.log('--响应拦截器--',res.data)
        return  res.data
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
    })
    win.$axios = service
})(window);

function getToken() {
    return localStorage.getItem("token")
}
