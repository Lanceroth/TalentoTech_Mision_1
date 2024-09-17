package com.talentotechg4.gestiondocumento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Autenticacion {
    private boolean sesionActiva = false;
    private Usuario usuarioActual;

    // Método para iniciar sesión usando email y contraseña
    public boolean iniciarSesion(Connection conn, String email, String contraseña) throws SQLException {
        Usuario usuario = buscarUsuarioPorEmail(conn, email);

        if (usuario != null && usuario.validarCredenciales(email, contraseña)) {
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
    private Usuario buscarUsuarioPorEmail(Connection conn, String email) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            // Asegúrate de incluir el ID si es necesario
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("contraseña"),
                            rs.getString("tipo_usuario")
                    );
                }
            }
        }
        return null;
    }

    // Getters
    public boolean isSesionActiva() {
        return sesionActiva;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    // Métodos para CRUD de usuarios en la base de datos

    public void actualizarUsuario(Connection conn, Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nombre = ?, contraseña = ?, tipo_usuario = ? WHERE email = ?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, usuario.getNombre());
            stm.setString(2, usuario.getContraseña());
            stm.setString(3, usuario.getTipoUsuario());
            stm.setString(4, usuario.getEmail());
            stm.executeUpdate();
        }
    }

    public void eliminarUsuario(Connection conn, String email) throws SQLException {
        String sql = "DELETE FROM usuario WHERE email = ?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, email);
            stm.executeUpdate();
        }
    }

    public List<Usuario> listarUsuarios(Connection conn) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario(
                            // Incluye el ID si es necesario
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("contraseña"),
                            rs.getString("tipo_usuario")
                    );
                    usuarios.add(usuario);
                }
            }
        }
        return usuarios;
    }
}


