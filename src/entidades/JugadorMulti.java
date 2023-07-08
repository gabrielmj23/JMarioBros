package entidades;

import java.io.Serializable;
import java.net.InetAddress;
import multijugador.Usuario;

/**
 *
 * @author Gabriel
 */
public class JugadorMulti extends Jugador implements Serializable {

    private int puerto;
    private InetAddress ip;

    public JugadorMulti(float x, float y, int tipo, Usuario usuario, InetAddress ip, int puerto) {
        super(x, y, tipo, usuario);
        this.ip = ip;
        this.puerto = puerto;
    }

    public int getPuerto() {
        return puerto;
    }

    public InetAddress getIp() {
        return ip;
    }

}
