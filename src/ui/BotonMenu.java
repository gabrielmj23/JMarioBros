package ui;

import estadojuego.EstadoJuego;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static main.Juego.ESCALA;

/**
 *
 * @author Gabriel
 */
public class BotonMenu {

    // Atributos base
    private int x;
    private int y;
    private int fila;
    private int indice;
    private EstadoJuego estado;
    private Rectangle limites;

    // Atributos de imagen
    private BufferedImage[] imagenes;
    private boolean hover;
    private boolean click;

    public static int ANCHO_BOTON_DEF = 140;
    public static int ALTO_BOTON_DEF = 56;
    public static int ANCHO_BOTON = (int) (140 * ESCALA);
    public static int ALTO_BOTON = (int) (56 * ESCALA);

    public BotonMenu(int x, int y, int fila, EstadoJuego estado) {
        this.x = x;
        this.y = y;
        this.fila = fila;
        this.estado = estado;
        // cargarImagenes();
        limites = new Rectangle(x - ANCHO_BOTON, y, ANCHO_BOTON, ALTO_BOTON);
    }

    private void cargarImagenes() {
        try {
            imagenes = new BufferedImage[3];
            BufferedImage tmp = ImageIO.read(new File("media/sprites/BotonMenu.png"));
            for (int i = 0; i < imagenes.length; i++) {
                imagenes[i] = tmp.getSubimage(i * ANCHO_BOTON_DEF, fila * ALTO_BOTON_DEF, ANCHO_BOTON_DEF, ALTO_BOTON_DEF);
            }
        } catch (IOException e) {
            System.out.println("Error cargando imagenes de boton");
        }
    }

    public void dibujar(Graphics g) {
        //g.drawImage(imagenes[indice], x - ANCHO_BOTON / 2, y, ANCHO_BOTON, ALTO_BOTON, null);
        g.fillRect(x - ANCHO_BOTON, y, ANCHO_BOTON, ALTO_BOTON);
    }

    public void actualizar() {
        indice = 0;
        if (hover) {
            indice = 1;
        }
        if (click) {
            indice = 2;
        }
    }
    
    public void aplicarEstado() {
        EstadoJuego.estado = estado;
    }
    
    public void resetear() {
        hover = click = false;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public Rectangle getLimites() {
        return limites;
    }
}
