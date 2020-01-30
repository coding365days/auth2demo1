package com.a2.config;

import static com.a2.config.SecurityConstants.EXPIRATION_TIME;
import static com.a2.config.SecurityConstants.SECRET;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	public String tokenGeneration(Authentication authentication) {

//		AppUser appUser = (AppUser) authentication.getPrincipal();
//		String username = appUser.getUsername();
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		Date now = new Date(System.currentTimeMillis());
		Date expiry = new Date(now.getTime() + EXPIRATION_TIME);
		
		String username = authentication.getName();
		Map<String, Object> claims = new HashMap();
		claims.put("username", username);
		claims.put("authorities", authorities);

		return Jwts.builder().setSubject(username).setClaims(claims).setIssuedAt(now).setExpiration(expiry)
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}
	
	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String username = (String) claims.get("username");
		return username;
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			System.out.println("Invalid JWT Signature");
		} catch (MalformedJwtException ex) {
			System.out.println("Invalid JWT Token");
		} catch (ExpiredJwtException ex) {
			System.out.println("Expired JWT Token");
		} catch (UnsupportedJwtException ex) {
			System.out.println("Unsupported JWT Token");
		} catch (IllegalArgumentException ex) {
			System.out.println("JWT Claims String is Empty.");
		}
		return false;
	}
	
	public UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth,
			final UserDetails userDetails) {

		final JwtParser jwtParser = Jwts.parser().setSigningKey(SECRET);

		final Jws claimsJws = jwtParser.parseClaimsJws(token);

		final Claims claims = (Claims) claimsJws.getBody();

		final Collection authorities = Arrays.stream(claims.get("authorities").toString().split(","))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}
}
