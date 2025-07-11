package com.cg.config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
 
@Configuration 
@EnableWebSecurity
public class AuthConfig {
 
	@Bean
	//to returns an instance of your custom user details service
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}
 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().and().csrf().disable().authorizeHttpRequests() //Disables CSRF protection & begins config. of endpoints
				.requestMatchers("/auth/register", "/auth/login", "/auth/validate", "/swagger-ui/**", "/v3/api-docs/**","/auth/washers","/washers","/auth/activewashers","/auth/toggleStatus/**","/auth/user/**")
				.permitAll() //Grants public access
				.and().build(); //finalize security filter chain
	}
 
	@Bean
	//instence for BCrypt hashinf of password
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
 
	@Bean
	//sets the user details service and the password encoder on the provider to ensure that passwords are checked using BCrypt.
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
 
	@Bean
	//AuthenticationConfiguration is used to fetch the AuthenticationManager
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}