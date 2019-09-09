package com.example.carloz.gymapp.items;

public class ItemEjercicioClienteAyuda {
    String Nombre, Peso, Repeticiones, Series, Estado, Area, id, Instructor;

    public ItemEjercicioClienteAyuda(String nombre, String peso, String repeticiones, String series, String estado, String area, String id, String instructor) {
        Nombre = nombre;
        Peso = peso;
        Repeticiones = repeticiones;
        Series = series;
        Estado = estado;
        Area = area;
        this.id = id;
        Instructor = instructor;
    }

    public String getInstructor() {
        return Instructor;
    }

    public void setInstructor(String instructor) {
        Instructor = instructor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPeso() {
        return Peso;
    }

    public void setPeso(String peso) {
        Peso = peso;
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

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }
}
