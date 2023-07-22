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
        avatar = "/ui/Caparazon.png";
    }

    public Usuario(String nombre, String correo, String login, String contraseña, String avatar) {
        if (nombre.equals("") || correo.equals("") || login.equals("") || contraseña.equals("")) {
            throw new IllegalArgumentException("No puede dejar campos vacios");
        }
        this.nombre = nombre;
        this.correo = correo;
        this.login = login;
        this.contraseña = contraseña;
        this.avatar = "/ui/" + avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getLogin() {
        return login;
    }

    public String getContraseña() {
        return contraseña;
    }
    
    @Override
    public String toString() {
        return "Nombre: " + nombre + " / Correo: " + correo + " / Login: " + login + " / Contraseña: " + contraseña;
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
