package niveles;

import entidades.Enemigo;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Juego;
import static utils.UtilsMovimiento.obtenerDatos;
import static utils.UtilsMovimiento.obtenerEnemigos;

/**
 *
 * @author rober
 */
public class Nivel {
    private BufferedImage imagen;
    private int[][] nivelDatos;
    private ArrayList<Enemigo> enemigos;
    private int casillasNivel;
    private int desfaseMaximoCasilla;
    private int desfaseMaximoNivel;
    
    /**
     * 
     * @param imagen 
     */
    public Nivel(BufferedImage imagen){
        this.imagen = imagen;
        crearDatoNivel();
        crearEnemigos();
        desfaseCalculoNivel();
    }
    
    
    private void crearDatoNivel() {
        nivelDatos = obtenerDatos(imagen);
    }
    
    private void crearEnemigos(){
        if(enemigos==null || enemigos.size()==0)
            enemigos = obtenerEnemigos(imagen);
        System.out.println("cantidad de goombas: " + enemigos.size());        
    }
    
    private void desfaseCalculoNivel(){
        casillasNivel = imagen.getWidth();
        desfaseMaximoCasilla = casillasNivel - Juego.CASILLAS_HORIZONTAL;
        desfaseMaximoNivel = desfaseMaximoCasilla * Juego.TAMAÑO_REAL_CASILLAS;
    }
    /**
     * 
     * @param x
     * @param y
     * @return 
     */
    public int obtenerIndiceSprite(int x, int y){
        return nivelDatos[y][x];
    }
    public int[][] obtenerNivelDatos(){
        return nivelDatos;
    }

    public int obtenerDesfaseMaximoNivel() {
        return desfaseMaximoNivel;
    }

    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }
}
