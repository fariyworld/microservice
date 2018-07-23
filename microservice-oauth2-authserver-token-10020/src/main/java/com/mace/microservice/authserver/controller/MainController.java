package com.mace.microservice.authserver.controller;

import com.mace.microservice.common.annotation.EnableControllerLog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * description:
 * <br />
 * Created by mace on 16:13 2018/7/17.
 */
@Controller
@EnableControllerLog
public class MainController {

    @EnableControllerLog(description = "登录/注销")
    @RequestMapping("/login")
    public String login(String logout, String error, Model model){

        if(logout == null && error == null){// 没有登录去注销
            model.addAttribute("logout_error", true);
        }
        return "login";
    }

}
