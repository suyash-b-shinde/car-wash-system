package com.carwash.CarManagement.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.carwash.CarManagement.entities.CarDetails;
 
@Repository 
public interface CarRepository extends JpaRepository<CarDetails, Integer> { 
	List<CarDetails> findByWasherName(String washerName);
	List<CarDetails> findByName(String username);

}