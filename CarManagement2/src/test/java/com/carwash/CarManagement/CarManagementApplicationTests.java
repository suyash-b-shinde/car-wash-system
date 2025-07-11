package com.carwash.CarManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.carwash.CarManagement.client.WasherClient;
import com.carwash.CarManagement.dto.WasherDTO;
import com.carwash.CarManagement.entities.CarDetails;
import com.carwash.CarManagement.entities.Rating;
import com.carwash.CarManagement.repository.CarRepository;
import com.carwash.CarManagement.repository.RatingRepository;
import com.carwash.CarManagement.service.CarServiceImpl;

@SpringBootTest
class CarManagementApplicationTests {

    @Mock
    private CarRepository carRepository; // Mocked car repository

    @Mock
    private RatingRepository ratingRepository; // Mocked rating repository
    
    @Mock
    private WasherClient washerClient;
    
    @InjectMocks
    private CarServiceImpl carService; //to inject the mocked dependencies automatically

    private CarDetails car;
    private Rating rating;
    private WasherDTO washerDTO;

    @BeforeEach
    void setUp() {
        // Sample car object
        car = new CarDetails();
        car.setCarId(1);
        car.setCarName("Toyota");
        car.setCarModel("Corolla");
        car.setWashServiceName("Express Wash");
        car.setWashPackId(1);
        car.setLocation("New York");
        car.setPhoneNo(1234567890L);
        car.setEmail("test@example.com");

        // Sample rating object
//        rating = new Rating();
//        rating.setRatingId(1);
//        rating.setUserId(101);
//        rating.setWasherId(201);
//        rating.setRating(5);
//        rating.setReviewText("Excellent service!");
        
        //Sample active washer data
        washerDTO = new WasherDTO();
        washerDTO.setId(1);
        washerDTO.setName("John Doe");
        washerDTO.setStatus("active");
    }
    
 // Test saving a car
    @Test
    void addCar_ShouldReturnCar() {
        when(carRepository.save(any(CarDetails.class))).thenReturn(car);
        CarDetails savedCar = carService.addcar(car);

        assertNotNull(savedCar);
        assertEquals("Toyota", savedCar.getCarName());
        assertEquals("Corolla", savedCar.getCarModel());
    }
    
 // Test retrieving all cars
    @Test
    void getUsers_ShouldReturnListOfCars() {
        
        when(carRepository.findAll()).thenReturn(Arrays.asList(car));
        var carList = carService.getUsers();

        assertNotNull(carList);
        assertFalse(carList.isEmpty());
        assertEquals(1, carList.size());
    }
    
 // Test finding car by ID
    @Test
    void findById_ShouldReturnCar() {
        // Test finding car by ID
        when(carRepository.findById(1)).thenReturn(Optional.of(car));
        Optional<CarDetails> foundCar = carService.findById(1);

        assertTrue(foundCar.isPresent());
        assertEquals(1, foundCar.get().getCarId());
    }
    
 // Test checking car existence by ID
    @Test
    void existsById_ShouldReturnTrue_WhenCarExists() {
        when(carRepository.existsById(1)).thenReturn(true);
        boolean exists = carService.existsById(1);

        assertTrue(exists);
    }
 // Test deleting a car by ID
    @Test
    void deleteById_ShouldDeleteCar_WhenCarExists() {
        when(carRepository.existsById(1)).thenReturn(true);
        Mockito.doNothing().when(carRepository).deleteById(1);
        carService.deleteById(1);

        Mockito.verify(carRepository, Mockito.times(1)).deleteById(1);
    }
    
 // Test saving a rating
    @Test
    void addRating_ShouldReturnSavedRating() {
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        Rating savedRating = carService.addRating(rating);

        assertNotNull(savedRating);
        assertEquals(5, savedRating.getRating());
    }
    
 // Test retrieving ratings by washer ID
//    @Test
//    void getRatingsByWasherId_ShouldReturnListOfRatings() {
//        when(ratingRepository.findByWasherId(201)).thenReturn(Arrays.asList(rating));
//        var ratings = carService.getRatingsByWasherId(201);
//
//        assertNotNull(ratings);
//        assertFalse(ratings.isEmpty());
//        assertEquals(1, ratings.size());
//    }
//    
    @Test
    void getActiveWashers_ShouldReturnListOfWashers() {
        when(washerClient.getAllActiveWashers()).thenReturn(Arrays.asList(washerDTO));
        
        List<WasherDTO> activeWashers = carService.getActiveWashers();
        
        assertNotNull(activeWashers);
        assertFalse(activeWashers.isEmpty());
        assertEquals(1, activeWashers.size());
        assertEquals("active", activeWashers.get(0).getStatus());
    }
}
