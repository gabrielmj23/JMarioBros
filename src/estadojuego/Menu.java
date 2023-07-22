package estadojuego;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import main.Juego;

/**
 *
 * @author Bertorelli
 */
public class Menu extends Estado implements MetodosDeEstados {

    public Menu(Juego juego) {
        super(juego);
    }

    @Override
    public void actualizar() {

    }

    @Override
    public void dibujar(Graphics g) {
        //g.setColor(Color.black);
        //g.drawString("MENU", Juego.JUEGO_ANCHO / 2, 200);
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

}
