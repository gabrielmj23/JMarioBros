package main;

/**
 *
 * @author Gabriel
 */
public class Juego implements Runnable {

    private VentanaJuego ventana;
    private PanelJuego panel;
    private Thread hiloJuego;
    private final int FPS_FIJOS = 120;

    public Juego() {
        panel = new PanelJuego();
        ventana = new VentanaJuego(panel);
        panel.requestFocus();
        iniciarJuego();
    }

    private void iniciarJuego() {
        hiloJuego = new Thread(this);
        hiloJuego.start();
    }

    @Override
    public void run() {
        long ultimoFrame = System.nanoTime();
        final double TIEMPO_POR_FRAME = 1000000000.0 / FPS_FIJOS;

        while (true) {
            long ahora = System.nanoTime();
            if (ahora - ultimoFrame >= TIEMPO_POR_FRAME) {
                panel.repaint();
                ultimoFrame = ahora;
            }
        }
    }
}
