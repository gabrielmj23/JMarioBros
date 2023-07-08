package multijugador;

import entidades.JugadorMulti;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import main.Juego;
import multijugador.Paquete.TiposPaquete;

/**
 *
 * @author Gabriel
 */
public class Servidor extends Thread {

    private DatagramSocket socket;
    private Juego juego;
    private ArrayList<JugadorMulti> jugadoresConectados;

    public Servidor(Juego juego) {
        this.juego = juego;
        jugadoresConectados = new ArrayList();
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
                interpretarPaquete(paquete.getData(), paquete.getAddress(), paquete.getPort());
            } catch (IOException e) {
                System.out.println("Error recibiendo paquete en servidor");
                System.out.println(e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Error leyendo datos");
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
        System.out.println(Integer.parseInt("00"));
        TiposPaquete tipo = Paquete.determinarPaquete(Integer.parseInt(msj.substring(0, 2)));

        // Ejecutar distintas acciones dependiendo del tipo paquete
        switch (tipo) {
            default:
            case INVALIDO:
                break;
            case UNIR:
                PaqueteUnir paquete = new PaqueteUnir(datos);
                System.out.println("[" + ip.getHostAddress() + ":" + puerto + "] UNIDO");
                JugadorMulti jugador = paquete.getJugador();
                jugadoresConectados.add(jugador);
                break;
            case DESCONECTAR:
                break;
        }
    }

    /**
     * Envía datos a un cliente en específico
     *
     * @param datos
     * @param ip
     * @param puerto
     */
    public void enviarDatos(byte[] datos, InetAddress ip, int puerto) {
        DatagramPacket paquete = new DatagramPacket(datos, datos.length, ip, puerto);
        try {
            socket.send(paquete);
        } catch (IOException e) {
            System.out.println("Error enviando paquete al cliente");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Envía datos a todos los clientes conectados
     *
     * @param datos
     */
    public void enviarDatosATodos(byte[] datos) {
        for (JugadorMulti j : jugadoresConectados) {
            enviarDatos(datos, j.getIp(), j.getPuerto());
        }
    }
}
