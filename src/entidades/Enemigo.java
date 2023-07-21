package entidades;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Juego;
import static main.Juego.ESCALA;
import utils.UtilsEnemigo;
import utils.UtilsEnemigo.EstadoEnemigo;
import static utils.UtilsEnemigo.EstadoEnemigo.CORRIENDO;
import static utils.UtilsEnemigo.EstadoEnemigo.IDLE;
import static utils.UtilsMovimiento.*;

/**
 *
 * @author rober
 */
public abstract class Enemigo extends Entidad {

    // Atributos de animación
    private int tipo;
    private int deltaAnimacion;
    private int indiceAnimacion;
    private transient BufferedImage[] animaciones;
    protected int muriendo = 0;

    private boolean primeraActualizacion = true;
    private boolean enAire;
    private float aireVelocidad = 0f;
    private float gravedad = 0.04f * Juego.ESCALA;
    protected float velocidad = 0.7f;
    private String direccion = "izquierda";

    protected EstadoEnemigo estado = IDLE;
    boolean vivo = true;
    protected int velocidadAnimacion = 17;

    public Enemigo(float x, float y, int ancho, int altura, int tipo) throws IOException {
        super(x, y, ancho, altura);
        this.tipo = tipo;
        iniHitbox(x, y, ancho, altura);
        cargarImagenes();
    }

    @Override
    public void iniHitbox(float x, float y, int ancho, int altura) {
        hitbox = new Rectangle2D.Float(x + 4, y + 40, ancho - 2, altura - 5);
    }

    /**
     * Revisa por donde ha chocado con el jugador y decide qué hacer
     *
     * @param mario
     * @param conf
     */
    public abstract void revisarColision(Jugador mario, EnemigosConfig conf);

    private void cargarImagenes() {
        try {
            animaciones = new BufferedImage[3];
            BufferedImage img = ImageIO.read(new File("media/sprites/Enemigos.png"));
            for (int i = 0; i < 3; i++) {
                animaciones[i] = img.getSubimage(i * 32, tipo * 48, 32, 48);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo sprite de enemigo");
            System.out.println(e.getMessage());
        }
    }

    private void actualizarMovimiento(int[][] nivelDatos) {
        if (primeraActualizacion) {
            if (!EstaEnSuelo(hitbox, nivelDatos)) {
                enAire = true;
            }
            primeraActualizacion = false;
        }
        if (enAire) {
            if (puedeMoverse(hitbox.x, hitbox.y + aireVelocidad, hitbox.width, hitbox.height, nivelDatos)) {
                hitbox.y += aireVelocidad;
                aireVelocidad += gravedad;
            } else {
                enAire = false;
                hitbox.y = obtenerYPosLimite(hitbox, aireVelocidad);
            }
        } else {
            switch (estado) {
                case IDLE:
                    estado = CORRIENDO;
                    break;
                case CORRIENDO:
                    float xVelocidad = 0;

                    if (direccion.equals("izquierda")) {
                        xVelocidad = -velocidad;
                    } else {
                        xVelocidad = velocidad;
                    }

                    if (puedeMoverse(hitbox.x + xVelocidad, hitbox.y, hitbox.width, hitbox.height, nivelDatos)) {
                        if (EsPiso(hitbox, xVelocidad, nivelDatos)) {
                            //System.out.println("No es piso");
                            hitbox.x += xVelocidad;
                            return;
                        }
                    }
                    cambiarDireccion();
            }
        }
    }

    private void cambiarDireccion() {
        if (direccion.equals("izquierda")) {
            direccion = "derecha";
        } else {
            direccion = "izquierda";
        }

    }

    /**
     * Actualiza el frame de animación al correr según pasa el tiempo
     */
    private void actualizarFrameAnimacion() {
        deltaAnimacion++;
        if (deltaAnimacion >= velocidadAnimacion) {
            deltaAnimacion = 0;
            indiceAnimacion++;
            if (indiceAnimacion >= 2) {
                indiceAnimacion = 0;
            }
        }
    }

    public void actualizar(int[][] nivelDatos) {
        if (muriendo > 0) {
            muriendo--;
            if (muriendo == 0) {
                vivo = false;
            }
        }
        actualizarMovimiento(nivelDatos);
        actualizarFrameAnimacion();
    }

    public void dibujar(Graphics g, int xNivelDesfase) {
        if (animaciones == null) {
            cargarImagenes();
        }
        if (indiceAnimacion >= animaciones.length) {
            indiceAnimacion = 0;
        }
        if (muriendo > 0) {
            indiceAnimacion = 2;
        }
        float desfase = 0;
        if (tipo == UtilsEnemigo.GOOMBA_INDEX) {
            desfase = 21 * ESCALA;
        }
        if (direccion.equals("derecha")) {
            g.drawImage(animaciones[indiceAnimacion], (int) (hitbox.x - xNivelDesfase - 3 + 32 * ESCALA), (int) (hitbox.y - desfase), (int) (-32 * ESCALA), (int) (48 * ESCALA), null);
        } else {
            g.drawImage(animaciones[indiceAnimacion], (int) (hitbox.x - xNivelDesfase - 3), (int) (hitbox.y - desfase), (int) (32 * ESCALA), (int) (48 * ESCALA), null);
        }
    }

    public String getDireccion() {
        return direccion;
    }

    public int obtenerIndiceAnimacion() {
        return indiceAnimacion;
    }

    public EstadoEnemigo obtenerEnemigoEstado() {
        return estado;
    }

    public boolean estaVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

}
