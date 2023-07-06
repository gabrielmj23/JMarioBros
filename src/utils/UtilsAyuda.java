/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import main.Juego;

/**
 *
 * @author rober
 */
public class UtilsAyuda {

    public static boolean PuedeMoverse(float x, float y, int ancho, int largo, int[][] nivelDatos) {
        if (!EsSolido(x, y, nivelDatos)) {
            if (!EsSolido(x + ancho, y + largo, nivelDatos)) {
                if (!EsSolido(x + ancho, y, nivelDatos)) {
                    if (!EsSolido(x, y + largo, nivelDatos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean EsSolido(float x, float y, int[][] nivelDatos) {
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
