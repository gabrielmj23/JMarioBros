package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import main.Juego;
import multijugador.PaqueteEnemigo;
import niveles.Nivel;

/**
 *
 * @author rober
 */
public class EnemigosConfig {

    private Juego juego;
    private ArrayList<Enemigo> enemigos = new ArrayList<>();

    public EnemigosConfig(Juego juego) {
        this.juego = juego;
        //enemigos = obtenerEnemigos();
        //System.out.println("cantidad de goombas: " + enemigos.size());
    }
    
    /**
     * 
     * @param nivel 
     */
    public void cargarEnemigos(Nivel nivel){
        enemigos = nivel.getEnemigos();
    }
    
    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }

    /**
     * 
     * @param enemigos 
     */
    public void setEnemigos(ArrayList<Enemigo> enemigos) {
        this.enemigos = enemigos;
    }

    /**
     * 
     * @param nivelDatos 
     */
    public void actualizar(int[][] nivelDatos) {
        boolean estaAlgunoActivo = false;
        for (Enemigo go : enemigos) {
            if (go.estaVivo()) {
                go.actualizar(nivelDatos);
            }
        }
        if (!estaAlgunoActivo)
            juego.getJugando().cargarSiguienteNivel();
    }

    /**
     * 
     * @param g
     * @param xNivelDesfase 
     */
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

    /**
     * 
     * @param mario 
     */
    public void revisarColision(Jugador mario) {
        float margen = 1.8f;
        for (Enemigo go : enemigos) {
            if (go.estaVivo()) {
                if (mario.hitbox.intersects(go.hitbox.x, go.hitbox.y - 24, go.hitbox.width, go.hitbox.height)) { //Si hay colision

                    //Revisar si es colision por arriba o abajo
                    if (mario.hitbox.y + mario.hitbox.height - margen < go.hitbox.y - 24) { //Choque por abajo de mario
                        mario.setAireVelocidad();
                        go.setVivo(false);
                        // Informar a los otros clientes del cambio
                        PaqueteEnemigo paquete = new PaqueteEnemigo(enemigos);
                        paquete.escribirDatos(juego.getCliente());
                    } else if (mario.hitbox.y + margen > go.hitbox.y - 24 + go.altura) { //Choque por arriba de mario
                        System.out.println("por abajo");
                    }

                    //Revisar si es colision por los lados
                    if (mario.hitbox.x + margen > go.hitbox.x + go.hitbox.width) { //Choque por la izquierda de mario
                        mario.palSpawn();
                        return;
                    } else if (mario.hitbox.x + mario.hitbox.width - margen < go.hitbox.x) { //Choque por la derecha de mario
                        mario.palSpawn();
                        return;
                    }
                }

            }
        }
    }
}
