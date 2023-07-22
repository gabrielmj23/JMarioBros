package ui;

import archivos.ArchivoEstadistica;
import archivos.Estadistica;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import main.Juego;

/**
 *
 * @author Gabriel
 */
public class PanelEstadisticas extends javax.swing.JPanel {

    private Juego juego;
    private ArrayList<Estadistica> estadisticas;

    /**
     * Creates new form PanelEstadisticas
     */
    public PanelEstadisticas(Juego juego) {
        initComponents();
        Salir.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Salir.png")).getImage().getScaledInstance(200, 40, java.awt.Image.SCALE_SMOOTH))
        );
        Fondo.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/FondoMenu.png")).getImage().getScaledInstance(1040, 558, java.awt.Image.SCALE_SMOOTH))
        );
        ArchivoEstadistica archivo = new ArchivoEstadistica();
        try {
            MsjVacio.setVisible(false);
            Tabla.setVisible(false);
            MsjOrdenar.setVisible(false);
            SelOrdenar.setVisible(false);
            this.juego = juego;
            archivo.abrirArchivoLectura();
            estadisticas = archivo.leerEstadisticas();
            if (estadisticas == null || estadisticas.isEmpty()) {
                MsjVacio.setVisible(true);
            } else {
                MsjOrdenar.setVisible(true);
                SelOrdenar.setVisible(true);
                generarTabla("Usuario");
            }
        } catch (IOException | ClassNotFoundException ex) {
            MsjVacio.setVisible(true);
        }
    }

    private String[] obtenerUsuarios(ArrayList<Estadistica> estadisticas) {
        String[] u = new String[estadisticas.size()];
        for (int i = 0; i < estadisticas.size(); i++) {
            u[i] = estadisticas.get(i).getUsuario();
        }
        return u;
    }

    private Integer[] obtenerJugadas(ArrayList<Estadistica> estadisticas) {
        Integer[] u = new Integer[estadisticas.size()];
        for (int i = 0; i < estadisticas.size(); i++) {
            u[i] = estadisticas.get(i).getJugadas();
        }
        return u;
    }

    private Integer[] obtenerGanadas(ArrayList<Estadistica> estadisticas) {
        Integer[] u = new Integer[estadisticas.size()];
        for (int i = 0; i < estadisticas.size(); i++) {
            u[i] = estadisticas.get(i).getGanadas();
        }
        return u;
    }

    private Integer[] obtenerPerdidas(ArrayList<Estadistica> estadisticas) {
        Integer[] u = new Integer[estadisticas.size()];
        for (int i = 0; i < estadisticas.size(); i++) {
            u[i] = estadisticas.get(i).getPerdidas();
        }
        return u;
    }

    private Integer[] obtenerAbandonadas(ArrayList<Estadistica> estadisticas) {
        Integer[] u = new Integer[estadisticas.size()];
        for (int i = 0; i < estadisticas.size(); i++) {
            u[i] = estadisticas.get(i).getAbandonadas();
        }
        return u;
    }

    private void generarTabla(String criterio) {
        // Ordenar tabla según sea requerido
        switch (criterio) {
            case "Usuario":
                Collections.sort(estadisticas);
                break;
            case "Jugadas":
                Collections.sort(estadisticas, new Comparator<Estadistica>() {
                    @Override
                    public int compare(Estadistica o1, Estadistica o2) {
                        return Integer.compare(o1.getJugadas(), o2.getJugadas());
                    }
                });
                break;
            case "Ganadas":
                Collections.sort(estadisticas, new Comparator<Estadistica>() {
                    @Override
                    public int compare(Estadistica o1, Estadistica o2) {
                        return Integer.compare(o1.getGanadas(), o2.getGanadas());
                    }
                });
                break;
            case "Perdidas":
                Collections.sort(estadisticas, new Comparator<Estadistica>() {
                    @Override
                    public int compare(Estadistica o1, Estadistica o2) {
                        return Integer.compare(o1.getPerdidas(), o2.getPerdidas());
                    }
                });
                break;
            case "Abandonadas":
                Collections.sort(estadisticas, new Comparator<Estadistica>() {
                    @Override
                    public int compare(Estadistica o1, Estadistica o2) {
                        return Integer.compare(o1.getAbandonadas(), o2.getAbandonadas());
                    }
                });
                break;
        }
        // Crear tabla en posición
        Tabla = new javax.swing.JTable();
        Tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{obtenerUsuarios(estadisticas), obtenerJugadas(estadisticas), obtenerGanadas(estadisticas), obtenerPerdidas(estadisticas), obtenerAbandonadas(estadisticas)},
                new String[]{
                    "Usuario", "Partidas Jugadas", "Partidas Ganadas", "Partidas Perdidas", "Partidas Abandonadas"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tabla);
        if (Tabla.getColumnModel().getColumnCount() > 0) {
            Tabla.getColumnModel().getColumn(0).setResizable(false);
            Tabla.getColumnModel().getColumn(1).setResizable(false);
            Tabla.getColumnModel().getColumn(2).setResizable(false);
            Tabla.getColumnModel().getColumn(3).setResizable(false);
            Tabla.getColumnModel().getColumn(4).setResizable(false);
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

        Salir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        MsjVacio = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        MsjOrdenar = new javax.swing.JLabel();
        SelOrdenar = new javax.swing.JComboBox<>();
        Fondo = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1040, 558));
        setMinimumSize(new java.awt.Dimension(1040, 558));
        setPreferredSize(new java.awt.Dimension(1040, 558));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Salir.setContentAreaFilled(false);
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });
        add(Salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 200, 40));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Estadísticas");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 230, 60));

        MsjVacio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MsjVacio.setText("No hay registros que mostrar");
        add(MsjVacio, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 130, 310, -1));

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Usuario", "Partidas Jugadas", "Partidas Ganadas", "Partidas Perdidas", "Partidas Abandonadas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla.setGridColor(new java.awt.Color(0, 51, 0));
        jScrollPane1.setViewportView(Tabla);
        if (Tabla.getColumnModel().getColumnCount() > 0) {
            Tabla.getColumnModel().getColumn(0).setResizable(false);
            Tabla.getColumnModel().getColumn(1).setResizable(false);
            Tabla.getColumnModel().getColumn(2).setResizable(false);
            Tabla.getColumnModel().getColumn(3).setResizable(false);
            Tabla.getColumnModel().getColumn(4).setResizable(false);
        }

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 680, 360));

        MsjOrdenar.setText("Ordenar por:");
        add(MsjOrdenar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, -1, 20));

        SelOrdenar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Jugadas", "Ganadas", "Perdidas", "Abandonadas" }));
        SelOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelOrdenarActionPerformed(evt);
            }
        });
        add(SelOrdenar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 120, -1));
        add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 560));
    }// </editor-fold>//GEN-END:initComponents

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        juego.cambiarPanel("Iniciado");
    }//GEN-LAST:event_SalirActionPerformed

    private void SelOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelOrdenarActionPerformed
        generarTabla((String) SelOrdenar.getSelectedItem());
    }//GEN-LAST:event_SelOrdenarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JLabel MsjOrdenar;
    private javax.swing.JLabel MsjVacio;
    private javax.swing.JButton Salir;
    private javax.swing.JComboBox<String> SelOrdenar;
    private javax.swing.JTable Tabla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
