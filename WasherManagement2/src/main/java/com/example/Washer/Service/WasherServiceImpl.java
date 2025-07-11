package com.example.Washer.Service;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.example.Washer.Client.CarClient;
import com.example.Washer.Client.UserClient;
import com.example.Washer.DTO.CarDetailsDTO;
import com.example.Washer.DTO.RatingDetailsDTO;
import com.example.Washer.DTO.WasherDetailsDTO;
import com.example.Washer.Entity.Washer;
import com.example.Washer.Exception.WasherException;
import com.example.Washer.Repository.WasherRepo;
 
@Service
public class WasherServiceImpl implements WasherService {
 
	@Autowired
	private WasherRepo washerRepo;
 
	@Autowired
	private CarClient carClient; // Inject the CarClient
    
	@Autowired
    private UserClient userClient;
	
	@Override
	public Washer addWasher(Washer washer) {
		return washerRepo.save(washer);
	}
 
	@Override
	public Washer updateWasherStatus(int id, Washer washer) {
		Washer existingWasher = getWasherById(id);
		existingWasher.setWasherStatus(washer.getWasherStatus());
		existingWasher.setWashStatus(washer.getWashStatus());
		return washerRepo.save(existingWasher);
	}
 
	// Example: Get car details for a washer
	@Override
	public CarDetailsDTO getCarDetailsForWasher(int carId) {
		try {
	        return carClient.getCarById(carId); // Using Feign Client to fetch car details
	    } catch (Exception ex) {
	        throw new WasherException("Error fetching car details for ID: " + carId);
	    }
	}
 
	@Override
	public List<Washer> getAllWashers() {
		return washerRepo.findAll();
	}
	
	@Override
	public List<Washer> getActiveWashers() {
	    return washerRepo.findByWasherStatus("active");
	}
 
	@Override
	public Washer getWasherById(int id) {
		return washerRepo.findById(id).orElseThrow(() -> new WasherException("Washer not found with ID:"+id));
	}
 
	@Override
	public void deleteWasher(int id) {
		if (!washerRepo.existsById(id)) {
            throw new WasherException("Cannot delete. Washer with ID " + id + " does not exist.");
        }
        washerRepo.deleteById(id);
	}
 
	@Override
	public List<CarDetailsDTO> getAllCarDetails() {
		return carClient.getUsers();
	}
	
	@Override
    public List<RatingDetailsDTO> getRatingsForWasher(String washerName) {
        return carClient.getRatingsByWasherName(washerName);
    }
	
	@Override
	public List<WasherDetailsDTO> fetchAllWashers() {
        return userClient.getAllWashers();
    }
	
	@Override
    public List<WasherDetailsDTO> fetchAllActiveWashers() {
        return userClient.getActiveWashers();
    }
	
	
	@Override
	public List<CarDetailsDTO> getOrdersAssignedToWasher(String washerName) {
        return carClient.getOrdersByWasher(washerName);
    }
	
	@Override
	public void updateCarWashStatus(int carId, String status) {
		carClient.updateWashStatus(carId, status);
	}
}