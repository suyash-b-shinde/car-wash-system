package com.cg.config;
 
import com.cg.entity.UserCredential;
import com.cg.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
 
import java.util.Optional;
 
@Component
//to load user-specific data during authentication
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserCredentialRepository repository;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> credential = repository.findByUsername(username);
        System.out.println(credential.isPresent());
     // Converts the found UserCredential entity into a CustomUserDetails object or throws an exception if not found.
        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
    }
}