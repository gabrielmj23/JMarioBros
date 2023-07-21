package objetos;

/**
 *
 * @author rober
 */
public class Poder extends ObjetoJuego {
    
    public Poder(int x, int y , int tipo){
        super(x, y, tipo);
        animado = false;
        iniHitbox(32, 37);
    }
    
    public void actualizar(){
        actualizarFrameAnimacion();
    }
    
}
