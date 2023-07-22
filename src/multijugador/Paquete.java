package multijugador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Gabriel
 * @param <T>
 */
public abstract class Paquete<T> {

    /**
     * Define los tipos de paquete con sus códigos respectivos
     */
    public static enum TiposPaquete {
        INVALIDO(-1),
        UNIR(00),
        DESCONECTAR(01),
        ACTUALIZAR(02),
        INICIAR(03),
        ENEMIGO(04),
        PODER(05),
        BLOQUESINT(06),
        PROYECTILES(07);

        private int id;

        private TiposPaquete(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public byte id;

    public Paquete(int id) {
        this.id = (byte) id;
    }

    public T leerObj(byte[] datos) throws IOException, ClassNotFoundException {
        int inicioT = "00".getBytes().length;
        byte[] datosJugador = new byte[datos.length - inicioT];
        for (int i = inicioT; i < datos.length; i++) {
            datosJugador[i - inicioT] = datos[i];
        }
        ByteArrayInputStream streamLectura = new ByteArrayInputStream(datosJugador);
        ObjectInputStream streamObjeto = new ObjectInputStream(streamLectura);
        return (T) streamObjeto.readObject();
    }

    public static TiposPaquete determinarPaquete(int id) {
        for (TiposPaquete p : TiposPaquete.values()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return TiposPaquete.INVALIDO;
    }

    /**
     * Envía un paquete de datos desde un cliente
     *
     * @param cliente
     */
    public abstract void escribirDatos(Cliente cliente);

    /**
     * Envía un paquete a cada cliente conectado al servidor dado
     *
     * @param servidor
     */
    public abstract void escribirDatos(Servidor servidor);

    /**
     * Recibe del paquete sus datos en forma de byte[]
     *
     * @return Datos serializados en el paquete
     */
    public abstract byte[] getDatos();
}
