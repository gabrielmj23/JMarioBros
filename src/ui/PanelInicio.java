package ui;

import estadojuego.Menu;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.Juego;
import static main.Juego.JUEGO_ALTO;
import static main.Juego.JUEGO_ANCHO;

/**
 *
 * @author Gabriel
 */
public class PanelInicio extends javax.swing.JPanel {

    private Menu menu;
    private Juego juego;

    /**
     * Creates new form Inicio
     *
     * @param juego
     */
    public PanelInicio(Juego juego) {
        initComponents();
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    juego.toggleMusica();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        this.juego = juego;
        Titulo.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Titulo.png")).getImage().getScaledInstance(320, 120, java.awt.Image.SCALE_SMOOTH))
        );
        BotonIniRegistro.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Registrarse.png")).getImage().getScaledInstance(300, 60, java.awt.Image.SCALE_SMOOTH))
        );
        BotonIniSesion.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Iniciar Sesion.png")).getImage().getScaledInstance(300, 60, java.awt.Image.SCALE_SMOOTH))
        );
        BotonAcerca.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Acerca De.png")).getImage().getScaledInstance(300, 60, java.awt.Image.SCALE_SMOOTH))
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

        Titulo = new javax.swing.JLabel();
        BotonIniRegistro = new javax.swing.JButton();
        BotonIniSesion = new javax.swing.JButton();
        BotonAcerca = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1040, 558));
        setMinimumSize(new java.awt.Dimension(1040, 558));
        setPreferredSize(new java.awt.Dimension(1040, 558));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Titulo.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        Titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, 340, 150));

        BotonIniRegistro.setBackground(new java.awt.Color(60, 63, 65));
        BotonIniRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Registrarse.png"))); // NOI18N
        BotonIniRegistro.setContentAreaFilled(false);
        BotonIniRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonIniRegistroActionPerformed(evt);
            }
        });
        add(BotonIniRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, 300, 80));

        BotonIniSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Iniciar Sesion.png"))); // NOI18N
        BotonIniSesion.setContentAreaFilled(false);
        BotonIniSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonIniSesionActionPerformed(evt);
            }
        });
        add(BotonIniSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 310, 300, 80));

        BotonAcerca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Iniciar Sesion.png"))); // NOI18N
        BotonAcerca.setContentAreaFilled(false);
        BotonAcerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAcercaActionPerformed(evt);
            }
        });
        add(BotonAcerca, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 410, 300, 80));

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/FondoMenu.png"))); // NOI18N
        add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 560));
    }// </editor-fold>//GEN-END:initComponents

    private void BotonIniRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonIniRegistroActionPerformed
        juego.cambiarPanel("Registro");
    }//GEN-LAST:event_BotonIniRegistroActionPerformed

    private void BotonIniSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonIniSesionActionPerformed
        juego.cambiarPanel("Sesion");
    }//GEN-LAST:event_BotonIniSesionActionPerformed

    private void BotonAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAcercaActionPerformed
        juego.cambiarPanel("Acerca");
    }//GEN-LAST:event_BotonAcercaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonAcerca;
    private javax.swing.JButton BotonIniRegistro;
    private javax.swing.JButton BotonIniSesion;
    private javax.swing.JLabel Fondo;
    private javax.swing.JLabel Titulo;
    // End of variables declaration//GEN-END:variables
}
