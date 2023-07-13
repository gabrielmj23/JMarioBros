package estadojuego;

import main.Juego;

public class Estado {
    
    protected Juego juego;    
    public Estado(Juego juego){
        this.juego = juego;        
    }

    public Juego getJuego() {
        return juego;
    }
    
    
}
