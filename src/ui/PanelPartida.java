package ui;

import entidades.JugadorMulti;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import main.Juego;
import multijugador.PaqueteDesconectar;
import utils.UtilsJugador;

/**
 *
 * @author Gabriel
 */
public class PanelPartida extends javax.swing.JPanel {

    private Juego juego;
    private ArrayList<JugadorMulti> jugadores;
    private boolean anfitrion;

    /**
     * Creates new form PanelPartida
     *
     * @param juego
     */
    public PanelPartida(Juego juego) {
        this.juego = juego;
        this.juego.setUps(0.5f);
        initComponents();
        Salir.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Salir.png")).getImage().getScaledInstance(200, 40, java.awt.Image.SCALE_SMOOTH))
        );
        IniciarBtn.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Iniciar Partida.png")).getImage().getScaledInstance(200, 40, java.awt.Image.SCALE_SMOOTH))
        );
        Fondo.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/FondoMenu.png")).getImage().getScaledInstance(1040, 558, java.awt.Image.SCALE_SMOOTH))
        );
    }

    public void setAnfitrion(boolean anfitrion) {
        this.anfitrion = anfitrion;
        IniciarBtn.setVisible(anfitrion);
    }

    /**
     * Actualiza los jugadores presentes en el lobby
     */
    public void actualizar() {
        jugadores = juego.getJugando().getJugadores();
        crearListaLobby();
        String seleccionado = (String) SelecPersonaje.getSelectedItem();
        SelecPersonaje.setModel(new DefaultComboBoxModel(juego.getJugando().obtenerPersonajesDisponibles()));
        SelecPersonaje.setSelectedItem(seleccionado);
        IniciarBtn.setEnabled(jugadores.size() > 1);
    }

    private void crearListaLobby() {
        for (int i = 0; i < 4; i++) {
            // Agregar jugador a lista
            if (i < jugadores.size()) {
                JugadorMulti j = jugadores.get(i);
                TablaLobby.getModel().setValueAt(j.getUsuario().getLogin(), i, 0);
                TablaLobby.getModel().setValueAt(UtilsJugador.obtenerNombrePersonaje(j.getTipo()), i, 1);
            } else {
                TablaLobby.getModel().setValueAt("", i, 0);
                TablaLobby.getModel().setValueAt("", i, 1);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaLobby = new javax.swing.JTable();
        SelecPersonaje = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        IniciarBtn = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1040, 558));
        setMinimumSize(new java.awt.Dimension(1040, 558));
        setPreferredSize(new java.awt.Dimension(1040, 558));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        jLabel1.setText("Esperando jugadores");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 120, -1, -1));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TablaLobby.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "USUARIO", "PERSONAJE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaLobby.setIntercellSpacing(new java.awt.Dimension(0, 10));
        TablaLobby.setRowHeight(40);
        TablaLobby.setRowSelectionAllowed(false);
        TablaLobby.setShowGrid(true);
        jScrollPane1.setViewportView(TablaLobby);
        if (TablaLobby.getColumnModel().getColumnCount() > 0) {
            TablaLobby.getColumnModel().getColumn(0).setResizable(false);
            TablaLobby.getColumnModel().getColumn(1).setResizable(false);
        }

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, -1, 190));

        SelecPersonaje.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        SelecPersonaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelecPersonajeActionPerformed(evt);
            }
        });
        add(SelecPersonaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, 150, -1));

        jLabel2.setText("Seleccione su personaje:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, -1, 20));

        IniciarBtn.setContentAreaFilled(false);
        IniciarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IniciarBtnActionPerformed(evt);
            }
        });
        add(IniciarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 420, 200, 40));

        Salir.setContentAreaFilled(false);
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });
        add(Salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 200, 40));
        add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 560));
    }// </editor-fold>//GEN-END:initComponents

    private void SelecPersonajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelecPersonajeActionPerformed
        juego.getJugando().getJugador().setTipo(UtilsJugador.obtenerIdPersonaje((String) SelecPersonaje.getSelectedItem()));
        juego.getJugando().getJugador().cargarImagenes();
    }//GEN-LAST:event_SelecPersonajeActionPerformed

    private void IniciarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IniciarBtnActionPerformed
        juego.getJugando().iniciarMundo();
    }//GEN-LAST:event_IniciarBtnActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        PaqueteDesconectar paquete = new PaqueteDesconectar((JugadorMulti) juego.getJugando().getJugador());
        paquete.escribirDatos(juego.getCliente());
        juego.cambiarPanel("Iniciado");
    }//GEN-LAST:event_SalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton IniciarBtn;
    private javax.swing.JButton Salir;
    private javax.swing.JComboBox<String> SelecPersonaje;
    private javax.swing.JTable TablaLobby;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
