package com.cg.service;
 
import com.cg.entity.UserCredential;
import com.cg.exception.UserException;
import com.cg.repository.UserCredentialRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
 
@Service
public class AuthService {
 
    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Autowired
    private JwtService jwtService;
    
    //Encrypts (hashes) the user's password using BCrypt, saves the user
    public String saveUser(UserCredential credential) {
    	if (repository.findByUsername(credential.getUsername()).isPresent()) {
            throw new UserException("Username already exists");
        }
        if (repository.findByEmail(credential.getEmail()).isPresent()) {
            throw new UserException("Email already exists");
        }
    	// Encrypt the user's password before saving to the database.
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }
 
    public String generateToken(String username,String role) {
    	// token generation to JwtService,
        // passing the username and the user's role.
        return jwtService.generateToken(username,role);
    }
 
    public void validateToken(String token) {
    	//token validation to JwtService.
        jwtService.validateToken(token);
    }
    
    
    public boolean toggleWasherStatus(int id) {
        Optional<UserCredential> optionalWasher = repository.findById(id);
        if (optionalWasher.isPresent()) {
        	UserCredential washer = optionalWasher.get();
            washer.setStatus(washer.getStatus().equalsIgnoreCase("active") ? "inactive" : "active");
            repository.save(washer);
            return true;
        }
        return false;
    }
 
}