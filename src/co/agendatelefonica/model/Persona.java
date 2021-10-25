/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.agendatelefonica.model;

import java.io.Serializable;

/**
 *
 * @author MIGUEL
 */
public class Persona implements Serializable {
    
    private static final long serialVersionUID = 3191818431124702061L;;
    
    private String nombre;
    private String apellido;

    public Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "{" + "nombre=" + nombre + ", apellido=" + apellido + '}';
    }
    
    
    
}
