package main;

import entidades.Jugador;
import entidades.JugadorMulti;
import java.awt.Graphics;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import multijugador.Cliente;
import multijugador.PaqueteUnir;
import multijugador.Servidor;
import multijugador.Usuario;
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

    // Atributos de graficos
    private VentanaJuego ventana;
    private PanelJuego panel;
    private NivelConfig nivelConfig;

    // Atributos de hilos/concurrencia
    private Thread hiloJuego;

    // Atributos de multijugador
    private ArrayList<JugadorMulti> jugadores;
    private Servidor servidor;
    private Cliente cliente;

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
        iniciarConexiones();
        hiloJuego = new Thread(this);
        hiloJuego.start();
    }

    /**
     * Devuelve el jugador local, siempre en la primera posicion del arreglo
     * jugadores
     *
     * @return Jugador local de esta instancia de juego
     */
    public final Jugador getJugador() {
        return jugadores.get(0);
    }

    public ArrayList<JugadorMulti> getJugadores() {
        return jugadores;
    }

    public NivelConfig getNivelConfig() {
        return nivelConfig;
    }

    public void agregarJugador(JugadorMulti jugador) {
        if (jugadores.size() == 4) {
            System.out.println("Capacidad maxima alcanzada");
            return;
        }
        jugadores.add(jugador);
    }

    /**
     * Inicializa las clases involucradas en el juego
     */
    private void iniciarClases() {
        jugadores = new ArrayList();
        String login = JOptionPane.showInputDialog(panel, "Ingresar usuario");
        jugadores.add(new JugadorMulti(
                200f, 200f, MARIO_INDEX, new Usuario("a", "a", login, "a"), null, -1)
        );
        nivelConfig = new NivelConfig(this);
    }

    /**
     * Inicializa las conexiones para el multijugador
     */
    private void iniciarConexiones() {
        if (JOptionPane.showConfirmDialog(panel, "Iniciar servidor?") == 0) {
            servidor = new Servidor(this);
            servidor.start();
        }
        cliente = new Cliente(this, "localhost");
        cliente.start();

        // Unirse a la partida
        PaqueteUnir paquete = new PaqueteUnir((JugadorMulti) getJugador());
        System.out.println("ENVIA PAQUETE DESDE JUEGO");
        if (servidor != null) {
            servidor.agregarConexion((JugadorMulti) getJugador(), paquete);
        }
        paquete.escribirDatos(cliente);
    }

    /**
     * Gestiona actualizaciones de objetos (personajes, paneles, etc)
     */
    public void actualizar() {
        panel.actualizarJuego();
    }

    /**
     * Gestiona la accion de dibujar objetos del juego en pantalla
     *
     * @param g
     */
    public void render(Graphics g) {
        nivelConfig.dibujar(g);
        for (JugadorMulti j : jugadores) {
            if (j != null) {
                j.render(g);
            }
        }
    }

    /**
     * Detiene el juego cuando se pierde el enfoque de la ventana
     */
    public void ventanaPerdida() {
        jugadores.get(0).resetearEstado();
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
