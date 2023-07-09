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
    
    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
    
    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JugadorMulti other = (JugadorMulti) obj;
        return usuario.equals(other.usuario);
    }
}
