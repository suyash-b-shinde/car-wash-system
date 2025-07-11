package com.cg.controller;
 
import com.cg.dto.AuthRequest;
import com.cg.dto.ResponseDto;
import com.cg.entity.UserCredential;
import com.cg.exception.UserException;
import com.cg.repository.UserCredentialRepository;
import com.cg.service.AuthService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService service; // Business logic for authentication
	@Autowired
	private UserCredentialRepository userRepo;// Repository to fetch user data
	@Autowired
	private AuthenticationManager authenticationManager; // Manages authentication
 
	@PostMapping("/register")
	public ResponseEntity<?> addNewUser(@Valid @RequestBody UserCredential user) {
	    try {
	        String message = service.saveUser(user);
	        return ResponseEntity
	                .ok()
	                .header("Content-Type", "application/json")
	                .body(java.util.Map.of("message", message)); // ✅ JSON response
	    } catch (Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(java.util.Map.of("error", e.getMessage()));
	    }
	}

 
	@PostMapping("/login")
	public ResponseDto getToken(@Valid @RequestBody AuthRequest authRequest) {
		System.out.println("yes .." + authRequest.getUsername() + "  " + authRequest.getPassword());
		//Creates an authentication token
		Authentication authenticate =  
				//Authenticates the user
				authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		System.out.println(authenticate.isAuthenticated());
		if (authenticate.isAuthenticated()) {
			UserCredential user = userRepo.findByUsername(authRequest.getUsername()).get();
			String token =
					//to generate a JWT token
					service.generateToken(user.getId() + "", user.getRole());
			//Construct ResponseDto with the token and role and returns it
			ResponseDto resDto = new ResponseDto();
			resDto.setToken(token);
			resDto.setRole(user.getRole());
			resDto.setName(user.getName());
			resDto.setEmail(user.getEmail());
			return resDto;
		} else {
			throw new UserException("Invalid access");
		}
	}
 
	@GetMapping("/validate")
	public String validateToken(@Valid @RequestParam/*("token")*/ String token) {
		service.validateToken(token);
		return "Token is valid";
	}

	@GetMapping("/washers")
	public List<UserCredential> getAllWashers() {
	    return userRepo.findByRole("washer");
	}
	
	@GetMapping("/activewashers")
	public List<UserCredential> getActiveWashers() {
	    return userRepo.findByRoleAndStatus("WASHER", "ACTIVE");
	}
    
	@PutMapping("/toggleStatus/{id}")
	public ResponseEntity<String> toggleWasherStatus(@PathVariable int id) {
	    boolean updated = service.toggleWasherStatus(id);
	    if (updated) {
	        return ResponseEntity.ok("Washer status updated.");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Washer not found.");
	    }
	} 
	
	@GetMapping("/user/{name}")
	public ResponseEntity<UserCredential> getUserByUsername(@PathVariable String name) {
	    return userRepo.findByName(name)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

 
}