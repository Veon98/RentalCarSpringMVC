package com.example.progetto1.controller;

import com.example.progetto1.entities.Auto;
import com.example.progetto1.entities.Utente;
import com.example.progetto1.services.AutoService;
import com.example.progetto1.services.UtenteService;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static com.example.progetto1.others.OtherMethods.getLoggedUser;
import static java.time.temporal.ChronoUnit.DAYS;

//a contatto con la view, riceve i dati dal dao passando per il service
@Controller
@RequestMapping("/auto")   //path generale di questo controller, gli si devono concatenare i path dei metodi sottostanti: http://localhost:8080/progetto1_war_exploded/utente
public class AutoController {

    //code injection (interazione tra Beans)
    private final AutoService autoService;
    final
    UtenteService utenteService;

    public AutoController(AutoService autoService, UtenteService utenteService) {
        this.autoService = autoService;
        this.utenteService = utenteService;
    }


    //SELECT
    @GetMapping("/showcars")     //http://localhost:8080/progetto1_war_exploded/auto/showcars
    private String getAllCars(Model model) {

        List<Auto> listaAuto = autoService.selAutos();

        model.addAttribute("listaAuto", listaAuto);


        String userLoggedCF = getLoggedUser();
        Utente user = utenteService.SelUtenteByCodF(userLoggedCF);
        Boolean isAdmin = user.isAdmin();
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("Utente", user);

        return "showAuto";
    }


    //SELECT LE AUTO DISPONIBILI
    @GetMapping("/showcarsDisponibili")  //non viene usata, sostituita dalla selezione per date
    private String getAllCarsDisp(Model model) {

        List<Auto> listaAuto = autoService.selAutosDisp();

        model.addAttribute("listaAuto", listaAuto);


        String userLoggedCF = getLoggedUser();
        Utente user = utenteService.SelUtenteByCodF(userLoggedCF);
        Boolean isAdmin = user.isAdmin();
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("Utente", user);

        return "showAuto";
    }


    @GetMapping("/disponibili")  //select auto disponibili sulla base delle date inserite
    public  String disponibili(Model model, HttpServletRequest request){
        LocalDate dataInizio = LocalDate.parse(request.getParameter("dataInizio"));
        LocalDate dataFine = LocalDate.parse(request.getParameter("dataFine"));


        List<Auto> prenDisponibili = autoService.SelAutoDisponibili(dataInizio, dataFine);

        model.addAttribute("listaAuto", prenDisponibili);

        //servono per effettuare la ricerca del modello specifico
        model.addAttribute("dataInizio", dataInizio);
        model.addAttribute("dataFine", dataFine);

        return "showDisp";
    }




    //METODI GET E POST INSERT
    @GetMapping("/insauto")     //http://localhost:8080/progetto1_war_exploded/auto/insauto
    public String insAuto(Model model) {   //qui creiamo l'oggetto per fare il binding col form e apriamo il form stesso (come si vede dal return). Inoltre se c'è bisogno di pre-compilare il form ciò viene fatto qui: basta recuperare i valori da service e dao e poi passarli con l'addAttribute e recuperarli nel jsp attraverso jstl

        Auto car = new Auto();
        model.addAttribute("Auto", car);  //data bindng (attributeModel del form uguale ad attributeName)

        String userLoggedCF = getLoggedUser();
        Utente user = utenteService.SelUtenteByCodF(userLoggedCF);
        model.addAttribute("Utente", user);

        return "formAuto";  //pagina del form
    }

    @PostMapping("/insauto")  //qui vengono recuperati i dati inseriti nel form e vengono passati allo stato di servizio
    public String doInsCar(@ModelAttribute("Auto") Auto car) {  //anche qui viene effettuato il data binding, per popolare l'oggetto car
        autoService.insAuto(car);  //car viene inviato al service
        return "redirect:/auto/showcars";  //con redirect si può passare un url con tanto di parametro specifico (magari un id dell'auto)
    }



    //METODO DELETE
    @GetMapping("/elimina/{idAuto}")       //http://localhost:8080/progetto1_war_exploded/auto/elimina/idAutospecifico
    private String deleteCar(@PathVariable("idAuto") Integer idAuto) {   //rintraccia l'auto da eliminare attraverso l'id recuperata dall'url
        autoService.elimAuto(idAuto);
        return "redirect:/auto/showcars";
    }



