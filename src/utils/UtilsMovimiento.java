package utils;

import java.awt.geom.Rectangle2D;
import main.Juego;

/**
 *
 * @author rober
 */
public class UtilsMovimiento {

    public static boolean puedeMoverse(float xDestino, float yDestino, float anchoHitbox, float alturaHitbox, int[][] nivelDatos) {
        if (!esSolido(xDestino, yDestino, nivelDatos)) {
            if (!esSolido(xDestino + anchoHitbox, yDestino + alturaHitbox, nivelDatos)) {
                if (!esSolido(xDestino + anchoHitbox, yDestino, nivelDatos)) {
                    if (!esSolido(xDestino, yDestino + alturaHitbox, nivelDatos)) {
                        if (!esSolido(xDestino, yDestino + alturaHitbox / 2, nivelDatos)) {
                            if (!esSolido(xDestino + anchoHitbox, yDestino + alturaHitbox / 2, nivelDatos)) {
                                if (!esSolido(xDestino + anchoHitbox / 2, yDestino + alturaHitbox, nivelDatos)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean esSolido(float x, float y, int[][] nivelDatos) {
        if (x < 0 || x >= Juego.JUEGO_ANCHO) {
            return true;
        }
        if (y < 0 || y >= Juego.JUEGO_ALTO) {
            return true;
        }

        float xIndice = x / Juego.TAMAÑO_REAL_CASILLAS;
        float yIndice = y / Juego.TAMAÑO_REAL_CASILLAS;

        int value = nivelDatos[(int) yIndice][(int) xIndice];

        if (value >= 40 || value < 0 || value != 11) {
            return true;
        }
        return false;
    }

    public static float ObtenerXPosLimite(Rectangle2D.Float hitbox, float xVelocidad) {
        int casillaActual = (int) (hitbox.x / Juego.TAMAÑO_REAL_CASILLAS);
        if (xVelocidad > 0) {
            //Derecha
            int casillaX = casillaActual * Juego.TAMAÑO_REAL_CASILLAS;
            int xDesfase = (int) (Juego.TAMAÑO_REAL_CASILLAS - hitbox.width);
            return casillaX + xDesfase - 1;
        } else {
            //Izquierda
            return casillaActual * Juego.TAMAÑO_REAL_CASILLAS;

        }
    }

    public static float obtenerYPosLimite(Rectangle2D.Float hitbox, float aireVelocidad) {
        int casillaActual = (int) (hitbox.y / Juego.TAMAÑO_REAL_CASILLAS);
        if (aireVelocidad > 0) {
            //cayendo - piso
            int casillaY = casillaActual * Juego.TAMAÑO_REAL_CASILLAS;
            int yDesfase = (int) (Juego.TAMAÑO_REAL_CASILLAS*2 - hitbox.height);
            return casillaY + yDesfase - 1;
        } else {
            //Saltando
            return casillaActual * Juego.TAMAÑO_REAL_CASILLAS;

        }
    }

    public static boolean EstaEnSuelo(Rectangle2D.Float hitbox, int[][] nivelDatos) {
        //Revisar las esquinas inferiores
        if (!esSolido(hitbox.x, hitbox.y + hitbox.height + 1, nivelDatos)) {
            if (!esSolido(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, nivelDatos)) {
                return false;
            }
        }
        return true;
    }

}
