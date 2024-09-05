package java.com.talentotechg4.gestiondocumento;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

class Usuario {
    private int idUsuario;
    private String nombre;
    private String email;
    private String contraseña;
    private String tipoUsuario;

    public Usuario(String nombre, String email, String contraseña, String tipoUsuario) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
    }

    public boolean validarCredenciales(String email, String contrasena) {
        return this.email.equals(email) && this.contraseña.equals(contrasena);
    }

    public boolean iniciarSesion() {
        System.out.println("Iniciando sesión para: " + email);
        return true;
    }

    public void cerrarSesion() {
        System.out.println("Cerrando sesión para: " + email);
    }

    public void comprarDocumento(Documento doc) {
        System.out.println("Comprando el documento: " + doc.getTitulo() + " por el usuario: " + nombre);
    }

    public void alquilarDocumento(Documento doc) {
        System.out.println("Alquilando el documento: " + doc.getTitulo() + " por el usuario: " + nombre);
    }

    public String getEmail() {
        return email;
    }

    // Otros getters y setters según sea necesario
}
