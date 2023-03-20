package com.udacity.jwdnd.course1.cloudstorage.controllers;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.udacity.jwdnd.course1.cloudstorage.models.File;

import java.io.IOException;


/*
Resources used:
1) Udacity Cognizant Full Stack Developer Train to Hire Course videos and execise projects
2) Udacity Connect session notes and examples demo code
3) https://examples.javacodegeeks.com/enterprise-java/spring/mvc/spring-mvc-file-download-example/
4)https://www.baeldung.com/spring-controller-return-image-file
5)https://www.youtube.com/watch?v=znjhY71F-8I
6) Tony Session Lead Doc -https://docs.google.com/spreadsheets/d/1RdQnR5scVZUVORQbcf1YsoZltlJxaP5tKubf0ceECs4/edit#gid=0
 */
@Controller
@RequestMapping
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/file-upload")
    public String fileupload(@RequestParam(name="id", required = true) Integer uid, MultipartFile fileUpload, Model model) throws IOException {
        if(fileUpload.getOriginalFilename().isEmpty()){
            model.addAttribute("resultError", true);
            model.addAttribute("errorMessage", "Please select a file to upload!!");
        }
        else {
            File file = new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(), fileUpload.getSize(), uid, fileUpload.getBytes());
            if (this.fileService.isFileExists(file)) {
                model.addAttribute("resultError", true);
                model.addAttribute("errorMessage", "File already exists!!");
            } else {
                int fileAdded = this.fileService.createFile(file);
                if (fileAdded == 1) {
                    model.addAttribute("resultSuccess", true);
                } else if (fileAdded < 0) {
                    model.addAttribute("resultFailure", true);
                } else {
                    model.addAttribute("resultError", true);
                    model.addAttribute("errorMessage", "Large File upload failed!!");
                }
            }
        }
        return "result";
    }

    @GetMapping("/delFile")
    public String delUserFile(@RequestParam(name="fid", required = true) Integer fid, Model model) {

        int file_deleted = this.fileService.deleteUserFile(fid);
        if(file_deleted < 0) {
            model.addAttribute("resultFailure", true);
        }
        else {
            model.addAttribute("resultSuccess", true);
        }
        return "result";
    }
    @GetMapping("/downloadFile")
     public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(name="fid", required = true) Integer fid, Model model) {
        File file = this.fileService.getFile(fid);
        byte[] fileContent = file.getFiledata();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+file.getFilename()+"\"")
                .body(new ByteArrayResource(fileContent));


    }

}
