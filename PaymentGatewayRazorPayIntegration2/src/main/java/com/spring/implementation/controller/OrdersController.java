package com.spring.implementation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.razorpay.RazorpayException;
import com.spring.implementation.model.Orders;
import com.spring.implementation.service.OrderService;
@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class OrdersController {
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public String ordersPage() {
		return "orders";
	}
	
	@PostMapping(value = "/createOrder", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Orders> createOrder(@RequestBody Orders orders) throws RazorpayException{
		Orders razorpayOrder = orderService.createOrder(orders);
		return new ResponseEntity<>(razorpayOrder,HttpStatus.CREATED);
	}
	
	@PostMapping("/paymentCallback")
	public String paymentCallback(@RequestParam Map<String, String> response) {
		 	orderService.updateStatus(response);
		 	return "success";
		
	}
	
//	@PostMapping("/paymentCallback")
//	public String paymentCallback(@RequestParam("razorpay_order_id") String razorpayOrderId) {
//	    orderService.updateStatus(razorpayOrderId);
//	    return "success";
//	}
}