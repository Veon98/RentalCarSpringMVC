package com.example.progetto1.controller;

 
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.progetto1.services.UtenteService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/login/form")
public class LoginController 
{

	@GetMapping  //apre il form in sè nel momento in cui si accede ad una pagina che lo richiede
	public String getLogin(Model model)    //ti porta al form di login nel momento in cui vai in una pagina vietata
	{
		return "login";
	}


}
