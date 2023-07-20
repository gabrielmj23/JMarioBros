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
        super(x, y, 32, 32, GOOMBA_INDEX);
        iniHitbox(x, y, ancho, altura);
    }

    @Override
    public void iniHitbox(float x, float y, int ancho, int altura) {
        hitbox = new Rectangle2D.Float(x, y + 1, ancho, altura - 2);
    }

}
