package com.example.carloz.gymapp.items;

public class ItemEjercicioEditar {
    String Nombre,Area,Series,Repeticiones, ID, EjercicioID, Peso, Registro;

    public ItemEjercicioEditar(String nombre, String area, String series, String repeticiones, String ID, String ejercicioID, String peso, String registro) {
        Nombre = nombre;
        Area = area;
        Series = series;
        Repeticiones = repeticiones;
        this.ID = ID;
        EjercicioID = ejercicioID;
        Peso = peso;
        Registro = registro;
    }

    public String getRegistro() {
        return Registro;
    }

    public void setRegistro(String registro) {
        Registro = registro;
    }

    public String getPeso() {
        return Peso;
    }

    public void setPeso(String peso) {
        Peso = peso;
    }

    public String getEjercicioID() {
        return EjercicioID;
    }

    public void setEjercicioID(String ejercicioID) {
        EjercicioID = ejercicioID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getSeries() {
        return Series;
    }

    public void setSeries(String series) {
        Series = series;
    }

    public String getRepeticiones() {
        return Repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        Repeticiones = repeticiones;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
