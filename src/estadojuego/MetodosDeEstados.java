package estadojuego;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author Bertorelli
 */
public interface MetodosDeEstados {

    public void actualizar();

    public void dibujar(Graphics g);

    /* EN CASO DE USAR EL MOUSE, VER #11
    public void mouseClicked();
    
    public void mousePressed();
    
    public void mouseReleased();
    
    public void mouseMoved();    
     */
    public void keyPressed(KeyEvent e);

    public void keyReleased(KeyEvent e);

}
