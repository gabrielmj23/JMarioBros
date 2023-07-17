package estadojuego;

import entidades.Jugador;
import entidades.JugadorMulti;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import main.Juego;
import multijugador.PaqueteActualizar;
import multijugador.Usuario;
import niveles.NivelConfig;
import static utils.UtilsJugador.MARIO_INDEX;

/**
 *
 * @author Bertorelli
 */
public class Jugando extends Estado implements MetodosDeEstados {

    // Atributos de graficos
    private NivelConfig nivelConfig;

    // Atributos de multijugador
    private ArrayList<JugadorMulti> jugadores;

    public Jugando(Juego juego) {
        super(juego);
        iniciarClases();
    }

    private void iniciarClases() {
        jugadores = new ArrayList();
        String login = JOptionPane.showInputDialog(juego.getPanel(), "Ingresar usuario");
        jugadores.add(new JugadorMulti(
                200f, 200f, MARIO_INDEX, new Usuario("a", "a", login, "a"), null, -1)
        );
        nivelConfig = new NivelConfig(juego);
        getJugador().cargarNivelDatos(NivelConfig.obtenerDatos());
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

    /**
     * Agrega un jugador a la partida
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
        jugadores.get(idx).setX(jugador.getX());
        jugadores.get(idx).setY(jugador.getY());
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

    /**
     * Detiene el juego cuando se pierde el enfoque de la ventana
     */
    public void ventanaPerdida() {
        if (EstadoJuego.estado == EstadoJuego.JUGANDO) {
            jugadores.get(0).resetearEstado();
        }
    }

    @Override
    public void actualizar() {
        nivelConfig.actualizar();
        getJugador().actualizar();
        PaqueteActualizar paquete = new PaqueteActualizar((JugadorMulti) getJugador());
        paquete.escribirDatos(juego.getCliente());
        nivelConfig.actualizar();
    }

    @Override
    public void dibujar(Graphics g) {
        nivelConfig.dibujar(g);
        for (JugadorMulti j : jugadores) {
            j.render(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                getJugador().setArriba(true);
                break;
            case KeyEvent.VK_A:
                getJugador().setIzquierda(true);
                break;
            case KeyEvent.VK_S:
                getJugador().setAbajo(true);
                break;
            case KeyEvent.VK_D:
                getJugador().setDerecha(true);
                break;
            case KeyEvent.VK_BACK_SPACE:
                EstadoJuego.estado = EstadoJuego.MENU;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                getJugador().setIndiceAnimacion(0);
                getJugador().setArriba(false);
                break;
            case KeyEvent.VK_A:
                getJugador().setIndiceAnimacion(0);
                getJugador().setIzquierda(false);
                break;
            case KeyEvent.VK_S:
                getJugador().setIndiceAnimacion(0);
                getJugador().setAbajo(false);
                break;
            case KeyEvent.VK_D:
                getJugador().setIndiceAnimacion(0);
                getJugador().setDerecha(false);
                break;
        }
    }
}
