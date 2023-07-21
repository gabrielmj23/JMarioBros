package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import main.Juego;
import static niveles.NivelConfig.obtenerEnemigos;
import utils.UtilsEnemigo;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Juego;
import static utils.UtilsJugador.PoderJugador.*;

/**
 *
 * @author rober
 */
public class EnemigosConfig {

    private Juego juego;
    private ArrayList<Enemigo> enemigos = new ArrayList<>();

    public EnemigosConfig(Juego juego) {
        this.juego = juego;
        enemigos = obtenerEnemigos();
        System.out.println("cantidad de goombas: " + enemigos.size());
    }

    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }

    public void setEnemigos(ArrayList<Enemigo> enemigos) {
        this.enemigos = enemigos;
    }

    public Juego getJuego() {
        return juego;
    }

    public void actualizar(int[][] nivelDatos) {
        for (Enemigo go : enemigos) {
            if (go.estaVivo()) {
                go.actualizar(nivelDatos);
            }
        }
    }

    public void dibujar(Graphics g, int xNivelDesfase) {
        dibujarEnemigos(g, xNivelDesfase);
    }

    private void dibujarEnemigos(Graphics g, int xNivelDesfase) {
        for (Enemigo go : enemigos) {
            if (go.estaVivo()) {
                g.setColor(Color.black);
                g.drawRect((int) go.hitbox.x - xNivelDesfase, (int) go.hitbox.y, (int) go.hitbox.width, (int) go.hitbox.height);
                go.dibujar(g, xNivelDesfase);
            }
        }
    }

    public void revisarColision(Jugador mario) {
        for (Enemigo go : enemigos) {
            if (go.estado != UtilsEnemigo.EstadoEnemigo.MURIENDO) {
                go.revisarColision(mario, this);
            }
        }
    }
}
