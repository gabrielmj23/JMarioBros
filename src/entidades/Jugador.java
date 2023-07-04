package entidades;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import utils.UtilsJugador.*;
import static main.Juego.ESCALA;
import static utils.UtilsAyuda.PuedeMoverse;
import static utils.UtilsJugador.PoderJugador.NINGUNO;

/**
 *
 * @author Gabriel
 * @author rober
 */
public class Jugador extends Entidad {

    private boolean izquierda;
    private boolean derecha;
    private boolean arriba;
    private boolean abajo;
    private float velocidad;
    private int tipo;
    private EstadoJugador estado;
    private PoderJugador poder;
    private int deltaAnimacion;
    private int indiceAnimacion;
    private BufferedImage img;
    private BufferedImage[][] animacionesCorrer;
    private BufferedImage[] animacionActual;
    private int[][] nivelDatos;

    private static final int VELOCIDAD_ANIMACION = 17;
    private static final String[] SPRITE_PATHS = {"MarioSprites.png", "LuigiSprites.png", "ToadSprites.png", "ToadetteSprites.png"};

    public Jugador(float x, float y, int tipo) {
        super(x, y, 32, 64);
        this.tipo = tipo;
        velocidad = 1.7f;
        estado = EstadoJugador.IDLE;
        poder = PoderJugador.SUPER;
        deltaAnimacion = 0;
        indiceAnimacion = 0;
        iniHitbox();
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

       

        float xVelocidad = 0, yVelocidad = 0;

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
        
        if(PuedeMoverse(x+xVelocidad, y+yVelocidad, ancho, largo, nivelDatos)){
            this.x += xVelocidad;
            this.y += yVelocidad;
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
        actualizarHitbox();
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
        if (izquierda && !derecha) {
            g.drawImage(animacionActual[indiceAnimacion], (int) (x + 32 * ESCALA), (int) y, (int) (-32 * ESCALA), (int) (64 * ESCALA), null);
            g.drawRect(hitbox.x, hitbox.y, (int) (hitbox.width * ESCALA), (int) (hitbox.height * ESCALA));
        } else {
            g.drawImage(animacionActual[indiceAnimacion], (int) x, (int) y, (int) (32 * ESCALA), (int) (64 * ESCALA), null);
            g.drawRect(hitbox.x, hitbox.y, (int) (hitbox.width * ESCALA), (int) (hitbox.height * ESCALA));
        }
    }

    @Override
    public void iniHitbox() {
        if (poder == NINGUNO) //Acomodar la hitbox a mario chikito
        {
            hitbox = new Rectangle((int) x, (int) y, ancho - 2, largo - 26);
        } else {
            hitbox = new Rectangle((int) x, (int) y, ancho, largo);
        }
    }

    @Override
    public void actualizarHitbox() {
        hitbox.x = (int) x;
        if (poder == NINGUNO) // acomodar la hitbox a mario chikito
        {
            hitbox.y = (int) (y + 32);
        } else {
            hitbox.y = (int) y;
        }
    }

    /**
     * Detiene al jugador
     */
    public void resetearEstado() {
        izquierda = derecha = arriba = abajo = false;
    }

}
