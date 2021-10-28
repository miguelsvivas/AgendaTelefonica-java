/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.agendatelefonica.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author MIGUEL
 */
public class AgendaTelefonica implements Serializable {

    private static final long serialVersionUID = 3270647972060179935L;

    public List<Contacto> contactos = new LinkedList<>();

    public AgendaTelefonica() {
    }

    public void add(Contacto contacto) {
        this.contactos.add(contacto);
    }
    
    public List<Contacto> getContactos(){
        return this.contactos;
    }
    
        /**
     * Elimina contactos de la agenda.
     *
     * @param contacto contacto a eliminar
     */
    public void remove(Contacto contacto) {
        this.contactos.remove(contacto);
    }

}
