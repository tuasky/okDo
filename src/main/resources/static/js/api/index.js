// const app = Vue.createApp({
//     data() {
//         return {
//             loginUser: null,
//             showUserInfo: false,
//         }
//     },
//     created() {
//         this.resizeHeader.loginUser = localStorage.getItem("loginUser");
//     },
//     logout() {
//         localStorage.removeItem("loginUser");
//         localStorage.removeItem("token");
//     },
//     methods: {
//     }
// }).mount('#app')

const app = Vue.createApp({
    data() {
        return {
            resizeHeader: new ResizeHeader(),
            loginUser: null,
            sw : new ShowWindow(),
            loginContext: new LoginContext(),
            loginForm: new LoginForm(),
            calendar: null,
        };
    },
    mounted() {
        this.initCalendar()
    },
    created() {
        this.loginUser = JSON.parse(localStorage.getItem("loginUser"));
    },
    methods: {
        initCalendar() {
            const calendarEl = this.$refs.calendarEl;
            const calendar = new FullCalendar.Calendar(calendarEl, calendarConfig);
            calendar.render();
        },
        startResize(event) {
            this.resizeHeader.isResizing = true;
            this.resizeHeader.startX = event.clientX;
            this.resizeHeader.startWidth = this.resizeHeader.width;

            document.addEventListener('mousemove', this.resize);
            document.addEventListener('mouseup', this.stopResize);
        },
        resize(event) {
            if (this.resizeHeader.isResizing) {
                const diffX = event.clientX - this.resizeHeader.startX;
                this.resizeHeader.width = this.resizeHeader.startWidth + diffX;
                if (this.resizeHeader.width < 300) {
                    this.resizeHeader.width = 300;
                }
            }
        },
        stopResize() {
            this.resizeHeader.isResizing = false;
            document.removeEventListener('mousemove', this.resize);
            document.removeEventListener('mouseup', this.stopResize);
        },
        homeHide() {
            let w = this.resizeHeader.width;
            let timer = setInterval(() => {
                w -= 5;
                if (w > 0) {
                    this.resizeHeader.width = w;
                } else {
                    this.resizeHeader.width = 0;
                    clearInterval(timer);
                    this.sw.homeHide = true;
                }
            }, 1)
        },
        homeShow() {
            this.sw.homeHide = false;
            let w = 0;
            let timer = setInterval(() => {
                w += 5;
                if (w < 300) {
                    this.resizeHeader.width = w;
                } else {
                    this.resizeHeader.width = 300;
                    clearInterval(timer);
                }
            }, 1)
        },

        // login
        closeLoginWindow() {
            this.sw.login = false;
            this.loginContext = new LoginContext();
            this.loginForm = new LoginForm();
        },
        sendCode() {
            if (this.loginContext.isSentCode)
                return;
            if (!isValidEmail(this.loginForm.receiver, this.loginContext))
                return
            if (!this.loginContext.isAccept) {
                this.loginContext.errorRemain = '请阅读并同意用户协议、隐私政策';
                return;
            }
            this.startCountdown().then(r => null)
            sendCodeApi(this.loginForm)
        },
        async handleLogin() {
            if (!isValidLoginForm(this.loginForm, this.loginContext))
                return;
            if (!this.loginContext.isAccept) {
                this.loginContext.errorRemain = '请阅读并同意用户协议、隐私政策';
                return;
            }
            let res = await loginApi(this.loginForm);
            if (res.code > 0) {
                localStorage.setItem("token", res.data.token);
                localStorage.setItem("loginUser", JSON.stringify(res.data.user));
                window.location.href = '/index.html';
            } else {
                this.loginContext.errorRemain = res.msg;
            }
        },
        async startCountdown() {
            this.loginContext.errorRemain = null;
            this.loginContext.timeLeft = 60;
            let timer = setInterval(() => {
                this.loginContext.codeMsg = --this.loginContext.timeLeft + 's后重发';
                if (this.loginContext.timeLeft === 59)
                    this.loginContext.isSentCode = true;
                if (this.loginContext.timeLeft <= 0) {
                    clearInterval(timer);
                    this.loginContext.isSentCode = false;
                    this.loginContext.codeMsg = '重新发送';
                }
            }, 1000)
        },
        logout() {
            this.loginUser = null;
            localStorage.removeItem("loginUser");
            localStorage.removeItem("token");
            this.sw.userMenu = false;
        }
    }
}).mount('#app');