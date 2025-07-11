package com.carwash.CarManagement.service;
 
import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carwash.CarManagement.client.WasherClient;
import com.carwash.CarManagement.dto.WasherDTO;
import com.carwash.CarManagement.entities.CarDetails;
import com.carwash.CarManagement.entities.Rating;
import com.carwash.CarManagement.exception.ApiRequestException;
import com.carwash.CarManagement.repository.CarRepository;
import com.carwash.CarManagement.repository.RatingRepository;
 
@Service 
public class CarServiceImpl implements CarService { 
 
 @Autowired 
 private CarRepository carrepository;
 
 @Autowired
 private RatingRepository ratingRepo;
 
 @Autowired
 private WasherClient washerClient;
 
 @Override 
 public CarDetails addcar(CarDetails car) { 
	 return carrepository.save(car);
 } 
 
 @Override 
 public List<CarDetails> getUsers() { 
	 List<CarDetails> cars = carrepository.findAll();
	 System.out.println("Getting data from DB: " + cars);
	 return cars;
 } 
 
 @Override 
 public void deleteById(int id) { 
	 carrepository.deleteById(id);
 } 
 
 @Override 
 public boolean existsById(int id) { 
	 return carrepository.existsById(id);
 } 
 
 @Override
 public Optional<CarDetails> findById(int id) { 
	 return carrepository.findById(id); // Assuming carRepository is a JPA repository
 } 
 
 @Override
 public Rating addRating(Rating rating) {
     return ratingRepo.save(rating);
 }
 
 @Override
 public List<Rating> getRatings(){
	 List<Rating> Rating = ratingRepo.findAll();
	 System.out.println("Getting data from DB: " + Rating);
	 return Rating; 
 }
 
// @Override
// public List<Rating> getRatingsByWasherId(int washerId) {
//     return ratingRepo.findByWasherId(washerId);
// }
 
 @Override
 public List<Rating> getRatingsByWasherName(String washerName) {
     return ratingRepo.findByWasherName(washerName);
 }
 
 @Override
 public List<Rating> getRatingsByUserName(String userName) {
     return ratingRepo.findByUserName(userName);
 }
 
 @Override
 public List<WasherDTO> getActiveWashers() {
     return washerClient.getAllActiveWashers();
 }
 
 @Override
 public List<CarDetails> getOrdersByWasher(String washerName) {
     return carrepository.findByWasherName(washerName);
 }
 @Override
 public List<CarDetails> getOrdersByUsername(String username) {
	    return carrepository.findByName(username);
 }
 
 @Override
 public void updateWashStatus(int carId, String status) {
     CarDetails car = carrepository.findById(carId)
         .orElseThrow(() -> new ApiRequestException("Car not found with ID: " + carId));
     car.setWashStatus(status);
     carrepository.save(car);
 }


}