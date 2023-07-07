package multijugador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import main.Juego;

/**
 *
 * @author Gabriel
 */
public class Servidor extends Thread {
    
    private DatagramSocket socket;
    private Juego juego;
    
    public Servidor(Juego juego) {
        this.juego = juego;
        try {
            socket = new DatagramSocket(1331);
        } catch (SocketException e) {
            System.out.println("Error iniciando socket de servidor");
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void run() {
        while (true) {
            byte[] datos = new byte[1024];
            DatagramPacket paquete = new DatagramPacket(datos, datos.length);
            try {
                socket.receive(paquete);
                String msj = new String(paquete.getData()).trim();
                System.out.println("CLIENTE > " + msj);
                if (msj.equals("Ping")) {
                    enviarDatos("Pong".getBytes(), paquete.getAddress(), paquete.getPort());
                }
            } catch (IOException e) {
                System.out.println("Error recibiendo paquete en servidor");
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void enviarDatos(byte[] datos, InetAddress ip, int puerto) {
        DatagramPacket paquete = new DatagramPacket(datos, datos.length, ip, puerto);
        try {
            socket.send(paquete);
        } catch (IOException e) {
            System.out.println("Error enviando paquete al cliente");
            System.out.println(e.getMessage());
        }
    }
}
