package com.example.progetto1.dao;


import com.example.progetto1.entities.Utente;
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
import javax.persistence.criteria.Root;
import java.util.List;

//a contatto col db, vengono effettuate le query e si passano i dati al controller attraverso il service
@Repository
public class UtenteDao {

    @PersistenceContext
    EntityManager em;



    //RECUPERA TUTTI GLI UTENTI
    public List<Utente> selUsers() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Utente> qu = cb.createQuery(Utente.class);
        Root<Utente> root = qu.from(Utente.class);   //root di base da cui effettuare la select
        qu.select(root);
        qu.orderBy(cb.desc(root.get("idUtente")));
        List<Utente> results = em.createQuery(qu).getResultList();
        em.clear();

        return results;
    }
    //RECUPERA TUTTI GLI UTENTI MA CON JPQL
    /*public List <Utente> selUsers() {

        String qu = "SELECT u.codFiscale, u.nome, u.cognome FROM Utente u order by u.idUtente desc";
        List<Utente> result = em.createQuery(qu).getResultList();

        return result;
    }*/


    //RECUPERA IL SINGOLO UTENTE
    public  Utente getUser(Integer id) {

        return  em.find(Utente.class, id);
    }


    //INSERISCE L'UTENTE NEL DB
    public void saveUser(Utente user) {

        em.persist(user);

        flushAndClear();
    }


    //AGGIORNA L'UTENTE
    public void updateUser(Utente user) {

        em.merge(user);

        flushAndClear();
    }


    //ELIMINA L'UTENTE
    public void deleteUser(Integer id) {
        Utente user;
        user =  em.find(Utente.class, id);
        em.remove(user);

        flushAndClear();
    }


    //Selezione attraverso filtro
    public List <Utente> selUsersByFilter(String filtro, String tipo){

        List<Utente> results = null;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Utente> qu = cb.createQuery(Utente.class);
        Root<Utente> root = qu.from(Utente.class);

        if (tipo.equals("codFiscale")){
            qu.select(root);
            qu.where(cb.equal(root.get("codFiscale"), filtro));
            results = em.createQuery(qu).getResultList();
            em.clear();
        } else if (tipo.equals("nome")) {
            qu.select(root);
            qu.where(cb.equal(root.get("nome"), filtro));
            results = em.createQuery(qu).getResultList();
            em.clear();
        } else if (tipo.equals("cognome")) {
            qu.select(root);
            qu.where(cb.equal(root.get("cognome"), filtro));
            results = em.createQuery(qu).getResultList();
            em.clear();
        }

        return results;
    }


    //RECUPERA IL SINGOLO UTENTE ATTRAVERSO IL CODICE FISCALE
    public  Utente getUserByCodF(String codFiscale) {

        //return  em.find(Utente.class, codFiscale);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Utente> qu = cb.createQuery(Utente.class);
        Root<Utente> root = qu.from(Utente.class);
        qu.select(root);
        qu.where(cb.equal(root.get("codFiscale"), codFiscale));
        Utente result = em.createQuery(qu).getSingleResult();
        em.clear();

        return result;
    }


    //riguarda il legame tra transazioni e cache a livello prestazionale
    private void flushAndClear()   //consolidazione dati nel db
    {
        em.flush();
        em.clear();
    }

}
