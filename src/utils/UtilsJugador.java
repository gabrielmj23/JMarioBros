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
    
    /**
     * Indices que representan los sprites segun el personaje
     */
    public static final int MARIO_INDEX = 0;
    public static final int LUIGI_INDEX = 1;
    public static final int TOAD_INDEX = 2;
    public static final int TOADETTE_INDEX = 3;
}
