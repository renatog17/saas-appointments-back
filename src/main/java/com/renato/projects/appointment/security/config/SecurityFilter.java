package com.renato.projects.appointment.security.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.renato.projects.appointment.security.repository.UserRepository;
import com.renato.projects.appointment.security.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	TokenService tokenService;
	@Autowired
	UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = this.recoverToken(request);
		if(token!=null) {
			var login = tokenService.validateToken(token);
			Optional<UserDetails> optionalUser = userRepository.findByLogin(login);
			
			
			if (optionalUser.isPresent()) {
	            UserDetails user = optionalUser.get();
	            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        } else {
	            // Token inválido -> apagar cookie
	            var cookie = new jakarta.servlet.http.Cookie("token", "");
	            cookie.setPath("/");
	            cookie.setMaxAge(0);
	            cookie.setHttpOnly(true);
	            cookie.setSecure(true); // se estiver usando HTTPS
	            response.addCookie(cookie);
	        }
		}
		filterChain.doFilter(request, response);
	}
	
	//agora a autenticação é por cookies
//	private String recoverToken(HttpServletRequest request) {
//		var authHeader = request.getHeader("Authorization");
//		if(authHeader == null)
//			return null;
//		return authHeader.replace("Bearer ", "");
//
//	}
	
	private String recoverToken(HttpServletRequest request) {
	    if (request.getCookies() == null) return null;

	    for (var cookie : request.getCookies()) {
	        if (cookie.getName().equals("token")) {
	            return cookie.getValue();
	        }
	    }

	    return null;
	}
}