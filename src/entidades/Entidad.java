package entidades;

import java.io.Serializable;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author rober
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

    public abstract void iniHitbox(float x, float y, int ancho, int altura);

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle2D.Float hitbox) {
        this.hitbox = hitbox;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
