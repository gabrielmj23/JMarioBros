package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import main.Juego;
import utils.UtilsJugador.*;
import static main.Juego.ESCALA;
import multijugador.Usuario;
import static utils.UtilsMovimiento.puedeMoverse;
import static utils.UtilsMovimiento.*;

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
    protected float velocidad;
    protected boolean salto;

    //Salto y gravedad
    protected float aireVelocidad = 0f;
    protected float gravedad = 0.04f * Juego.ESCALA;
    protected float saltoVelocidad = -2.25f * Juego.ESCALA;
    protected float velocidadCaidaColision = 0.5f * Juego.ESCALA;
    protected boolean enVuelo = false;

    // Atributos de estado
    protected EstadoJugador estado;
    protected PoderJugador poder;
    protected int invencible = 0;

    // Atributos de nivel
    private transient int[][] nivelDatos;

    // Atributos de multijugador
    protected Usuario usuario;

    // Atributos de animación
    protected int tipo;
    protected int deltaAnimacion;
    protected int indiceAnimacion;
    protected transient BufferedImage img;
    protected transient BufferedImage[][] animacionesCorrer;
    protected transient BufferedImage[] animacionActual;

    protected static final int VELOCIDAD_ANIMACION = 17;
    protected static final String[] SPRITE_PATHS = {"MarioSprites.png", "LuigiSprites.png", "ToadSprites.png", "ToadetteSprites.png"};

    public Jugador(float x, float y, int tipo, Usuario usuario) {
        super(x, y, 30, 62);
        this.tipo = tipo;
        this.usuario = usuario;
        velocidad = 1.7f;
        estado = EstadoJugador.IDLE;
        poder = PoderJugador.FUEGO;
        deltaAnimacion = 0;
        indiceAnimacion = 0;
        iniHitbox(x, y, ancho, altura);
        cargarImagenes();
    }

    @Override
    public void iniHitbox(float x, float y, int ancho, int altura) {
        if (poder == PoderJugador.NINGUNO) //Acomodar la hitbox a mario chikito
        {
            hitbox = new Rectangle2D.Float(x + 4, y + 40, ancho + 2, altura - 25);
        } else {
            hitbox = new Rectangle2D.Float(x, y + 2, ancho + 9, altura + 16);
        }
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

    public int getInvencible() {
        return invencible;
    }

    public void tickInvencible() {
        invencible--;
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

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setSalto(boolean salto) {
        this.salto = salto;
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

    public void setAireVelocidad() {
        aireVelocidad = saltoVelocidad;
    }

    public void palSpawn() {
        this.hitbox.x = 100;
        this.hitbox.y = 200;
    }

    public void disminuirPoder() {
        invencible = 300;
        switch (poder) {
            case NINGUNO:
                palSpawn();
                break;
            case SUPER:
                poder = PoderJugador.NINGUNO;
                iniHitbox(x, y, ancho, altura); // Cambiar hitbox
                break;
            default:
                poder = PoderJugador.SUPER;
                break;
        }
        cargarImagenes();
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
    private void actualizarPosicion() {
        float xVelocidad = 0f;

        if (salto) {
            salto();
        }
        if (izquierda) {
            xVelocidad -= velocidad;
        }

        if (derecha) {
            xVelocidad += velocidad;
        }

        if (!enVuelo) {
            if (!EstaEnSuelo(hitbox, nivelDatos)) {
                enVuelo = true;
            }
        }

        if (enVuelo) {

            if (puedeMoverse(hitbox.x, hitbox.y + aireVelocidad, hitbox.width, hitbox.height, nivelDatos)) {
                hitbox.y += aireVelocidad;
                aireVelocidad += gravedad;
                actualizarXPos(xVelocidad);
            } else {
                if (poder == PoderJugador.NINGUNO) {
                    hitbox.y = obtenerYPosLimite(hitbox, aireVelocidad);
                }
                if (aireVelocidad > 0) {
                    reiniciarEnVuelo();
                } else {
                    aireVelocidad = velocidadCaidaColision;
                }
                actualizarXPos(xVelocidad);
            }

        } else {
            actualizarXPos(xVelocidad);
        }

        // Actualizar estado de movimiento para evitar errores
        if (enVuelo) {
            estado = EstadoJugador.SALTANDO;
        } else if ((izquierda ^ derecha) || (arriba ^ abajo)) {
            estado = EstadoJugador.CORRIENDO;
        } else {
            estado = EstadoJugador.IDLE;
        }
    }

    private void actualizarXPos(float xVelocidad) {
        if (puedeMoverse(hitbox.x + xVelocidad, hitbox.y, hitbox.width, hitbox.height, nivelDatos)) {
            hitbox.x += xVelocidad;
        } else {
            if (poder == PoderJugador.NINGUNO) {
                hitbox.x = ObtenerXPosLimite(hitbox, xVelocidad);
            }
        }
    }

    private void reiniciarEnVuelo() {
        enVuelo = false;
        aireVelocidad = 0;

    }

    private void salto() {
        if (enVuelo) {
            return;
        }
        enVuelo = true;
        aireVelocidad = saltoVelocidad;
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

    public void cargarNivelDatos(int[][] nivelDatos) {
        this.nivelDatos = nivelDatos;
    }

    /**
     * Dibuja al jugador en pantalla
     *
     * @param g
     * @param xNivelDesfase
     */
    public void render(Graphics g, int xNivelDesfase) {
        if (animacionActual == null) {
            obtenerAnimacion();
        }
        if (indiceAnimacion >= animacionActual.length) {
            indiceAnimacion = 0;
        }
        g.setColor(Color.red);
        int yJugador = (int) hitbox.y;
        if (poder == PoderJugador.NINGUNO) {
            yJugador -= 40;
        }
        if (izquierda && !derecha) {
            g.drawImage(animacionActual[indiceAnimacion], (int) (hitbox.x - xNivelDesfase + 32 * ESCALA), yJugador, (int) (-32 * ESCALA), (int) (64 * ESCALA), null);
            g.drawRect((int) (hitbox.x - xNivelDesfase), (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
        } else {
            g.drawImage(animacionActual[indiceAnimacion], (int) hitbox.x - xNivelDesfase - 3, yJugador, (int) (32 * ESCALA), (int) (64 * ESCALA), null);
            g.drawRect((int) (hitbox.x - xNivelDesfase), (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
        }
    }

    /**
     * Detiene al jugador
     */
    public void resetearEstado() {
        izquierda = derecha = arriba = abajo = false;
    }

}
