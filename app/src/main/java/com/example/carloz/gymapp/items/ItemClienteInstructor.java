package com.example.carloz.gymapp.items;

public class ItemClienteInstructor {

    private String Nombre,Registro,Apellido, estado;

    public ItemClienteInstructor(String nombre, String registro, String apellido, String estado) {
        Nombre = nombre;
        Registro = registro;
        Apellido = apellido;
        this.estado = estado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getRegistro() {
        return Registro;
    }

    public void setRegistro(String registro) {
        Registro = registro;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
