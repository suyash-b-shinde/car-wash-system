package com.example.Washer.Entity;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Washer {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int washerid;
 
	@NotBlank(message = "WasherStatus must not be empty")
	private String washerStatus;
	
	@NotBlank(message = "Washername must not be empty")
	private String washername;
 
	@NotBlank(message = "Wash Status must not be empty")
	private String WashStatus = "pending";
    
	
 
}