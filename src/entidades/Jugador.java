package entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import utils.UtilsJugador.*;
import static main.Juego.ESCALA;
import multijugador.Usuario;

/**
 *
 * @author Gabriel
 * @author rober
 */
public class Jugador extends Entidad implements Serializable {

    // Atributos de movimiento
    protected boolean izquierda;
    protected boolean derecha;
    protected boolean arriba;
    protected boolean abajo;

    // Atributos de estado
    protected EstadoJugador estado;
    protected PoderJugador poder;

    // Atributos de multijugador
    protected Usuario usuario;

    // Atributos de animación
    protected int tipo;
    protected int deltaAnimacion;
    protected int indiceAnimacion;
    protected transient BufferedImage img;
    protected transient BufferedImage[][] animacionesCorrer;
    protected transient BufferedImage[] animacionActual;

    protected static final float VELOCIDAD = 1.7f;
    protected static final int VELOCIDAD_ANIMACION = 17;
    protected static final String[] SPRITE_PATHS = {"MarioSprites.png", "LuigiSprites.png", "ToadSprites.png", "ToadetteSprites.png"};

    public Jugador(float x, float y, int tipo, Usuario usuario) {
        super(x, y);
        this.tipo = tipo;
        this.usuario = usuario;

        estado = EstadoJugador.IDLE;
        poder = PoderJugador.NINGUNO;
        deltaAnimacion = 0;
        indiceAnimacion = 0;
        cargarImagenes();
    }

    public boolean isIzquierda() {
        return izquierda;
    }

    public boolean isDerecha() {
        return derecha;
    }

    public boolean isArriba() {
        return arriba;
    }

    public boolean isAbajo() {
        return abajo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public int getTipo() {
        return tipo;
    }

    public EstadoJugador getEstado() {
        return estado;
    }

    public PoderJugador getPoder() {
        return poder;
    }

    public int getDeltaAnimacion() {
        return deltaAnimacion;
    }

    public int getIndiceAnimacion() {
        return indiceAnimacion;
    }

    public void setIzquierda(boolean izquierda) {
        this.izquierda = izquierda;
    }

    public void setDerecha(boolean derecha) {
        this.derecha = derecha;
    }

    public void setArriba(boolean arriba) {
        this.arriba = arriba;
    }

    public void setAbajo(boolean abajo) {
        this.abajo = abajo;
    }

    public void setEstado(EstadoJugador estado) {
        this.estado = estado;
    }

    public void setPoder(PoderJugador poder) {
        this.poder = poder;
    }

    public void setDeltaAnimacion(int deltaAnimacion) {
        this.deltaAnimacion = deltaAnimacion;
    }

    public void setIndiceAnimacion(int indiceAnimacion) {
        this.indiceAnimacion = indiceAnimacion;
    }

    /**
     * Carga el spritesheet y las animaciones de movimiento del jugador
     */
    public final void cargarImagenes() {
        try {
            animacionesCorrer = new BufferedImage[3][3];
            img = ImageIO.read(new File("media/sprites/" + SPRITE_PATHS[tipo]));

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    animacionesCorrer[i][j] = img.getSubimage(j * 32 + 32, i * 64, 32, 64);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo sprite de jugador");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Modifica la posición del jugador dependiendo de la dirección en que se
     * mueve
     */
    protected void actualizarPosicion() {
        if (izquierda && !derecha) {
            x -= VELOCIDAD;
        } else if (derecha && !izquierda) {
            x += VELOCIDAD;
        }

        if (arriba && !abajo) {
            y -= VELOCIDAD;
        } else if (abajo && !arriba) {
            y += VELOCIDAD;
        }

        // Actualizar estado de movimiento para evitar errores
        if ((izquierda ^ derecha) || (arriba ^ abajo)) {
            estado = EstadoJugador.CORRIENDO;
        } else {
            estado = EstadoJugador.IDLE;
        }
    }

    /**
     * Actualiza el frame de animación al correr según pasa el tiempo
     */
    protected void actualizarFrameAnimacion() {
        deltaAnimacion++;
        if (deltaAnimacion >= VELOCIDAD_ANIMACION) {
            deltaAnimacion = 0;
            indiceAnimacion++;
            if (indiceAnimacion >= animacionActual.length) {
                indiceAnimacion = 0;
            }
        }
    }

    /**
     * Guarda en animacionActual el arreglo que contenga la animación del
     * personaje, según su estado y poder
     */
    public void obtenerAnimacion() {
        if (img == null) {
            cargarImagenes();
        }
        int yAnimacion = 0;
        switch (poder) {
            case NINGUNO:
                yAnimacion = 0;
                break;
            case SUPER:
                yAnimacion = 1;
                break;
            case FUEGO:
                yAnimacion = 2;
        }
        switch (estado) {
            case IDLE:
                animacionActual = new BufferedImage[]{img.getSubimage(0, yAnimacion * 64, 32, 64)};
                break;
            case CORRIENDO:
                animacionActual = animacionesCorrer[yAnimacion];
                break;
            case SALTANDO:
                animacionActual = new BufferedImage[]{img.getSubimage(4 * 32, yAnimacion * 64, 32, 64)};
                break;
            case MURIENDO:
                animacionActual = new BufferedImage[]{img.getSubimage(5 * 32, 0, 32, 64)};
                break;
            case ATACANDO:
                animacionActual = new BufferedImage[]{img.getSubimage(5 * 32, 2 * 64, 32, 64)};
                break;
        }
    }

    /**
     * Actualiza valores correspondientes al jugador
     */
    public void actualizar() {
        if (estado == EstadoJugador.CORRIENDO) {
            actualizarFrameAnimacion();
        }
        obtenerAnimacion();
        actualizarPosicion();
    }

    /**
     * Dibuja al jugador en pantalla
     *
     * @param g
     */
    public void render(Graphics g) {
        if (animacionActual == null) {
            obtenerAnimacion();
        }

        if (indiceAnimacion >= animacionActual.length) {
            indiceAnimacion = 0;
        }
        if (izquierda && !derecha) {
            g.drawImage(animacionActual[indiceAnimacion], (int) (x + 32 * ESCALA), (int) y, (int) (-32 * ESCALA), (int) (64 * ESCALA), null);
        } else {
            g.drawImage(animacionActual[indiceAnimacion], (int) x, (int) y, (int) (32 * ESCALA), (int) (64 * ESCALA), null);
        }
    }

    /**
     * Detiene al jugador
     */
    public void resetearEstado() {
        izquierda = derecha = arriba = abajo = false;
    }

}
