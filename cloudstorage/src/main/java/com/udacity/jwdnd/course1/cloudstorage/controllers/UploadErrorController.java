package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/*
Resources used:
1) Udacity Cognizant Full Stack Developer Train to Hire Course videos and execise projects
2) Udacity Connect session notes and examples demo code
3)Tony Session Lead Doc -https://docs.google.com/spreadsheets/d/1RdQnR5scVZUVORQbcf1YsoZltlJxaP5tKubf0ceECs4/edit#gid=0
 4) https://www.javadevjournal.com/spring-security/spring-security-custom-403-access-denied-page/
5) https://www.baeldung.com/spring-security-custom-access-denied-page
 */

@Controller
public class UploadErrorController {
    @GetMapping("/accessForbidden")
    public String getAccessDenied() {
       return "error";
    }
}

