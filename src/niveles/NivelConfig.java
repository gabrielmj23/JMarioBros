/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niveles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Juego;
import static main.Juego.CASILLAS_HORIZONTAL;
import static main.Juego.CASILLAS_VERTICAL;
import static main.Juego.TAMAÑO_REAL_CASILLAS;
import utils.LoadSave;

/**
 *
 * @author rober
 */
public class NivelConfig {

    private Juego juego;
    private BufferedImage[] levelSprite;
    private Nivel nivelUno;

    private static final String SPRITE_PATHS = "SpritesNiveles.png";
    private static final String NIVELES_PATHS = "nivel1.png";

    public NivelConfig(Juego juego) {
        this.juego = juego;
        nivelUno = new Nivel(obtenerDatos());
        try {
            levelSprite = new BufferedImage[40];
            BufferedImage img = ImageIO.read(new File("media/sprites/" + SPRITE_PATHS));
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 10; i++) {
                    int index = j * 10 + i;
                    levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
                }
            }

        } catch (IOException e) {
            System.out.println("Error leyendo sprite del nivel");
            System.out.println(e.getMessage());
        }

    }

    public void draw(Graphics g) {
        for(int j = 0; j < CASILLAS_VERTICAL; j++)
            for(int i=0; i < CASILLAS_HORIZONTAL; i++){
                int index = nivelUno.obtenerIndiceSprite(i, j);
                g.drawImage(levelSprite[index], TAMAÑO_REAL_CASILLAS*i, TAMAÑO_REAL_CASILLAS*j, TAMAÑO_REAL_CASILLAS,TAMAÑO_REAL_CASILLAS, null);
            }
        
    }

    public void update() {

    }

    public static int[][] obtenerDatos() {
        int[][] nivelDatos = new int[CASILLAS_VERTICAL][CASILLAS_HORIZONTAL];
        try {
            BufferedImage img = ImageIO.read(new File("media/sprites/" + NIVELES_PATHS));
            for (int j = 0; j < img.getHeight(); j++) {
                for (int i = 0; i < img.getWidth(); i++) {
                    Color color = new Color(img.getRGB(i, j));
                    int valor = color.getRed();
                    if (valor >= 40) //No existe el sprite
                        valor = 0;
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

}
