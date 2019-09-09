package com.example.carloz.gymapp.items;

public class ItemEjercicioCliente {
    String idRutina, Nombre, Aparato, Repeticiones, Series, Descanso, Polea;

    public ItemEjercicioCliente(String idRutina, String nombre, String aparato, String repeticiones, String series, String descanso, String polea) {
        this.idRutina = idRutina;
        Nombre = nombre;
        Aparato = aparato;
        Repeticiones = repeticiones;
        Series = series;
        Descanso = descanso;
        Polea = polea;
    }

    public String getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(String idRutina) {
        this.idRutina = idRutina;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getAparato() {
        return Aparato;
    }

    public void setAparato(String aparato) {
        Aparato = aparato;
    }

    public String getRepeticiones() {
        return Repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        Repeticiones = repeticiones;
    }

    public String getSeries() {
        return Series;
    }

    public void setSeries(String series) {
        Series = series;
    }

    public String getDescanso() {
        return Descanso;
    }

    public void setDescanso(String descanso) {
        Descanso = descanso;
    }

    public String getPolea() {
        return Polea;
    }

    public void setPolea(String polea) {
        Polea = polea;
    }
}
