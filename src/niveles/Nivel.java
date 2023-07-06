package niveles;

/**
 *
 * @author rober
 */
public class Nivel {
    private int[][] nivelDatos;
    
    public Nivel(int[][] nivelDatos){
        this.nivelDatos = nivelDatos;
    }
    
    public int obtenerIndiceSprite(int x, int y){
        return nivelDatos[y][x];
    }
    public int[][] obtenerNivelDatos(){
        return nivelDatos;
    }
}
