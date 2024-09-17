package com.talentotechg4.gestiondocumento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int idVenta;
    private Date fechaVenta;
    private Usuario usuario;
    private Documento documento;
    private double precioTotal;

    // Constructor
    public Venta(int idVenta, Date fechaVenta, Usuario usuario, Documento documento, double precioTotal) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.usuario = usuario;
        this.documento = documento;
        this.precioTotal = precioTotal;
    }

    // Getters y Setters
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
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

    // Método para registrar una venta en la base de datos
    public void registrarVenta(Connection conn) throws SQLException {
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser nula.");
        }

        String sql = "INSERT INTO venta (fecha_venta, usuario_id, documento_id, precio_total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setDate(1, fechaVenta);
            stm.setLong(2, usuario.getIdUsuario());
            stm.setLong(3, documento.getIdDocumento());
            stm.setDouble(4, precioTotal);
            stm.executeUpdate();
        }
    }

    // Metodo para consultar el histórico de ventas
    public static List<Venta> consultarHistoricoVentas(Connection conn) throws SQLException {
        List<Venta> ventas = new ArrayList<>();
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser nula.");
        }

        String sql = "SELECT v.id_venta, v.fecha_venta, v.usuario_id, v.documento_id, v.precio_total, "
                + "u.nombre AS usuario_nombre, d.titulo AS documento_titulo "
                + "FROM venta v "
                + "JOIN usuario u ON v.usuario_id = u.id_usuario "
                + "JOIN documento d ON v.documento_id = d.id_documento";
        try (PreparedStatement stm = conn.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                Venta venta = new Venta(
                        rs.getInt("id_venta"),
                        rs.getDate("fecha_venta"),
                        new Usuario(rs.getInt("usuario_id"), rs.getString("usuario_nombre")),
                        new Documento(rs.getInt("documento_id"), rs.getString("documento_titulo"), null, 0, null, 0, 0, true),
                        rs.getDouble("precio_total")
                );
                ventas.add(venta);
            }
        }
        return ventas;
    }
}
