package com.example.Washer;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.Washer.Client.CarClient;
import com.example.Washer.DTO.RatingDetailsDTO;
import com.example.Washer.Entity.Washer;
import com.example.Washer.Repository.WasherRepo;
import com.example.Washer.Service.WasherServiceImpl;
 
@SpringBootTest
class DemoApplicationTests {
 
	@Mock
	private WasherRepo washerRepo;
	@Mock
	private CarClient carClient;
 
	@InjectMocks
	private WasherServiceImpl washerService;
 
	private Washer washer;
	private Washer washer2;
 
	@BeforeEach
	void setUp() {
		//sample washer object
		washer = new Washer();
		washer.setWasherid(1); // Set the washer ID
		washer.setWasherStatus("active");
		washer.setWashername("Aqua speed"); // Example washername
		washer.setWashStatus("pending");
		
		washer2 = new Washer();
		washer2.setWasherid(2);
		washer2.setWasherStatus("inactive");
		washer2.setWashername("Blue Drop");
		washer2.setWashStatus("pending");
	}
 
	@Test
	void testAddWasher() {
		// When the washer is saved, it should return the same washer object
		when(washerRepo.save(any(Washer.class))).thenReturn(washer);
 
		Washer result = washerService.addWasher(washer);
 
		assertNotNull(result);
		assertEquals(1, result.getWasherid()); // Test the washer ID is correctly set
		assertEquals("active", result.getWasherStatus());
		assertEquals("pending", result.getWashStatus());
		assertEquals("Aqua speed", result.getWashername());
	}
 
	@Test
	void testGetWasherById() {
		// Mock the repository to return a specific washer when findById is called
		when(washerRepo.findById(1)).thenReturn(Optional.of(washer));
 
		Washer result = washerService.getWasherById(1);
 
		assertNotNull(result);
		assertEquals(1, result.getWasherid()); // Test the washer ID
		assertEquals("active", result.getWasherStatus());
	}
 
	@Test
	void testUpdateWasherStatus() {
		// Mock the repository to return a washer when findById is called
		when(washerRepo.findById(1)).thenReturn(Optional.of(washer));
		when(washerRepo.save(any(Washer.class))).thenReturn(washer);
 
		washer.setWasherStatus("Inactive");
		washer.setWashStatus("completed");
 
		Washer updatedWasher = washerService.updateWasherStatus(1, washer);
 
		assertNotNull(updatedWasher);
		assertEquals("Inactive", updatedWasher.getWasherStatus());
		assertEquals("completed", updatedWasher.getWashStatus());
	}
 
	@Test
	void testDeleteWasher() {
		// Mock that the washer with ID 1 exists in the repository
	    when(washerRepo.existsById(1)).thenReturn(true);
	    doNothing().when(washerRepo).deleteById(1);

	    washerService.deleteWasher(1);
	 // Verify that deleteById was called exactly once with ID 1
	    verify(washerRepo, times(1)).deleteById(1);
	}
	
//	@Test
//    void testGetRatingsForWasher() {
//        // Simulate sample rating DTOs from CarClient
//        RatingDetailsDTO rating1 = new RatingDetailsDTO(1, 201, 5, "Excellent", null);
//        RatingDetailsDTO rating2 = new RatingDetailsDTO(1, 202, 4, "Very good", null);
//
//        List<RatingDetailsDTO> mockRatings = Arrays.asList(rating1, rating2);
//
//        // Mock the Feign client response
//        when(carClient.getRatingsByWasherId(1)).thenReturn(mockRatings);
//
//        List<RatingDetailsDTO> ratings = washerService.getRatingsForWasher(1);
//
//        assertNotNull(ratings);
//        assertEquals(2, ratings.size());
//        assertEquals(201, ratings.get(0).getUserId());
//        assertEquals(4, ratings.get(1).getRating());
//    }
	
	@Test
	void testGetActiveWashers() {
		List<Washer> mockWashers = Arrays.asList(washer);

		when(washerRepo.findByWasherStatus("active")).thenReturn(mockWashers);

		List<Washer> activeWashers = washerService.getActiveWashers();

		assertNotNull(activeWashers);
		assertEquals(1, activeWashers.size());
		assertEquals("Aqua speed", activeWashers.get(0).getWashername());
		assertEquals("active", activeWashers.get(0).getWasherStatus());
	}
	
	
	
}