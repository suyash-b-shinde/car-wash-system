package com.cg.service;
 
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
 
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
 
@Component
public class JwtService {
	 // A static secret key used to sign the JWT tokens.
	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
 
	public void validateToken(final String token) {
		Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
	}
 
	public String generateToken(String userName, String role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", "ROLE_" + role);
		return createToken(claims, userName);
	}
 
	private String createToken(Map<String, Object> claims, String userName) {
		// Build the token:
        // - Set claims and subject (the username)
        // - Set issue and expiration times (here token valid for 30 minutes)
        // - Sign the token using HS256 algorithm and the secret key.
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}
 
	private Key getSignKey() {
		// Decode the secret from Base64 and generate an HMAC key for signing.
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}