package utils;

import main.Juego;

/**
 *
 * @author rober
 */
public class UtilsMovimiento {

    public static boolean puedeMoverse(float xDestino, float yDestino, int anchoHitbox, int alturaHitbox, int[][] nivelDatos) {
        if (!esSolido(xDestino, yDestino, nivelDatos)) {
            if (!esSolido(xDestino + anchoHitbox, yDestino + alturaHitbox, nivelDatos)) {
                if (!esSolido(xDestino + anchoHitbox, yDestino, nivelDatos)) {
                    if (!esSolido(xDestino, yDestino + alturaHitbox, nivelDatos)) {
                        return true;
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

}
