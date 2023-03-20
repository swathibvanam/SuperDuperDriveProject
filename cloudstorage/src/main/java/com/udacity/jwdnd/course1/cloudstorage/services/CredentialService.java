package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/*
Resources used:
1) Udacity Cognizant Full Stack Developer Train to Hire Course videos and execise projects
2) Udacity Connect session notes and examples demo code
3)Tony Session Lead Doc -https://docs.google.com/spreadsheets/d/1RdQnR5scVZUVORQbcf1YsoZltlJxaP5tKubf0ceECs4/edit#gid=0
 */

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }
    public int createCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper.insert(new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey,encryptedPassword,credential.getUserId()));
    }
    public boolean isCredentialExists(Credential c) {
        boolean found = false;
        List<Credential> credentials = new ArrayList<>();
        credentials =  credentialMapper.getUserCredentials(c.getUserId());
        if(!credentials.isEmpty()) {
            for (Credential credential : credentials) {
                if (credential.getUrl().equals(c.getUrl())) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
    public List<Credential> getAllUserCredentials(Integer id) {
       return credentialMapper.getUserCredentials(id);
    }
    public int updateUserCredential(Credential credential){
        String key = credentialMapper.getCredentialKey(credential.getCredentialId());
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);
        credential.setPassword(encryptedPassword);
        return credentialMapper.updateCredential(credential);
    }
    public int deleteUserCredential(Integer id){
        return credentialMapper.delete(id);
    }
}
