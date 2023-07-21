package objetos;

import entidades.Goomba;
import entidades.Jugador;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import main.Juego;
import static niveles.NivelConfig.obtenerBloquesInteractivos;
import static niveles.NivelConfig.obtenerPoderes;
import static niveles.NivelConfig.obtenerCanones;
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
    private BufferedImage[] canonImg;
    private ArrayList<BloqueInteractivo> bloques = new ArrayList<>();
    private ArrayList<Poder> poderes = new ArrayList<>();
    private ArrayList<Canon> canones = new ArrayList<>();
    private ArrayList<Proyectil> proyectiles = new ArrayList<>();
    private static final String SPRITE_PATHS = "Objetos.png";
    private static final String CANON_PATHS = "Proyectiles.png";

    public ObjetosConfig(Juego juego) {
        this.juego = juego;

        bloques = obtenerBloquesInteractivos();
        poderes = obtenerPoderes();
        canones = obtenerCanones();

        try {
            objetosImg = new BufferedImage[8];
            canonImg = new BufferedImage[3];
            BufferedImage img = ImageIO.read(new File("media/sprites/" + SPRITE_PATHS));
            BufferedImage img2 = ImageIO.read(new File("media/sprites/" + CANON_PATHS));
            for (int i = 0; i < 8; i++) {
                objetosImg[i] = img.getSubimage(i * 32, 0 * 32, 32, 32); //Obtener los 8 sprites
            }
            for (int i = 0; i < 3; i++) {
                canonImg[i] = img2.getSubimage(i * 32, 0 * 32, 32, 32);
            }

        } catch (IOException e) {
            System.out.println("Error leyendo sprite de objetos");
            System.out.println(e.getMessage());
        }

    }

    public void actualizar(Jugador mario) {
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
        actualizarCanones(mario);
        actualizarProyectiles(mario);
    }
   
    private void actualizarCanones(Jugador mario) {
        for (Canon c : canones) {
            if (c.getEnfriamiento() == 0) {
             
                if (c.getCasillaY() == (int)(mario.getHitbox().y/ Juego.TAMAÑO_REAL_CASILLAS) || c.getCasillaY() - 1 == (int)(mario.getHitbox().y / Juego.TAMAÑO_REAL_CASILLAS)) {
                    if (jugadorEnRango(c, mario)) {
                        dispararCanon(c,jugadorDireccionCanon(c, mario));
                    }
                }
            }
            c.actualizar();           
        }
    }
    
    private void actualizarProyectiles(Jugador mario){
        for(Proyectil p : proyectiles)
            if(p.estaActivo())
                p.actualizarPosicion();
        
    }
   
    private void dispararCanon(Canon c, int dir){
        System.out.println("Disparo");
        proyectiles.add(new Proyectil ((int)c.hitbox.x, (int) c.hitbox.y, dir));
        c.setEnfriamiento(800);
    }

    private boolean jugadorEnRango(Canon c, Jugador mario) {
        int absValor = (int) Math.abs(mario.getHitbox().x - c.hitbox.x);
        return absValor <= Juego.TAMAÑO_REAL_CASILLAS * 8;
    }
    
    private int jugadorDireccionCanon(Canon c, Jugador mario) {
        if(mario.getHitbox().x - c.hitbox.x > 0)
            return 1;
        else
            return -1;
    }

    public void dibujar(Graphics g, int xNivelDesfase) {
        dibujarObjetos(g, xNivelDesfase);
        dibujarBloques(g, xNivelDesfase);
        dibujarProyectiles(g, xNivelDesfase);
        dibujarCanones(g, xNivelDesfase);

    }
    
    private void dibujarProyectiles(Graphics g, int xNivelDesfase) {
        for(Proyectil p : proyectiles)
            if (p.estaActivo()){
                int derechaDesfase = 0;
                if(p.getDir() == 1)
                     derechaDesfase = (int) p.getHitbox().width;
                g.drawImage(canonImg[2], (int) (p.getHitbox().x - xNivelDesfase + derechaDesfase), (int) p.getHitbox().y, 30 * -p.getDir(), 30, null);
                g.drawRect((int) p.getHitbox().x - xNivelDesfase, (int) p.getHitbox().y, (int) p.getHitbox().width, (int) p.getHitbox().height);
            }    
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
                g.drawImage(objetosImg[b.getTipo()], (int) b.hitbox.x - xNivelDesfase, (int) b.hitbox.y, Juego.TAMAÑO_REAL_CASILLAS, Juego.TAMAÑO_REAL_CASILLAS, null);
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

    private void dibujarCanones(Graphics g, int xNivelDesfase) {
        for (Canon c : canones) {
            if (c.isActivo()) {
                g.drawImage(canonImg[0], (int) c.hitbox.x - xNivelDesfase, (int) c.hitbox.y, Juego.TAMAÑO_REAL_CASILLAS, Juego.TAMAÑO_REAL_CASILLAS, null);
                g.drawImage(canonImg[1], (int) c.hitbox.x - xNivelDesfase, (int) c.hitbox.y + Juego.TAMAÑO_REAL_CASILLAS, Juego.TAMAÑO_REAL_CASILLAS, Juego.TAMAÑO_REAL_CASILLAS, null);
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
                    if (poderActual == NINGUNO && poderActual != mario.getPoder()) //Si el poder de mario cambio de chikito a super/fuego ajustar hitbox
                    {
                        mario.iniHitbox((int) mario.getHitbox().x, (int) mario.getHitbox().y - 45, mario.getAncho(), mario.getAltura());
                    }
                    if (p.getTipo() == MONEDA_INDEX) {
                        //SUMAR PUNTAJE??
                    }
                }
            }

        }
    }

    public void revisarBloqueTocado(Jugador mario, int[][] nivelDatos) {
        Random rand = new Random();
        for (BloqueInteractivo b : bloques) {

            if (mario.getHitbox().intersects(b.hitbox.x, b.hitbox.y + 2, b.hitbox.width, b.hitbox.height)) {
                if (mario.getAireVelocidad() != 0) {
                    if (mario.getHitbox().y > b.hitbox.y + b.hitbox.height - 1) { //Choque por arriba de mario
                        //Coordenadas del bloque
                        int j = b.y / Juego.TAMAÑO_REAL_CASILLAS;
                        int i = b.x / Juego.TAMAÑO_REAL_CASILLAS;
                        if (b.getTipo() != LUCKYBLOCK_INDEX) {
                            nivelDatos[j][i] = mario.getNivelDatos()[j][i] = 0;
                        } else {
                            nivelDatos[j][i] = mario.getNivelDatos()[j][i] = 4;
                        }
                        if (b.isActivo()) {
                            mario.setAireVelocidad(mario.getVelocidadCaidaColision());
                        }
                        if (b.isActivo() && b.getTipo() == LUCKYBLOCK_INDEX) {
                            poderes.add(new Poder(b.x, (int) (b.y - b.hitbox.height), rand.nextInt(2)));
                        }
                        b.setActivo(false);

                    }
                }
            }
        }
    }
}
