/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.agendatelefonica.model;

/**
 *
 * @author MIGUEL
 */
public enum TipoTelefono {
    
    INDEFINIDO, FIJO, MOVIL;

    @Override
    public String toString() {
        switch (this) {
            case INDEFINIDO : return "";
            case FIJO : return "Fijo";
            case MOVIL : return "Móvil";
        }
        return this.name();
    }
}
