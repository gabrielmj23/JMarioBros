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
import main.Juego;
import utils.UtilsEnemigo.*;
import static utils.UtilsEnemigo.EstadoEnemigo.*;
import static utils.UtilsMovimiento.*;

/**
 *
 * @author rober
 */
abstract class Enemigo extends Entidad {

    // Atributos de animación
    private int tipo;
    private int deltaAnimacion;
    private int indiceAnimacion;

    private boolean primeraActualizacion = true;
    private boolean enAire ;
    private float aireVelocidad = 0f;
    private float gravedad = 0.04f * Juego.ESCALA;
    private float velocidad = 0.5f;
    private String direccion = "izquierda";

    private EstadoEnemigo estado = CORRIENDO;
    private boolean vivo = true;
    private static final int VELOCIDAD_ANIMACION = 17;

    public Enemigo(float x, float y, int ancho, int altura, int tipo) throws IOException {
        super(x, y, ancho, altura);
        iniHitbox(x, y, ancho, altura);

    }

    @Override
    public void iniHitbox(float x, float y, int ancho, int altura) {
        hitbox = new Rectangle2D.Float(x + 4, y + 40, ancho + 2, altura - 25);

    }

    private void actualizarMovimiento(int[][] nivelDatos) {

        if (primeraActualizacion) {
            if (!EstaEnSuelo(hitbox, nivelDatos)) {
                enAire = true;
            }
  
            primeraActualizacion = false;
        }
        if (enAire) {
            if (puedeMoverse(hitbox.x, hitbox.y + aireVelocidad, hitbox.width, hitbox.height, nivelDatos)) {
                hitbox.y += aireVelocidad;
                aireVelocidad += gravedad;
            } else {
                enAire = false;
                hitbox.y = obtenerYPosLimite(hitbox, aireVelocidad);
            
            }
        } else {
            switch (estado) {
                case IDLE:
                    estado = CORRIENDO;
                    break;
                case CORRIENDO:
                    float xVelocidad = 0;
                  
                    if (direccion.equals("izquierda")) {
                        xVelocidad = -velocidad;
                    } else {
                        xVelocidad = velocidad;
                    }
                    
                    if (puedeMoverse(hitbox.x + xVelocidad, hitbox.y, hitbox.width, hitbox.height, nivelDatos)) {
                        if (EsPiso(hitbox, xVelocidad, nivelDatos)) {
                            hitbox.x += xVelocidad;
                            return;
                        }
                    }
                    cambiarDireccion();
            }

        }

    }

    private void cambiarDireccion() {
        if (direccion.equals("izquierda")) {
            direccion = "derecha";
        } else {
            direccion = "izquierda";
        }

    }

    /**
     * Actualiza el frame de animación al correr según pasa el tiempo
     */
    private void actualizarFrameAnimacion() {
        deltaAnimacion++;
        if (deltaAnimacion >= VELOCIDAD_ANIMACION) {
            deltaAnimacion = 0;
            indiceAnimacion++;
            if (indiceAnimacion >= 7) {
                indiceAnimacion = 0;
            }
        }
    }

    public void actualizar(int[][] nivelDatos) {
        actualizarMovimiento(nivelDatos);
        actualizarFrameAnimacion();
    }

    public int obtenerIndiceAnimacion() {
        return indiceAnimacion;
    }

    public EstadoEnemigo obtenerEnemigoEstado() {
        return estado;
    }
    
    public boolean estaVivo(){
        return vivo;
    }

}
