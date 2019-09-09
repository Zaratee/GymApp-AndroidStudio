package com.example.carloz.gymapp.items;

public class ItemAlimentoEditar {
    String Nombre,TipoAlimento, Marca, Tiempo, Cantidad,CantidadTipo, ID, CantidadAsignada, Lista;

    public ItemAlimentoEditar(String nombre, String tipoAlimento, String marca, String tiempo, String cantidad, String cantidadTipo, String ID, String cantidadAsignada, String lista) {
        Nombre = nombre;
        TipoAlimento = tipoAlimento;
        Marca = marca;
        Tiempo = tiempo;
        Cantidad = cantidad;
        CantidadTipo = cantidadTipo;
        this.ID = ID;
        CantidadAsignada = cantidadAsignada;
        Lista = lista;
    }

    public String getLista() {
        return Lista;
    }

    public void setLista(String lista) {
        Lista = lista;
    }

    public String getCantidadAsignada() {
        return CantidadAsignada;
    }

    public void setCantidadAsignada(String cantidadAsignada) {
        CantidadAsignada = cantidadAsignada;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipoAlimento() {
        return TipoAlimento;
    }

    public void setTipoAlimento(String tipoAlimento) {
        TipoAlimento = tipoAlimento;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getTiempo() {
        return Tiempo;
    }

    public void setTiempo(String tiempo) {
        Tiempo = tiempo;
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
}
