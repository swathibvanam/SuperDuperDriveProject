package com.udacity.jwdnd.course1.cloudstorage.models;
/*
Resources used:
1) Udacity Cognizant Full Stack Developer Train to Hire Course videos and execise projects
2) Udacity Connect session notes and examples demo code
3)Tony Session Lead Doc -https://docs.google.com/spreadsheets/d/1RdQnR5scVZUVORQbcf1YsoZltlJxaP5tKubf0ceECs4/edit#gid=0
 */
public class Note {

    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

    public Note(Integer noteId, String noteTitle, String noteDescription, Integer userId) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userId = userId;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
