package com.udacity.jwdnd.course1.cloudstorage.mappers;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

/*
Resources used:
1) Udacity Cognizant Full Stack Developer Train to Hire Course videos and execise projects
2) Udacity Connect session notes and examples demo code
3)Tony Session Lead Doc -https://docs.google.com/spreadsheets/d/1RdQnR5scVZUVORQbcf1YsoZltlJxaP5tKubf0ceECs4/edit#gid=0
 */

// This is a mapper class that supports CRUD operations on the user table
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer insert(User user);

    @Delete("DELETE FROM USERS WHERE userid = #{id}")
    void delete(Integer id);

}
