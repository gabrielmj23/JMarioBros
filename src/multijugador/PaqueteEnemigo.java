package multijugador;

import entidades.Enemigo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Gabriel
 */
public class PaqueteEnemigo extends Paquete<ArrayList<Enemigo>> {

    private ArrayList<Enemigo> enemigos;

    public PaqueteEnemigo(byte[] datos) throws IOException, ClassNotFoundException {
        super(04);
        this.enemigos = leerObj(datos);
    }

    public PaqueteEnemigo(ArrayList<Enemigo> enemigos) {
        super(04);
        this.enemigos = enemigos;
    }

    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
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
            streamObj.writeObject(enemigos);
            streamObj.flush();
            byte[] enemigosBytes = streamBytes.toByteArray();
            // Crear arreglo de retorno
            byte[] datosPaquete = new byte[codBytes.length + enemigosBytes.length];
            int idxPaquete = 0;
            for (int i = 0; i < codBytes.length; i++) {
                datosPaquete[idxPaquete++] = codBytes[i];
            }
            for (int i = 0; i < enemigosBytes.length; i++) {
                datosPaquete[idxPaquete++] = enemigosBytes[i];
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
