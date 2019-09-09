package com.example.carloz.gymapp.items;

public class ItemAlimentoBuscado {

    private String Nombre,Marca,Cantidad,Tiempo;
    private String Id;

    public ItemAlimentoBuscado(String nombre, String marca, String cantidad, String tiempo, String id) {
        Nombre = nombre;
        Marca = marca;
        Cantidad = cantidad;
        Tiempo = tiempo;
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getTiempo() {
        return Tiempo;
    }

    public void setTiempo(String tiempo) {
        Tiempo = tiempo;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
