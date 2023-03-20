package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/createUpdateNote")
    public String createUserNote(@RequestParam(name="id", required = true) Integer uid, @ModelAttribute Note note, Model model) {

        note.setUserId(uid);
        boolean note_to_be_edited = false;
        if(note.getNoteId() != null) {
            note_to_be_edited = true;
        }
        if(note.getNoteId() == null && !note_to_be_edited) {
            // Create new note

            //Check if note exists already
            if(this.noteService.isNoteExists(note)) {
                model.addAttribute("resultError", true);
                model.addAttribute("errorMessage", "Note already exists!!");
            }
            else{
                int note_created = this.noteService.createNote(note);
                if (note_created < 0) {
                    model.addAttribute("resultFailure", true);
                } else {
                    model.addAttribute("resultSuccess", true);
                }
            }
        }
        else{
            //Note has to be edited

            int note_updated = this.noteService.updateUserNote(note);
            if(note_updated < 0) {
                model.addAttribute("resultFailure", true);
            }
            else {
                model.addAttribute("resultSuccess", true);
            }
        }
        return "result";
    }

    @GetMapping("/delNote")
    public String delUserNote(@RequestParam(name="nid", required = true) Integer nid, Model model) {
        int note_deleted = this.noteService.deleteUserNote(nid);
        if(note_deleted < 0) {
            model.addAttribute("resultFailure", true);
        }
        else {
            model.addAttribute("resultSuccess", true);
        }
        return "result";
    }

}
