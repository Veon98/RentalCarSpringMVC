package com.example.progetto1.controller;

import com.example.progetto1.config.security.SpringSecurityUserContext;
import com.example.progetto1.entities.Auto;
import com.example.progetto1.entities.Utente;
import com.example.progetto1.services.AutoService;
import com.example.progetto1.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.example.progetto1.others.OtherMethods.getLoggedUser;


@Controller
@RequestMapping("/")
public class IndexController {

    final
    UtenteService utenteService;
    final
    AutoService autoService;

    public IndexController(UtenteService utenteService, AutoService autoService) {
        this.utenteService = utenteService;
        this.autoService = autoService;
    }



    @RequestMapping //gestisce le richieste senza un url specifico
    public String getWelcome(Model model)
    {
        model.addAttribute("titolo", "Benvenuto in Rental Car");
        model.addAttribute("Utente", new SpringSecurityUserContext().getCurrentUser());

        /*List<Auto> listaAuto = autoService.selAutosDisp();
        model.addAttribute("listaAuto", listaAuto);*/

        return "index";
    }


    @RequestMapping(value="index")  //gestisce tutto ciò che ha index nell'url
    public String getWelcome2(Model model)
    {
        model.addAttribute("titolo", "Benvenuto in Rental Car");
        model.addAttribute("Utente", new SpringSecurityUserContext().getCurrentUser());

        /*List<Auto> listaAuto = autoService.selAutosDisp();
        model.addAttribute("listaAuto", listaAuto);*/

        return "index";   //pagina jsp richiamata
    }
}
