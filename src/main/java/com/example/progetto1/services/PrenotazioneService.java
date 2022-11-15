package com.example.progetto1.services;

import com.example.progetto1.dao.AutoDao;
import com.example.progetto1.dao.PrenotazioneDao;
import com.example.progetto1.dao.UtenteDao;
import com.example.progetto1.entities.Auto;
import com.example.progetto1.entities.Prenotazione;
import com.example.progetto1.entities.Utente;
import com.example.progetto1.dto.PrenotazioneDto;
import com.example.progetto1.dto.PrenotazioneMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PrenotazioneService {

    final
    UtenteDao utenteDao;
    final
    AutoDao autoDao;
    private final PrenotazioneDao prenotazioneDao;
    public PrenotazioneService(PrenotazioneDao prenotazioneDao, AutoDao autoDao, UtenteDao utenteDao) {
        this.prenotazioneDao = prenotazioneDao;
        this.autoDao = autoDao;
        this.utenteDao = utenteDao;
    }


    public List<Prenotazione> selPrens() {
        return prenotazioneDao.selPrens();
    }


    public List<Prenotazione> selPrensUser(Utente user) {
        return prenotazioneDao.selPrensUtente(user);
    }


    public Prenotazione selPren(Integer idPren){
        return prenotazioneDao.getPren(idPren);
    }



    public void insPren(PrenotazioneDto prenDto) {
        Auto auto = autoDao.getCar(Math.toIntExact(prenDto.getIdAuto()));  //getIdAuto è del dto
        Utente utente = utenteDao.getUser(Math.toIntExact(prenDto.getIdUtente()));  //getIdUtente è del dto
        Prenotazione prenotazione = new Prenotazione();  //istanzio l'entità prenotazione

        PrenotazioneMapper.fromDtoToEntity(prenDto,utente,auto,prenotazione);   //converto il dto nell'entità usando il mapper

        prenotazioneDao.savePren(prenotazione);
    }


    public void elimPren(Integer idPren) {
        prenotazioneDao.deletePren(idPren);
    }


    //public void updPren(PrenotazioneDto pren){
    public  void updPren(PrenotazioneDto prenDto, Integer idPren){
        Auto auto = autoDao.getCar(Math.toIntExact(prenDto.getIdAuto()));  //getIdAuto è del dto
        Utente utente = utenteDao.getUser(Math.toIntExact(prenDto.getIdUtente()));  //getIdUtente è del dto
        Prenotazione prenotazione = prenotazioneDao.getPren(idPren);  //recupero la prenotazione da modificare

        PrenotazioneMapper.fromDtoToEntity(prenDto,utente,auto,prenotazione);   //converto il dto nell'entità usando il mapper

        prenotazioneDao.updatePren(prenotazione);
    }



    public List <Prenotazione> SelPrensByFilter(String filtro, String tipo){
        return prenotazioneDao.selPrensByFilter(filtro, tipo);
    }


    public List <Prenotazione> SelPrensByFilterUtente(String filtro, String tipo, Utente user){
        return prenotazioneDao.selPrensByFilterUtente(filtro, tipo, user);
    }

}
