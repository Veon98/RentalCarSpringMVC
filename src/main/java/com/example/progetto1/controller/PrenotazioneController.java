package com.example.progetto1.controller;

import com.example.progetto1.dto.InvertedMapper;
import com.example.progetto1.entities.Auto;
import com.example.progetto1.entities.Prenotazione;
import com.example.progetto1.entities.Utente;
import com.example.progetto1.services.AutoService;
import com.example.progetto1.services.PrenotazioneService;
import com.example.progetto1.services.UtenteService;
import com.example.progetto1.dto.PrenotazioneDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import static com.example.progetto1.others.OtherMethods.getLoggedUser;
import static java.time.temporal.ChronoUnit.DAYS;

//a contatto con la view, riceve i dati dal dao passando per il service
@Controller
@RequestMapping("/prenotazione")   //path generale di questo controller, gli si devono concatenare i path dei metodi sottostanti: http://localhost:8080/progetto1_war_exploded/utente
public class PrenotazioneController {

    private static final Logger LOGGER = Logger.getLogger("Customer detail service: ");

    //code injection (interazione tra Beans)
    final
    AutoService autoService;

    final
    UtenteService utenteService;

    final
    PrenotazioneService prenotazioneService;

    public PrenotazioneController(AutoService autoService, UtenteService utenteService, PrenotazioneService prenotazioneService) {
        this.autoService = autoService;
        this.utenteService = utenteService;
        this.prenotazioneService = prenotazioneService;
    }


    //SELECT
    @GetMapping("/showprens")
    private String getAllPren(Model model) {

        String userLoggedCF = getLoggedUser();
        Utente user = utenteService.SelUtenteByCodF(userLoggedCF);
        Boolean isAdmin = user.isAdmin();
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("Utente", user);

        if (isAdmin==true){
            List<Prenotazione> listaPrens = prenotazioneService.selPrens();
            model.addAttribute("listaPrens", listaPrens);
        } else if (isAdmin==false) {
            List<Prenotazione> listaPrens = prenotazioneService.selPrensUser(user);
            model.addAttribute("listaPrens", listaPrens);
        }

        return "showPren";
    }



    //METODI GET E POST INSERT
    @GetMapping("/insprenotazione")
    public String insPren(@RequestParam Integer idAuto, @RequestParam String dataInizio, @RequestParam String dataFine, Model model) {   //qui creiamo l'oggetto per fare il binding col form e apriamo il form stesso (come si vede dal return). Inoltre se c'è bisogno di pre-compilare il form ciò viene fatto qui: basta recuperare i valori da service e dao e poi passarli con l'addAttribute e recuperarli nel jsp attraverso jstl

        Auto car = autoService.selAuto(idAuto);
        model.addAttribute("Auto", car);

        String userLoggedCF = getLoggedUser();
        Utente user = utenteService.SelUtenteByCodF(userLoggedCF);
        model.addAttribute("Utente", user);

        LocalDate oggi = LocalDate.now();
        String dataPren = oggi.toString();
        //model.addAttribute("dataPren", oggi);

        PrenotazioneDto pren = new PrenotazioneDto();
        pren.setDataInizio(dataInizio);
        pren.setDataFine(dataFine);
        pren.setDataPren(dataPren);
        pren.setIdAuto((long) car.getIdAuto());
        pren.setIdUtente((long) user.getIdUtente());
        model.addAttribute("PrenotazioneDto", pren);  //data bindng (attributeModel del form uguale ad attributeName)

        return "formPrenotazione";  //pagina del form
    }

    @PostMapping("/insprenotazione")  //qui vengono recuperati i dati inseriti nel form e vengono passati allo stato di servizio
    public String doInsPren(@ModelAttribute("PrenotazioneDto") PrenotazioneDto prenDto) {  //anche qui viene effettuato il data binding, per popolare l'oggetto car

        prenotazioneService.insPren(prenDto);

        return "redirect: ../prenotazione/showprens";
    }



    //METODO DELETE
    @GetMapping("/elimina/{idPren}")       //http://localhost:8080/progetto1_war_exploded/auto/elimina/idAutospecifico
    private String deletePren(@PathVariable("idPren") Integer idPren, Model model) {   //rintraccia l'auto da eliminare attraverso l'id recuperata dall'url

        Prenotazione pren = prenotazioneService.selPren(idPren);
        LocalDate inizio = pren.getDataInizio();
        LocalDate oggi = LocalDate.now();
        long check = DAYS.between(oggi, inizio);


        if (check>=2) {
            prenotazioneService.elimPren(idPren);
            return "redirect:/prenotazione/showprens";
        }
        else
            return "Error";

    }



    //METODI GET E POST UPLOAD
    @GetMapping("/modifica/{idPren}")
    public String modPren(@PathVariable("idPren") Integer idPren, Model model) {    //recupera l'id utente dall'url

        Prenotazione pren = prenotazioneService.selPren(idPren); //seleziona l'auto che si vuole modificare attraverso l'id
        Integer idAuto = pren.getAuto().getIdAuto();
        Integer idUtente = pren.getUtente().getIdUtente();

        Auto car = autoService.selAuto(idAuto);
        model.addAttribute("Auto", car);
        Utente user = utenteService.selUtente(idUtente);
        model.addAttribute("Utente", user);

        PrenotazioneDto prenDto = new PrenotazioneDto();
        InvertedMapper.fromEntitytoDto(prenDto, idAuto, idUtente, pren);

        model.addAttribute("PrenotazioneDto", prenDto);   //dati che pre-compileranno il form

        Integer check = new Integer(pren.getIdPren());   //serve per il controllo sul form e capire se è un insert o un update
        model.addAttribute("check", check);

        String userLoggedCF = getLoggedUser();
        Utente userLog = utenteService.SelUtenteByCodF(userLoggedCF);
        model.addAttribute("UtenteLog", userLog);

        return "formPrenotazione";    //pagina del form
    }

    @PostMapping("/modifica/{idPren}")
    public String doModPren(@Valid @ModelAttribute("Prenotazione") PrenotazioneDto prenDto) {    //si ricevono i dati dal form (data binding)

        Integer idPren = Math.toIntExact(prenDto.getIdPren());
        prenotazioneService.updPren(prenDto, idPren);

        return "redirect:/prenotazione/showprens";
    }



    //SEARCH
    @GetMapping(value = "/search")
    public String SearchCar(@RequestParam("filter") String filtro, @RequestParam("tipo") String tipo, Model model) {

        String userLoggedCF = getLoggedUser();
        Utente user = utenteService.SelUtenteByCodF(userLoggedCF);

        List<Prenotazione> prenCercate = null;

        if (user.isAdmin()==true){
             prenCercate = prenotazioneService.SelPrensByFilter(filtro, tipo);
        } else if (user.isAdmin()==false) {
            prenCercate = prenotazioneService.SelPrensByFilterUtente(filtro, tipo, user);
        }

        model.addAttribute("listaPrens", prenCercate);
        model.addAttribute("filter", filtro);   //fa vedere la stringa di ricerca nella input bar dopo aver effettuato la ricwerca
        model.addAttribute("Utente", user);

        return "showPren";
    }

}

