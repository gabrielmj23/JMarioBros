package main;

import inputs.Teclado;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Gabriel
 */
public class PanelJuego extends JPanel {

    private Juego juego;

    public PanelJuego(Juego juego) {
        this.juego = juego;
        addKeyListener(new Teclado(this));
    }

    public Juego getJuego() {
        return juego;
    }

    /**
     * Gestiona actualizaciones referentes a animaciones y objetos en el panel
     */
    public void actualizarJuego() {
        juego.getJugador().actualizar();
    }

    /**
     * Dibuja el panel en pantalla
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        juego.render(g);
    }
}
