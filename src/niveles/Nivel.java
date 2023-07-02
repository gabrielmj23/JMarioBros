/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niveles;

/**
 *
 * @author rober
 */
public class Nivel {
    private int[][] nivelDatos;
    
    public Nivel(int[][] nivelDatos){
        this.nivelDatos = nivelDatos;
    }
    
    public int obtenerIndiceSprite(int x, int y){
        return nivelDatos[y][x];
    }
}
