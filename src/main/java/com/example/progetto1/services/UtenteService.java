package com.example.progetto1.services;

import com.example.progetto1.dao.UtenteDao;
import com.example.progetto1.entities.Utente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//richiamato dal controller, richiama il dao
@Service
@Transactional   //permette il rollback delle transazioni (un fallimento nella transazione porta all'annullamento di tutte le operazioni svolte fino a quel punto della transazione
public class UtenteService {

    //code injection
    private final UtenteDao utenteDao;
    public UtenteService(UtenteDao utenteDao) {
        this.utenteDao = utenteDao;
    }


    public List <Utente> selUtenti() {
        return utenteDao.selUsers();
    }


    public Utente selUtente(Integer idUtente){
        return utenteDao.getUser(idUtente);
    }


    public void insUtente(Utente user) {
        utenteDao.saveUser(user);
    }


    public void elimUtente(Integer idUtente) {
        utenteDao.deleteUser(idUtente);
    }


    public void updUtente(Utente user){
        utenteDao.updateUser(user);
    }


    public List <Utente> SelUtentiByFilter(String filtro, String tipo){
        return utenteDao.selUsersByFilter(filtro, tipo);
    }


    public Utente SelUtenteByCodF(String codFiscale){
        return  utenteDao.getUserByCodF(codFiscale);
    }
}
