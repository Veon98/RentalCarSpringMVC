package com.example.progetto1.dto;

import com.example.progetto1.entities.Auto;
import com.example.progetto1.entities.Prenotazione;
import com.example.progetto1.entities.Utente;

import java.time.LocalDate;

//mappa i dati per evitare problemi nella fase di orm
public class PrenotazioneMapper {
    public static void fromDtoToEntity(PrenotazioneDto prenotazioneDto, Utente user, Auto car, Prenotazione pren) {
        pren.setApprovata(prenotazioneDto.getApprovata());
        pren.setDataFine(LocalDate.parse(prenotazioneDto.getDataFine()));  //le date vengono ricevute come stringhe e convertite in localdate
        pren.setDataInizio(LocalDate.parse(prenotazioneDto.getDataInizio()));
        pren.setDataPren(LocalDate.parse(prenotazioneDto.getDataPren()));
        pren.setUtente(user);
        pren.setAuto(car);
    }
}
