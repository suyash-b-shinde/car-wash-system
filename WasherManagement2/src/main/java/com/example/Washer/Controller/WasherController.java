package com.example.Washer.Controller;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import com.example.Washer.DTO.CarDetailsDTO;
import com.example.Washer.DTO.RatingDetailsDTO;
import com.example.Washer.DTO.WasherDetailsDTO;
import com.example.Washer.Entity.Washer;
import com.example.Washer.Service.WasherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/washers")
public class WasherController {
 
	private static final Logger logger = LoggerFactory.getLogger(WasherController.class);
 
	@Autowired
	private WasherService washerService;
 
	//Add a new washer
	@PostMapping("/add")
	public ResponseEntity<String> addWasher(@RequestBody @Valid Washer washer) {
		logger.info("Received request to add a new washer: {}", washer);
		Washer savedWasher = washerService.addWasher(washer);
		logger.info("Washer added successfully: {}", savedWasher);
		return new ResponseEntity<>("Washer added successfully: " + savedWasher, HttpStatus.CREATED);
	}
 
	//Get all washers
	@GetMapping("/getallwashers")
	public ResponseEntity<List<Washer>> get() {
		logger.info("Fetching all washers");
		List<Washer> washers = washerService.getAllWashers();
		logger.info("Successfully fetched {} washers", washers.size());
		return new ResponseEntity<>(washers, HttpStatus.OK);
	}
	
	//Get washer whose status is active
//	@GetMapping("/activewashers")
//	public ResponseEntity<List<Washer>> getActiveWashers() {
//	    logger.info("Fetching active washers");
//	    List<Washer> activeWashers = washerService.getActiveWashers();
//	    logger.info("Successfully fetched {} active washers", activeWashers.size());
//	    return new ResponseEntity<>(activeWashers, HttpStatus.OK);
//	}
 
	//Get washer by ID
	@GetMapping("getwasherbyid/{id}")
	public ResponseEntity<Washer> getWasherById(@PathVariable @Valid int id) {
		logger.info("Fetching washer with ID: {}", id);
		Washer washer = washerService.getWasherById(id);
		logger.info("Successfully fetched washer: {}", washer);
		return new ResponseEntity<>(washer, HttpStatus.OK);
	}
 
	//Fetch car details using Feign client
	@GetMapping("/getcar/{carId}")
	public ResponseEntity<CarDetailsDTO> getCarDetails(@PathVariable @Valid int carId) {
		logger.info("Fetching car details for car ID: {}", carId);
		CarDetailsDTO carDetails = washerService.getCarDetailsForWasher(carId);
		logger.info("Successfully fetched car details: {}", carDetails);
		return new ResponseEntity<>(carDetails, HttpStatus.OK);
	}
 
	//Fetch all orders using Feign client
	@GetMapping("/allorders")
	public ResponseEntity<List<CarDetailsDTO>> getUsers() {
		logger.info("Fetching all car orders");
		List<CarDetailsDTO> carDetails = washerService.getAllCarDetails();
		logger.info("Successfully fetched {} car orders", carDetails.size());
		return new ResponseEntity<>(carDetails, HttpStatus.OK);
	}
	
	//Fetch ratings for a washer using Feign Client
	@GetMapping("/ratings/{washerName}")
    public ResponseEntity<List<RatingDetailsDTO>> getRatingsForWasher(@PathVariable @Valid String washerName) {
        logger.info("Fetching ratings for washer : {}", washerName);
        List<RatingDetailsDTO> ratings = washerService.getRatingsForWasher(washerName);
        logger.info("Fetched {} ratings", ratings.size());
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
 

	// Keep this for washer-side updates
//	@PutMapping("/updatecarstatus/{carId}")
//	public ResponseEntity<Map<String, String>> updateCarStatusFromWasher(
//	        @PathVariable int carId,
//	        @RequestParam String status) {
//
//	    washerService.updateCarWashStatus(carId, status);
//
//	    Map<String, String> response = new HashMap<>();
//	    response.put("message", "Car wash status updated to " + status);
//	    return ResponseEntity.ok(response);
//	}




 
	//Delete washer by ID
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteWasher(@PathVariable @Valid int id) {
		logger.warn("Received request to delete washer with ID: {}", id);
		washerService.deleteWasher(id);
		logger.info("Washer deleted successfully with ID: {}", id);
		return new ResponseEntity<>("Washer deleted with ID: " + id, HttpStatus.OK);
	}
	
	@GetMapping("/allwashers")
    public List<WasherDetailsDTO> getAllWashers() {
        return washerService.fetchAllWashers();
    }
	
	@GetMapping("/activewashers")
	public List<WasherDetailsDTO> getAllActiveWashers() {
	    return washerService.fetchAllActiveWashers();
	}

	
	@GetMapping("/order/{washerName}")
    public ResponseEntity<List<CarDetailsDTO>> getWasherOrders(@PathVariable String washerName) {
        List<CarDetailsDTO> orders = washerService.getOrdersAssignedToWasher(washerName);
        return ResponseEntity.ok(orders);
    }
	
	@PutMapping("/updatecarstatus/{carId}")
	public ResponseEntity<String> updateCarStatusFromWasher(@PathVariable int carId,@RequestParam String status) {
	    washerService.updateCarWashStatus(carId, status);
	    return ResponseEntity.ok("Car wash status updated by washer.");
	}
}