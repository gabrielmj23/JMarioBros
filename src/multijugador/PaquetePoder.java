package multijugador;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import objetos.Poder;

/**
 *
 * @author Gabriel
 */
public class PaquetePoder extends Paquete<ArrayList<Poder>> {

    private ArrayList<Poder> poderes;

    public PaquetePoder(byte[] datos) throws IOException, ClassNotFoundException {
        super(05);
        this.poderes = leerObj(datos);
    }

    public PaquetePoder(ArrayList<Poder> poderes) {
        super(05);
        this.poderes = poderes;
    }

    public ArrayList<Poder> getPoderes() {
        return poderes;
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
            byte[] codBytes = "04".getBytes(); // Obtener bytes de código
            // Obtener bytes de usuario
            ByteArrayOutputStream streamBytes = new ByteArrayOutputStream();
            streamObj = new ObjectOutputStream(streamBytes);
            streamObj.writeObject(poderes);
            streamObj.flush();
            byte[] poderesBytes = streamBytes.toByteArray();
            // Crear arreglo de retorno
            byte[] datosPaquete = new byte[codBytes.length + poderesBytes.length];
            int idxPaquete = 0;
            for (int i = 0; i < codBytes.length; i++) {
                datosPaquete[idxPaquete++] = codBytes[i];
            }
            for (int i = 0; i < poderesBytes.length; i++) {
                datosPaquete[idxPaquete++] = poderesBytes[i];
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
