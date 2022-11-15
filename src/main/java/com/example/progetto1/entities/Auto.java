package com.example.progetto1.entities;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "auto")
public class Auto implements Serializable {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)  //generazione chiave primaria automatica
    @Column(name="idAuto")
    @NotNull
    //@Size(min=10, max=10)
    protected int idAuto;

    @Column(name = "Targa")
    @NotNull(message = "Campo obbligatorio")
    //@Size(min=7, max=7, message = "Inserire i 7 caratteri della Targa")
    protected String targa;


    @Column(name = "Marca")
    @NotNull(message = "Campo obbligatorio")
    //@Size(min=5, max=100, message = "Inserisci tra i 5 e i 100 caratteri")
    private String marca;


    @Column(name = "Modello")
    @NotNull(message = "Campo obbligatorio")
    //@Size(min=2, max=50, message = "Inserisci tra i 5 e i 100 caratteri")
    private String modello;


    @Column(name = "Disponibilita")
    @NotNull(message = "Campo obbligatorio")
    private Boolean isDisponibile;   //l'auto potrebbe essere rotta(la disponibilità a livello di data viene gestita diversamente)



    @OneToMany(fetch = FetchType.EAGER, mappedBy = "auto")  //auto è la proprietà che referenzia l'entità auto nell'entità prenotazione
    private Set<Prenotazione> prenotazioniAuto = new HashSet<>();


    public Auto(){}

    public Auto(String targa, String marca, String modello, Boolean isDisponibile) {
        this.targa = targa;
        this.marca = marca;
        this.modello = modello;
        this.isDisponibile = isDisponibile;
    }

    public int getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public Boolean getDisponibile() {
        return isDisponibile;
    }

    public void setDisponibile(Boolean disponibile) {
        isDisponibile = disponibile;
    }

    public Set<Prenotazione> getPrenotazioniAuto() {
        return prenotazioniAuto;
    }

    public void setPrenotazioniAuto(Set<Prenotazione> prenotazioniAuto) {
        this.prenotazioniAuto = prenotazioniAuto;
    }
}