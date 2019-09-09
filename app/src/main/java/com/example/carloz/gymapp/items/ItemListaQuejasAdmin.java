package com.example.carloz.gymapp.items;

public class ItemListaQuejasAdmin {

    private String Nombre,Registro,Apellido,Queja;

    public ItemListaQuejasAdmin(String nombre, String registro, String apellido, String queja) {
        Nombre = nombre;
        Registro = registro;
        Apellido = apellido;
        Queja = queja;
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
