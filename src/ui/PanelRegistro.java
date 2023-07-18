package ui;

import archivos.ArchivoUsuarios;
import java.io.IOException;
import multijugador.Usuario;

/**
 *
 * @author Gabriel
 */
public class PanelRegistro extends javax.swing.JPanel {

    /**
     * Creates new form PanelRegistro
     */
    public PanelRegistro() {
        initComponents();
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

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTRO");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 230, 60));

        jLabel2.setText("Nombre");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, -1, -1));
        add(RegNombreField, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 170, 250, -1));

        jLabel3.setText("Correo");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, -1, -1));
        add(RegCorreoField, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 230, 250, -1));

        jLabel4.setText("Login");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 270, -1, -1));
        add(RegLoginField, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 290, 250, -1));

        jLabel5.setText("Contraseña");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 330, -1, -1));
        add(RegContraField, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 350, 250, -1));

        RegBoton.setText("Registrarse");
        RegBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegBotonActionPerformed(evt);
            }
        });
        add(RegBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 390, 110, 30));

        RegError.setForeground(new java.awt.Color(255, 0, 51));
        RegError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RegError.setText("jLabel6");
        add(RegError, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 430, 250, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void RegBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegBotonActionPerformed
        try {
            RegError.setVisible(false);
            Usuario u = new Usuario(
                    RegNombreField.getText().trim(),
                    RegCorreoField.getText().trim(),
                    RegLoginField.getText().trim(),
                    new String(RegContraField.getPassword()).trim()
            );
            ArchivoUsuarios archivo = new ArchivoUsuarios();
            archivo.abrirArchivoEscritura();
        } catch (IllegalArgumentException e) {
            RegError.setText("Algun campo es invalido");
            RegError.setVisible(true);
        } catch (IOException e) {
            RegError.setText("Error abriendo archivo");
            RegError.setVisible(true);
        } catch (ClassNotFoundException e) {
            RegError.setText("Error leyendo archivo");
            RegError.setVisible(true);
        }
    }//GEN-LAST:event_RegBotonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton RegBoton;
    private javax.swing.JPasswordField RegContraField;
    private javax.swing.JTextField RegCorreoField;
    private javax.swing.JLabel RegError;
    private javax.swing.JTextField RegLoginField;
    private javax.swing.JTextField RegNombreField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}