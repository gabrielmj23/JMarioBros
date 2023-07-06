package main;

import entidades.Jugador;
import java.awt.Graphics;
import niveles.NivelConfig;
import static utils.UtilsJugador.MARIO_INDEX;
import static utils.UtilsJugador.LUIGI_INDEX;
import static utils.UtilsJugador.TOAD_INDEX;
import static utils.UtilsJugador.TOADETTE_INDEX;

/**
 *
 * @author Gabriel
 */
public class Juego implements Runnable {

    private VentanaJuego ventana;
    private PanelJuego panel;
    private Thread hiloJuego;
    private Jugador jugador;
    private NivelConfig nivelConfig;
    private final int FPS_FIJOS = 120;
    private final int UPS_FIJOS = 200;

    public final static int TAMAÑO_GENERAL_CASILLAS = 32;
    public final static float ESCALA = 1.25f;
    public final static int CASILLAS_HORIZONTAL = 26;
    public final static int CASILLAS_VERTICAL = 14;
    public final static int TAMAÑO_REAL_CASILLAS = (int) (TAMAÑO_GENERAL_CASILLAS * ESCALA);
    public final static int JUEGO_ANCHO = TAMAÑO_REAL_CASILLAS * CASILLAS_HORIZONTAL;
    public final static int JUEGO_ALTO = TAMAÑO_REAL_CASILLAS * CASILLAS_VERTICAL;

    public Juego() {
        iniciarClases();
        panel = new PanelJuego(this);
        ventana = new VentanaJuego(panel);
        panel.requestFocus();
        // Iniciar ciclo de juego
        hiloJuego = new Thread(this);
        hiloJuego.start();
    }

    public Jugador getJugador() {
        return jugador;
    }

    public NivelConfig getNivelConfig() {
        return nivelConfig;
    }

    /**
     * Inicializa las clases involucradas en el juego
     */
    private void iniciarClases() {
        jugador = new Jugador(200, 200, MARIO_INDEX);
        nivelConfig = new NivelConfig(this);
    }

    /**
     * Gestiona actualizaciones de objetos (personajes, paneles, etc)
     */
    public void actualizar() {
        switch(EstadoJuego.estado){
            case MENU:
                
                break;
            case JUGANDO:
                //Agregar aqui si es antes del EstadoJuego
                panel.actualizarJuego();
                break;                
            default:
                break;
            
        }
    }

    /**
     * Gestiona la accion de dibujar objetos del juego en pantalla
     *
     * @param g
     */
    public void render(Graphics g) {
        switch(EstadoJuego.estado){
            case MENU:
                
                break;
            case JUGANDO:
                //Agregar aqui si es antes del EstadoJuego
                nivelConfig.dibujar(g);
                jugador.render(g);
                break;                
            default:
                break;
            
        }
    }

    /**
     * Detiene el juego cuando se pierde el enfoque de la ventana
     */
    public void ventanaPerdida() {
        jugador.resetearEstado();
    }

    /**
     * Gestiona el ciclo de juego. Por mantiene 120 frames por segundo y 200
     * actualizaciones por segundo
     */
    @Override
    public void run() {
        final double TIEMPO_POR_FRAME = 1000000000.0 / FPS_FIJOS;
        final double TIEMPO_POR_ACTUALIZAR = 1000000000.0 / UPS_FIJOS;
        long tiempoPrevio = System.nanoTime();
        // Las variables delta acumulan la fraccion de tiempo restante antes de cambiar de frame/actualizar
        double deltaActualizar = 0;
        double deltaFrames = 0;

        while (true) {
            long tiempoAhora = System.nanoTime();
            deltaActualizar += (tiempoAhora - tiempoPrevio) / TIEMPO_POR_ACTUALIZAR;
            deltaFrames += (tiempoAhora - tiempoPrevio) / TIEMPO_POR_FRAME;
            tiempoPrevio = tiempoAhora;

            if (deltaActualizar >= 1) {
                actualizar();
                deltaActualizar--;
            }
            if (deltaFrames >= 1) {
                panel.repaint();
                deltaFrames--;
            }
        }
    }
}
