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
            byte[] datos = new byte[2048];
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
        TiposPaquete tipo = Paquete.determinarPaquete(Integer.parseInt(msj.substring(0, 2)));

        // Ejecutar distintas acciones dependiendo del tipo paquete
        Paquete paquete;
        switch (tipo) {
            default:
            case INVALIDO:
                break;
            case UNIR:
                paquete = new PaqueteUnir(datos);
                System.out.println("[" + ip.getHostAddress() + ":" + puerto + "] UNIDO");
                JugadorMulti jugador = ((PaqueteUnir) paquete).getJugador();
                JugadorMulti paraConexion = new JugadorMulti(jugador.getX(), jugador.getY(), 0, jugador.getUsuario(), ip, puerto);
                agregarConexion(paraConexion, (PaqueteUnir) paquete);
                break;
            case DESCONECTAR:
                break;
        }
    }

    public void agregarConexion(JugadorMulti jugador, PaqueteUnir paquete) {
        boolean conectado = false;
        for (JugadorMulti j: jugadoresConectados) {
            // Si el paquete corresponde al jugador, solo actualizar atributos de conexion
            if (j.getUsuario().getLogin().equals(jugador.getUsuario().getLogin())) {
                if (j.getIp() == null) {
                    j.setIp(jugador.getIp());
                }
                if (j.getPuerto() <= 0) {
                    j.setPuerto(jugador.getPuerto());
                }
                conectado = true;
                System.out.println("Hola");
            } else {
                // Si no corresponde, informarle a su cliente del nuevo jugador en la partida
                enviarDatos(paquete.getDatos(), j.getIp(), j.getPuerto());
                PaqueteUnir p = new PaqueteUnir(j);
                enviarDatos(p.getDatos(), jugador.getIp(), jugador.getPuerto());
            }
        }
        if (!conectado) {
            jugadoresConectados.add(jugador);
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
