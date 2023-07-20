/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Juego;
import static niveles.NivelConfig.obtenerGoombas;
import static utils.UtilsJugador.PoderJugador.*;

/**
 *
 * @author rober
 */
public class EnemigosConfig {

    private Juego juego;
    private ArrayList<Goomba> goombas = new ArrayList<>();

    public EnemigosConfig(Juego juego) {
        this.juego = juego;

        goombas = obtenerGoombas();
        System.out.println("cantidad de goombas: " + goombas.size());

    }

    public void actualizar(int[][] nivelDatos) {
        for (Goomba go : goombas) {
            if (go.estaVivo()) {
                go.actualizar(nivelDatos);
            }
        }
    }

    public void dibujar(Graphics g, int xNivelDesfase) {
        dibujarGoomba(g, xNivelDesfase);

    }

    private void dibujarGoomba(Graphics g, int xNivelDesfase) {
        for (Goomba go : goombas) {
            if (go.estaVivo()) {
                {
                    g.setColor(Color.black);
                    g.fillRect((int) go.hitbox.x - xNivelDesfase, (int) go.hitbox.y - 24, go.ancho, go.altura);
                }
            }
        }
    }

    public void revisarColision(Jugador mario) {
        float margen = 1.8f;
        for (Goomba go : goombas) {
            if (go.estaVivo()) {
                if (mario.hitbox.intersects(go.hitbox.x, go.hitbox.y - 24, go.hitbox.width, go.hitbox.height)) { //Si hay colision

                    //Revisar si es colision por arriba o abajo
                    if (mario.hitbox.y + mario.hitbox.height - margen < go.hitbox.y - 24) { //Choque por abajo de mario
                        mario.setAireVelocidad();
                        go.setVivo(false);

                    } else if (mario.hitbox.y + margen > go.hitbox.y - 24 + go.altura) { //Choque por arriba de mario
                        System.out.println("por abajo");
                    }

                    //Revisar si es colision por los lados
                    if (mario.hitbox.x + margen > go.hitbox.x + go.hitbox.width || mario.hitbox.x + mario.hitbox.width - margen < go.hitbox.x) { //Choque por la izquierda o derecha de mario
                        if (mario.getPoder() == SUPER) {
                            mario.setPoder(NINGUNO);
                            mario.iniHitbox(mario.hitbox.x, mario.hitbox.y, mario.ancho, mario.altura);
                        } else if (mario.getPoder() == FUEGO) {
                            mario.setPoder(SUPER);
                        }
                     
                        return;
                    }
                }
            }

        }
    }

}


