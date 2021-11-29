package com.elefante.app_saude.measurement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VaccineItem {
    public String name;
    public Date date;

    @Override
    public String toString() {
        return "Vacina " + name + "\nRealizada em: " + new SimpleDateFormat("dd/MM/yyyy hh:mm").format(date);
    }
}
