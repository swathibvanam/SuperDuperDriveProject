package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/*
Resources used:
1) Udacity Cognizant Full Stack Developer Train to Hire Course videos and execise projects
2) Udacity Connect session notes and examples demo code
3)Tony Session Lead Doc -https://docs.google.com/spreadsheets/d/1RdQnR5scVZUVORQbcf1YsoZltlJxaP5tKubf0ceECs4/edit#gid=0
 */

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(Note note) {

        return noteMapper.insert(new Note(null, note.getNoteTitle(), note.getNoteDescription(), note.getUserId()));
    }
    public boolean isNoteExists(Note n) {
        boolean found = false;
        List<Note> notes = new ArrayList<>();
        notes =  noteMapper.getUserNotes(n.getUserId());
        if(!notes.isEmpty()) {
            for (Note note : notes) {
                if (note.getNoteTitle().equals(n.getNoteTitle())) {
                    found = true;
                    break;
                }
            }
        }
        return found;
   }
    public List<Note> getAllUserNotes(Integer id) {
        return noteMapper.getUserNotes(id);
    }
    public int updateUserNote(Note note){
        return noteMapper.updateNote(note);
    }
    public int deleteUserNote(Integer id){
        return noteMapper.delete(id);
    }
}
