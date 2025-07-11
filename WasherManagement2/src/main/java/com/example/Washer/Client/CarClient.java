package com.example.Washer.Client;
 
import java.util.List;
 
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Washer.DTO.CarDetailsDTO;
import com.example.Washer.DTO.RatingDetailsDTO;
 
@FeignClient(name = "CARMANAGEMENT") // Feign Client for Car Management Service
public interface CarClient {
 
	@GetMapping(value = "/cars/car/{id}", produces = "application/json", consumes = "application/json")
	CarDetailsDTO getCarById(@PathVariable("id") int id);
 
	@GetMapping(value = "/cars/allorders")
	List<CarDetailsDTO> getUsers();
	
	// New method to fetch ratings for a washer
    @GetMapping(value = "/cars/washer/{washerName}", produces = "application/json")
    List<RatingDetailsDTO> getRatingsByWasherName(@PathVariable("washerName") String washerName);
    
    @GetMapping("/cars/order/washer/{washerName}")
    List<CarDetailsDTO> getOrdersByWasher(@PathVariable("washerName") String washerName);
    
    @PutMapping("/cars/updateStatus/{carId}")
    ResponseEntity<String> updateWashStatus(@PathVariable("carId") int carId,@RequestParam("status") String status);
}