package ui;

import archivos.ArchivoUsuarios;
import archivos.UsuarioRepetidoException;
import java.awt.Dimension;
import java.io.IOException;
import main.Juego;
import static main.Juego.JUEGO_ALTO;
import static main.Juego.JUEGO_ANCHO;
import multijugador.Usuario;

/**
 *
 * @author Gabriel
 */
public class PanelRegistro extends javax.swing.JPanel {

    private Juego juego;

    /**
     * Creates new form PanelRegistro
     *
     * @param juego
     */
    public PanelRegistro(Juego juego) {
        initComponents();
        Salir.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Salir.png")).getImage().getScaledInstance(200, 40, java.awt.Image.SCALE_SMOOTH))
        );
        RegBoton.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Registrarse.png")).getImage().getScaledInstance(200, 40, java.awt.Image.SCALE_SMOOTH))
        );
        Fondo.setIcon(
                new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/FondoMenu.png")).getImage().getScaledInstance(1040, 558, java.awt.Image.SCALE_SMOOTH))
        );
        this.juego = juego;
        RegError.setVisible(false);
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
        jLabel2 = new javax.swing.JLabel();
        RegNombreField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        RegCorreoField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        RegLoginField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        RegContraField = new javax.swing.JPasswordField();
        RegBoton = new javax.swing.JButton();
        RegError = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        AvatarBox = new javax.swing.JComboBox<>();
        Salir = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1040, 558));
        setMinimumSize(new java.awt.Dimension(1040, 558));
        setPreferredSize(new java.awt.Dimension(1040, 558));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTRO");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 230, 60));

        jLabel2.setText("Nombre");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, -1, -1));
        add(RegNombreField, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 140, 250, -1));

        jLabel3.setText("Correo");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, -1, -1));
        add(RegCorreoField, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, 250, -1));

        jLabel4.setText("Login");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 240, -1, -1));
        add(RegLoginField, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 260, 250, -1));

        jLabel5.setText("Contraseña");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 300, -1, -1));
        add(RegContraField, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 320, 250, -1));

        RegBoton.setContentAreaFilled(false);
        RegBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegBotonActionPerformed(evt);
            }
        });
        add(RegBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 430, 210, 40));

        RegError.setForeground(new java.awt.Color(255, 0, 51));
        RegError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RegError.setText("jLabel6");
        add(RegError, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 490, 250, -1));

        jLabel6.setText("Avatar");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 360, -1, -1));

        AvatarBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default", "Mario", "Luigi", "Plantita", "Toadette" }));
        AvatarBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AvatarBoxActionPerformed(evt);
            }
        });
        add(AvatarBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 380, 250, 30));

        Salir.setContentAreaFilled(false);
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });
        add(Salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 200, 40));
        add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 560));
    }// </editor-fold>//GEN-END:initComponents

    private void RegBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegBotonActionPerformed
        try {
            RegError.setVisible(false);
            String opcion = (String) AvatarBox.getSelectedItem();
            String avatar = "Caparazon.png";
            switch (opcion) {
                case "Default":
                    break;
                case "Mario":
                    avatar = "Mario.png";
                    break;
                case "Luigi":
                    avatar = "Luigi.png";
                    break;
                case "Plantita":
                    avatar = "Plantita.png";
                    break;
                case "Toadette":
                    avatar = "Toadette.png";
                    break;
            }
            Usuario u = new Usuario(
                    RegNombreField.getText().trim(),
                    RegCorreoField.getText().trim(),
                    RegLoginField.getText().trim(),
                    new String(RegContraField.getPassword()).trim(),
                    avatar
            );
            ArchivoUsuarios archivo = new ArchivoUsuarios();
            archivo.abrirArchivoEscritura();
            archivo.agregarUsuarioUnico(u);
            juego.getPanelIniciado().setUsuario(u);
            juego.cambiarPanel("Iniciado");
        } catch (IllegalArgumentException e) {
            RegError.setText("Algun campo es invalido");
            RegError.setVisible(true);
        } catch (IOException e) {
            RegError.setText("Error abriendo archivo");
            RegError.setVisible(true);
        } catch (ClassNotFoundException e) {
            RegError.setText("Error leyendo archivo");
            RegError.setVisible(true);
        } catch (UsuarioRepetidoException ex) {
            RegError.setText("El usuario ya existe");
            RegError.setVisible(true);
        }
    }//GEN-LAST:event_RegBotonActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        juego.cambiarPanel("Inicio");
        juego.getPanelInicio().requestFocusInWindow();
    }//GEN-LAST:event_SalirActionPerformed

    private void AvatarBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AvatarBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AvatarBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AvatarBox;
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton RegBoton;
    private javax.swing.JPasswordField RegContraField;
    private javax.swing.JTextField RegCorreoField;
    private javax.swing.JLabel RegError;
    private javax.swing.JTextField RegLoginField;
    private javax.swing.JTextField RegNombreField;
    private javax.swing.JButton Salir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables
}
