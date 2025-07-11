package com.cg.config;
 
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
 
import com.cg.filter.JwtAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

 
@Configuration 
@EnableWebFluxSecurity 
public class SecurityConfig { 
 
	 private final JwtAuthenticationFilter jwtAuthenticationFilter;
	 
	 public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) { 
		 this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	 } 
	
	 @Bean 
	 //defines the reactive security filter chain
	 public SecurityWebFilterChain securitydWebFilterChain(ServerHttpSecurity http) { 
	 return http.authorizeExchange(exchanges -> exchanges 
			 .pathMatchers("/usermanagement/auth/login", 
					 "/usermanagement/auth/register", 
					 "/usermanagement/auth/validate"
					//"/usermanagement/auth/toggleWasherStatus/**"
					 )
			 .permitAll() // Allow access without authentication 
			 .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

			 .pathMatchers(HttpMethod.PUT, "/usermanagement/auth/toggleWasherStatus/**").hasRole("ROLE_admin")
			 //carmanagement
			 .pathMatchers(HttpMethod.POST, "/carmanagement/cars/**").hasRole("ROLE_user")//user  add order for carwash
			 .pathMatchers(HttpMethod.POST, "/carmanagement/cars/giveRating/**").hasRole("ROLE_user")
			 .pathMatchers(HttpMethod.GET, "/carmanagement/cars/washer/user/**").hasAnyRole("ROLE_user","ROLE_admin")
			 .pathMatchers(HttpMethod.GET, "/carmanagement/cars/order/washer/**").hasAnyRole("ROLE_user","ROLE_admin")
			 .pathMatchers(HttpMethod.GET, "/carmanagement/cars/viewratings/**").hasAnyRole("ROLE_admin","ROLE_user","ROLE_washer")
			 .pathMatchers(HttpMethod.GET, "/carmanagement/cars/washer/**").hasRole("ROLE_admin")//admin can see rating of washer using washerid
			 .pathMatchers(HttpMethod.GET, "/carmanagement/cars/userorders/**").hasRole("ROLE_user")
			 .pathMatchers(HttpMethod.GET, "/carmanagement/cars/washServicePrice/**").hasAnyRole("ROLE_user","ROLE_admin")//user & admin can see washserviceprice
			 .pathMatchers(HttpMethod.GET, "/carmanagement/cars/activewashers/**").hasAnyRole("ROLE_admin","ROLE_user","ROLE_washer")//admin can get all carorders, getcarorderbyid
			 .pathMatchers(HttpMethod.GET, "/carmanagement/cars/**").hasRole("ROLE_admin")//admin can get all carorders, getcarorderbyi
			 .pathMatchers(HttpMethod.DELETE, "/carmanagement/cars/delete/**").hasAnyRole("ROLE_admin","ROLE_user")//admin can delete the order 
			 //washermanagement
			 .pathMatchers(HttpMethod.POST, "/washermanagement/washers/add/**").hasRole("ROLE_admin")//admin can add washer
			 .pathMatchers(HttpMethod.PUT, "/washermanagement/washers/update/**").hasRole("ROLE_washer")// washer can change status
			 .pathMatchers(HttpMethod.PUT, "/washermanagement/washers/updatecarstatus/**").hasRole("ROLE_washer")//washer can change wash status of car
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/ratings/**").hasRole("ROLE_washer")//washer can see ratings
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/getallwashers").hasRole("ROLE_admin")//admin can see all washer
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/getwasherbyid/**").hasRole("ROLE_admin")//admin can get washer by id
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/allorders/**").hasAnyRole("ROLE_admin","ROLE_washer")//Washer and admin can see all orders
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/getcar/**").hasAnyRole("ROLE_admin","ROLE_washer")//washer an admin can see order by id
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/activewashers/**").hasAnyRole("ROLE_admin","ROLE_user")//admin can the active washers
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/allwashers/**").hasAnyRole("ROLE_admin","ROLE_washer")
			 .pathMatchers(HttpMethod.DELETE, "/washermanagement/washers/delete/**").hasRole("ROLE_admin")//admin can delete the washer
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/orders/**").hasAnyRole("ROLE_washer")
			 .anyExchange().authenticated()) // All other routes require authentication 
			 .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
			// Add JWT filter at the authentication stage
			 .csrf().disable() // Disable CSRF for stateless APIs 
			 .build();
	 } 
	 
	 @Bean
	 public CorsConfigurationSource corsConfigurationSource() {
	     CorsConfiguration config = new CorsConfiguration();
	     config.setAllowCredentials(true);
	     config.addAllowedOrigin("http://localhost:4200");
	     config.addAllowedHeader("*");
	     config.addAllowedMethod("*");

	     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	     source.registerCorsConfiguration("/**", config);
	     return source;
	 }

	 @Bean
	 public CorsWebFilter corsWebFilter(CorsConfigurationSource source) {
	     return new CorsWebFilter(source);
	 }

}