package com.example.Washer.Client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.Washer.DTO.WasherDetailsDTO;

@FeignClient(name = "USERMANAGEMENT")
public interface UserClient {
   
	@GetMapping("/auth/washers")
    List<WasherDetailsDTO> getAllWashers();
	
	@GetMapping("/auth/activewashers")
    List<WasherDetailsDTO> getActiveWashers();
}
