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
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) { 
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

      
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { 
            String token = authorizationHeader.substring(7); 

            try { 
                System.out.println("Token: " + token);
       
                var claims = JwtUtil.extractClaims(token);
                System.out.println("Claims: " + claims);
                if (!JwtUtil.isTokenExpired(claims)) { 
                 
                    String id = JwtUtil.getUsername(claims);
                    String role = JwtUtil.getRoles(claims);
                    System.out.println("Role: " + role);

                    var authorities = Arrays.stream(new String[] { role })
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(id, null, authorities);
          
                    SecurityContext context = new SecurityContextImpl(authenticationToken);

                 
                    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) { 
                        @Override 
                        public HttpHeaders getHeaders() { 
                            HttpHeaders headers = new HttpHeaders();
                            headers.putAll(super.getHeaders()); 
                            headers.add("loggedInUser", id); 
                            return headers;
                        } 
                    };

             
                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

                
                    return chain.filter(mutatedExchange)
                            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
                }
            } catch (Exception e) { 
                e.printStackTrace();
                return Mono.error(new RuntimeException("Invalid JWT token"));
            }
        }
        return chain.filter(exchange);
    }
}
