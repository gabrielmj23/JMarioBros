package ui;

import main.Juego;

/**
 *
 * @author Gabriel
 */
public class PanelAcerca extends javax.swing.JPanel {

    private Juego juego;
    
    /**
     * Creates new form PanelAcerca
     */
    public PanelAcerca(Juego juego) {
        initComponents();
        this.juego = juego;
        Salir.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Salir.png")).getImage().getScaledInstance(200, 40, java.awt.Image.SCALE_SMOOTH))
        );
        Fondo.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/FondoMenu.png")).getImage().getScaledInstance(1040, 558, java.awt.Image.SCALE_SMOOTH))
        );
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
        jTextArea1 = new javax.swing.JTextArea();
        Salir = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1040, 558));
        setMinimumSize(new java.awt.Dimension(1040, 558));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Acerca De");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, 270, 80));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Este juego fue desarrollado en el lenguaje de programación Java, en su versión 20. Utilizó librerías nativas del lenguaje, además de la librería Swing para componentes gráficos como el presente menú.\n\n\nEl equipo de desarrollo fue: José Bertorelli, Roberth Graffe y Gabriel Méndez.");
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 500, 180));

        Salir.setContentAreaFilled(false);
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });
        add(Salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 200, 40));
        add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 560));
    }// </editor-fold>//GEN-END:initComponents

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        juego.cambiarPanel("Inicio");
    }//GEN-LAST:event_SalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton Salir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
