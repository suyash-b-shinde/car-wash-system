package com.cg.util;
 
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
 
import java.security.Key;
import java.util.Date;
 
//import javax.crypto.SecretKey;
 
import org.springframework.stereotype.Component;
 
@Component 
public class JwtUtil { 
	 
	 private static final String SECRET_KEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	 
	 // Parse and validate the JWT token 
	 public static Claims extractClaims(String token) { 
		 return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	 } 
	 //Decodes the SECRET_KEY from Base64
	 private static Key getSignKey() { 
		 byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		 return Keys.hmacShaKeyFor(keyBytes);
	 } 
	 
	 // Check if the token is expired 
	 public static boolean isTokenExpired(Claims claims) { 
		 return claims.getExpiration().before(new Date());
	 } 
	 
	 // Extract username (subject) from token 
	 public static String getUsername(Claims claims) { 
		 return claims.getSubject();
	 } 
	 
	 // Extract roles from token 
	 public static String getRoles(Claims claims) { 
	 return claims.get("role", String.class);
	 } 
}