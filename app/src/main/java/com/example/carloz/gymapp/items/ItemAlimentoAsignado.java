package com.example.carloz.gymapp.items;

public class ItemAlimentoAsignado {
    String Nombre,Marca,Cantidad,Tiempo,CantidadTipo,Hora;

    public ItemAlimentoAsignado(String nombre, String marca, String cantidad, String tiempo, String cantidadTipo, String hora) {
        Nombre = nombre;
        Marca = marca;
        Cantidad = cantidad;
        Tiempo = tiempo;
        CantidadTipo = cantidadTipo;
        Hora = hora;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
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

    public String getCantidadTipo() {
        return CantidadTipo;
    }

    public void setCantidadTipo(String cantidadTipo) {
        CantidadTipo = cantidadTipo;
    }
}
