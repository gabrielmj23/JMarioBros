package objetos;

import entidades.EnemigosConfig;
import entidades.Jugador;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import multijugador.PaqueteEnemigo;
import utils.UtilsEnemigo;

/**
 *
 * @author rober
 */
public class Proyectil implements Serializable {
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
    
    public void revisarColision(Jugador mario) {
        float margen = 1.8f;
        if (mario.getHitbox().intersects(hitbox.x, hitbox.y , hitbox.width, hitbox.height)) { //Si hay colision
            //Revisar si es colision por arriba o abajo
            if (mario.getHitbox().y + mario.getHitbox().height - margen < hitbox.y) { //Choque por abajo de mario
                if (mario.getInvencible() > 0) {
                    return;
                }
                mario.setAireVelocidad();
                mario.setPuntaje(mario.getPuntaje() + 10);
                // Informar a los otros clientes del cambio
      
            } else if (mario.getHitbox().y + margen > hitbox.y - 24 + hitbox.height) { //Choque por arriba de mario
                System.out.println("por abajo");
            }

            //Revisar si es colision por los lados
            if (mario.getHitbox().x + margen > hitbox.x + hitbox.width) { //Choque por la izquierda de mario
                if (mario.getInvencible() == 0) {
                    mario.disminuirPoder();
                }
            } else if (mario.getHitbox().x + mario.getHitbox().width - margen < hitbox.x) { //Choque por la derecha de mario
                if (mario.getInvencible() == 0) {
                    mario.disminuirPoder();
                }
            }
        }
    }
}
