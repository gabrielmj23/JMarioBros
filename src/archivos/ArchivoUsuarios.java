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

    private void abrirArchivoEscritura() throws IOException, ClassNotFoundException {
        abrirArchivoLectura();
        usuarios = leerUsuarios();
        salida = new ObjectOutputStream(new FileOutputStream(new File("usuarios.txt")));
    }

    private void abrirArchivoLectura() throws IOException {
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
        return lista;
    }
}
