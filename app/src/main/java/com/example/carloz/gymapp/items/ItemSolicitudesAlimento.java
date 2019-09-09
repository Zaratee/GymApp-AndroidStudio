package com.example.carloz.gymapp.items;

public class ItemSolicitudesAlimento {
    String Nombre, Marca, TipoAlimento, Cantidad, CantidadTipo, ID;

    public ItemSolicitudesAlimento(String nombre, String marca, String tipoAlimento, String cantidad, String cantidadTipo, String ID) {
        Nombre = nombre;
        Marca = marca;
        TipoAlimento = tipoAlimento;
        Cantidad = cantidad;
        CantidadTipo = cantidadTipo;
        this.ID = ID;
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

    public String getTipoAlimento() {
        return TipoAlimento;
    }

    public void setTipoAlimento(String tipoAlimento) {
        TipoAlimento = tipoAlimento;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getCantidadTipo() {
        return CantidadTipo;
    }

    public void setCantidadTipo(String cantidadTipo) {
        CantidadTipo = cantidadTipo;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}

