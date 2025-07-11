package com.cg.config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
 
import com.cg.filter.JwtAuthenticationFilter;
 
@Configuration 
@EnableWebFluxSecurity 
public class SecurityConfig { 
 
	 private final JwtAuthenticationFilter jwtAuthenticationFilter;
	 
	 public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) { 
		 this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	 } 
	
	 @Bean 
	
	 public SecurityWebFilterChain securitydWebFilterChain(ServerHttpSecurity http) { 
	 return http.authorizeExchange(exchanges -> exchanges 
			 .pathMatchers("/usermanagement/auth/login", 
					 "/usermanagement/auth/register", 
					 "/usermanagement/auth/validate",
					 "/payment/orders",
					 "/payment/createOrder"
					
					 )
			 .permitAll() 
			 //carmanagement
			 .pathMatchers(HttpMethod.POST, "/carmanagement/cars/**").hasRole("ROLE_user")
			 .pathMatchers(HttpMethod.GET, "/carmanagement/cars/washer/**").hasRole("ROLE_admin")
			 .pathMatchers(HttpMethod.GET, "/carmanagement/cars/washServicePrice/**").hasAnyRole("ROLE_user","ROLE_admin")
			 .pathMatchers(HttpMethod.GET, "/carmanagement/cars/activewashers").hasAnyRole("ROLE_admin","ROLE_user")
			 .pathMatchers(HttpMethod.GET, "/carmanagement/cars/**").hasRole("ROLE_admin")
			 .pathMatchers(HttpMethod.DELETE, "/carmanagement/cars/delete/**").hasRole("ROLE_admin")
			 //washermanagement
//			 .pathMatchers(HttpMethod.POST, "/washermanagement/washers/add/**").hasRole("ROLE_admin")
			 .pathMatchers(HttpMethod.PUT, "/washermanagement/washers/update/**").hasRole("ROLE_washer")
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/ratings/**").hasRole("ROLE_washer")
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/getallwashers").hasRole("ROLE_admin")
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/getwasherbyid/**").hasRole("ROLE_admin")
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/allorders/**").hasAnyRole("ROLE_admin","ROLE_washer")
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/getcar/**").hasAnyRole("ROLE_admin","ROLE_washer")
			 .pathMatchers(HttpMethod.GET, "/washermanagement/washers/activewashers/**").hasAnyRole("ROLE_admin")
			 .pathMatchers(HttpMethod.DELETE, "/washermanagement/washers/delete/**").hasRole("ROLE_admin")
//			 .pathMatchers(HttpMethod.POST, "/payment/createOrder/**").hasRole("ROLE_user")
			 .anyExchange().authenticated())  
			 .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
			 
			 .csrf().disable() 
			 .build();
	 } 
}