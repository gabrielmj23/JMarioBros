package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Gabriel
 */
public abstract class Entidad {

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

    protected void dibujarHitbox(Graphics g) {
        //Para probar la hitbox
        g.setColor(Color.PINK);
        //g.drawRect(hitbox.x, hitbox.y, ancho, ancho);

    }

    public abstract void iniHitbox(float x,float  y, int ancho,  int altura);

    

    public Rectangle2D.Float getHitbox() {
        return hitbox;
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
