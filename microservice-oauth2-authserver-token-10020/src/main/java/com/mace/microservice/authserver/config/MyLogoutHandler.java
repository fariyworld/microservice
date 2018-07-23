package com.mace.microservice.authserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: 注销成功后的处理器 日志展示用户信息
 * <br />
 * Created by mace on 14:04 2018/7/18.
 */
@Slf4j
public class MyLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {

        if(authentication != null){
//            log.info("当前认证状态：{}",authentication.isAuthenticated());
//            User userDetails = (User) authentication.getPrincipal();
            log.info("用户 {} 注销成功", authentication.getName());
        }else {
            log.error("您当前还未登录");
            try {
                response.sendRedirect("/oauth/login?logout=error");
            } catch (IOException e) {
                log.error("转发失败, {}", e.getMessage());
            }
        }

    }
}
