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
        if (nombre.equals("") || correo.equals("") || login.equals("") || contraseña.equals("")) {
            throw new IllegalArgumentException("No puede dejar campos vacios");
        }
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        return login.equals(other.login);
    }
}
