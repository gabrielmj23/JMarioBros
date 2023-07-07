package estadojuego;

import entidades.Jugador;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import main.Juego;
import niveles.NivelConfig;
import static utils.UtilsJugador.MARIO_INDEX;

public class Jugando extends Estado implements MetodosDeEstados {
    private Jugador jugador;
    private NivelConfig nivelConfig;

    public Jugando(Juego juego) {
        super(juego);
        iniciarClases();
    }
    
    private void iniciarClases() {
        jugador = new Jugador(200, 200, MARIO_INDEX);
        nivelConfig = new NivelConfig(juego);
        jugador.cargarNivelDatos(NivelConfig.obtenerDatos());
    }
    
    public void ventanaPerdida() {
        jugador.resetearEstado();
    }
    
    public Jugador getJugador() {
        return jugador;
    }

    @Override
    public void actualizar() {
        nivelConfig.actualizar();
        jugador.actualizar();
    }

    @Override
    public void dibujar(Graphics g) {
        nivelConfig.dibujar(g);
        jugador.render(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                jugador.setArriba(true);
                break;
            case KeyEvent.VK_A:
                jugador.setIzquierda(true);
                break;
            case KeyEvent.VK_S:
                jugador.setAbajo(true);
                break;
            case KeyEvent.VK_D:
                jugador.setDerecha(true);
                break;
            case KeyEvent.VK_BACK_SPACE:
                EstadoJuego.estado = EstadoJuego.MENU;
                
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                jugador.setIndiceAnimacion(0);
                jugador.setArriba(false);
                break;
            case KeyEvent.VK_A:
                jugador.setIndiceAnimacion(0);
                jugador.setIzquierda(false);
                break;
            case KeyEvent.VK_S:
                jugador.setIndiceAnimacion(0);
                jugador.setAbajo(false);
                break;
            case KeyEvent.VK_D:
                jugador.setIndiceAnimacion(0);
                jugador.setDerecha(false);
                break;
        }
    }
}
