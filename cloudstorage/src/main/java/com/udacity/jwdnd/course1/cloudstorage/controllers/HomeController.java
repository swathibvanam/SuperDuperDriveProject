package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final NoteService noteService;
    private final FileService fileService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;


    public HomeController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }
    @GetMapping
    public String homePage(Authentication authentication, Model model){
        String username = authentication.getName();
        User user = userService.getUser(username);
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("listUserNotes", noteService.getAllUserNotes(user.getUserId()));
        model.addAttribute("listUserFiles", fileService.getAllUserFiles(user.getUserId()));
        model.addAttribute("listUserCredentials", credentialService.getAllUserCredentials(user.getUserId()));
        model.addAttribute("user", user);
        return "home";
    }


}
