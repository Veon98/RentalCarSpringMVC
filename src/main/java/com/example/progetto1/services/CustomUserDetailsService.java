package com.example.progetto1.services;

import com.example.progetto1.entities.Utente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import  org.springframework.security.core.userdetails.User;

import java.util.function.Supplier;
import java.util.logging.Logger;

//qui viene gestito l'utente nel momento in cui effettua il login, recuperando le crednziali e assegnandogli il giusto ruolo
@Service("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private final UtenteService utenteService;

	public CustomUserDetailsService(UtenteService utenteService) {
		this.utenteService = utenteService;
	}

	private static final Logger LOGGER = Logger.getLogger("Customer detail service: ");


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String codFiscale)
			throws UsernameNotFoundException
	{
		if (codFiscale == null) {
			throw new UsernameNotFoundException("Inserisci il tuo Codice Fiscale");
		}

		 Utente utente = utenteService.SelUtenteByCodF(codFiscale);   //recupero l'utente a seconda dal suo codice fiscale
		 
		 if (utente == null)
		 {
			 throw new UsernameNotFoundException("Utente non Trovato!!");
		 }

		LOGGER.info("Utente prelevato: " + utente.getNome() + " " + utente.getCognome());

		 UserBuilder builder = null;
		
		 builder = User.withUsername(utente.getCodFiscale());  //si recupera username  (ovvero il codice fiscale)
		 builder.password(utente.getPwd());   //e password criptata

		if (utente.isAdmin()==true){	//decido il ruolo dell'utente loggato controllando il suo booleano
			builder.roles("ADMIN");  //diviene ROLE_ADMIN
		}
		else {
			builder.roles("USER");   //diviene ROLE_USER
		}

		LOGGER.info("Ruolo ok");

		return builder.build();
	}
	 
}
