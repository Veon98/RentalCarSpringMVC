package com.example.progetto1.controller;

import com.example.progetto1.entities.Utente;
import com.example.progetto1.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.progetto1.others.OtherMethods.getLoggedUser;

//a contatto con la view, riceve i dati dal dao passando per il service
@Controller
@RequestMapping("/utente")   //path generale di questo controller, gli si devono concatenare i path dei metodi sottostanti: http://localhost:8080/progetto1_war_exploded/utente
public class UtenteController {

    //code injection (interazione tra Beans)
    private final UtenteService utenteService;
    private final BCryptPasswordEncoder pwdEnc;

    public UtenteController(UtenteService utenteService, BCryptPasswordEncoder pwdEnc) {
        this.utenteService = utenteService;
        this.pwdEnc = pwdEnc;
    }




    //SELECT
    @GetMapping ("/showusers")     //http://localhost:8080/progetto1_war_exploded/utente/showusers
    private String getAllUsers(Model model) {

        List<Utente> listaUtenti = utenteService.selUtenti();

        model.addAttribute("listaUtenti", listaUtenti);

        String userLoggedCF = getLoggedUser();
        Utente user = utenteService.SelUtenteByCodF(userLoggedCF);
        model.addAttribute("Utente", user);

        return "showUtenti";
    }



    //METODI GET E POST INSERT
    @GetMapping("/insutente")     //http://localhost:8080/progetto1_war_exploded/utente/insutente
    public String insUtente(Model model){   //qui creiamo l'oggetto per fare il binding col form e apriamo il form stesso (come si vede dal return). Inoltre se c'è bisogno di pre-compilare il form ciò viene fatto qui: basta recuperare i valori da service e dao e poi passarli con l'addAttribute e recuperarli nel jsp attraverso jstl

        Utente user = new Utente();
        model.addAttribute("Utente", user);  //data bindng (attributeModel del form uguale ad attributeName)

        String userLoggedCF = getLoggedUser();
        Utente userLogged = utenteService.SelUtenteByCodF(userLoggedCF);
        model.addAttribute("UtenteLog", userLogged);

        return "formUtente";  //pagina del form
    }

    @PostMapping("/insutente")  //qui vengono recuperati i dati inseriti nel form e vengono passati allo stato di servizio
    public String doInsUser(@ModelAttribute("Utente") Utente user){  //anche qui viene effettuato il data binding, per popolare l'oggetto user

        user.setPwd(pwdEnc.encode(user.getPwd()));   //codifica password

        utenteService.insUtente(user);  //user viene inviato al service
        return "redirect:/utente/showusers";  //con redirect si può passare un url con tanto di parametro specifico (magari un id dell'utente)
    }



    //METODO DELETE
    @GetMapping("/elimina/{idUtente}")       //http://localhost:8080/progetto1_war_exploded/utente/elimina/idUtentespecifico
    private String deleteUser(@PathVariable("idUtente") Integer idUtente) {   //rintraccia l'utente da eliminare attraverso l'id recuperata dall'url
        utenteService.elimUtente(idUtente);
        return "redirect:/utente/showusers";
    }



    //METODI GET E POST UPLOAD
    @GetMapping("/modifica/{idUtente}")        //http://localhost:8080/progetto1_war_exploded/utente/modifica/idUtentespecifico
    public String modUtente(@PathVariable("idUtente") Integer idUtente, Model model){    //recupera l'id utente dall'url

        Utente user = utenteService.selUtente(idUtente);  //seleziona l'utente che si vuole modificare attraverso l'id
        model.addAttribute("Utente", user);   //dati che pre-compileranno il form

        Integer check = new Integer(user.getIdUtente());   //serve per il controllo sul form e capire se è un insert o un update
        model.addAttribute("check", check);

        String userLoggedCF = getLoggedUser();
        Utente userLogged = utenteService.SelUtenteByCodF(userLoggedCF);
        model.addAttribute("UtenteLog", userLogged);

        return "formUtente";    //pagina del form
    }

    @PostMapping("/modifica/{idUtente}")
    public String doModUtente(@Valid @ModelAttribute("Utente") Utente user){    //si ricevono i dati dal form (data binding)

        user.setPwd(pwdEnc.encode(user.getPwd()));   //codifica password

        utenteService.updUtente(user);

        return "redirect:/utente/showusers";
    }



    //SEARCH
    @GetMapping(value = "/search")
    public String SearchItem(@RequestParam("filter") String filtro, @RequestParam("tipo") String tipo, Model model)
    {
        List <Utente> utentiCercati = utenteService.SelUtentiByFilter(filtro, tipo);
        
        model.addAttribute("listaUtenti", utentiCercati);
        //model.addAttribute("IsUtenti", true);   //utile se si vuole usare la barra di ricerca per oiù tipologie di cose (utenti, auto, prenotazioni ecc)
        model.addAttribute("filter", filtro);   //fa vedere la stringa di ricerca nella input bar dopo aver effettuato la ricwerca

        String userLoggedCF = getLoggedUser();
        Utente user = utenteService.SelUtenteByCodF(userLoggedCF);
        model.addAttribute("Utente", user);

        return "showUtenti";
    }

}

