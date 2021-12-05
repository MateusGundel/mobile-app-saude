package com.elefante.app_saude.utils;

public class ListItem {
    public int id;
    public String descricao;
    public String data;

    public ListItem(int id, String descricao, String data){
        this.descricao = descricao;
        this.data = data;
        this.id = id;
    }
}
