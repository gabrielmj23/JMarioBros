/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import static utils.UtilsObjetos.*;

/**
 *
 * @author rober
 */
public class ObjetoJuego implements Serializable {

    protected int x, y;
    protected Rectangle2D.Float hitbox;
    protected boolean animado, activo = true;

    //Atributos de animacion
    private int tipo;
    private int deltaAnimacion;
    private int indiceAnimacion;
    private static final int VELOCIDAD_ANIMACION = 17;

    public ObjetoJuego(int x, int y, int tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
    }

    protected void iniHitbox(int ancho, int altura) {
        hitbox = new Rectangle2D.Float(x, y, ancho, altura);
    }

    public void dibujarHitbox(Graphics g, int xNivelDesfase) {
        g.drawRect((int) (hitbox.x - xNivelDesfase), (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public void reiniciar() {
        deltaAnimacion = 0;
        indiceAnimacion = 0;
        activo = true;

        if (tipo == HONGO_INDEX || tipo == FLOR_INDEX) {
            animado = false;
        } else {
            animado = true;
        }
    }

    /**
     * Actualiza el frame de animación según pasa el tiempo
     */
    protected void actualizarFrameAnimacion() {
        deltaAnimacion++;
        if (deltaAnimacion >= VELOCIDAD_ANIMACION) {
            deltaAnimacion = 0;
            indiceAnimacion++;
            if (indiceAnimacion >= 7) {
                indiceAnimacion = 0;
            }
        }
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}
