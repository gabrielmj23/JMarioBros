package entidades;

import java.net.InetAddress;
import multijugador.Usuario;

/**
 *
 * @author Gabriel
 */
public class JugadorMulti extends Jugador {

    private int puerto;
    private InetAddress ip;

    public JugadorMulti(float x, float y, int tipo, Usuario usuario, InetAddress ip, int puerto, boolean local) {
        super(x, y, tipo, usuario, local);
        this.ip = ip;
        this.puerto = puerto;
    }

}
