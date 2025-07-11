package com.example.Washer.Service;
 
import java.util.List;
 
import com.example.Washer.DTO.CarDetailsDTO;
import com.example.Washer.DTO.RatingDetailsDTO;
import com.example.Washer.DTO.WasherDetailsDTO;
import com.example.Washer.Entity.Washer;
 
public interface WasherService {
 
    Washer addWasher(Washer washer);
 
    List<Washer> getAllWashers();
    
    List<Washer> getActiveWashers();
 
    Washer getWasherById(int id);
 
    Washer updateWasherStatus(int id, Washer washer);
 
    void deleteWasher(int id);
 
	CarDetailsDTO getCarDetailsForWasher(int carId);
	
	List<CarDetailsDTO> getAllCarDetails();
	
	List<RatingDetailsDTO> getRatingsForWasher(String washerName);
	
	
	public List<WasherDetailsDTO> fetchAllWashers();
	
	public List<WasherDetailsDTO> fetchAllActiveWashers();
	

	List<CarDetailsDTO> getOrdersAssignedToWasher(String washerName);

	void updateCarWashStatus(int carId, String status);

}