package entidades;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import utils.UtilsJugador.*;
import static main.Juego.ESCALA;
import static utils.UtilsMovimiento.puedeMoverse;

/**
 *
 * @author Gabriel
 * @author rober
 */
public class Jugador extends Entidad {

    // Atributos de movimiento
    private boolean izquierda;
    private boolean derecha;
    private boolean arriba;
    private boolean abajo;
    private float velocidad;

    // Atributos de estado
    private EstadoJugador estado;
    private PoderJugador poder;

    // Atributos de animación
    private int tipo;
    private int deltaAnimacion;
    private int indiceAnimacion;
    private BufferedImage img;
    private BufferedImage[][] animacionesCorrer;
    private BufferedImage[] animacionActual;

    // Atributos de nivel
    private int[][] nivelDatos;

    private static final int VELOCIDAD_ANIMACION = 17;
    private static final String[] SPRITE_PATHS = {"MarioSprites.png", "LuigiSprites.png", "ToadSprites.png", "ToadetteSprites.png"};

    public Jugador(float x, float y, int tipo) {
        super(x, y, 30, 62);
        this.tipo = tipo;
        velocidad = 1.7f;
        estado = EstadoJugador.IDLE;
        poder = PoderJugador.NINGUNO;
        deltaAnimacion = 0;
        indiceAnimacion = 0;
        iniHitbox(x, y, ancho, altura);
        // Cargar spritesheet y animacionesCorrer
        try {
            animacionesCorrer = new BufferedImage[3][3];
            img = ImageIO.read(new File("media/sprites/" + SPRITE_PATHS[tipo]));
            // Cargar animacionesCorrer de caminar
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

    @Override
    public void iniHitbox(float x, float y, int ancho, int altura) {
        if (poder == PoderJugador.NINGUNO) //Acomodar la hitbox a mario chikito
        {
            hitbox = new Rectangle2D.Float(x + 4, y + 40, ancho + 2, altura - 25);
        } else {
            hitbox = new Rectangle2D.Float(x, y + 2, ancho + 9, altura + 16);
        }
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

    public void setIndiceAnimacion(int indiceAnimacion) {
        this.indiceAnimacion = indiceAnimacion;
    }

    /**
     * Modifica la posición del jugador dependiendo de la dirección en que se
     * mueve
     */
    private void actualizarPosicion() {
        float xVelocidad = 0f, yVelocidad = 0f;

        if (izquierda && !derecha) {
            xVelocidad = -velocidad;
        } else if (derecha && !izquierda) {
            xVelocidad = velocidad;
        }

        if (arriba && !abajo) {
            yVelocidad = -velocidad;
        } else if (abajo && !arriba) {
            yVelocidad = velocidad;
        }

        if (puedeMoverse(hitbox.x + xVelocidad, hitbox.y + yVelocidad, hitbox.width, hitbox.height, nivelDatos)) {
            hitbox.x += xVelocidad;
            hitbox.y += yVelocidad;
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
    private void actualizarFrameAnimacion() {
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
    private void obtenerAnimacion() {
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
     */
    public void render(Graphics g) {
        g.setColor(Color.red);
        if (animacionActual == null || indiceAnimacion >= animacionActual.length) {
            indiceAnimacion = 0;
        }
        int yJugador = (int) hitbox.y;
        if (poder == PoderJugador.NINGUNO) {
            yJugador -= 40;
        }
        if (izquierda && !derecha) {
            g.drawImage(animacionActual[indiceAnimacion], (int) (hitbox.x + 32 * ESCALA), yJugador, (int) (-32 * ESCALA), (int) (64 * ESCALA), null);
            g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
        } else {
            g.drawImage(animacionActual[indiceAnimacion], (int) hitbox.x, yJugador, (int) (32 * ESCALA), (int) (64 * ESCALA), null);
            g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
        }
    }

    /**
     * Detiene al jugador
     */
    public void resetearEstado() {
        izquierda = derecha = arriba = abajo = false;
    }

}
