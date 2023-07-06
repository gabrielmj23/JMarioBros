package inputs;

import estadojuego.EstadoJuego;
import static estadojuego.EstadoJuego.JUGANDO;
import static estadojuego.EstadoJuego.MENU;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.PanelJuego;

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
        switch(EstadoJuego.estado){
            case MENU:
                panel.getJuego().getMenu().keyPressed(e);
                break;
            case JUGANDO:
                 panel.getJuego().getJugando().keyPressed(e);
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(EstadoJuego.estado){
            case MENU:
                panel.getJuego().getMenu().keyReleased(e);
                break;
            case JUGANDO:
                 panel.getJuego().getJugando().keyReleased(e);
            default:
                break;
        }
    }

}
