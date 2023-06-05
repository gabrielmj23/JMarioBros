package main;

/**
 *
 * @author Gabriel
 */
public class Juego {

    private VentanaJuego ventana;
    private PanelJuego panel;

    public Juego() {
        panel = new PanelJuego();
        ventana = new VentanaJuego(panel);
        panel.requestFocus();
    }
}
