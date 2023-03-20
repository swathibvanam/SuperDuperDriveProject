package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
/*
Resources used:
1) Udacity Cognizant Full Stack Developer Train to Hire Course videos and execise projects
2) Udacity Connect session notes and examples demo code
3)Tony Session Lead Doc -https://docs.google.com/spreadsheets/d/1RdQnR5scVZUVORQbcf1YsoZltlJxaP5tKubf0ceECs4/edit#gid=0
 */

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userId = #{id}")
    List<Note> getUserNotes(Integer id);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insert(Note note);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription =#{noteDescription} WHERE noteid =#{noteId}")
    Integer updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{id}")
    Integer delete(Integer id);
}
