package com.cg.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import com.cg.util.JwtUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component 
public class JwtAuthenticationFilter implements WebFilter { 
 
    @Override 
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) { //does not return any data
        // Retrieve the Authorization header from the incoming request.
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // Check if header is present and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { 
            String token = authorizationHeader.substring(7); // Extract token after "Bearer "

            try { 
                System.out.println("Token: " + token);
                // Parse the JWT token and extract claims.
                var claims = JwtUtil.extractClaims(token);
                System.out.println("Claims: " + claims);

                // Only proceed if the token is not expired.
                if (!JwtUtil.isTokenExpired(claims)) { 
                    // Extract the subject (username/id) and role from the token.
                    String id = JwtUtil.getUsername(claims);
                    String role = JwtUtil.getRoles(claims);
                    System.out.println("Role: " + role);

                    // Create an authority object from the role.
                    var authorities = Arrays.stream(new String[] { role })
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                            .collect(Collectors.toList());

                    // Create an authentication token to say that the user is authenticated.
                    UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(id, null, authorities);
                    // Wrap it in a SecurityContext.
                    SecurityContext context = new SecurityContextImpl(authenticationToken);

                    // Decorate the existing request by adding a custom header "loggedInUser".
                    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) { 
                        @Override 
                        public HttpHeaders getHeaders() { 
                            HttpHeaders headers = new HttpHeaders();
                            headers.putAll(super.getHeaders()); // Retain all existing headers.
                            headers.add("loggedInUser", id); // Add custom header with the user's identity.
                            return headers;
                        } 
                    };

                    // Build a new exchange object containing the mutated request.
                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

                    // Pass the exchange down the filter chain, also writing the security context into the reactive context.
                    return chain.filter(mutatedExchange)
                            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
                }
            } catch (Exception e) { 
                e.printStackTrace();
                return Mono.error(new RuntimeException("Invalid JWT token"));
            }
        }
        // If there is no Authorization header or it doesn't start with "Bearer ", proceed without modifications.
        return chain.filter(exchange);
    }
}
