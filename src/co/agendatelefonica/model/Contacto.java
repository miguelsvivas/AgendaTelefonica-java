/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.agendatelefonica.model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author MIGUEL
 */
public class Contacto implements Serializable {

    private static final long serialVersionUID = 1384734519601452697L;

    private Persona persona;
    private LinkedList<Telefono> telefonos = new LinkedList<Telefono>();

    public Contacto(Persona persona) {
        this.persona = persona;
    }

    public Persona getPersona() {
        return persona;
    }

    public void add(Telefono tel) {
        this.telefonos.add(tel);
    }

    public void remove(Telefono tel) {
        this.telefonos.remove(tel);
    }

    public LinkedList<Telefono> getTelefonos() {
        return telefonos;
    }

    @Override
    public String toString() {
        return persona.toString();
    }

}
