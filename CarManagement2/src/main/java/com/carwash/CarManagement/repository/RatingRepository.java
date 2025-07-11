package com.carwash.CarManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carwash.CarManagement.entities.Rating;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
   // List<Rating> findByWasherId(int washerId);
	List<Rating> findByWasherName(String washerName);
	List<Rating> findByUserName(String userName);

}