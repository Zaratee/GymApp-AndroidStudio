package com.example.carloz.gymapp.items;

public class ItemHistorial {
    String Nombre, Apellido,Registro, Telefono, Fecha, Estado;

    public ItemHistorial(String nombre, String apellido, String registro, String telefono, String fecha, String estado) {
        Nombre = nombre;
        Apellido = apellido;
        Registro = registro;
        Telefono = telefono;
        Fecha = fecha;
        Estado = estado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getRegistro() {
        return Registro;
    }

    public void setRegistro(String registro) {
        Registro = registro;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
