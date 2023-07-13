package entidades;

import java.io.Serializable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Gabriel
 */
public abstract class Entidad implements Serializable {

    protected float x;
    protected float y;
    protected int ancho;
    protected int altura;
    protected Rectangle2D.Float hitbox;

    public Entidad(float x, float y, int ancho, int altura) { //ancho = 32 //altura = 64
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.altura = altura;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    
    public void setX(float x) {
        this.x = x;
    }
    
    public void setY(float y) {
        this.y = y;
    }

    public abstract void iniHitbox();

    public abstract void actualizarHitbox();

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

}
