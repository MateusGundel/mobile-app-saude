package com.elefante.app_saude.measurement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HeightItem {
    public String valor;
    public Date date;
    public Integer id;

    @Override
    public String toString() {
        return "Altura " + valor + "\nLançado em: " + new SimpleDateFormat("dd/MM/yyyy hh:mm").format(date);
    }
}
