package com.example.progetto1.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "prenotazione")
public class Prenotazione implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)  //generazione chiave primaria automatica
    @Column(name="idPren")
    @NotNull
    //@Size(min=10, max=10)
    protected int idPren;


    @Column(name = "DataInizio")
    @NotNull(message = "Campo obbligatorio")
    private LocalDate dataInizio;


    @Column(name = "DataFine")
    @NotNull(message = "Campo obbligatorio")
    private LocalDate dataFine;


    @Column(name = "DataPrenotazione")
    @NotNull(message = "Campo obbligatorio")
    private LocalDate dataPren;


    @Column(name = "Approvazione")
    @NotNull(message = "Campo obbligatorio")
    //@Size(min=1, max=1)
    private Boolean isApprovata;



    @ManyToOne
    @JoinColumn(name = "idAuto", referencedColumnName = "idAuto")  //foreign key
    private Auto auto;  //proprità che mappa la relazione con l'entità auto


    @ManyToOne
    @JoinColumn(name = "idUtente", referencedColumnName = "idUtente")  //foreign key
    private Utente utente;   //proprità che mappa la relazione con l'entità utente


    public Prenotazione(){}

    public Prenotazione(LocalDate dataInizio, LocalDate dataFine, LocalDate dataPren) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.dataPren = dataPren;
    }

    public int getIdPren() {
        return idPren;
    }

    public void setIdPren(int idPren) {
        this.idPren = idPren;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate  getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public LocalDate getDataPren() {
        return dataPren;
    }

    public void setDataPren(LocalDate dataPren) {
        this.dataPren = dataPren;
    }

    public Boolean getApprovata() {
        return isApprovata;
    }

    public void setApprovata(Boolean approvata) {
        isApprovata = approvata;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

}


