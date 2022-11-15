package com.example.progetto1.services;

import com.example.progetto1.dao.AutoDao;
import com.example.progetto1.entities.Auto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

//richiamato dal controller, richiama il dao
@Service
@Transactional   //permette il rollback delle transazioni (un fallimento nella transazione porta all'annullamento di tutte le operazioni svolte fino a quel punto della transazione
public class AutoService {

    //code injection
    private final AutoDao autoDao;
    public AutoService(AutoDao autoDao) {
        this.autoDao = autoDao;
    }


    public List <Auto> selAutos() {
        return autoDao.selCars();
    }


    public Auto selAuto(Integer idAuto){
        return autoDao.getCar(idAuto);
    }


    public List <Auto> selAutosDisp() {
        return autoDao.selCarsDisp();
    }


    public void insAuto(Auto car) {
        autoDao.saveCar(car);
    }


    public void elimAuto(Integer idAuto) {
        autoDao.deleteCar(idAuto);
    }


    public void updAuto(Auto car){
        autoDao.updateCar(car);
    }


    public List <Auto> SelAutoByFilter(String filtro, String tipo){
        return autoDao.selCarsByFilter(filtro, tipo);
    }


    public List <Auto> SelAutoDispByFilter(String filtro, String tipo){
        return autoDao.selCarsDispByFilter(filtro, tipo);
    }

    public List<Auto> SelAutoDisponibili(LocalDate dataInizio, LocalDate dataFine){
        return autoDao.SelDisponibili(dataInizio, dataFine);
    }


    public  List<Auto> SearchDateDisp(LocalDate dataInizio, LocalDate dataFine, String filter, String tipo){
        return autoDao.SearchDateDisp(dataInizio, dataFine, filter, tipo);
    }


}
