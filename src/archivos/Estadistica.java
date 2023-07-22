package archivos;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Gabriel
 */
public class Estadistica implements Serializable, Comparable<Estadistica> {
    
    private String usuario;
    private int jugadas;
    private int ganadas;
    private int perdidas;
    private int abandonadas;

    public Estadistica(String usuario, int jugadas, int ganadas, int perdidas, int abandonadas) {
        this.usuario = usuario;
        this.jugadas = jugadas;
        this.ganadas = ganadas;
        this.perdidas = perdidas;
        this.abandonadas = abandonadas;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getJugadas() {
        return jugadas;
    }

    public void setJugadas(int jugadas) {
        this.jugadas = jugadas;
    }

    public int getGanadas() {
        return ganadas;
    }

    public void setGanadas(int ganadas) {
        this.ganadas = ganadas;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }

    public int getAbandonadas() {
        return abandonadas;
    }

    public void setAbandonadas(int abandonadas) {
        this.abandonadas = abandonadas;
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
        final Estadistica other = (Estadistica) obj;
        return Objects.equals(this.usuario, other.usuario);
    }
    
    

    @Override
    public int compareTo(Estadistica o) {
        return usuario.compareTo(o.usuario);
    }
}
