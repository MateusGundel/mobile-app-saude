package com.elefante.app_saude.measurement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BloodPressureItem {
    public String sistolica, diastolica;
    public Date date;
    public Integer id;

    @Override
    public String toString() {
        return "Pressao sistolica " + sistolica + " diasolica: "+ diastolica +"\nRealizada em: " + new SimpleDateFormat("dd/MM/yyyy hh:mm").format(date);
    }
}
