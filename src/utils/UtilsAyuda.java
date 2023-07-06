package utils;

import main.Juego;

/**
 *
 * @author rober
 */
public class UtilsAyuda {

    public static boolean puedeMoverse(float x, float y, int ancho, int largo, int[][] nivelDatos) {
        if (!esSolido(x, y, nivelDatos)) {
            if (!esSolido(x + ancho, y + largo, nivelDatos)) {
                if (!esSolido(x + ancho, y, nivelDatos)) {
                    if (!esSolido(x, y + largo, nivelDatos)) {
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
