package entidades;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import multijugador.PaqueteEnemigo;
import utils.UtilsEnemigo;
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
        hitbox = new Rectangle2D.Float(x, y + 2, ancho, altura - 2);
    }

    @Override
    public void revisarColision(Jugador mario, EnemigosConfig conf) {
        float margen = 1.8f;
        if (mario.hitbox.intersects(hitbox.x, hitbox.y - 24, hitbox.width, hitbox.height)) { //Si hay colision
            //Revisar si es colision por arriba o abajo
            if (mario.hitbox.y + mario.hitbox.height - margen < hitbox.y) { //Choque por abajo de mario
                if (mario.getInvencible() > 0) {
                    return;
                }
                mario.setAireVelocidad();
                muriendo = 300;
                estado = UtilsEnemigo.EstadoEnemigo.MURIENDO;
                mario.setPuntaje(mario.getPuntaje() + 10);
                // Informar a los otros clientes del cambio
                PaqueteEnemigo paquete = new PaqueteEnemigo(conf.getEnemigos());
                paquete.escribirDatos(conf.getJuego().getCliente());
            } else if (mario.hitbox.y + margen > hitbox.y - 24 + altura) { //Choque por arriba de mario
                System.out.println("por abajo");
            }

            //Revisar si es colision por los lados
            if (mario.hitbox.x + margen > hitbox.x + hitbox.width) { //Choque por la izquierda de mario
                if (mario.getInvencible() == 0) {
                    mario.disminuirPoder();
                }
            } else if (mario.hitbox.x + mario.hitbox.width - margen < hitbox.x) { //Choque por la derecha de mario
                if (mario.getInvencible() == 0) {
                    mario.disminuirPoder();
                }
            }
        }
    }
}
