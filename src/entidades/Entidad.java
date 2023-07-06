package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Gabriel
 */
public abstract class Entidad {

    protected float x;
    protected float y;
    protected int ancho;
    protected int largo;
    protected Rectangle hitbox;

    public Entidad(float x, float y, int ancho, int largo) { //ancho = 32 //largo = 64
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.largo = largo;
    }

    protected void dibujarHitbox(Graphics g) {
        //Para probar la hitbox
        g.setColor(Color.PINK);
        g.drawRect(hitbox.x, hitbox.y, ancho, ancho);

    }

    public abstract void iniHitbox();

    public abstract void actualizarHitbox();

    public Rectangle getHitbox() {
        return hitbox;
    }

}
