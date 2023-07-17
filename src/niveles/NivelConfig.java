package niveles;

import entidades.Goomba;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Juego;
import static main.Juego.CASILLAS_HORIZONTAL;
import static main.Juego.CASILLAS_VERTICAL;
import static main.Juego.TAMAÑO_REAL_CASILLAS;
import static utils.UtilsEnemigo.GOOMBA_INDEX;

/**
 *
 * @author rober
 */
public class NivelConfig {

    private Juego juego;
    private BufferedImage[] levelSprite;
    private BufferedImage levelFondo;
    private Nivel nivelUno;

    private static final String SPRITE_PATHS = "SpritesNiveles.png";
    private static final String NIVELES_PATHS = "nivel1grande.png";
    private static final String FONDO_PATHS = "fondo1.png";

    public NivelConfig(Juego juego) {
        this.juego = juego;
        nivelUno = new Nivel(obtenerDatos());
        try {
            levelFondo = ImageIO.read(new File("media/sprites/" + FONDO_PATHS));
            levelSprite = new BufferedImage[40];
            BufferedImage img = ImageIO.read(new File("media/sprites/" + SPRITE_PATHS));
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 10; i++) {
                    int index = j * 10 + i;
                    levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32); //Obtener los 40 sprites
                }
            }

        } catch (IOException e) {
            System.out.println("Error leyendo sprite del nivel");
            System.out.println(e.getMessage());
        }

    }

    /**
     * Dibuja el nivel
     *
     * @param g
     * @param xNivelDesfase
     */
    public void dibujar(Graphics g, int xNivelDesfase) {
        for (int j = 0; j < CASILLAS_VERTICAL; j++) {
            for (int i = 0; i < nivelUno.obtenerNivelDatos()[0].length; i++) {
                int index = nivelUno.obtenerIndiceSprite(i, j);
                g.drawImage(levelSprite[index], TAMAÑO_REAL_CASILLAS * i - xNivelDesfase, TAMAÑO_REAL_CASILLAS * j, TAMAÑO_REAL_CASILLAS, TAMAÑO_REAL_CASILLAS, null);
            }
        }
    }

    /**
     * Actualiza el estado del juego
     */
    public void actualizar() {

    }

    /**
     * Obtener los datos de que debe ir en cada casilla
     *
     * @return
     */
    public static int[][] obtenerDatos() {
        int[][] nivelDatos = null;
        try {
            BufferedImage img = ImageIO.read(new File("media/sprites/" + NIVELES_PATHS));
            nivelDatos = new int[img.getHeight()][img.getWidth()];
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
        } catch (IOException e) {
            System.out.println("Error leyendo datos del nivel");
            System.out.println(e.getMessage());
        }
        return nivelDatos;
    }

    public static ArrayList<Goomba> obtenerGoombas() {
        ArrayList<Goomba> list = new ArrayList<>();
        try {
            BufferedImage img = ImageIO.read(new File("media/sprites/" + NIVELES_PATHS));

            for (int j = 0; j < img.getHeight(); j++) {
                for (int i = 0; i < img.getWidth(); i++) {
                    Color color = new Color(img.getRGB(i, j));
                    int valor = color.getGreen();
                    if (valor == GOOMBA_INDEX) //No existe el sprite
                    {
                        list.add(new Goomba(i * Juego.TAMAÑO_REAL_CASILLAS, j * Juego.TAMAÑO_REAL_CASILLAS));
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

    public Nivel getNivelUno() {
        return nivelUno;
    }

    public void dibujarFondo(Graphics g) {
        g.drawImage(levelFondo, 0, 0, Juego.JUEGO_ANCHO, Juego.JUEGO_ALTO, null);
    }

}
