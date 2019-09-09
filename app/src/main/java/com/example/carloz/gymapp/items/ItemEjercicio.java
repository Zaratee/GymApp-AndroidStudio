package com.example.carloz.gymapp.items;

public class ItemEjercicio {
    String Nombre, id, registro,dia;

    public ItemEjercicio(String nombre, String id, String registro, String dia) {
        Nombre = nombre;
        this.id = id;
        this.registro = registro;
        this.dia = dia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
