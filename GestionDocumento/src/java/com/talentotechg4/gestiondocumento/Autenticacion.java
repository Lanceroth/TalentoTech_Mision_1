package java.com.talentotechg4.gestiondocumento;

import java.util.List;
import java.util.ArrayList;

public class Autenticacion {
    private boolean sesionActiva = false;
    private Usuario usuarioActual;

    // Lista simulada de usuarios
    private List<Usuario> listaUsuarios = new ArrayList<>();

    // Método para iniciar sesión usando email y contraseña
    public boolean iniciarSesion(String email, String contrasena) {
        Usuario usuario = buscarUsuarioPorEmail(email);

        if (usuario != null && usuario.validarCredenciales(email, contrasena)) {
            usuario.iniciarSesion();
            this.sesionActiva = true;
            this.usuarioActual = usuario;
            return true;
        }
        return false;
    }

    // Método para cerrar sesión
    public void cerrarSesion() {
        if (this.sesionActiva) {
            usuarioActual.cerrarSesion();
            this.sesionActiva = false;
            this.usuarioActual = null;
        }
    }

    // Método para buscar al usuario por email
    private Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }

    // Método para agregar un usuario a la lista (simulación)
    public void agregarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    // Getters
    public boolean isSesionActiva() {
        return sesionActiva;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}
