package com.example.progetto1.dao;


import com.example.progetto1.entities.Auto;
import com.example.progetto1.entities.Prenotazione;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//a contatto col db, vengono effettuate le query e si passano i dati al controller attraverso il service
@Repository
public class AutoDao {

    @PersistenceContext
    EntityManager em;



    //RECUPERA TUTTE LE AUTO
    public List<Auto> selCars() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Auto> qu = cb.createQuery(Auto.class);
        Root<Auto> root = qu.from(Auto.class);
        qu.select(root);
        qu.orderBy(cb.desc(root.get("idAuto")));
        List<Auto> results = em.createQuery(qu).getResultList();
        em.clear();

        return results;
    }



    //RECUPERA LA SINGOLA AUTO
    public  Auto getCar(Integer id) {

        return  em.find(Auto.class, id);
    }


    //RECUPERA AUTO DISPONIBILI
    public List<Auto> selCarsDisp() {
        List <Auto> results = null;

        String qu = "SELECT a FROM Auto a WHERE a.isDisponibile = true ORDER BY a.idAuto desc";

        results = em.createQuery(qu).getResultList();

        return results;
    }



    //INSERISCE L'AUTO NEL DB
    public void saveCar(Auto car) {

        em.persist(car);

        flushAndClear();
    }


    //AGGIORNA L'AUTO
    public void updateCar(Auto car) {

        em.merge(car);

        flushAndClear();
    }


    //ELIMINA L'AUTO
    public void deleteCar(Integer id) {
        Auto car;
        car =  em.find(Auto.class, id);
        em.remove(car);

        flushAndClear();
    }


    //SELEZIONE ATTRAVERSO FILTRO
    public List <Auto> selCarsByFilter(String filtro, String tipo){

        List<Auto> results = null;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Auto> qu = cb.createQuery(Auto.class);
        Root<Auto> root = qu.from(Auto.class);

        if (tipo.equals("targa")){
            qu.select(root);
            qu.where(cb.equal(root.get("targa"), filtro));
            results = em.createQuery(qu).getResultList();
            em.clear();
        } else if (tipo.equals("marca")) {
            qu.select(root);
            qu.where(cb.equal(root.get("marca"), filtro));
            results = em.createQuery(qu).getResultList();
            em.clear();
        } else if (tipo.equals("modello")) {
            qu.select(root);
            qu.where(cb.equal(root.get("modello"), filtro));
            results = em.createQuery(qu).getResultList();
            em.clear();
        }

        return results;
    }



    //SELEZIONE DISPONIBILI ATTRAVERSO FILTRO (deprecata, ma utile per vedere le macchine disponibili a livello di eventuali rotture)
    public List <Auto> selCarsDispByFilter(String filtro, String tipo){

        List <Auto> results = null;

        if (tipo.equals("marca")){
            String qu = "SELECT a FROM Auto a WHERE a.marca = :filtro and a.isDisponibile = true ORDER BY a.idAuto desc";
            results = em.createQuery(qu).setParameter("filtro", filtro).getResultList();
            em.clear();
        } else if (tipo.equals("modello")) {
            String qu = "SELECT a FROM Auto a WHERE a.modello = :filtro and a.isDisponibile = true ORDER BY a.idAuto desc";
            results = em.createQuery(qu).setParameter("filtro", filtro).getResultList();
            em.clear();
        }

        return results;
    }


    //RICERCA SULLA BASE DELLA DATA INSERITA DALL'UTENTE
    public List<Auto> SelDisponibili(LocalDate dataInizio, LocalDate dataFine){
        List <Auto>list1 = null;

        String qu = "Select a from Auto a where a.idAuto not in (SELECT distinct p.auto FROM Prenotazione p WHERE (((p.dataInizio<=:dataInizio and p.dataFine>=:dataFine) or ((p.dataInizio between :dataInizio and :dataFine) and (p.dataFine between :dataInizio and :dataFine))) and (p.isApprovata=true))) and a.isDisponibile=true order by a.idAuto desc";
        list1 = em.createQuery(qu).setParameter("dataInizio", dataInizio).setParameter("dataFine", dataFine).getResultList();

        return list1;
    }



    //RICERCA L'AUTO SPECIFICA NELLA DATA SCELTA IN PRECEDENZA SULLA BASE DEL FILTRO INSERITO
    public List<Auto> SearchDateDisp(LocalDate dataInizio, LocalDate dataFine, String filtro, String tipo){
        List <Auto>list1 = null;
        if (tipo.equals("marca")) {
            String qu = "Select a from Auto a where a.idAuto not in (SELECT distinct p.auto FROM Prenotazione p WHERE (((p.dataInizio<=:dataInizio and p.dataFine>=:dataFine) or ((p.dataInizio between :dataInizio and :dataFine) and (p.dataFine between :dataInizio and :dataFine))) and (p.isApprovata=true))) and a.isDisponibile=true and a.marca=:filtro order by a.idAuto desc";
            list1 = em.createQuery(qu).setParameter("dataInizio", dataInizio).setParameter("dataFine", dataFine).setParameter("filtro", filtro).getResultList();
        } else if (tipo.equals("modello")) {
            String qu = "Select a from Auto a where a.idAuto not in (SELECT distinct p.auto FROM Prenotazione p WHERE (((p.dataInizio<=:dataInizio and p.dataFine>=:dataFine) or ((p.dataInizio between :dataInizio and :dataFine) and (p.dataFine between :dataInizio and :dataFine))) and (p.isApprovata=true))) and a.isDisponibile=true and a.modello=:filtro order by a.idAuto desc";
            list1 = em.createQuery(qu).setParameter("dataInizio", dataInizio).setParameter("dataFine", dataFine).setParameter("filtro", filtro).getResultList();
        }

        return list1;
    }



    //riguarda il legame tra transazioni e cache a livello prestazionale
    private void flushAndClear()
    {
        em.flush();
        em.clear();
    }

}



