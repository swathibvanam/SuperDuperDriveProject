package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
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
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int createFile(File file) {
       return fileMapper.insert(new File(null, file.getFilename(), file.getContenttype(), file.getFilesize(), file.getUserId(),file.getFiledata() ));
    }
    public boolean isFileExists(File f) {
        boolean found = false;
        List<File> files = new ArrayList<>();
        files =  fileMapper.getUserFiles(f.getUserId());
        if(!files.isEmpty()) {
            for (File file : files) {
                if (file.getFilename().equals(f.getFilename())) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    public List<File> getAllUserFiles(Integer id) {
        return fileMapper.getUserFiles(id);
    }

    public int deleteUserFile(Integer id){
        return fileMapper.delete(id);
    }
    public File getFile(Integer id){
        return fileMapper.getFileDetails(id);
    }

}
