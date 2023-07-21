package niveles;

import entidades.Enemigo;
import entidades.Goomba;
import estadojuego.EstadoJuego;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Juego;
import static main.Juego.CASILLAS_HORIZONTAL;
import static main.Juego.CASILLAS_VERTICAL;
import static main.Juego.TAMAÑO_REAL_CASILLAS;
import static utils.UtilsEnemigo.*;
import static utils.UtilsMovimiento.obtenerDatos;

/**
 *
 * @author rober
 */
public class NivelConfig {

    private Juego juego;
    private BufferedImage[] levelSprite;
    private BufferedImage levelFondo;
    private ArrayList<Nivel> niveles;
    private int nivelContActual = 0;

    private static final String SPRITE_PATHS = "SpritesNiveles.png";
    private static final String NIVELES_PATHS = "1.png";
    private static final String FONDO_PATHS = "fondo1.png";

    public NivelConfig(Juego juego) {
        this.juego = juego;
        niveles= new ArrayList<>();
        crearNiveles();
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
    
    public void cargarSiguienteNivel() {
        nivelContActual++;
        if(nivelContActual >= niveles.size()){
            nivelContActual = 0;
            System.out.println("No hay mas niveles, se completaron todos!!!");
            EstadoJuego.estado = EstadoJuego.MENU;
        }
        
        Nivel nuevoNivel = niveles.get(nivelContActual);
        juego.getJugando().getEnemigosConfig().cargarEnemigos(nuevoNivel);
        juego.getJugando().getJugador().cargarNivelDatos(nuevoNivel.obtenerNivelDatos());
        juego.getJugando().setDesfaseMaximoNivel(nuevoNivel.obtenerDesfaseMaximoNivel());
    }
    
    private void crearNiveles(){
        BufferedImage[] todosNiveles = obtenerNiveles();
        for(BufferedImage img: todosNiveles){
            niveles.add(new Nivel(img));
            System.out.println("ELEPEPEPEPEPEPEPE");
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
            for (int i = 0; i < niveles.get(nivelContActual).obtenerNivelDatos()[0].length; i++) {
                int index = niveles.get(nivelContActual).obtenerIndiceSprite(i, j);
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
    
    
    public static BufferedImage[] obtenerNiveles() {
        File archivo = new File("media/sprites/niv/");
        
        File[] archivos = archivo.listFiles();
        File[] archivosOrdenados = new File[archivos.length];
        
        for(int i = 0; i < archivosOrdenados.length; i++)
            for(int j = 0; j < archivos.length; j++){
                if(archivos[j].getName().equals((i+1) + ".png"))
                    archivosOrdenados[i] = archivos[j];
            }
        BufferedImage[] imagen = new BufferedImage[archivosOrdenados.length];
        
        for(int i = 0;i < imagen.length; i++)
            try {
                imagen[i] = ImageIO.read(archivosOrdenados[i]);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        
        return imagen;
    }    

    public Nivel obtenerNivelActual() {
        return niveles.get(nivelContActual);
    }
    
    /**
     * 
     * @param g 
     */
    public void dibujarFondo(Graphics g) {
        g.drawImage(levelFondo, 0, 0, Juego.JUEGO_ANCHO, Juego.JUEGO_ALTO, null);
    }
    
    public int obtenerCantidadNiveles(){
        return niveles.size();
    }
}
