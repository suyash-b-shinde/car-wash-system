package com.cg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//DTO to hold JWT token and role
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
private String token;
private String role; 
private String name;
private String email;

}