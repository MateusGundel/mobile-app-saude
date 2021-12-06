package com.elefante.app_saude.measurement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiabetesItem {
    public String valor;
    public Date date;
    public Integer id;

    @Override
    public String toString() {
        return "Valor " + valor + "\nRealizada em: " + new SimpleDateFormat("dd/MM/yyyy hh:mm").format(date);
    }
}
