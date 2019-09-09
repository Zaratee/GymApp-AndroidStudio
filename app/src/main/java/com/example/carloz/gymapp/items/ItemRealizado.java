package com.example.carloz.gymapp.items;

public class ItemRealizado {
    String Nombre, Fecha, Repeticiones, Realizado;

    public ItemRealizado(String nombre, String fecha, String repeticiones, String realizado) {
        Nombre = nombre;
        Fecha = fecha;
        Repeticiones = repeticiones;
        Realizado = realizado;
    }

    public String getRealizado() {
        return Realizado;
    }

    public void setRealizado(String realizado) {
        Realizado = realizado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getRepeticiones() {
        return Repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        Repeticiones = repeticiones;
    }
}
