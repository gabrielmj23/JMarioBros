package main;

import entidades.JugadorMulti;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import multijugador.PaqueteDesconectar;

/**
 *
 * @author Gabriel
 */
public class VentanaJuego {

    private JFrame frame;

    public VentanaJuego(PanelJuego panel) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        // Para detectar cuando la ventana pierde el enfoque del sistema
        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                panel.getJuego().getJugando().ventanaPerdida();
            }

        });
        // Para detectar cuando se cierra el juego
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                PaqueteDesconectar paquete = new PaqueteDesconectar((JugadorMulti) panel.getJuego().getJugando().getJugador());
                paquete.escribirDatos(panel.getJuego().getJugando().getCliente());
            }

            @Override
            public void windowClosed(WindowEvent e) {

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
    }
}
