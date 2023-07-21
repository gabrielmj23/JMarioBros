package main;

import inputs.Teclado;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import static main.Juego.JUEGO_ANCHO;
import static main.Juego.JUEGO_ALTO;

/**
 *
 * @author Gabriel
 */
public class PanelJuego extends JPanel {

    private Juego juego;

    public PanelJuego(Juego juego) {
        this.juego = juego;
        addKeyListener(new Teclado(this));
        // Dar tamaño al panel
        Dimension dimension = new Dimension(JUEGO_ANCHO, JUEGO_ALTO);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
    }

    public Juego getJuego() {
        return juego;
    }

    /**
     * Gestiona actualizaciones referentes a animaciones y objetos en el panel
     */
    public void actualizarJuego() { //TODO: ACTUALIZAR METODO DE BLOQUE TOCADO PARA CADA NIVEL
        juego.getEnemigosConfig().revisarColision(juego.getJugador());
        juego.getObjetosConfig().revisarPoderTocado(juego.getJugador());
        juego.getObjetosConfig().revisarBloqueTocado(juego.getJugador(), juego.getNivelConfig().getNivelUno().obtenerNivelDatos());
        juego.getJugador().actualizar();
        juego.getEnemigosConfig().actualizar(juego.getNivelConfig().getNivelUno().obtenerNivelDatos());
        juego.getNivelConfig().actualizar();
        juego.getObjetosConfig().actualizar();
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
