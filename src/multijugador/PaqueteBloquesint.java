package multijugador;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import objetos.BloqueInteractivo;

/**
 *
 * @author Gabriel
 */
public class PaqueteBloquesint extends Paquete<ArrayList<BloqueInteractivo>> {

    private ArrayList<BloqueInteractivo> bloques;

    public PaqueteBloquesint(byte[] datos) throws IOException, ClassNotFoundException {
        super(06);
        this.bloques = leerObj(datos);
    }

    public PaqueteBloquesint(ArrayList<BloqueInteractivo> bloques) {
        super(06);
        this.bloques = bloques;
    }

    public ArrayList<BloqueInteractivo> getBloques() {
        return bloques;
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
            streamObj.writeObject(bloques);
            streamObj.flush();
            byte[] bloquesBytes = streamBytes.toByteArray();
            // Crear arreglo de retorno
            byte[] datosPaquete = new byte[codBytes.length + bloquesBytes.length];
            int idxPaquete = 0;
            for (int i = 0; i < codBytes.length; i++) {
                datosPaquete[idxPaquete++] = codBytes[i];
            }
            for (int i = 0; i < bloquesBytes.length; i++) {
                datosPaquete[idxPaquete++] = bloquesBytes[i];
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
