package com.cg.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//DTO for login request 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
	
	@NotBlank(message = "Username must not be blank")
    private String username;
	
	@NotBlank(message = "Password must not be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}