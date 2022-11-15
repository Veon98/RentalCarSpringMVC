package com.example.progetto1.dao;


import com.example.progetto1.entities.Auto;
import com.example.progetto1.entities.Prenotazione;
import com.example.progetto1.entities.Utente;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PrenotazioneDao {

    @PersistenceContext
    EntityManager em;



    //RECUPERA TUTTE LE PRENOTAZIONI
    public List<Prenotazione> selPrens() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Prenotazione> qu = cb.createQuery(Prenotazione.class);
        Root<Prenotazione> root = qu.from(Prenotazione.class);
        qu.select(root);
        qu.orderBy(cb.desc(root.get("idPren")));
        List<Prenotazione> results = em.createQuery(qu).getResultList();
        em.clear();

        return results;
    }


    //RECUPERA LE PRENOTAZIONI DELL'UTENTE LOGGATO
    public List<Prenotazione> selPrensUtente(Utente user) {

        String qu = "SELECT p FROM Prenotazione p WHERE p.utente = :filtro ORDER BY p.idPren desc";
        List <Prenotazione>results = em.createQuery(qu).setParameter("filtro", user).getResultList();
        em.clear();

        return results;
    }


    //RECUPERA LA SINGOLA PRENOTAZIONE
    public  Prenotazione getPren(Integer id) {

        return  em.find(Prenotazione.class, id);
    }


    //INSERISCE LA PRENOTAZIONE NEL DB
    public void savePren(Prenotazione pren) {

        em.persist(pren);

        flushAndClear();
    }


    //AGGIORNA LA PRENOTAZIONE
    public void updatePren(Prenotazione pren) {

        em.merge(pren);

        /*String qu = "UPDATE Prenotazione p SET p.isApprovata = '1' WHERE p.idPren = :idPren";
        em.createQuery(qu)
                .setParameter("idPren", idPren);*/

        flushAndClear();
    }


    //ELIMINA LA PRENOTAZIONE
    public void deletePren(Integer id) {
        Prenotazione pren;
        pren =  em.find(Prenotazione.class, id);
        em.remove(pren);

        flushAndClear();
    }



    //SELEZIONE ATTRAVERSO FILTRO
    public List <Prenotazione> selPrensByFilter(String filtro, String tipo){

        List <Prenotazione> results = null;

        if (tipo.equals("codFiscale")){
            String qu = "SELECT p FROM Prenotazione p WHERE p.utente.codFiscale = :filtro ORDER BY p.idPren desc";
            results = em.createQuery(qu).setParameter("filtro", filtro).getResultList();
        } else if (tipo.equals("targa")) {
            String qu = "SELECT p FROM Prenotazione p WHERE p.auto.targa = :filtro ORDER BY p.idPren desc";
            results = em.createQuery(qu).setParameter("filtro", filtro).getResultList();
        } else if (tipo.equals("dataInizio")) {
            LocalDate filter = LocalDate.parse(filtro);
            String qu = "SELECT p FROM Prenotazione p WHERE p.dataInizio = :filtro ORDER BY p.idPren desc";
            results = em.createQuery(qu).setParameter("filtro", filter).getResultList();
        } else if (tipo.equals("dataFine")) {
            LocalDate filter = LocalDate.parse(filtro);
            String qu = "SELECT p FROM Prenotazione p WHERE p.dataFine = :filtro ORDER BY p.idPren desc";
            results = em.createQuery(qu).setParameter("filtro", filter).getResultList();
        } else if (tipo.equals("dataPren")) {
            LocalDate filter = LocalDate.parse(filtro);
            String qu = "SELECT p FROM Prenotazione p WHERE p.dataPren = :filtro ORDER BY p.idPren desc";
            results = em.createQuery(qu).setParameter("filtro", filter).getResultList();
        }

        return results;
    }


    //SELEZIONE ATTRAVERSO FILTRO PER L'UTENTE LOGGATO
    public List <Prenotazione> selPrensByFilterUtente(String filtro, String tipo, Utente user){
        List <Prenotazione> results = null;

        if (tipo.equals("codFiscale")){
            String qu = "SELECT p FROM Prenotazione p WHERE p.utente = :user and p.utente.codFiscale = :filtro ORDER BY p.idPren desc";
            results = em.createQuery(qu).setParameter("user", user).setParameter("filtro", filtro).getResultList();
        } else if (tipo.equals("targa")) {
            String qu = "SELECT p FROM Prenotazione p WHERE p.utente = :user and p.auto.targa = :filtro ORDER BY p.idPren desc";
            results = em.createQuery(qu).setParameter("user", user).setParameter("filtro", filtro).getResultList();
        } else if (tipo.equals("dataInizio")) {
            LocalDate filter = LocalDate.parse(filtro);
            String qu = "SELECT p FROM Prenotazione p WHERE p.utente = :user and p.dataInizio = :filtro ORDER BY p.idPren desc";
            results = em.createQuery(qu).setParameter("user", user).setParameter("filtro", filter).getResultList();
        } else if (tipo.equals("dataFine")) {
            LocalDate filter = LocalDate.parse(filtro);
            String qu = "SELECT p FROM Prenotazione p WHERE p.utente = :user and  p.dataFine = :filtro ORDER BY p.idPren desc";
            results = em.createQuery(qu).setParameter("user", user).setParameter("filtro", filter).getResultList();
        } else if (tipo.equals("dataPren")) {
            LocalDate filter = LocalDate.parse(filtro);
            String qu = "SELECT p FROM Prenotazione p WHERE p.utente = :user and  p.dataPren = :filtro ORDER BY p.idPren desc";
            results = em.createQuery(qu).setParameter("user", user).setParameter("filtro", filter).getResultList();
        }

        return results;
    }


    //riguarda il legame tra transazioni e cache a livello prestazionale
    private void flushAndClear()
    {
        em.flush();
        em.clear();
    }
}
