package com.talentotechg4.gestiondocumento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Alquiler {
    private int idAlquiler;
    private Date fechaAlquiler;
    private Date fechaDevolucion;
    private Usuario usuario;
    private Documento documento;
    private double precioTotal;

    // Constructor
    public Alquiler(int idAlquiler, Date fechaAlquiler, Date fechaDevolucion, Usuario usuario, Documento documento, double precioTotal) {
        this.idAlquiler = idAlquiler;
        this.fechaAlquiler = fechaAlquiler;
        this.fechaDevolucion = fechaDevolucion;
        this.usuario = usuario;
        this.documento = documento;
        this.precioTotal = precioTotal;
    }

    // Getters y Setters
    public int getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Date getFechaAlquiler() {
        return fechaAlquiler;
    }

    public void setFechaAlquiler(Date fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    // Método para registrar un alquiler en la base de datos
    public void registrarAlquiler(Connection conn) throws SQLException {
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser nula.");
        }

        String sql = "INSERT INTO alquiler (fecha_alquiler, fecha_devolucion, usuario_id, documento_id, precio_total) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setDate(1, new java.sql.Date(fechaAlquiler.getTime()));
            stm.setDate(2, fechaDevolucion != null ? new java.sql.Date(fechaDevolucion.getTime()) : null);
            stm.setLong(3, usuario.getIdUsuario());
            stm.setLong(4, documento.getIdDocumento());
            stm.setDouble(5, precioTotal);
            stm.executeUpdate();
        }
    }

    // Método para consultar el histórico de alquileres
    public static List<Alquiler> consultarHistoricoAlquileres(Connection conn) throws SQLException {
        List<Alquiler> alquileres = new ArrayList<>();
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser nula.");
        }

        String sql = "SELECT a.id_alquiler, a.fecha_alquiler, a.fecha_devolucion, a.usuario_id, a.documento_id, a.precio_total, "
                + "u.nombre AS usuario_nombre, d.titulo AS documento_titulo "
                + "FROM alquiler a "
                + "JOIN usuario u ON a.usuario_id = u.id_usuario "
                + "JOIN documento d ON a.documento_id = d.id_documento";
        try (PreparedStatement stm = conn.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                Alquiler alquiler = new Alquiler(
                        rs.getInt("id_alquiler"),
                        rs.getDate("fecha_alquiler"),
                        rs.getDate("fecha_devolucion"),
                        new Usuario(rs.getInt("usuario_id"), rs.getString("usuario_nombre")),
                        new Documento(rs.getInt("documento_id"), rs.getString("documento_titulo"), null, 0, null, 0, 0, true),
                        rs.getDouble("precio_total")
                );
                alquileres.add(alquiler);
            }
        }
        return alquileres;
    }
}
