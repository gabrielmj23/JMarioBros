package estadojuego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import main.Juego;
import ui.PanelInicio;

/**
 *
 * @author Bertorelli
 */
public class Menu extends Estado implements MetodosDeEstados {
    
    JPanel panelMenu;

    public Menu(Juego juego) {
        super(juego);
        panelMenu = new PanelInicio();
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
        //if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          //  EstadoJuego.estado = EstadoJuego.JUGANDO;
        //}
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}