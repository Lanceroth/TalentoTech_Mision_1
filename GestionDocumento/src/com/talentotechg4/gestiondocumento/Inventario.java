package com.talentotechg4.gestiondocumento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventario {
    private int idInventario;
    private Map<Documento, Integer> inventarioPorDocumento;

    public Inventario(int idInventario) {
        this.idInventario = idInventario;
        this.inventarioPorDocumento = new HashMap<>();
    }

    // Método para añadir un documento al inventario
    public void agregarDocumento(Connection conn, Documento documento, int cantidad_disponible) throws SQLException {
        if (conn == null) {
            System.out.println("No se pudo establecer la conexión.");
            return;
        }

        String sql = "INSERT INTO inventario (documento_id, cantidad_disponible) VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE cantidad_disponible = cantidad_disponible + ?";

        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            // Parámetros para el INSERT
            stm.setInt(1, documento.getIdDocumento());
            stm.setInt(2, cantidad_disponible);
            // Parámetro adicional para el UPDATE (se usa nuevamente cantidad_disponible)
            stm.setInt(3, cantidad_disponible);

            stm.executeUpdate();
        }
    }

    // Método para actualizar la cantidad de un documento en el inventario
    public void actualizarInventario(Connection conn, Documento documento, int cantidad_disponible) throws SQLException {
        if (conn == null) {
            System.out.println("No se pudo establecer la conexión.");
            return;
        }

        String sql = "UPDATE inventario SET cantidad_disponible = ? WHERE documento_id = ?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, cantidad_disponible);
            stm.setInt(2, documento.getIdDocumento());
            stm.executeUpdate();
        }
    }

    // Método para consultar el inventario
    public List<Documento> consultarInventario(Connection conn) throws SQLException {
        List<Documento> documentos = new ArrayList<>();
        if (conn == null) {
            System.out.println("No se pudo establecer la conexión.");
            return documentos;
        }

        String sql = "SELECT d.id_documento, d.titulo, d.autor, d.año_publicacion, d.tipo_documento, d.precio_compra, "
                + "d.precio_alquiler, d.disponible, i.cantidad_disponible FROM documento d "
                + "JOIN inventario i ON d.id_documento = i.documento_id";

        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Documento doc = new Documento();
                doc.setIdDocumento(rs.getInt("id_documento"));
                doc.setTitulo(rs.getString("titulo"));
                doc.setAutor(rs.getString("autor"));
                doc.setAñoPublicacion(rs.getInt("año_publicacion"));
                doc.setTipoDocumento(rs.getString("tipo_documento"));
                doc.setPrecioCompra(rs.getDouble("precio_compra"));
                doc.setPrecioAlquiler(rs.getDouble("precio_alquiler"));
                doc.setDisponible(rs.getBoolean("disponible"));
                documentos.add(doc);
                // Se guarda la cantidad disponible en el inventario
                this.inventarioPorDocumento.put(doc, rs.getInt("cantidad_disponible"));
            }
        }
        return documentos;
    }

    // Método para eliminar un documento del inventario
    public void eliminarDocumento(Connection conn, Documento documento) throws SQLException {
        if (conn == null) {
            System.out.println("No se pudo establecer la conexión.");
            return;
        }

        String sql = "DELETE FROM inventario WHERE documento_id = ?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, documento.getIdDocumento());
            stm.executeUpdate();
        }
    }

    // Getters y Setters
    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public Map<Documento, Integer> getInventarioPorDocumento() {
        return inventarioPorDocumento;
    }

    public void setInventarioPorDocumento(Map<Documento, Integer> inventarioPorDocumento) {
        this.inventarioPorDocumento = inventarioPorDocumento;
    }
}
