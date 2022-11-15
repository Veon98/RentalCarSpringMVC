package com.example.progetto1.controller;

import com.example.progetto1.entities.Utente;
import com.example.progetto1.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.progetto1.others.OtherMethods.getLoggedUser;

@Controller
@RequestMapping("/profilo")
public class ProfiloController {

    final
    UtenteService utenteService;

    public ProfiloController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }


    @GetMapping
    public  String getProf(Model model){
        String userLoggedCF = getLoggedUser();
        Utente user = utenteService.SelUtenteByCodF(userLoggedCF);
        model.addAttribute("Utente", user);

        return "profilo";
    }
}
