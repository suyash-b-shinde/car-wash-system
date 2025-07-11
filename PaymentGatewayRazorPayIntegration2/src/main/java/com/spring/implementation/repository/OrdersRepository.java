package com.spring.implementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.implementation.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer>{

	Orders findByRazorpayOrderId(String razorpayId);

}