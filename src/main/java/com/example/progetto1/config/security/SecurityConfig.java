package com.example.progetto1.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter
{

	private static final Logger LOGGER = Logger.getLogger("Customer security service: ");

	//nome dato nella notazione service della classe di servizio customUserDetailsService
	@Qualifier("CustomUserDetailsService")
	private final UserDetailsService userDetailsService;



	public SecurityConfig(UserDetailsService userDetailsService) {  //il qualifier riconosce la classe attraverso il nome che gli è staato attribuito
		this.userDetailsService = userDetailsService;
	}


	@Bean
	public BCryptPasswordEncoder passwordEncoder()  //criptazione password
	{
		return new BCryptPasswordEncoder();
	};



	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception
	{
		auth.authenticationProvider(authenticationProvider());  //lettura dal dbms
	}



	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		//dao che si basa sulla lettura dei dati dal dbms
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());

		return authenticationProvider;
	}



	//operazioni consentite solo all'admin
	private static final String[] ADMIN_PATH_MATCHER =
	{
			"/utente/showusers",
			"/utente/insutente",
			"/utente/elimina/**",
			"/utente/search",
			"/auto/search",
			"/auto/showcars",
			"/auto/insauto",
			"/auto/modifica/**",
			"/auto/elimina/**",
			"/prenotazione/modifica/**"
	};



	//operazioni consentite solo all'user
	private static final String[]  USER_PATH_MATCHER =
	{
			"/utente/modifica/**",
			"/prenotazione/insprenotazione/**",
			"/prenotazione/elimina/**"
	};



	//operazioni consentite a entrambi
	private static final String[]  ALL_PATH_MATCHER =
	{
			"/prenotazione/showprens",
		    "/prenotazione/search",
			"/profilo"
	};



	@Override
	protected void configure(final HttpSecurity http) throws Exception
	{
		http
				//Parti di codice liberamente accessibili
				.authorizeRequests()
				.antMatchers("/login/**", "/", "/index", "/auto/showcarsDisponibili", "/auto/searchDisp", "/auto/disponibili", "/auto/searchDateDisp").permitAll()   //permesso a tutti
				.antMatchers(ALL_PATH_MATCHER).access("hasAnyRole('ADMIN','USER')")
				.antMatchers(ADMIN_PATH_MATCHER).access("hasRole('ADMIN')")
				.antMatchers(USER_PATH_MATCHER).access("hasRole('USER')")
				.and()
				.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.formLogin()
					.loginPage("/login/form") //Dove si trova il form di login, path che in sè lo fa aprire attraverso il controller
					.loginProcessingUrl("/login")  //avvia l'operazione di autenticazione
					.failureUrl("/login/form?error") //in caso di errore (riporta al form)
					.usernameParameter("username")
					.passwordParameter("password")
				.and()
				.exceptionHandling()
					.accessDeniedPage("/login/form?forbidden")   //accesso negato
				.and()
				.logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/index")  //si può inserire un qualsiasi path
					.invalidateHttpSession(true)
				.and()
				.csrf().disable() //utile in fase di sviluppo ma da riattivare in fase operativa (se abilitato non permette di usare il form in post)
		;
	}



	public AuthenticationFilter authenticationFilter()
			throws Exception
	{

		 AuthenticationFilter filter = new AuthenticationFilter();

		 filter.setAuthenticationManager(authenticationManagerBean());   //auth manager
		 filter.setAuthenticationFailureHandler(failureHandler());   //gestione fallimento
		 filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());    //gestione successo
		 //filter.setRememberMeServices(customRememberMeServices());   //gestione del bottone ricordami inserendo le credenziali in un cookie

		 return filter;
	}



	public SimpleUrlAuthenticationFailureHandler failureHandler()    //operazione per autenticazione con fallimento
	{
        return new SimpleUrlAuthenticationFailureHandler("/login/form?error");
    }



	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler()   //operazione per autenticazione con successo
	{
		return new SuccessHandler();   //classe che gestisce l'operazione che viene effettuata a seconda dal tipo di utente loggato
	}

}