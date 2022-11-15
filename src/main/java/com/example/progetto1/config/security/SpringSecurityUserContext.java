package com.example.progetto1.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

//restituisce l'username dell'utente
public class SpringSecurityUserContext
{
	SecurityContext context = SecurityContextHolder.getContext();
	Authentication authentication = context.getAuthentication();
	
	public String getCurrentUser()
	{
		String CurrentUser = (authentication != null) ?  authentication.getName() : null;
		
		if (CurrentUser != null && CurrentUser.equals("anonymousUser"))  //username dell'utente non loggato
			CurrentUser = null;
				
		return CurrentUser;
	}
}
