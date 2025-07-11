package com.carwash.CarManagement.controller;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import com.carwash.CarManagement.dto.WashServiceResponse;
import com.carwash.CarManagement.dto.WasherDTO;
import com.carwash.CarManagement.entities.CarDetails;
import com.carwash.CarManagement.entities.Rating;
import com.carwash.CarManagement.exception.ApiRequestException;
import com.carwash.CarManagement.repository.RatingRepository;
import com.carwash.CarManagement.service.CarService;

import jakarta.validation.Valid;
//@CrossOrigin(origins = "http://localhost:4200")
@RestController 
@RequestMapping("/cars") 
public class CarController { 
 
 private static final Logger logger = LoggerFactory.getLogger(CarController.class);
 
 @Autowired 
 private CarService service;
 
// @Autowired
// private RatingRepository ratingRepo;
 
// @PostMapping("/addcar") 
// public ResponseEntity<String> addcar(@Valid @RequestBody CarDetails car) { 
//	logger.info("Received request to add car: {}", car);
//	car = service.addcar(car);
//	String result = "Order is placed with washer and will be processed soon: " + car;
//	logger.info("Car added successfully: {}", result);
//	return new ResponseEntity<>(result, HttpStatus.CREATED);
// } 
 
 @PostMapping("/addcar") 
 public ResponseEntity<CarDetails> addcar(@Valid @RequestBody CarDetails car) { 
	logger.info("Received request to add car: {}", car);
	car = service.addcar(car);
	//String result = "Order is placed with washer and will be processed soon: " + car;
	logger.info("Car added successfully: {}", car);
	return new ResponseEntity<>(car, HttpStatus.CREATED);
 }
 
 
 @GetMapping("/allorders") 
 public List<CarDetails> getcar() { 
	logger.info("Fetching all car orders");
	return service.getUsers();
 } 
 
 
 @GetMapping("/car/{id}") 
 public ResponseEntity<CarDetails> getCarById(@Valid @PathVariable int id) { 
	logger.info("Fetching car order with ID: {}", id);
	return service.findById(id).map(car -> {logger.info("Car order found: {}", car);
	return new ResponseEntity<>(car, HttpStatus.OK);}).orElseThrow(() -> { 
	logger.error("Car order not found with ID: {}", id);
	return new ApiRequestException("Car order not found with ID: " + id);
	});
 } 
 
// @DeleteMapping("/delete/{id}") 
// public ResponseEntity<Object> deletecar(@Valid @PathVariable int id) { 
//	 logger.warn("Request received to delete car order with ID: {}", id);
//	 boolean isCarExist = service.existsById(id);
//	 if (isCarExist) { 
//		 service.deleteById(id);
//		 logger.info("Car order deleted successfully with ID: {}", id);
//		 return new ResponseEntity<>("Order deleted with id " + id, HttpStatus.OK);
//	 } 
//	 else { 
//		 logger.error("Car order not found with ID: {}", id);
//		 throw new ApiRequestException("Cannot delete order, as order not found with this ID: " + id);
//	 } 
// } 
 @DeleteMapping("/delete/{id}")
 public ResponseEntity<Map<String, String>> deletecar(@Valid @PathVariable int id) {
     logger.warn("Request received to delete car order with ID: {}", id);
     boolean isCarExist = service.existsById(id);

     Map<String, String> response = new HashMap<>();
     if (isCarExist) {
         service.deleteById(id);
         logger.info("Car order deleted successfully with ID: {}", id);
         response.put("message", "Order deleted with ID: " + id);
         return new ResponseEntity<>(response, HttpStatus.OK);
     } else {
         logger.error("Car order not found with ID: {}", id);
         throw new ApiRequestException("Cannot delete order, as order not found with this ID: " + id);
     }
 }

 
 @GetMapping("/washServicePrice/{washServiceName}") 
 public ResponseEntity<WashServiceResponse> getWashServiceDetails(@Valid @PathVariable String washServiceName) { 
	 logger.info("Fetching wash service details for: {}", washServiceName);
	 
	 String description;
	 int price;
 
	 switch (washServiceName.toLowerCase()) { 
		 case "basic": 
			 description = "Complete vacuuming of cars incl. seats and boot + Washing and cleaning of foot mats + Body Shampooing and washing including door frames + Tyre arches cleaning + Underbody wash + Engine hot water wash and dressing + Side doors cleaning + Dash board polishing + Car perfume spray";
			 price = 1000;
			 logger.debug("Basic Wash selected. Description: {}, Price: {}", description, price);
			 break;
		 case "premium": 
			 description = "Complete vacuuming of cars incl. seats and boot + Washing and cleaning of foot mats + Body Shampooing and washing including door frames + Tyre arches cleaning + Underbody wash + Engine hot water wash and dressing + Side doors cleaning + Dash board cleaning and polishing + tires and alloy wheels treatment + Car perfume spray";
			 price = 2000;
			 logger.debug("Premium Wash selected. Description: {}, Price: {}", description, price);
			 break;
		 case "deluxe": 
			 description = "Complete dry cleaning of a interior including seats (carpet/leather), roof, mats and boot + Complete vacuuming of cars incl. seats and boot + Washing and cleaning of foot mats + Pre washing of car + Foam cleaning of exterior + Tyre arches cleaning + Underbody wash + Engine hot water wash and dressing + Washing and cleaning of door frames + Side doors cleaning and polishing + Dash board cleaning and polishing + Tyre and alloy polishing + Scratch proof windshield spray + Car perfume spray";
			 price = 3000;
			 logger.debug("Deluxe Wash selected. Description: {}, Price: {}", description, price);
			 break;
		 default: 
			 logger.error("Invalid Wash Service Name: {}", washServiceName);
			 throw new ApiRequestException("Invalid Wash Service Name");
 		} 
 
	 WashServiceResponse response = new WashServiceResponse(washServiceName, description, price);
	 logger.info("Wash service details fetched successfully: {}", response);
	 return new ResponseEntity<>(response, HttpStatus.OK);
 } 
 
 
 
// @PostMapping("/giveRating")
// public ResponseEntity<String> addRating(@Valid @RequestBody Rating rating) {
//     logger.info("Received rating submission: {}", rating);
//     ratingRepo.save(rating);
//     logger.info("Rating saved successfully for Washer ID: {}", rating.getWasherId());
//     return ResponseEntity.ok("Rating added successfully");
// }
//
// @GetMapping("/washer/{washerId}")
// public ResponseEntity<List<Rating>> getRatingsByWasher(@Valid @PathVariable int washerId) {
//     logger.info("Fetching ratings for Washer ID: {}", washerId);
//     List<Rating> ratings = ratingRepo.findByWasherId(washerId);
//     logger.info("Found {} ratings for Washer ID: {}", ratings.size(), washerId);
//     return ResponseEntity.ok(ratings);
// }
  
// @PostMapping("/giveRating")
// public ResponseEntity<String> addRating(@Valid @RequestBody Rating rating) {
//     logger.info("Received rating submission: {}", rating);
//     service.addRating(rating);
//     logger.info("Rating saved successfully for Washer: {}", rating.getWasherName());
//     return ResponseEntity.ok("Rating added successfully");
// }
 @PostMapping("/giveRating")
 public ResponseEntity<Map<String, String>> addRating(@Valid @RequestBody Rating rating) {
     logger.info("Received rating submission: {}", rating);
     service.addRating(rating);
     logger.info("Rating saved successfully for Washer: {}", rating.getWasherName());

     Map<String, String> response = new HashMap<>();
     response.put("message", "Rating added successfully");
     return ResponseEntity.ok(response);
 }

 
 @GetMapping("/viewratings") 
 public List<Rating> getRatings() { 
	logger.info("Fetching all Ratings");
	return service.getRatings();
 } 

