package entidades;

/**
 *
 * @author Gabriel
 */
public abstract class Entidad {

    protected float x;
    protected float y;
    int escala;

    public Entidad(float x, float y, int escala) {
        this.x = x;
        this.y = y;
        this.escala = escala;
    }
}
