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

    /**
     * Abre el archivo para poder escribir sobre él
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void abrirArchivoEscritura() throws IOException, ClassNotFoundException {
        abrirArchivoLectura();
        usuarios = leerUsuarios();
        salida = new ObjectOutputStream(new FileOutputStream(new File("usuarios.txt")));
    }

    /**
     * Abre el archivo para poder leer sobre él
     * 
     * @throws IOException
     */
    public void abrirArchivoLectura() throws IOException {
        File f = new File("usuarios.txt");
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

    private void agregarUsuario(Usuario usuario) {
        try {
            for (Usuario u : usuarios) {
                salida.writeObject(u);
            }
            if (usuario != null) {
                salida.writeObject(usuario);
            }
            cerrarArchivo();
        } catch (IOException e) {
            System.err.println("Error al escribir en archivo");
        }
    }

    public void agregarUsuarioUnico(Usuario usuario) throws UsuarioRepetidoException, IOException {
        if (usuarios.contains(usuario)) {
            cerrarArchivo();
            throw new UsuarioRepetidoException("El usuario ya se encuentra en el archivo");
        }
        agregarUsuario(usuario);
        cerrarArchivo();
    }
}
