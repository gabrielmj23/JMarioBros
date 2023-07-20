package multijugador;

/**
 * Le indica a los otros clientes que deben iniciar el juego, esto implica
 * aumentar los UPS y cambiar el panel activo
 *
 * @author Gabriel
 */
public class PaqueteIniciar extends Paquete {

    public PaqueteIniciar() {
        super(03);
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
        return "03".getBytes();
    }

}
