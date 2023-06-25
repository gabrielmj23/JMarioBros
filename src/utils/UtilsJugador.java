package utils;

/**
 *
 * @author Gabriel
 */
public class UtilsJugador {

    /**
     * Cada valor representa un posible estado del jugador
     */
    public static enum EstadoJugador {
        IDLE,
        CORRIENDO,
        SALTANDO,
        MURIENDO,
        ATACANDO
    }
    
    /**
     * Cada valor representa un posible poder del jugador
     */
    public static enum PoderJugador {
        NINGUNO,
        SUPER,
        FUEGO
    }
}
