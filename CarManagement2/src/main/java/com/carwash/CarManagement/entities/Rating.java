//package com.carwash.CarManagement.entities;
//
//import java.time.LocalDateTime;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Rating {
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int ratingId;
//    
//    @NotNull(message = "Washer ID must not be null")
//    @Min(value = 1, message = "Washer ID must be a positive number")
//    private int washerId;
//    
//    @NotNull(message = "User ID must not be null")
//    @Min(value = 1, message = "User ID must be a positive number")
//    private int userId;
//    
//    @NotNull(message = "Rating must not be null")
//    @Min(value = 1, message = "Rating must be at least 1")
//    @Max(value = 5, message = "Rating cannot be more than 5")
//    private int rating; // e.g., 1 to 5
//    
//    @Size(max = 500, message = "Review text cannot exceed 500 characters")
//    private String reviewText;
//    
//    @NotNull(message = "Creation date must not be null")
//    private LocalDateTime createdAt = LocalDateTime.now();
//}


package com.carwash.CarManagement.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ratingId;
    
    @NotNull(message = "carId must not be blank")
    private int carId;
    
    @NotBlank(message = "Washer name must not be blank")
    private String washerName;

    @NotBlank(message = "User name must not be blank")
    private String userName;

    @NotNull(message = "Rating must not be null")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private int rating;

    @Size(max = 500, message = "Review text cannot exceed 500 characters")
    private String reviewText;

    @NotNull(message = "Creation date must not be null")
    private LocalDateTime createdAt = LocalDateTime.now();
}
