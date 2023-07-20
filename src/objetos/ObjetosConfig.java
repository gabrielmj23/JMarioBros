/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

import entidades.Goomba;
import entidades.Jugador;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.Juego;
import static niveles.NivelConfig.obtenerBloquesInteractivos;
import static niveles.NivelConfig.obtenerPoderes;
import utils.UtilsJugador.PoderJugador;
import static utils.UtilsJugador.PoderJugador.*;
import static utils.UtilsObjetos.*;

/**
 *
 * @author rober
 */
public class ObjetosConfig {

    private Juego juego;

    private BufferedImage[] objetosImg;
    private ArrayList<BloqueInteractivo> bloques = new ArrayList<>();
    private ArrayList<Poder> poderes = new ArrayList<>();

    private static final String SPRITE_PATHS = "Objetos.png";

    public ObjetosConfig(Juego juego) {
        this.juego = juego;

        bloques = obtenerBloquesInteractivos();
        poderes = obtenerPoderes();

        try {
            objetosImg = new BufferedImage[8];
            BufferedImage img = ImageIO.read(new File("media/sprites/" + SPRITE_PATHS));
            for (int i = 0; i < 8; i++) {
                objetosImg[i] = img.getSubimage(i * 32, 0 * 32, 32, 32); //Obtener los 8 sprites
            }

        } catch (IOException e) {
            System.out.println("Error leyendo sprite de objetos");
            System.out.println(e.getMessage());
        }

    }

    public void actualizar() {
        for (BloqueInteractivo b : bloques) {
            if (b.isActivo()) {
                b.actualizar();
            }
        }

        for (Poder p : poderes) {
            if (p.isActivo()) {
                p.actualizar();
            }
        }
    }

    public void dibujar(Graphics g, int xNivelDesfase) {
        dibujarObjetos(g, xNivelDesfase);
        dibujarBloques(g, xNivelDesfase);

    }

    private void dibujarBloques(Graphics g, int xNivelDesfase) {
        for (BloqueInteractivo b : bloques) {
            if (b.getTipo() == LUCKYBLOCK_INDEX) { //Dibujar lucky blocks
                if (b.isActivo()) {
                    g.drawImage(objetosImg[LUCKYBLOCK_INDEX], (int) b.hitbox.x - xNivelDesfase, (int) b.hitbox.y, Juego.TAMAÑO_REAL_CASILLAS, Juego.TAMAÑO_REAL_CASILLAS, null);
                } else {
                    g.drawImage(objetosImg[LUCKYBLOCK_INDEX + 1], (int) b.hitbox.x - xNivelDesfase, (int) b.hitbox.y, Juego.TAMAÑO_REAL_CASILLAS, Juego.TAMAÑO_REAL_CASILLAS, null);
                }
            } else if (b.isActivo()) //Dibujar ladrillos
            {
                g.drawImage(objetosImg[b.getTipo() + 1], (int) b.hitbox.x - xNivelDesfase, (int) b.hitbox.y, Juego.TAMAÑO_REAL_CASILLAS, Juego.TAMAÑO_REAL_CASILLAS, null);
            }
        }

    }

    private void dibujarObjetos(Graphics g, int xNivelDesfase) {
        for (Poder p : poderes) {
            if (p.isActivo()) {
                g.drawImage(objetosImg[p.getTipo()], (int) p.hitbox.x - xNivelDesfase, (int) p.hitbox.y, Juego.TAMAÑO_REAL_CASILLAS, Juego.TAMAÑO_REAL_CASILLAS, null);
            }
        }
    }

    public void revisarPoderTocado(Jugador mario) {
        
        for (Poder p : poderes) {
            PoderJugador poderActual = mario.getPoder();
            if (p.isActivo()) {
                if (mario.getHitbox().intersects(p.hitbox)) {
                    p.setActivo(false);
                    if (p.getTipo() == HONGO_INDEX && mario.getPoder() != FUEGO) {
                        mario.setPoder(SUPER);
                    } else if (p.getTipo() == FLOR_INDEX) {
                        mario.setPoder(FUEGO);
                    }
                    if(poderActual == NINGUNO && poderActual != mario.getPoder()) //Si el poder de mario cambio de chikito a super/fuego ajustar hitbox
                    mario.iniHitbox((int)mario.getHitbox().x, (int) mario.getHitbox().y -45,  mario.getAncho(), mario.getAltura());
                    if(p.getTipo() == MONEDA_INDEX) {
                        //SUMAR PUNTAJE??
                    }
                }
            }

        }
    }
}
