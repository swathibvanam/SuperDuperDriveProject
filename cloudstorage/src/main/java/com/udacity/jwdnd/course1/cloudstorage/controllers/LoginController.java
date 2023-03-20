package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/*
Resources used:
1) Udacity Cognizant Full Stack Developer Train to Hire Course videos and execise projects
2) Udacity Connect session notes and examples demo code
3)Tony Session Lead Doc -https://docs.google.com/spreadsheets/d/1RdQnR5scVZUVORQbcf1YsoZltlJxaP5tKubf0ceECs4/edit#gid=0
 */

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping()
    public String loginView(@RequestParam(name="registration", defaultValue = "fail", required = false) String result, Model model) {

        if(result.equals("success")) {
            model.addAttribute("signupSuccess", true);
        }
        return "login";
    }
}
