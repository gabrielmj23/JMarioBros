package utils;

import entidades.Enemigo;
import entidades.Goomba;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.Juego;
import static utils.UtilsEnemigo.GOOMBA_INDEX;
import static utils.UtilsEnemigo.KOOPAR_INDEX;
import static utils.UtilsEnemigo.KOOPAV_INDEX;
import static utils.UtilsEnemigo.PLANTA_INDEX;

/**
 *
 * @author rober
 */
public class UtilsMovimiento {

    /**
     *
     * Revisa si una entidad puede moverse a una posicion destino
     *
     * @param xDestino
     * @param yDestino
     * @param anchoHitbox
     * @param alturaHitbox
     * @param nivelDatos
     * @return
     */
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

    /**
     * Revisa si un bloque es solido
     *
     * @param x
     * @param y
     * @param nivelDatos
     * @return
     */
    private static boolean esSolido(float x, float y, int[][] nivelDatos) {
        int anchoMaximo = nivelDatos[0].length * Juego.TAMAÑO_REAL_CASILLAS;
        if (x < 0 || x >= anchoMaximo) {
            return true;
        }
        if (y < 0 || y >= Juego.JUEGO_ALTO) {
            return true;
        }

        float xIndice = x / Juego.TAMAÑO_REAL_CASILLAS;
        float yIndice = y / Juego.TAMAÑO_REAL_CASILLAS;

        int value = nivelDatos[(int) yIndice][(int) xIndice];

        if (value >= 40 || value < 0 || value != 0) {
            return true;
        }
        return false;
    }

    /**
     * Devuelve la posicion pegada a una pared
     *
     * @param hitbox
     * @param xVelocidad
     * @return
     */
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

    /**
     * Devuelve la posicion pegada al techo o al piso
     *
     * @param hitbox
     * @param aireVelocidad
     * @return
     */
    public static float obtenerYPosLimite(Rectangle2D.Float hitbox, float aireVelocidad) {
        int casillaActual = (int) (hitbox.y / Juego.TAMAÑO_REAL_CASILLAS);
        if (aireVelocidad > 0) {
            //cayendo - piso
            int casillaY = casillaActual * Juego.TAMAÑO_REAL_CASILLAS;
            int yDesfase = (int) (Juego.TAMAÑO_REAL_CASILLAS - hitbox.height);
            return casillaY + yDesfase - 1;
        } else {
            //Saltando
            return casillaActual * Juego.TAMAÑO_REAL_CASILLAS;

        }
    }

    /**
     * Revisa si una entidad esta en el suelo
     *
     * @param hitbox
     * @param nivelDatos
     * @return
     */
    public static boolean EstaEnSuelo(Rectangle2D.Float hitbox, int[][] nivelDatos) {
        //Revisar las esquinas inferiores
        if (!esSolido(hitbox.x, hitbox.y + hitbox.height + 1, nivelDatos)) {
            if (!esSolido(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, nivelDatos)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @param hitbox
     * @param xVelocidad
     * @param nivelDatos
     * @return 
     */
    public static boolean EsPiso(Rectangle2D.Float hitbox, float xVelocidad, int[][] nivelDatos) {
        return (esSolido(hitbox.x + xVelocidad, hitbox.y + hitbox.height + 1, nivelDatos) && esSolido(hitbox.x + hitbox.width + xVelocidad, hitbox.y + hitbox.height + 1, nivelDatos));
    }
    
    /**
     * 
     * @param img
     * @return 
     */
    public static int[][] obtenerDatos(BufferedImage img) {
        int[][] nivelDatos = new int[img.getHeight()][img.getWidth()];
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int valor = color.getRed();
                if (valor >= 40) //No existe el sprite
                {
                    valor = 0;
                }
                nivelDatos[j][i] = valor;
            }
        }
        return nivelDatos;
    }
    
    /**
     * 
     * @param img
     * @return 
     */
    public static ArrayList<Enemigo> obtenerEnemigos(BufferedImage img) {
        ArrayList<Enemigo> list = new ArrayList<>();
        try {
            for (int j = 0; j < img.getHeight(); j++) {
                for (int i = 0; i < img.getWidth(); i++) {
                    Color color = new Color(img.getRGB(i, j));
                    int valor = color.getGreen();
                    if (valor == GOOMBA_INDEX) {
                        list.add(new Goomba(i * Juego.TAMAÑO_REAL_CASILLAS, j * Juego.TAMAÑO_REAL_CASILLAS));
                    } else if (valor == KOOPAR_INDEX) {
                        //list.add(new KoopaR(i * Juego.TAMAÑO_REAL_CASILLAS, j * Juego.TAMAÑO_REAL_CASILLAS));
                    } else if (valor == KOOPAV_INDEX) {
                        //list.add(new KoopaV(i * Juego.TAMAÑO_REAL_CASILLAS, j * Juego.TAMAÑO_REAL_CASILLAS));
                    } else if (valor == PLANTA_INDEX) {
                        // list.add(new Planta(i * Juego.TAMAÑO_REAL_CASILLAS, j * Juego.TAMAÑO_REAL_CASILLAS));
                    }
                }
            }
            return list;
        } catch (IOException e) {
            System.out.println("Error leyendo datos del nivel");
            System.out.println(e.getMessage());
        }
        return list;
    }
}
