/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.IOException;
import static utils.UtilsEnemigo.*;

/**
 *
 * @author rober
 */
public class Goomba extends Enemigo {

    public Goomba(float x, float y) throws IOException {
        super(x, y, 32, 37, GOOMBA_INDEX);
    }
    
    
    
}
