package com.example.carloz.gymapp.items;

public class ItemEjercicioSemana {
    String nombre, series,repeticiones,peso,dia;

    public ItemEjercicioSemana(String nombre, String series, String repeticiones, String peso, String dia) {
        this.nombre = nombre;
        this.series = series;
        this.repeticiones = repeticiones;
        this.peso = peso;
        this.dia = dia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
