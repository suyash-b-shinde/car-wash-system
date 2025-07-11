package com.carwash.CarManagement.entities;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
@Entity 
@Data 
public class CarDetails { 
 
	 @Id 
	 @GeneratedValue(strategy = GenerationType.IDENTITY) 
	 int carId;
	 
	 @NotEmpty(message = "Car Name must not be empty") 
	 String carName;
	 
	 @NotEmpty(message = "Car Model must not be empty") 
	 String carModel;
	 
	 @NotEmpty(message = "Wash Service name must not be empty") 
	 String washServiceName;
	 
	 @NotNull(message = "Wash pack must not be empty") 
	 int washPackId;
	 
	 @NotEmpty(message = "Washer Name must not be empty")
	 private String washerName;
	 
	 @NotNull(message = "Date must not be empty") 
	 String date;
	 
	 @NotNull(message = "Phone number must not be empty") 
	 @Min(value = 1000000000L, message = "Phone number must be 10 digits long") 
	 long phoneNo;
	 
	 @NotEmpty(message = "Location must not be empty") 
	 String location;
	 
	 @NotBlank(message = "Wash Status must not be empty")
	 private String WashStatus = "Pending";
	 
	 @NotBlank(message = "Razoray Payment ID must not be empty")
	 private String razorpay_payment_id;
	 
	 String name;
	 String email;
	 
}