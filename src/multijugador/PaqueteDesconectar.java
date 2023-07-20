package multijugador;

import entidades.JugadorMulti;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Gabriel
 */
public class PaqueteDesconectar extends Paquete<JugadorMulti> {

    private JugadorMulti jugador;

    public PaqueteDesconectar(byte[] datos) throws IOException, ClassNotFoundException {
        super(01);
        this.jugador = leerObj(datos);
    }

    public PaqueteDesconectar(JugadorMulti jugador) {
        super(01);
        this.jugador = jugador;
    }

    public JugadorMulti getJugador() {
        return jugador;
    }

    @Override
    public void escribirDatos(Cliente cliente) {
        cliente.enviarDatos(getDatos());
    }

    @Override
    public void escribirDatos(Servidor servidor) {
        servidor.enviarDatosATodos(getDatos());
    }

    @Override
    public byte[] getDatos() {
        ObjectOutputStream streamObj = null;
        try {
            byte[] codBytes = "01".getBytes(); // Obtener bytes de código
            // Obtener bytes de usuario
            ByteArrayOutputStream streamBytes = new ByteArrayOutputStream();
            streamObj = new ObjectOutputStream(streamBytes);
            streamObj.writeObject(jugador);
            streamObj.flush();
            byte[] jugadorBytes = streamBytes.toByteArray();
            // Crear arreglo de retorno
            byte[] datosPaquete = new byte[codBytes.length + jugadorBytes.length];
            int idxPaquete = 0;
            for (int i = 0; i < codBytes.length; i++) {
                datosPaquete[idxPaquete++] = codBytes[i];
            }
            for (int i = 0; i < jugadorBytes.length; i++) {
                datosPaquete[idxPaquete++] = jugadorBytes[i];
            }
            return datosPaquete;
        } catch (IOException e) {
            System.out.println("Error creando stream");
            e.printStackTrace();
        } finally {
            try {
                streamObj.close();
            } catch (IOException | NullPointerException e) {
                System.out.println("Error cerrando stream");
            }
        }
        return null;
    }
}
