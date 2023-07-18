package archivos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import multijugador.Usuario;

/**
 *
 * @author Gabriel
 */
public class ArchivoUsuarios {

    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private ArrayList<Usuario> usuarios;

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void abrirArchivoEscritura() throws IOException, ClassNotFoundException {
        abrirArchivoLectura();
        usuarios = leerUsuarios();
        salida = new ObjectOutputStream(new FileOutputStream(new File("usuarios.txt")));
    }

    public void abrirArchivoLectura() throws IOException {
        File f = new File("usuarios.txt");
        if (f.createNewFile()) {
            salida = new ObjectOutputStream(new FileOutputStream(f));
        }
        entrada = new ObjectInputStream(new FileInputStream(f));
    }

    private void cerrarArchivo() throws IOException {
        if (salida != null) {
            salida.close();
        }
        if (entrada != null) {
            entrada.close();
        }
    }

    public ArrayList<Usuario> leerUsuarios() throws IOException, ClassNotFoundException {
        ArrayList<Usuario> lista = new ArrayList();
        try {
            while (true) {
                lista.add((Usuario) entrada.readObject());
            }
        } catch (EOFException e) {
        }
        cerrarArchivo();
        return lista;
    }

    public void agregarRegistro(T obj) {
        try {
            for (T registro : registros) {
                salida.writeObject(registro);
            }
            if (obj != null) {
                salida.writeObject(obj);
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en archivo");
        }
    }

    public void agregarRegistroUnico(T obj) throws RegistroRepetidoException {
        if (registros.contains(obj)) {
            throw new RegistroRepetidoException("El objeto ya se encuentra en el archivo");
        }
        agregarRegistro(obj);
    }
}
