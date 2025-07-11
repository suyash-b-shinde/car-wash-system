package com.example.Washer.DTO;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDetailsDTO {
	
	private int carId;
	
    private String userName;
    
    private String washerName;
    
    private int rating;
    
    private String reviewText;
    
    private LocalDateTime createdAt = LocalDateTime.now();

}