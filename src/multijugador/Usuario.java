package multijugador;

import java.io.Serializable;

/**
 *
 * @author Gabriel
 */
public class Usuario implements Serializable {

    private String nombre;
    private String correo;
    private String login;
    private String contraseña;
    private String avatar;

    public Usuario(String nombre, String correo, String login, String contraseña) {
        this.nombre = nombre;
        this.correo = correo;
        this.login = login;
        this.contraseña = contraseña;
        avatar = "media/misc/avatarDefecto.png";
    }

    public Usuario(String nombre, String correo, String login, String contraseña, String avatar) {
        this.nombre = nombre;
        this.correo = correo;
        this.login = login;
        this.contraseña = contraseña;
        this.avatar = avatar;
    }
    
    public String getLogin() {
        return login;
    }
}
