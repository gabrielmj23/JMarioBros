package estadojuego;

import main.Juego;

/**
 * Representa el objeto de algún posible estado de juego
 *
 * @author Bertorelli
 */
public class Estado {

    protected Juego juego;

    public Estado(Juego juego) {
        this.juego = juego;
    }

    public Juego getJuego() {
        return juego;
    }
}
