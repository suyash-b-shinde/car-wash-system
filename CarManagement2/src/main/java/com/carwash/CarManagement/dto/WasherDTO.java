package com.carwash.CarManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WasherDTO {
	
	private int id;
	private String name;
	private String status;
	private String address;
	private String email;

}
