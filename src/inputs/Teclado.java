package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.PanelJuego;

/**
 *
 * @author Gabriel
 */
public class Teclado implements KeyListener {

    private PanelJuego panel;

    public Teclado(PanelJuego panel) {
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                panel.getJuego().getJugador().setArriba(true);
                break;
            case KeyEvent.VK_A:
                panel.getJuego().getJugador().setIzquierda(true);
                break;
            case KeyEvent.VK_S:
                panel.getJuego().getJugador().setAbajo(true);
                break;
            case KeyEvent.VK_D:
                panel.getJuego().getJugador().setDerecha(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                panel.getJuego().getJugador().setArriba(false);
                break;
            case KeyEvent.VK_A:
                panel.getJuego().getJugador().setIzquierda(false);
                break;
            case KeyEvent.VK_S:
                panel.getJuego().getJugador().setAbajo(false);
                break;
            case KeyEvent.VK_D:
                panel.getJuego().getJugador().setDerecha(false);
                break;
        }
    }

}
