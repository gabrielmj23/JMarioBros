package main;

import entidades.JugadorMulti;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import multijugador.PaqueteDesconectar;
import ui.PanelIniciado;
import ui.PanelInicio;
import ui.PanelPartida;
import ui.PanelRegistro;
import ui.PanelSesion;

/**
 *
 * @author Gabriel
 */
public class VentanaJuego {

    private JFrame frame;
    private CardLayout layout;

    public VentanaJuego(PanelInicio pInicio, PanelRegistro pRegistro, PanelSesion pSesion, PanelIniciado pIniciado, PanelPartida pPartida, PanelJuego pJuego) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();
        layout = new CardLayout();
        pane.setLayout(layout);
        pane.add("Inicio", pInicio);
        pane.add("Registro", pRegistro);
        pane.add("Sesion", pSesion);
        pane.add("Iniciado", pIniciado);
        pane.add("Partida", pPartida);
        pane.add("Juego", pJuego);
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
                pJuego.getJuego().getJugando().ventanaPerdida();
            }

        });
        // Para detectar cuando se cierra el juego
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (pJuego.getJuego().getCliente() != null) {
                    PaqueteDesconectar paquete = new PaqueteDesconectar((JugadorMulti) pJuego.getJuego().getJugando().getJugador());
                    paquete.escribirDatos(pJuego.getJuego().getCliente());
                }
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

    public JFrame getFrame() {
        return frame;
    }

    public CardLayout getLayout() {
        return layout;
    }
}
