package com.mace.microservice.authserver.config;

import com.mace.microservice.authserver.entity.User;
import com.mace.microservice.common.base.UniversalMenthod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * description:
 * <br />
 * Created by mace on 11:57 2018/7/18.
 */
@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        //获得授权后可得到用户信息
        User userDetails = (User) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        log.info("欢迎 {} 登录", userDetails.getUsername());
        log.info("{} 的权限有: {}", userDetails.getUsername(), authorities);
        log.info("IP : {}",UniversalMenthod.getIpAddr(request));

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
