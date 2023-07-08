package entidades;

import java.io.Serializable;

/**
 *
 * @author Gabriel
 */
public abstract class Entidad implements Serializable {

    protected float x;
    protected float y;

    public Entidad(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
