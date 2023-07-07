package multijugador;

/**
 *
 * @author Gabriel
 */
public abstract class Paquete {

    /**
     * Define los tipos de paquete con sus códigos respectivos
     */
    public static enum TiposPaquete {
        INVALIDO(-1),
        UNIR(00),
        DESCONECTAR(01);

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

    /**
     * Lee la cadena de información contenida en el paquete
     *
     * @param datos
     * @return Datos del paquete
     */
    public String leerDatos(byte[] datos) {
        String msj = new String(datos).trim();
        return msj.substring(2);
    }
    
    public static TiposPaquete determinarPaquete(int id) {
        for (TiposPaquete p: TiposPaquete.values()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return TiposPaquete.INVALIDO;
    }

    /**
     * Envía un paquete de datos a un cliente
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
}
