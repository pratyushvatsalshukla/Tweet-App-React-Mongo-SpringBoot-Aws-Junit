package com.tweetApp.blog.security;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService ;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper ;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Get Token
		String requestToken = request.getHeader("Authorization") ;
		Enumeration<String> headerNames = request.getHeaderNames();

		while(headerNames.hasMoreElements())
		{
			System.out.println(headerNames.nextElement());
		}
		
		System.out.println("TOKEN :--- " + requestToken);
		
		String username = null ;
		String token = null ;
		
		// Bearer 2352523sddsf
		
		if(requestToken!=null && requestToken.startsWith("Bearer"))
		{
			token = requestToken.substring(7) ;
			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token) ;
			}
			catch(IllegalArgumentException e)
			{
				System.out.println("Unable to get Jwt Token");
			}
			catch(ExpiredJwtException e) {
				System.out.println("Jwt Token has been Expired");
			}
			catch(MalformedJwtException e) {
				System.out.println("Invalid JWT");
			}
			
		}
		else {
			System.out.println("Jwt Token doesnot begin with Bearer !!");
		}
		
		// When we get the token,we'll now  Validate
		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username) ;
			System.out.println(userDetails + "---USER DETAILS ");
			if(this.jwtTokenHelper.validateToken(token, userDetails )) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) ;
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

				
			}
			else {
				System.out.println("INVALID JWT TOKEN !!");
			}
			
			
		}else {
			
			System.out.println("Username is Null or Context is not Null");
			
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	

}
