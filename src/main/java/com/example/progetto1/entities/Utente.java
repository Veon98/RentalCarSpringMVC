package com.example.progetto1.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "utente")
public class Utente implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)  //generazione chiave primaria automatica
    @Column(name="idUtente")
    @NotNull
    //@Size(min=10, max=10)
    protected int idUtente;


    @Column(name = "CodiceFiscale")
    @NotNull(message = "Campo obbligatorio")
    //@Size(min=15, max=15, message = "Inserire i 15 caratteri del Codice Fiscale")
    protected String codFiscale;


    @Column(name = "Pwd")
    @NotNull(message = "Campo obbligatorio")
    //@Size(min=5, max=100, message = "Inserisci tra i 5 e i 100 caratteri")
    private String pwd;


    @Column(name = "Nome")
    @NotNull(message = "Campo obbligatorio")
    //@Size(min=2, max=50, message = "Inserisci tra i 5 e i 100 caratteri")
    private String nome;


    @Column(name = "Cognome")
    @NotNull(message = "Campo obbligatorio")
    //@Size(min=2, max=50, message = "Inserisci tra i 5 e i 100 caratteri")
    private String cognome;


    @Column(name = "Ruolo")
    @NotNull(message = "Campo obbligatorio")
    //@Size(min=1, max=1)
    private boolean isAdmin;  //nel form (visibile solo all'admin) si potrà scegliere tra admin e customer. Al db verrà passato 1 per l'admin e 0 per il customer



    @OneToMany(fetch = FetchType.EAGER, mappedBy = "utente")    //utente è la proprietà che referenzia l'entità utente nell'entità prenotazione
    private Set<Prenotazione> prenotazioniUtente = new HashSet<>();


    public Utente(){}

    public Utente(String codFiscale, String pwd, String nome, String cognome, boolean isAdmin) {
        this.codFiscale = codFiscale;
        this.pwd = pwd;
        this.nome = nome;
        this.cognome = cognome;
        this.isAdmin = isAdmin;   //solo l'admin può creare utenti, di conseguenza sarà lui a poter scegliere il ruolo del nuovo utente
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Set<Prenotazione> getPrenotazioniUtente() {
        return prenotazioniUtente;
    }

    public void setPrenotazioniUtente(Set<Prenotazione> prenotazioniUtente) {
        this.prenotazioniUtente = prenotazioniUtente;
    }


    /*@Override
    public int hashCode() {
        return Objects.hash(getIdUtente(), getNome(), getCognome(), getCodFiscale(), getPwd());
    }*/
}

