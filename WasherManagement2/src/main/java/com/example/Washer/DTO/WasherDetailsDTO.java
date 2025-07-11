package com.example.Washer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WasherDetailsDTO {

	private int id;
	private String name;
	private String status;
	private String address;
	private String email;
	

}
