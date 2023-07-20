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

    /**
     * Devuelve el nombre del personaje dado su indice
     *
     * @param tipo
     * @return Nombre del personaje correspondiente
     */
    public static String obtenerNombrePersonaje(int tipo) {
        String tipoStr = "MARIO";
        switch (tipo) {
            case MARIO_INDEX:
                break;
            case LUIGI_INDEX:
                tipoStr = "LUIGI";
                break;
            case TOAD_INDEX:
                tipoStr = "TOAD";
                break;
            case TOADETTE_INDEX:
                tipoStr = "TOADETTE";
                break;
        }
        return tipoStr;
    }

    /**
     * Devuelve el código PERSONAJE_INDEX del personaje según su nombre
     *
     * @param nombre
     * @return Código del personaje dado
     */
    public static int obtenerIdPersonaje(String nombre) {
        int tipoInt = MARIO_INDEX;
        switch (nombre) {
            case "MARIO":
                break;
            case "LUIGI":
                tipoInt = LUIGI_INDEX;
                break;
            case "TOAD":
                tipoInt = TOAD_INDEX;
                break;
            case "TOADETTE":
                tipoInt = TOADETTE_INDEX;
                break;
        }
        return tipoInt;
    }
}
