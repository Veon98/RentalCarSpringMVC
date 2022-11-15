package com.example.progetto1.dto;

import com.example.progetto1.entities.Auto;
import com.example.progetto1.entities.Prenotazione;
import com.example.progetto1.entities.Utente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//converte da entità a dto per evitare problemi nel trasferimento dei dati da controller a view
public class InvertedMapper {

    public static void fromEntitytoDto(PrenotazioneDto prenotazioneDto, Integer userID, Integer carID, Prenotazione pren) {
        prenotazioneDto.setApprovata(pren.getApprovata());
        prenotazioneDto.setDataFine(pren.getDataFine().toString());  //le localdate vengono convertite in stringhe per mantere la coerenza col mapper
        prenotazioneDto.setDataInizio(pren.getDataInizio().toString());
        prenotazioneDto.setDataPren(pren.getDataPren().toString());
        prenotazioneDto.setIdAuto((long) pren.getAuto().getIdAuto());
        prenotazioneDto.setIdUtente((long) pren.getAuto().getIdAuto());
    }

}
