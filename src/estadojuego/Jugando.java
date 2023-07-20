package estadojuego;

import entidades.EnemigosConfig;
import entidades.Jugador;
import entidades.JugadorMulti;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import main.Juego;
import multijugador.PaqueteActualizar;
import multijugador.PaqueteIniciar;
import multijugador.Usuario;
import niveles.NivelConfig;
import utils.UtilsJugador;
import static utils.UtilsJugador.MARIO_INDEX;

/**
 *
 * @author Bertorelli
 * @author rober
 * @author Gabriel
 */
public class Jugando extends Estado implements MetodosDeEstados {

    // Atributos de graficos y juego
    private NivelConfig nivelConfig;
    private EnemigosConfig enemigosConfig;

    // Manejar nivel
    private int xNivelDesfase;
    private int bordeIzquierdo = (int) (0.2 * Juego.JUEGO_ANCHO);
    private int bordeDerecho = (int) (0.8 * Juego.JUEGO_ANCHO);
    private int casillasNivel = NivelConfig.obtenerDatos()[0].length;
    private int desfaseMaximoCasilla = casillasNivel - Juego.CASILLAS_HORIZONTAL;
    private int desfaseMaximoNivel = desfaseMaximoCasilla * Juego.TAMAÑO_REAL_CASILLAS;

    // Atributos de multijugador
    private ArrayList<JugadorMulti> jugadores;
    private boolean enLobby;

    public Jugando(Juego juego) {
        super(juego);
        iniciarClases();
        enLobby = true;
    }

    private void iniciarClases() {
        jugadores = new ArrayList();
        nivelConfig = new NivelConfig(juego);
        enemigosConfig = new EnemigosConfig(juego);
    }

    /**
     * Inicia el estado Jugando creando al jugador
     *
     * @param usuario
     */
    public void iniciarJugando(Usuario usuario) {
        jugadores.add(new JugadorMulti(100f, 100f, UtilsJugador.MARIO_INDEX, usuario, null, -1));
        getJugador().cargarNivelDatos(NivelConfig.obtenerDatos());
        enLobby = true;
        juego.unirAPartida();
        System.out.println(jugadores);
        // Acomodar tipo al primero disponible
        getJugador().setTipo(UtilsJugador.obtenerIdPersonaje(obtenerPersonajesDisponibles()[0]));
        actualizar();
    }

    /**
     * Inicia el estado Jugando de manera que ya se vea el mundo jugable
     */
    public void iniciarMundo() {
        PaqueteIniciar paquete = new PaqueteIniciar();
        paquete.escribirDatos(juego.getCliente());
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

    public void setEnLobby(boolean enLobby) {
        this.enLobby = enLobby;
    }

    /**
     * Devuelve la lista de personajes que no están ocupados ya
     *
     * @return Arreglo con los nombres de los personajes
     */
    public String[] obtenerPersonajesDisponibles() {
        ArrayList<String> inicial = new ArrayList();
        inicial.add("MARIO");
        inicial.add("LUIGI");
        inicial.add("TOAD");
        inicial.add("TOADETTE");
        ArrayList<String> usados = new ArrayList();
        for (int i = 1; i < jugadores.size(); i++) {
            usados.add(UtilsJugador.obtenerNombrePersonaje(jugadores.get(i).getTipo()));
        }
        inicial.removeAll(usados);
        return inicial.toArray(new String[inicial.size()]);
    }

    /**
     * Agrega un jugador a la partida activa
     *
     * @param jugador
     */
    public void agregarJugador(JugadorMulti jugador) {
        if (jugadores.size() == 4) {
            System.out.println("Capacidad maxima alcanzada");
            return;
        }
        jugadores.add(jugador);
    }

    /**
     * Elimina un jugador en caso de desconexión
     *
     * @param jugador
     */
    public void eliminarJugador(JugadorMulti jugador) {
        jugadores.remove(jugador);
    }

    /**
     * Actualiza los datos de un jugador como indique el servidor
     *
     * @param jugador
     */
    public void actualizarJugador(JugadorMulti jugador) {
        int idx = jugadores.indexOf(jugador);
        jugadores.get(idx).setTipo(jugador.getTipo());
        if (!enLobby) {
            jugadores.get(idx).setX(jugador.getX());
            jugadores.get(idx).setY(jugador.getY());
            jugadores.get(idx).setHitbox(jugador.getHitbox());
            jugadores.get(idx).setEstado(jugador.getEstado());
            jugadores.get(idx).setPoder(jugador.getPoder());
            jugadores.get(idx).setDeltaAnimacion(jugador.getDeltaAnimacion());
            jugadores.get(idx).setIndiceAnimacion(jugador.getIndiceAnimacion());
            jugadores.get(idx).setDerecha(jugador.isDerecha());
            jugadores.get(idx).setIzquierda(jugador.isIzquierda());
            jugadores.get(idx).setArriba(jugador.isArriba());
            jugadores.get(idx).setAbajo(jugador.isAbajo());
            jugadores.get(idx).obtenerAnimacion();
        }
    }

    /**
     * Detiene el juego cuando se pierde el enfoque de la ventana
     */
    public void ventanaPerdida() {
        if (EstadoJuego.estado == EstadoJuego.JUGANDO) {
            jugadores.get(0).resetearEstado();
        }
    }

    /**
     * Calcula el desfase del jugador con el nivel
     */
    private void revisarCercaBorde() {
        int jugadorX = (int) getJugador().getHitbox().x;
        int diff = jugadorX - xNivelDesfase;

        if (diff > bordeDerecho) {
            xNivelDesfase += diff - bordeDerecho;
        } else if (diff < bordeIzquierdo) {
            xNivelDesfase += diff - bordeIzquierdo;
        }

        if (xNivelDesfase > desfaseMaximoNivel) {
            xNivelDesfase = desfaseMaximoNivel;
        } else if (xNivelDesfase < 0) {
            xNivelDesfase = 0;
        }
    }

    @Override
    public void actualizar() {
        getJugador().actualizar();
        PaqueteActualizar paquete = new PaqueteActualizar((JugadorMulti) getJugador());
        paquete.escribirDatos(juego.getCliente());
        if (!enLobby) {
            nivelConfig.actualizar();
            enemigosConfig.actualizar(nivelConfig.getNivelUno().obtenerNivelDatos());
            revisarCercaBorde();
        } else {
            juego.getPanelPartida().actualizar();
        }
    }

    @Override
    public void dibujar(Graphics g) {
        if (!enLobby) {
            nivelConfig.dibujarFondo(g);
            nivelConfig.dibujar(g, xNivelDesfase);
            enemigosConfig.dibujar(g, xNivelDesfase);
            for (JugadorMulti j : jugadores) {
                j.render(g, xNivelDesfase);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!enLobby) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    getJugador().setSalto(true);
                    break;
                case KeyEvent.VK_A:
                    getJugador().setIzquierda(true);
                    break;
                case KeyEvent.VK_D:
                    getJugador().setDerecha(true);
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!enLobby) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    getJugador().setSalto(false);
                    break;
                case KeyEvent.VK_A:
                    getJugador().setIndiceAnimacion(0);
                    getJugador().setIzquierda(false);
                    break;
                case KeyEvent.VK_D:
                    getJugador().setIndiceAnimacion(0);
                    getJugador().setDerecha(false);
                    break;
            }
        }
    }
}
