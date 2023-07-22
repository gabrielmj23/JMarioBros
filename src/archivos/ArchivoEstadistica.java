package archivos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Gabriel
 */
public class ArchivoEstadistica {

    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private ArrayList<Estadistica> estadisticas;

    public ArrayList<Estadistica> getEstadisticas() {
        return estadisticas;
    }

    /**
     * Abre el archivo para poder escribir sobre él
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void abrirArchivoEscritura() throws IOException, ClassNotFoundException {
        abrirArchivoLectura();
        estadisticas = leerEstadisticas();
        salida = new ObjectOutputStream(new FileOutputStream(new File("estadisticas.txt")));
    }

    /**
     * Abre el archivo para poder leer sobre él
     *
     * @throws IOException
     */
    public void abrirArchivoLectura() throws IOException {
        File f = new File("estadisticas.txt");
        if (f.createNewFile()) {
            salida = new ObjectOutputStream(new FileOutputStream(f));
        }
        entrada = new ObjectInputStream(new FileInputStream(f));
    }

    public void cerrarArchivo() throws IOException {
        if (salida != null) {
            salida.close();
        }
        if (entrada != null) {
            entrada.close();
        }
    }

    public ArrayList<Estadistica> leerEstadisticas() throws IOException, ClassNotFoundException {
        ArrayList<Estadistica> lista = new ArrayList();
        try {
            while (true) {
                lista.add((Estadistica) entrada.readObject());
            }
        } catch (EOFException e) {
        }
        cerrarArchivo();
        return lista;
    }

    private void agregarEstadistica(Estadistica estadistica) {
        try {
            for (Estadistica e : estadisticas) {
                salida.writeObject(e);
            }
            if (estadistica != null) {
                salida.writeObject(estadistica);
            }
            cerrarArchivo();
        } catch (IOException e) {
            System.err.println("Error al escribir en archivo");
        }
    }

    public void agregarEstadisticaUnica(Estadistica estadistica) throws UsuarioRepetidoException, IOException {
        if (estadisticas.contains(estadistica)) {
            cerrarArchivo();
            throw new UsuarioRepetidoException("El usuario ya se encuentra en el archivo");
        }
        agregarEstadistica(estadistica);
        cerrarArchivo();
    }
    
    public void actualizarEstadistica(Estadistica estadistica) {
        int idx = estadisticas.indexOf(estadistica);
        if (idx > -1) {
            estadisticas.set(idx, estadistica);
        }
        agregarEstadistica(null);
    }
}
