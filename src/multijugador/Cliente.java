package multijugador;

import entidades.JugadorMulti;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import main.Juego;
import static multijugador.Paquete.TiposPaquete.DESCONECTAR;
import static multijugador.Paquete.TiposPaquete.INVALIDO;
import static multijugador.Paquete.TiposPaquete.UNIR;

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
            byte[] datos = new byte[2048];
            DatagramPacket paquete = new DatagramPacket(datos, datos.length); // Paquete que llegará al server
            try {
                socket.receive(paquete);
                interpretarPaquete(paquete.getData(), paquete.getAddress(), paquete.getPort());
            } catch (IOException e) {
                System.out.println("Error recibiendo paquete");
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("Error antes de interpretar paquete");
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Decide qué acción tomar según el tipo de paquete recibido
     *
     * @param datos
     * @param ip
     * @param puerto
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void interpretarPaquete(byte[] datos, InetAddress ip, int puerto) throws IOException, ClassNotFoundException {
        String msj = new String(datos).trim();
        Paquete.TiposPaquete tipo = Paquete.determinarPaquete(Integer.parseInt(msj.substring(0, 2)));

        // Ejecutar distintas acciones dependiendo del tipo paquete
        Paquete paquete;
        switch (tipo) {
            default:
            case INVALIDO:
                break;
            case UNIR:
                paquete = new PaqueteUnir(datos);
                System.out.println("[" + ip.getHostAddress() + ":" + puerto + "] ENTRO AL JUEGO");
                JugadorMulti jugador = ((PaqueteUnir) paquete).getJugador();
                juego.agregarJugador(jugador);
                break;
            case DESCONECTAR:
                break;
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
