/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

import main.Juego;

/**
 *
 * @author rober
 */
public class BloqueInteractivo extends ObjetoJuego {
    public BloqueInteractivo(int x, int y , int tipo){
        super(x, y, tipo);
        iniHitbox(Juego.TAMAÑO_REAL_CASILLAS, Juego.TAMAÑO_REAL_CASILLAS);
    }
    
    public void actualizar(){
        if(animado)
            actualizarFrameAnimacion();
    }
    
}
