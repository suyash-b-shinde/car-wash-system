package com.carwash.CarManagement.dto;
 
import lombok.AllArgsConstructor;
import lombok.Data;
 
@Data 
@AllArgsConstructor 
public class WashServiceResponse { 
	 private String serviceName;
	 private String description;
	 private int price;
}