package com.cg.config;
 
import com.cg.entity.UserCredential;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
 
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
//adapting the userentity to the security framework 
public class CustomUserDetails implements UserDetails {
 
	private static final long serialVersionUID = 1L;
	private String username;
    private String password;
     private String role;
    public CustomUserDetails(UserCredential userCredential) {
        this.username = userCredential.getUsername();
        this.password = userCredential.getPassword();
        this.role=userCredential.getRole();
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	// Wraps the user's role into a SimpleGrantedAuthority object.
    	List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(role));
        return roles;
    }
 
    @Override
    public String getPassword() {
        return password;
    }
 
    @Override
    public String getUsername() {
        return username;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
}