package com.okdo.controller.interceptor;

import com.okdo.common.core.security.HostHolder;
import com.okdo.common.core.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("auth");
        if (!jwtUtil.validateToken(auth)) {
//            response.sendRedirect(request.getContextPath() + "/static/page/login");
            return false;
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HostHolder.clear();
    }
}
