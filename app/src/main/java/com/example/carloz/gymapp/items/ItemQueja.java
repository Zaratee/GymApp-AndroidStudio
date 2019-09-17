package com.example.carloz.gymapp.items;

public class ItemQueja {
    String Queja,Estado, id;

    public ItemQueja(String queja, String estado, String id) {
        Queja = queja;
        Estado = estado;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
