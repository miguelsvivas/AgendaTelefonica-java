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
public class Telefono implements Serializable{
    
    private static final long serialVersionUID = 8589921978773378446L;
    
    private String number;
    
    private TipoTelefono tipo;

    public Telefono(String number, TipoTelefono tipo) {
        this.number = number;
        this.tipo = tipo;
    }

    public String getNumber() {
        return number;
    }

    public TipoTelefono getTipo() {
        return tipo;
    }
    
    
    
    
    
}
