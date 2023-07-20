package entidades;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import static utils.UtilsEnemigo.PLANTA_INDEX;

/**
 *
 * @author Gabriel
 */
public class Planta extends Enemigo {

    public Planta(float x, float y) throws IOException {
        super(x, y, 32, 48, PLANTA_INDEX);
        iniHitbox(x, y, ancho, altura);
    }

    @Override
    public void iniHitbox(float x, float y, int ancho, int altura) {
        hitbox = new Rectangle2D.Float(x, y + 2, ancho, altura - 2);
    }

    @Override
    public void revisarColision(Jugador mario, EnemigosConfig conf) {
        float margen = 1.8f;
        if (mario.hitbox.intersects(hitbox.x, hitbox.y - 24, hitbox.width, hitbox.height)) { //Si hay colision
            if (mario.getInvencible() > 0) {
                return;
            }
            mario.disminuirPoder();
        }
    }
}
