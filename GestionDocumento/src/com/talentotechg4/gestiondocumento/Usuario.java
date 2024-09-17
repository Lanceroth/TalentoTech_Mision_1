package com.talentotechg4.gestiondocumento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String email;
    private String contraseña;
    private String tipoUsuario;

    // Constructor para creación de un nuevo usuario
    public Usuario(String nombre, String email, String contraseña, String tipoUsuario) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
    }

    // Constructor para recuperar un usuario existente de la base de datos
    public Usuario(int idUsuario, String nombre, String email, String contraseña, String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
    }

    // Constructor adicional
    public Usuario(int idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    // Método para validar credenciales
    public boolean validarCredenciales(String email, String contraseña) {
        return this.email.equals(email) && this.contraseña.equals(contraseña);
    }

    // Método para iniciar sesión
    public void iniciarSesion() {
        System.out.println("Iniciando sesión para: " + email);
    }

    // Método para cerrar sesión
    public void cerrarSesion() {
        System.out.println("Cerrando sesión para: " + email);
    }

    // Método para comprar un documento
    public void comprarDocumento(Documento doc) {
        System.out.println("Comprando el documento: " + doc.getTitulo() + " por el usuario: " + nombre);
    }

    // Método para alquilar un documento
    public void alquilarDocumento(Documento doc) {
        System.out.println("Alquilando el documento: " + doc.getTitulo() + " por el usuario: " + nombre);
    }

    // Método para leer un usuario por su ID desde la base de datos
    public static Usuario leerUsuario(Connection conn, int idUsuario) throws SQLException {
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser nula.");
        }

        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, idUsuario);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("contraseña"),
                            rs.getString("tipo_usuario")
                    );
                } else {
                    return null; // Usuario no encontrado
                }
            }
        }
    }

    public void registrar(Connection conn) throws SQLException {
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser nula.");
        }

        // Verificar si el correo ya existe
        String verificarEmailSQL = "SELECT * FROM usuario WHERE email = ?";
        try (PreparedStatement verificarEmailStmt = conn.prepareStatement(verificarEmailSQL)) {
            verificarEmailStmt.setString(1, this.email);
            try (ResultSet rs = verificarEmailStmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("El correo ya está registrado.");
                    return;
                }
            }
        }

        // Insertar el nuevo usuario si no existe el correo
        String sql = "INSERT INTO usuario (nombre, email, contraseña, tipo_usuario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            // Verificar que los valores no sean nulos
            if (this.nombre == null || this.email == null || this.contraseña == null || this.tipoUsuario == null) {
                throw new IllegalArgumentException("Todos los campos son obligatorios.");
            }

            // Asignar valores en el orden correcto
            stm.setString(1, this.nombre);
            stm.setString(2, this.email);
            stm.setString(3, this.contraseña);
            stm.setString(4, this.tipoUsuario);

            int rowsInserted = stm.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Usuario registrado con éxito.");
            } else {
                System.out.println("No se pudo registrar el usuario.");
            }
        }
    }
}





