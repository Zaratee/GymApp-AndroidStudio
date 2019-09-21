package com.example.carloz.gymapp.items;

public class ItemListaQuejasAdmin {

    private String Nombre,Registro,Apellido,Queja,Estado;

    public ItemListaQuejasAdmin(String nombre, String registro, String apellido, String queja, String estado) {
        Nombre = nombre;
        Registro = registro;
        Apellido = apellido;
        Queja = queja;
        Estado = estado;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getRegistro() {
        return Registro;
    }

    public String getApellido() {
        return Apellido;
    }

    public String getQueja() {
        return Queja;
    }
}
