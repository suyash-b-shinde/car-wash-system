package com.carwash.CarManagement.service;
 
import java.util.List;
import java.util.Optional;

import com.carwash.CarManagement.dto.WasherDTO;
import com.carwash.CarManagement.entities.CarDetails;
import com.carwash.CarManagement.entities.Rating;
 
public interface CarService { 
 public CarDetails addcar(CarDetails car);
 
 public List<CarDetails> getUsers();
 
 public void deleteById(int id);
 
 public boolean existsById(int id);
 
 public Optional<CarDetails> findById(int id);
 
// public Rating addRating(Rating rating);
// 
// public List<Rating> getRatingsByWasherId(int washerId);
 
 Rating addRating(Rating rating);
 
 List<Rating> getRatingsByWasherName(String washerName);
 
 List<Rating> getRatingsByUserName(String userName);
  
 public List<WasherDTO> getActiveWashers();

 public List<CarDetails> getOrdersByWasher(String washerName);

 public List<CarDetails> getOrdersByUsername(String username);
 
 void updateWashStatus(int carId, String status);

 public List<Rating> getRatings();

 
}