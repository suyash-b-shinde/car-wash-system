package com.carwash.CarManagement;
 
import org.springframework.boot.SpringApplication;
 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication 
@EnableDiscoveryClient // Enable service registration with Eureka 
@EnableFeignClients 
public class CarManagementApplication { 
 
 public static void main(String[] args) { 
 SpringApplication.run(CarManagementApplication.class, args);
 } 
 
}