 @GetMapping("/washer/{washerName}")
 public ResponseEntity<List<Rating>> getRatingsByWasher(@PathVariable String washerName) {
     logger.info("Fetching ratings for Washer: {}", washerName);
     List<Rating> ratings = service.getRatingsByWasherName(washerName);
     logger.info("Found {} ratings for Washer: {}", ratings.size(), washerName);
     return ResponseEntity.ok(ratings);
 }
 
 @GetMapping("/washer/user/{userName}")
 public ResponseEntity<List<Rating>> getRatingsByUser(@PathVariable String userName) {
     logger.info("Fetching ratings for User: {}", userName);
     List<Rating> ratings = service.getRatingsByUserName(userName);
     logger.info("Found {} ratings for User: {}", ratings.size(), userName);
     return ResponseEntity.ok(ratings);
 }
 
 @GetMapping("/activewashers")
 public ResponseEntity<List<WasherDTO>> getActiveWashers() {
     List<WasherDTO> activeWashers = service.getActiveWashers();
     return new ResponseEntity<>(activeWashers, HttpStatus.OK);
 }

 @GetMapping("/toPrintLog") 
 public String print() { 
	 logger.error("This is car microservice log error message");
	 logger.warn("This is car microservice log warning message");
	 logger.info("This is car microservice log info message");
	 logger.debug("This is car microservice log debug message");
	 logger.trace("This is car microservice log trace");
	 return "This is car microservice API";
 } 
 
 @GetMapping("/order/washer/{washerName}")
 public List<CarDetails> getOrdersByWasher(@PathVariable String washerName) {
     return service.getOrdersByWasher(washerName);
 }
 
 @GetMapping("/userorders/{username}")
 public ResponseEntity<List<CarDetails>> getOrdersByUsername(@PathVariable String username) {
     List<CarDetails> orders = service.getOrdersByUsername(username);
     return new ResponseEntity<>(orders, HttpStatus.OK);
 }
 
 @PutMapping("/updateStatus/{carId}")
 public ResponseEntity<String> updateWashStatus(@PathVariable int carId,@RequestParam String status) {
	 service.updateWashStatus(carId, status);
     return ResponseEntity.ok("Wash status updated successfully");
 }

 
}