package com.example.progetto1.others;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class OtherMethods {

    //recupera l'username dell'utente loggato (per evitare che venga usato sempre SecurityContextHolder.getContext().getAuthentication())
    public static String getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  //recupero context
        return authentication.getName();   //recupero username
    }
}
