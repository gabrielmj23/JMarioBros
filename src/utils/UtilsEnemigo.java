/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author rober
 */
public class UtilsEnemigo {

    /**
     * Cada valor representa un posible estado del jugador
     */
    public static enum EstadoEnemigo {
        IDLE,
        CORRIENDO,
        SALTANDO,
        MURIENDO,
        ATACANDO
    }

    /**
     * Indices que representan los sprites segun el personaje
     */
    public static final int GOOMBA_INDEX = 0;
    public static final int KOOPA_INDEX = 1;
    public static final int TOAD_INDEX = 2;
    public static final int TOADETTE_INDEX = 3;

}
