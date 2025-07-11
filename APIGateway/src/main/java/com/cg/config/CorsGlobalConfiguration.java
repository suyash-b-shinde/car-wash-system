//// src/main/java/com/cg/config/CorsGlobalConfig.java
//package com.cg.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//
//@Configuration
//public class CorsGlobalConfiguration {
//
//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("http://localhost:4200"); // ✅ Angular frontend
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*"); // GET, POST, PUT, DELETE...
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config); // ✅ Applies to all routes
//
//        return new CorsWebFilter(source);
//    }
//}
////package com.cg.config;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.web.cors.CorsConfiguration;
////import org.springframework.web.cors.reactive.*;
////import org.springframework.web.cors.reactive.CorsWebFilter;
////
////@Configuration
////public class CorsGlobalConfiguration{
////	@Bean
////    public CorsWebFilter corsWebFilter() {
////        CorsConfiguration config = new CorsConfiguration();
////        config.setAllowCredentials(true);  // Allow cookies/token headers
////        config.addAllowedOrigin("http://localhost:4200"); // Allow Angular origin
////        config.addAllowedHeader("*");     // Allow all headers
////        config.addAllowedMethod("*");     // Allow GET, POST, PUT, DELETE, etc.
////
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", config); // Apply to all routes
////
////        return new CorsWebFilter(source);
////    }
////}