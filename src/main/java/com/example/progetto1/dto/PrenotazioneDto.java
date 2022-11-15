package com.example.progetto1.dto;


//classe basata sull'entità Prenotazione. Viene sfruttata nel form e nel controller lì dove i dati vengono inseriti nel db o aggiornati nel db
public class PrenotazioneDto {

    private Long idPren;
    private Long idUtente;
    private Long idAuto;

    private String dataInizio;

    private String dataFine;

    private String dataPren;

    private Boolean isApprovata;




    public Long getIdPren() {
        return idPren;
    }

    public void setIdPren(Long idPren) {
        this.idPren = idPren;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public Long getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(Long idAuto) {
        this.idAuto = idAuto;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public void setDataFine(String dataFine) {
        this.dataFine = dataFine;
    }

    public String getDataPren() {
        return dataPren;
    }

    public void setDataPren(String dataPren) {
        this.dataPren = dataPren;
    }

    public Boolean getApprovata() {
        return isApprovata;
    }

    public void setApprovata(Boolean approvata) {
        isApprovata = approvata;
    }
}
