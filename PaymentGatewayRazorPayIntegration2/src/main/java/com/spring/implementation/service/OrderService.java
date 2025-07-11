package com.spring.implementation.service;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.spring.implementation.model.Orders;
import com.spring.implementation.repository.OrdersRepository;

import jakarta.annotation.PostConstruct;

@Service
public class OrderService {
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Value("${razorpay.key.id}")
	private String razorpayId;
	@Value("${razorpay.key.secret}")
	private String razorpaySecret;
	
	private RazorpayClient razorpayCLient;
	
	@PostConstruct //for initializing things after dependency injection is done.
	public void init() throws RazorpayException {
		this.razorpayCLient = new RazorpayClient(razorpayId, razorpaySecret);//initializes the Razorpay client with the key ID and secret.
	}
	
	public Orders createOrder(Orders order) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", order.getAmount());
        options.put("currency", "INR");
        options.put("receipt", order.getEmail());
        Order razorpayOrder = razorpayCLient.orders.create(options);
        if(razorpayOrder != null) {
        order.setRazorpayOrderId(razorpayOrder.get("id"));
        order.setOrderStatus(razorpayOrder.get("status"));
        }
        return ordersRepository.save(order);
    }

	public Orders updateStatus(Map<String, String> map) {
    	String razorpayId = map.get("razorpay_order_id");
    	Orders order = ordersRepository.findByRazorpayOrderId(razorpayId);
    	order.setOrderStatus("PAYMENT DONE");
    	Orders orders = ordersRepository.save(order);
    	return orders;
    }
	
//	public Orders updateStatus(String razorpayOrderId) {
//	    Orders order = ordersRepository.findByRazorpayOrderId(razorpayOrderId);
//	    if (order != null) {
//	        order.setOrderStatus("PAYMENT DONE");
//	        return ordersRepository.save(order);
//	    } else {
//	        throw new RuntimeException("Order not found with Razorpay Order ID: " + razorpayOrderId);
//	    }
//	}

}