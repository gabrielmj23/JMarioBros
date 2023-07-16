/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import static utils.UtilsEnemigo.*;

/**
 *
 * @author rober
 */
public class Goomba extends Enemigo {

    public Goomba(float x, float y) throws IOException {
        super(x, y, 32, 37, GOOMBA_INDEX);
        iniHitbox(x,y, ancho, altura);
    }
    
            @Override
    public void iniHitbox(float x, float y, int ancho, int altura) {
            hitbox = new Rectangle2D.Float(x + 4, y + 40, ancho + 2, altura - 25);
    }
     
    
    
    
}
