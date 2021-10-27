/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.agendatelefonica;

import co.agendatelefonica.model.AgendaTelefonica;
import co.agendatelefonica.ui.AgendaUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MIGUEL
 */
public class Main {
    public static void main(String args[]){
        
        
        
                /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

               /**
                 * Lectura de objetos desde archivo
                 */
                AgendaTelefonica agenda = null;
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(
                            new FileInputStream("agendatelefonica.data"));
                    agenda = (AgendaTelefonica) ois.readObject();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (ois != null) {
                        try {
                            ois.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                if (agenda == null) {
                    agenda = new AgendaTelefonica();
                }

                try {
                    new AgendaUI(agenda).setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });        
        

}
    
} 
                   
