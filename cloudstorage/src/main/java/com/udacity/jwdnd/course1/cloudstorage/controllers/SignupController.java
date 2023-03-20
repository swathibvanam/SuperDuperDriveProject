package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*
Resources used:
1) Udacity Cognizant Full Stack Developer Train to Hire Course videos and execise projects
2) Udacity Connect session notes and examples demo code
3)Tony Session Lead Doc -https://docs.google.com/spreadsheets/d/1RdQnR5scVZUVORQbcf1YsoZltlJxaP5tKubf0ceECs4/edit#gid=0
 */

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String signupPage(){
        return "signup";
    }
    @PostMapping
    public String signup(@ModelAttribute User user, Model model){
        String signupErrorMessage = null;
        //Check if username already exists. If so, set up error message
        if (userService.isUsernameTaken(user.getUsername())) {
            signupErrorMessage = "The username already exists. Please try again! ";
            model.addAttribute("signupError", signupErrorMessage);
            return "signup";
        }
        else{
            //If username doesn't exist, create user
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                //if user wasn't successfully added to database, set up error message
                signupErrorMessage = "There was an error signing you up. Please try again.";
                model.addAttribute("signupError", signupErrorMessage);
                return "signup";
            }
            else{
                return "redirect:/login"+"?registration=success";
            }

        }


    }
}
