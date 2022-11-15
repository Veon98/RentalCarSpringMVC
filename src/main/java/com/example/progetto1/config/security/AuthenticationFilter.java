package com.example.progetto1.config.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//intercetta i parametri inseriti nel login form
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException
	{
		if (!request.getMethod().equals("POST"))
		{ 
	       throw new AuthenticationServiceException("Metodo di Autenticazione non supportato: " + request.getMethod());
	    }
		
		UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);  //richiama il metodo che viene implementato poco più giù
		setDetails(request, authRequest);
		
		return this.getAuthenticationManager().authenticate(authRequest);
		
	}
	
	
	private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) 
	{
		//recupero le credenziali
		 String username = request.getParameter("username");
	     String password = obtainPassword(request);

	     //si sostituisce con stringa vuota lì dove i parametri sono null
	     username = (username == null) ? "" : username;
	     password = (password == null) ? "" : password;
	     

	     return new UsernamePasswordAuthenticationToken(username, password);   //si passano codice fiscale e pwd al security config
	         
	}
}