    //METODI GET E POST UPLOAD
    @GetMapping("/modifica/{idAuto}")
    //http://localhost:8080/progetto1_war_exploded/auto/modifica/idAutospecifico
    public String modAuto(@PathVariable("idAuto") Integer idAuto, Model model) {    //recupera l'id utente dall'url

        Auto car = autoService.selAuto(idAuto);  //seleziona l'auto che si vuole modificare attraverso l'id
        model.addAttribute("Auto", car);   //dati che pre-compileranno il form

        Integer check = new Integer(car.getIdAuto());   //serve per il controllo sul form e capire se è un insert o un update
        model.addAttribute("check", check);

        String userLoggedCF = getLoggedUser();
        Utente user = utenteService.SelUtenteByCodF(userLoggedCF);
        model.addAttribute("Utente", user);

        return "formAuto";    //pagina del form
    }

    @PostMapping("/modifica/{idAuto}")
    public String doModAuto(@Valid @ModelAttribute("Auto") Auto car) {    //si ricevono i dati dal form (data binding)

        autoService.updAuto(car);

        return "redirect:/auto/showcars";
    }



    //SEARCH
    @GetMapping(value = "/search")
    public String SearchCar(@RequestParam("filter") String filtro, @RequestParam("tipo") String tipo, Model model) {
        List<Auto> autoCercate = autoService.SelAutoByFilter(filtro, tipo);

        model.addAttribute("listaAuto", autoCercate);
        //model.addAttribute("IsUtenti", true);   //utile se si vuole usare la barra di ricerca per oiù tipologie di cose (utenti, auto, prenotazioni ecc)
        model.addAttribute("filter", filtro);   //fa vedere la stringa di ricerca nella input bar dopo aver effettuato la ricwerca

        String userLoggedCF = getLoggedUser();
        Utente user = utenteService.SelUtenteByCodF(userLoggedCF);
        model.addAttribute("Utente", user);

        return "showAuto";
    }


    //deprecata
    @GetMapping(value = "/searchDisp")  //non viene più utilizzata perchè non più coerente con la ricerca basata sulle date
    public String SearchCarDisp(@RequestParam("filter") String filtro, @RequestParam("tipo") String tipo, Model model) {
        List<Auto> autoCercate = autoService.SelAutoDispByFilter(filtro, tipo);

        model.addAttribute("titolo", "Benvenuto in Rental Car");
        model.addAttribute("listaAuto", autoCercate);
        //model.addAttribute("IsUtenti", true);   //utile se si vuole usare la barra di ricerca per oiù tipologie di cose (utenti, auto, prenotazioni ecc)
        model.addAttribute("filter", filtro);   //fa vedere la stringa di ricerca nella input bar dopo aver effettuato la ricwerca

        String userLoggedCF = getLoggedUser();
        Utente user = utenteService.SelUtenteByCodF(userLoggedCF);
        model.addAttribute("Utente", user);

        return "index";
    }


    //ricerca dell'auto specifica sulla base delle date selezionate (l'utente può filtrare le auto disponibili per vedere solo quelle di suo interesse)
    @GetMapping("/searchDateDisp")
    public String SearchDateDisp(@RequestParam("filter") String filter, @RequestParam("tipo") String tipo, @RequestParam("dataInizio") String dataI, @RequestParam("dataFine") String dataF, Model model){

        LocalDate dataInizio = LocalDate.parse(dataI);
        LocalDate dataFine = LocalDate.parse(dataF);

        List searchDisp = autoService.SearchDateDisp(dataInizio, dataFine, filter, tipo);
        model.addAttribute("listaAuto", searchDisp);
        //servono per effettuare la ricerca del modello specifico ed evitano problemi in caso di ricerca consecutiva
        model.addAttribute("dataInizio", dataInizio);
        model.addAttribute("dataFine", dataFine);

        String userLoggedCF = getLoggedUser();
        Utente user = utenteService.SelUtenteByCodF(userLoggedCF);
        model.addAttribute("Utente", user);

        return "showDisp";
    }
}
