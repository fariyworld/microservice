package com.mace.microservice.oauth2.controller;

import com.mace.microservice.common.annotation.EnableControllerLog;
import org.apache.commons.lang3.StringUtils;
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

    @EnableControllerLog
    @RequestMapping("/index")
    public String index(){

        return "index";
    }

    @RequestMapping("/")
    public String home(){

        return "redirect:/index";
    }

    @EnableControllerLog(description = "登录/注销")
    @RequestMapping("/login")
    public String login(String logout, String error, Model model){

        if(logout == null && error == null){// 没有登录去注销
            model.addAttribute("logout_error", true);
        }
        return "login";
    }

    /*@RequestMapping("/login-error")
    public String loginError(Model model){

        model.addAttribute("loginError", true);
        return "login";
    }*/

    @GetMapping("/401")
    public String accessDenied(){

        return "401";
    }

    @RequestMapping("/user/index")
    public String userIndex(){

        return "/user/index";
    }

}
