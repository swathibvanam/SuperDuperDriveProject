package com.udacity.jwdnd.course1.cloudstorage.mappers;


import org.apache.ibatis.annotations.Mapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

/*
Resources used:
1) Udacity Cognizant Full Stack Developer Train to Hire Course videos and execise projects
2) Udacity Connect session notes and examples demo code
3)Tony Session Lead Doc -https://docs.google.com/spreadsheets/d/1RdQnR5scVZUVORQbcf1YsoZltlJxaP5tKubf0ceECs4/edit#gid=0
 */

import java.util.List;
@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{id}")
    List<Credential> getUserCredentials(Integer id);

    @Select("SELECT key FROM CREDENTIALS WHERE credentialid = #{id}")
    String getCredentialKey(Integer id);

    @Insert("INSERT INTO CREDENTIALS ( url, username, key, password, userid) VALUES(#{url}, #{username}, #{key},#{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username =#{username}, password =#{password} WHERE credentialid =#{credentialId}")
    Integer updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{id}")
    Integer delete(Integer id);
}
