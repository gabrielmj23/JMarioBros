/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author rober
 */
public class Proyectil {
    private Rectangle2D.Float hitbox;
    private int dir;
    private boolean activo = true;
   // private float velocidad;
    
    public Proyectil(int x, int y, int dir) {
        hitbox = new Rectangle2D.Float(x , y + 3 ,30,30);
        this.dir = dir;
        
    }
    
    public void actualizarPosicion(){
        hitbox.x += dir * 0.5f;
    }
    
    public void setPos(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }
    
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }
    
    public void setActivo(boolean activo){
        this.activo = activo;
    }
    public boolean estaActivo(){
        return activo;
    }
    
    public int getDir(){
        return dir;
    }
}
