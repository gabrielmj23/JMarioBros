/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Juego;
import static niveles.NivelConfig.obtenerGoombas;

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
            go.actualizar(nivelDatos);
        }
    }

    public void dibujar(Graphics g, int xNivelDesfase) {
        dibujarGoomba(g, xNivelDesfase);

    }

    private void dibujarGoomba(Graphics g, int xNivelDesfase) {
        for (Goomba go : goombas) {
            g.setColor(Color.black);
            g.drawRect((int) go.hitbox.x - xNivelDesfase, (int) go.hitbox.y-24, go.ancho, go.altura);
        }
    }

}
