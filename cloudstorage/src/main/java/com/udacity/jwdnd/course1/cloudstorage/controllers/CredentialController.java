package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
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
@RequestMapping
public class CredentialController {
    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/createUpdateCredential")
    public String createUserCredential(@RequestParam(name="id", required = true) Integer uid, @ModelAttribute Credential credential, Model model) {

        credential.setUserId(uid);
        boolean credential_to_be_edited = false;
        if(credential.getCredentialId() != null) {
            credential_to_be_edited = true;
        }
        if(credential.getCredentialId() == null && !credential_to_be_edited) {
            // Create new credential

            //Check if credential exists already
            if(this.credentialService.isCredentialExists(credential)) {
                model.addAttribute("resultError", true);
                model.addAttribute("errorMessage", "Credential already exists!!");
            }
            else{
                // If not , create a new one
                int credential_created = this.credentialService.createCredential(credential);
                if (credential_created < 0) {
                    model.addAttribute("resultFailure", true);
                } else {
                    model.addAttribute("resultSuccess", true);
                }
            }
        }
        else{
            //credential has to be edited

            int credential_updated = this.credentialService.updateUserCredential(credential);
            if(credential_updated < 0) {
                model.addAttribute("resultFailure", true);
            }
            else {
                model.addAttribute("resultSuccess", true);
            }
        }
        return "result";
    }

    @GetMapping("/delCredential")
    public String delUserCredential(@RequestParam(name="cid", required = true) Integer cid, Model model) {
        int credential_deleted = this.credentialService.deleteUserCredential(cid);
        if(credential_deleted < 0) {
            model.addAttribute("resultFailure", true);
        }
        else {
            model.addAttribute("resultSuccess", true);
        }
        return "result";
    }


}
