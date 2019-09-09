package com.example.carloz.gymapp.items;

public class ItemQueja {
    String Queja,Estado;

    public ItemQueja(String queja, String estado) {
        Queja = queja;
        Estado = estado;
    }

    public String getQueja() {
        return Queja;
    }

    public void setQueja(String queja) {
        Queja = queja;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
