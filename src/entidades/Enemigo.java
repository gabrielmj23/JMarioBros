/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import utils.UtilsEnemigo.*;

/**
 *
 * @author rober
 */
abstract class Enemigo extends Entidad {
    
        // Atributos de animación
    private int tipo;
    private int deltaAnimacion;
    private int indiceAnimacion;
    private BufferedImage img;
    private BufferedImage[][] animacionesCorrer;
    private BufferedImage[] animacionActual;
    
    private EstadoEnemigo estado;
    private static final int VELOCIDAD_ANIMACION = 17;
    
     private static final String[] SPRITE_PATHS = {"MarioSprites.png", "LuigiSprites.png", "ToadSprites.png", "ToadetteSprites.png"};

    public Enemigo(float x, float y, int ancho, int altura, int tipo) throws IOException {
        super(x, y, ancho, altura);
       // Cargar spritesheet y animacionesCorrer
        try {
            animacionesCorrer = new BufferedImage[3][3];
            img = ImageIO.read(new File("media/sprites/" + SPRITE_PATHS[tipo]));
            // Cargar animacionesCorrer de caminar
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    animacionesCorrer[i][j] = img.getSubimage(j * 32 + 32, i * 64, 32, 64);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo sprite de jugador");
            System.out.println(e.getMessage());
        }
        
    }
    
        @Override
    public void iniHitbox(float x, float y, int ancho, int altura) {
            hitbox = new Rectangle2D.Float(x + 4, y + 40, ancho + 2, altura - 25);
     
    }
    /**
     * Actualiza el frame de animación al correr según pasa el tiempo
     */
    
    private void actualizarFrameAnimacion() {
        deltaAnimacion++;
        if (deltaAnimacion >= VELOCIDAD_ANIMACION) {
            deltaAnimacion = 0;
            indiceAnimacion++;
            if (indiceAnimacion >= animacionActual.length) {
                indiceAnimacion = 0;
            }
        }
    }

    /**
     * Guarda en animacionActual el arreglo que contenga la animación del
     * personaje, según su estado y poder
     */
    private void obtenerAnimacion() {
        int yAnimacion = 0;
        switch (tipo) {
            case 1:
                yAnimacion = 0;
                break;
            case 2:
                yAnimacion = 1;
                break;
            case 3:
                yAnimacion = 2;
                break;
            case 4:
                yAnimacion = 3;
        }
        switch (estado) {
            case IDLE:
                animacionActual = new BufferedImage[]{img.getSubimage(0, yAnimacion * 64, 32, 64)};
                break;
            case CORRIENDO:
                animacionActual = animacionesCorrer[yAnimacion];
                break;
            case SALTANDO:
                animacionActual = new BufferedImage[]{img.getSubimage(4 * 32, yAnimacion * 64, 32, 64)};
                break;
            case MURIENDO:
                animacionActual = new BufferedImage[]{img.getSubimage(5 * 32, 0, 32, 64)};
                break;
            case ATACANDO:
                animacionActual = new BufferedImage[]{img.getSubimage(5 * 32, 2 * 64, 32, 64)};
                break;
        }
    }
    
    public void actualizar() {
        actualizarFrameAnimacion();
    }
    
    public int obtenerIndiceAnimacion(){
        return indiceAnimacion;
    }
    
    public EstadoEnemigo obtenerEnemigoEstado(){
        return estado;
    }
    
    
    
}
