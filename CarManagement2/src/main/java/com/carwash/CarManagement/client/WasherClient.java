package com.carwash.CarManagement.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.carwash.CarManagement.dto.WasherDTO;

@FeignClient(name = "WASHERMANAGEMENT")
public interface WasherClient {
	
	@GetMapping(value = "/washers/activewashers")
	List<WasherDTO> getAllActiveWashers();

}

