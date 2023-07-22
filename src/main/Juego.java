package main;

import entidades.JugadorMulti;
import estadojuego.EstadoJuego;
import static estadojuego.EstadoJuego.JUGANDO;
import static estadojuego.EstadoJuego.MENU;
import estadojuego.Jugando;
import estadojuego.Menu;
import java.awt.Graphics;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import multijugador.Cliente;
import multijugador.PaqueteUnir;
import multijugador.Servidor;
import ui.PanelAcerca;
import ui.PanelAyuda;
import ui.PanelIniciado;
import ui.PanelInicio;
import ui.PanelPartida;
import ui.PanelRegistro;
import ui.PanelSesion;
import ui.PanelEstadisticas;

/**
 *
 * @author Gabriel
 */
public class Juego implements Runnable {

    // Atributos de graficos
    private VentanaJuego ventana;
    private PanelInicio panelInicio;
    private PanelRegistro panelRegistro;
    private PanelSesion panelSesion;
    private PanelIniciado panelIniciado;
    private PanelPartida panelPartida;
    private PanelJuego panelJuego;
    private PanelEstadisticas panelEstad;
    private PanelAyuda panelAyuda;
    private PanelAcerca panelAcerca;
    private Thread hiloJuego;

    // Atributos de sonido
    private Clip clip;

    // Atributos de multijugador
    private Servidor servidor;
    private Cliente cliente;

    private final int FPS_FIJOS = 120;
    private float ups = 200;

    private Jugando jugando;
    private Menu menu;

    public final static int TAMAÑO_GENERAL_CASILLAS = 32;
    public final static float ESCALA = 1.25f;
    public final static int CASILLAS_HORIZONTAL = 26;
    public final static int CASILLAS_VERTICAL = 14;
    public final static int TAMAÑO_REAL_CASILLAS = (int) (TAMAÑO_GENERAL_CASILLAS * ESCALA);
    public final static int JUEGO_ANCHO = TAMAÑO_REAL_CASILLAS * CASILLAS_HORIZONTAL;
    public final static int JUEGO_ALTO = TAMAÑO_REAL_CASILLAS * CASILLAS_VERTICAL;

    public Juego() {
        iniciarClases();
        panelInicio = new PanelInicio(this);
        panelRegistro = new PanelRegistro(this);
        panelSesion = new PanelSesion(this);
        panelIniciado = new PanelIniciado(this);
        panelPartida = new PanelPartida(this);
        panelJuego = new PanelJuego(this);
        panelEstad = new PanelEstadisticas(this);
        panelAyuda = new PanelAyuda(this);
        panelAcerca = new PanelAcerca(this);
        ventana = new VentanaJuego(
                panelInicio, panelRegistro, panelSesion, panelIniciado, panelPartida, panelJuego, panelEstad, panelAyuda, panelAcerca
        );
        panelInicio.requestFocusInWindow();
        iniciarMusica();

        // Iniciar ciclo de juego
        iniciarHilo();
    }

    /**
     * Inicializa las clases involucradas en el juego
     */
    private void iniciarClases() {
        menu = new Menu(this);
        jugando = new Jugando(this);
    }

    private void iniciarMusica() {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource("/ui/musica.wav")));
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            System.out.println("Error iniciando");
            ex.printStackTrace();
        }
    }

    /**
     * Activa o desactiva musica
     */
    public void toggleMusica() {
        if (!clip.isActive()) {
            clip.start();
        } else {
            clip.stop();
        }
    }

    public void iniciarServidor() {
        servidor = new Servidor(this);
        servidor.start();
    }

    public void iniciarCliente() {
        cliente = new Cliente(this, "localhost");
        cliente.start();
    }

    public void unirAPartida() {
        PaqueteUnir paquete = new PaqueteUnir((JugadorMulti) jugando.getJugador());
        if (servidor != null) {
            servidor.agregarConexion((JugadorMulti) jugando.getJugador(), paquete);
        }
        paquete.escribirDatos(cliente);
    }

    /**
     * Inicia el hilo de juego
     */
    private void iniciarHilo() {
        hiloJuego = new Thread(this);
        hiloJuego.start();
    }

    public float getUps() {
        return ups;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Menu getMenu() {
        return menu;
    }

    public Jugando getJugando() {
        return jugando;
    }

    public PanelJuego getPanelJuego() {
        return panelJuego;
    }

    public PanelIniciado getPanelIniciado() {
        return panelIniciado;
    }

    public PanelPartida getPanelPartida() {
        return panelPartida;
    }

    public PanelInicio getPanelInicio() {
        return panelInicio;
    }

    public void setUps(float ups) {
        this.ups = ups;
    }

    public void cambiarPanel(String nombrePanel) {
        ventana.getLayout().show(ventana.getFrame().getContentPane(), nombrePanel);
    }

    /**
     * Gestiona actualizaciones de objetos (personajes, paneles, etc)
     */
    public void actualizar() {
        switch (EstadoJuego.estado) {
            case MENU:
                menu.actualizar();
                break;
            case JUGANDO:
                jugando.actualizar();
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
        switch (EstadoJuego.estado) {
            case MENU:
                menu.dibujar(g);
                break;
            case JUGANDO:
                jugando.dibujar(g);
                break;
            default:
                break;
        }
    }

    /**
     * Detiene el juego cuando se pierde el enfoque de la ventana
     */
    public void ventanaPerdida() {
        jugando.getJugador().resetearEstado();
    }

    /**
     * Gestiona el ciclo de juego. Mantiene 120 frames por segundo y 200
     * actualizaciones por segundo
     */
    @Override
    public void run() {
        final double TIEMPO_POR_FRAME = 1000000000.0 / FPS_FIJOS;
        long tiempoPrevio = System.nanoTime();
        // Las variables delta acumulan la fraccion de tiempo restante antes de cambiar de frame/actualizar
        double deltaActualizar = 0;
        double deltaFrames = 0;

        while (true) {
            long tiempoAhora = System.nanoTime();
            deltaActualizar += (tiempoAhora - tiempoPrevio) / (1000000000.0 / ups);
            deltaFrames += (tiempoAhora - tiempoPrevio) / TIEMPO_POR_FRAME;
            tiempoPrevio = tiempoAhora;

            if (deltaActualizar >= 1) {
                actualizar();
                deltaActualizar--;
            }
            if (deltaFrames >= 1) {
                panelJuego.repaint();
                deltaFrames--;
            }
        }
    }
}
