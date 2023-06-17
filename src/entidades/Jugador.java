package entidades;

import java.awt.Graphics;

/**
 *
 * @author Gabriel
 */
public class Jugador extends Entidad {

    private boolean izquierda;
    private boolean derecha;
    private boolean arriba;
    private boolean abajo;
    private float velocidad;

    public Jugador(float x, float y) {
        super(x, y);
        velocidad = 2f;
    }

    public void setIzquierda(boolean izquierda) {
        this.izquierda = izquierda;
    }

    public void setDerecha(boolean derecha) {
        this.derecha = derecha;
    }

    public void setArriba(boolean arriba) {
        this.arriba = arriba;
    }

    public void setAbajo(boolean abajo) {
        this.abajo = abajo;
    }

    /**
     * Modifica la posición del jugador dependiendo de la dirección en que se
     * mueve
     */
    public void actualizarPosicion() {
        if (izquierda && !derecha) {
            x -= velocidad;
        } else if (derecha && !izquierda) {
            x += velocidad;
        }

        if (arriba && !abajo) {
            y -= velocidad;
        } else if (abajo && !arriba) {
            y += velocidad;
        }
    }

    /**
     * Actualiza valores correspondientes al jugador
     */
    public void actualizar() {
        actualizarPosicion();
    }

    /**
     * Dibuja al jugador en pantalla
     *
     * @param g
     */
    public void render(Graphics g) {
        g.fillRect((int) x, (int) y, 100, 100);
    }

    /**
     * Detiene al jugador
     */
    public void resetearEstado() {
        izquierda = derecha = arriba = abajo = false;
    }

}
