package co.agendatelefonica.ui;

import co.agendatelefonica.model.AgendaTelefonica;
import co.agendatelefonica.model.Contacto;
import co.agendatelefonica.model.Telefono;
import co.agendatelefonica.model.Persona;
import co.agendatelefonica.model.TipoTelefono;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MIGUEL
 */
public class AgendaUI extends javax.swing.JFrame {

    private AgendaTelefonica agenda = null;
    private Contacto select = null;

    /**
     * Creates new form AgendaUI
     */
    public AgendaUI(AgendaTelefonica agenda) {

        this.agenda = agenda;

        initComponents();

        this.jList.setModel(new AbstractListModel() {

            @Override
            public int getSize() {
                return agenda.getContactos().size();
            }

            @Override
            public Object getElementAt(int index) {
                return agenda.getContactos().get(index);
            }
        });

        /*
         * Clase(anónima) o modelo que le provee información a la tabla de
        * número de teléfonos asociados a un contacto.
         */
        this.jTable.setModel(new AbstractTableModel() {

            @Override
            public int getRowCount() {
                if (select == null) {
                    return 0;
                }
                return select.getTelefonos().size();
            }

            private String[] columnNames = {"Tipo", "Numero"};

            @Override
            public int getColumnCount() {
                return this.columnNames.length;
            }

            @Override
            public String getColumnName(int column) {
                return columnNames[column];
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Telefono phone = select.getTelefonos().get(rowIndex);
                if (columnIndex == 0) {
                    return phone.getTipo();
                }
                if (columnIndex == 1) {
                    return phone.getNumber();
                }
                return null;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

        });

        /**
         * Clase (anónima) que implementa el manejador de eventos que guarda la
         * información en archivo cuando se finaliza la aplicación. En este caso
         * se implementa la interfaz ListSelectionListener.
         */
        super.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                //System.out.println("windowClosing");
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(new FileOutputStream("agendatelefonica.data"));
                    oos.writeObject(AgendaUI.this.agenda);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AgendaUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AgendaUI.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (oos != null) {
                        try {
                            oos.close();
                        } catch (IOException ex) {
                            Logger.getLogger(AgendaUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }

            @Override
            public void windowClosed(WindowEvent e) {
                //System.out.println("windowClosed");
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        /**
         * Clase (anónima) que implementa el manejador de eventos que muestra la
         * información de un contacto seleccionado en la lista de contactos. En
         * este caso se implementa la interfaz ListSelectionListener.
         */
        /**
         * Clase (anónima) que implementa el manejador de eventos que muestra la
         * información de un contacto seleccionado en la lista de contactos. En
         * este caso se implementa la interfaz ListSelectionListener.
         */
        jList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                // getSelectedValue() devuelve el objeto seleccionado, en este 
                // caso objetos de tipo contacto.
                setSelected((Contacto)jList.getSelectedValue());

                // Otra forma de conocer cual es el elemento seleccionado es a
                // través del objeto de tipo ListSelectionModel y el indice del
                // primer elemento seleccionado. 
                // A continuación se actualiza el elemento seleccionado en la 
                // agenda.
                // int index = ((ListSelectionModel) e.getSource()).getMinSelectionIndex();
                // if (index < 0) {
                //    return;
                // }
                // setSelected(phoneBook.getContacts().get(index));
            }
        });

        /**
         * Clase (anónima) que implementa el manejador de eventos que activa o
         * inactiva el botón que permite eliminar un teléfono de un contacto. El
         * botón se activa o inactiva dependiendo del elemento seleccionado en
         * la tabla que lista los teléfonos. En este caso se debe aclarar que la
         * tabla tiene un modelo que permite manejar la información de las
         * celdas seleccionadas (esta tabla en particular, solo permite
         * seleccionar filas), es por ello que el manejador de eventos se le
         * debe asignar a dicho modelo (this.jtbPhones.getSelectionModel())
         */
        this.jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selected = jTable.getSelectedRow(); // Devuelve -1 si no hay un elemento seleccionado
                /*    if (selected < 0) {
                    btnRemovePhone.setEnabled(false);
                } else {
                    btnRemovePhone.setEnabled(true);
                }*/
            }
        });

        /**
         * Clase (anónima) que implementa el manejador de eventos que guarda la
         * información del contacto en la agenda. En este caso se usa un mismo
         * botón para crear un nuevo contacto, o actualizar la información de un
         * contacto existente (seleccionado).
         */
        this.btnGuardar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String firstname = jtxnombre.getText().trim();
                String lastname = jtxapellido.getText().trim();
                if (select != null) { // si es un contacto existente
                    try {
                        select.getPersona().setNombre(firstname);
                        return;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(jtxnombre, ex.getMessage(), "PhoneBook 1.0", JOptionPane.ERROR_MESSAGE);
                        jtxnombre.setText(select.getPersona().getNombre());
                        Logger.getLogger(AgendaUI.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        select.getPersona().setApellido(lastname);
                        return;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(jtxnombre, ex.getMessage(), "PhoneBook 1.0", JOptionPane.ERROR_MESSAGE);
                        jtxapellido.setText(select.getPersona().getApellido());
                        Logger.getLogger(AgendaUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else { // en caso de ser un nuevo contacto
                    try {
                        setSelected(new Contacto(new Persona(firstname, lastname)));
                        agenda.add(select);
                        jList.updateUI();
                        jList.setSelectedIndex(agenda.getContactos().size() - 1);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(jtxnombre, ex.getMessage(), "PhoneBook 1.0", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(AgendaUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
            /**
         * Clase (anónima) que implementa el manejador de eventos que agrega un
         * nuevo número de teléfono al contacto.
         */
        this.btnAgregarNumero.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (select == null) {
                        String msg = "Save the contact before add phone number";
                        JOptionPane.showMessageDialog(btnGuardar, msg, "PhoneBook 1.0", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String number = txfNumber.getText().trim();
                    TipoTelefono type = (TipoTelefono) comboboxTipo.getSelectedItem();
                    select.add(new Telefono(number, type));
                    ((AbstractTableModel) jTable.getModel()).fireTableDataChanged();
                    txfNumber.setText("");
                    comboboxTipo.setSelectedItem(TipoTelefono.INDEFINIDO);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(jtxnombre, ex.getMessage(), "PhoneBook 1.0", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AgendaUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); 
        
        
                /**
         * Clase (anónima) que implementa el manejador de eventos que elimina un
         * número de teléfono seleccionado.
         */
        this.btnBorrarNumero.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = jTable.getSelectedRow();
                String msg = "Do you really want to delete the phone number ";
                int option = JOptionPane.showConfirmDialog(AgendaUI.this, msg);
                if (option == JOptionPane.OK_OPTION) {
                    select.remove(index);
                    // El actualizar el JTable usando el método updateUI funciona,
                    // para efectos de visualización, pero genera inconvenientes
                    // al momento de eliminar el último (o único) teléfono de un
                    // contacto. Ejecute y dese cuenta por si mismo.
                    //  jtbPhones.updateUI();

                    // El problema que se presenta con el updateUI, se resuelve
                    // actualizando la tabla con el método fireTableDataChanged
                    ((AbstractTableModel) jTable.getModel()).fireTableDataChanged();
                }
            }
        });
        
        

    }

    public void setSelected(Contacto selected) {
        this.select = selected;

        String firstname = "";
        String lastname = "";
        if (this.select != null) {
            firstname = this.select.getPersona().getNombre();
            lastname = this.select.getPersona().getApellido();
        }

        this.jtxnombre.setText(firstname);
        this.jtxapellido.setText(lastname);
//        jtbPhones.updateUI
        ((AbstractTableModel) jTable.getModel()).fireTableDataChanged();

        /*    if (selected == null) {
            jbtRemoveContact.setEnabled(false);
        } else {
            jbtRemoveContact.setEnabled(true);
        }*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtxnombre = new javax.swing.JTextField();
        jtxapellido = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txfNumber = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnAgregarNumero = new javax.swing.JButton();
        btnBorrarNumero = new javax.swing.JButton();
        comboboxTipo = new javax.swing.JComboBox();
        comboboxTipo.setSelectedItem(null);
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        jButton1.setText("Agregar Contacto ");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Contacto");

        jLabel2.setText("Nombre");

        jLabel3.setText("Apellido");

        jtxnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxnombreActionPerformed(evt);
            }
        });

        jtxapellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxapellidoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGuardar)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(34, 34, 34)
                            .addComponent(jLabel3))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jtxnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jtxapellido, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxapellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txfNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfNumberActionPerformed(evt);
            }
        });

        jLabel4.setText("Telefono");

        jLabel5.setText("Tipo");

        jLabel6.setText("Numero");

        btnAgregarNumero.setText("+");

        btnBorrarNumero.setText("x");
        btnBorrarNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarNumeroActionPerformed(evt);
            }
        });

        comboboxTipo.setModel(new DefaultComboBoxModel<TipoTelefono>(TipoTelefono.values())
        );
        comboboxTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxTipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(comboboxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addContainerGap(63, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarNumero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBorrarNumero)
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboboxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarNumero)
                    .addComponent(btnBorrarNumero))
                .addGap(6, 6, 6))
        );

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable);

        jList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList.setMinimumSize(new java.awt.Dimension(100, 80));
        jList.setPreferredSize(new java.awt.Dimension(100, 80));
        jScrollPane3.setViewportView(jList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtxnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxnombreActionPerformed

    private void jtxapellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxapellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxapellidoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txfNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfNumberActionPerformed

    private void btnBorrarNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarNumeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBorrarNumeroActionPerformed

    private void comboboxTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboboxTipoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarNumero;
    private javax.swing.JButton btnBorrarNumero;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox comboboxTipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList jList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jtxapellido;
    private javax.swing.JTextField jtxnombre;
    private javax.swing.JTextField txfNumber;
    // End of variables declaration//GEN-END:variables
}
