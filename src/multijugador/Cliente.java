package multijugador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import main.Juego;

/**
 *
 * @author Gabriel
 */
public class Cliente extends Thread {

    private InetAddress ip;
    private DatagramSocket socket;
    private Juego juego;

    public Cliente(Juego juego, String ip) {
        this.juego = juego;
        try {
            this.ip = InetAddress.getByName(ip);
            socket = new DatagramSocket();
        } catch (UnknownHostException e) {
            System.out.println("No detecta IP");
            System.out.println(e.getMessage());
        } catch (SocketException e) {
            System.out.println("Error abriendo socket");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Inicia el cliente, que escucha constantemente por paquetes del servidor
     */
    @Override
    public void run() {
        while (true) {
            byte[] datos = new byte[1024];
            DatagramPacket paquete = new DatagramPacket(datos, datos.length); // Paquete que llegará al server
            try {
                socket.receive(paquete);
                System.out.println("SERVIDOR > " + new String(paquete.getData()));
            } catch (IOException e) {
                System.out.println("Error recibiendo paquete");
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Envía datos hacia donde esté conectado en el socket
     *
     * @param datos
     */
    public void enviarDatos(byte[] datos) {
        DatagramPacket paquete = new DatagramPacket(datos, datos.length, ip, 1331);
        try {
            socket.send(paquete);
        } catch (IOException e) {
            System.out.println("Error enviando paquete");
            System.out.println(e.getMessage());
        }
    }
}
