package com.talentotechg4.gestiondocumento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Documento {
    private int idDocumento;
    private String titulo;
    private String autor;
    private int añoPublicacion;
    private String tipoDocumento;
    private double precioCompra;
    private double precioAlquiler;
    private boolean disponible;

    // Constructor
    public Documento(int idDocumento, String titulo, String autor, int añoPublicacion, String tipoDocumento, double precioCompra, double precioAlquiler, boolean disponible) {
        this.idDocumento = idDocumento;
        this.titulo = titulo;
        this.autor = autor;
        this.añoPublicacion = añoPublicacion;
        this.tipoDocumento = tipoDocumento;
        this.precioCompra = precioCompra;
        this.precioAlquiler = precioAlquiler;
        this.disponible = disponible;
    }

    public Documento() {

    }

    // Getters y Setters
    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAñoPublicacion() {
        return añoPublicacion;
    }

    public void setAñoPublicacion(int añoPublicacion) {
        this.añoPublicacion = añoPublicacion;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioAlquiler() {
        return precioAlquiler;
    }

    public void setPrecioAlquiler(double precioAlquiler) {
        this.precioAlquiler = precioAlquiler;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Métodos CRUD

    public static void crearDocumento(Connection conn, Documento doc) throws SQLException {
        String sql = "INSERT INTO documento (titulo, autor, año_publicacion, tipo_documento, precio_compra, precio_alquiler, disponible) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doc.getTitulo());
            stmt.setString(2, doc.getAutor());
            stmt.setInt(3, doc.getAñoPublicacion());
            stmt.setString(4, doc.getTipoDocumento());
            stmt.setDouble(5, doc.getPrecioCompra());
            stmt.setDouble(6, doc.getPrecioAlquiler());
            stmt.setBoolean(7, doc.isDisponible());
            stmt.executeUpdate();
        }
    }

    public static Documento leerDocumento(Connection conn, int idDocumento) throws SQLException {
        String sql = "SELECT * FROM documento WHERE id_documento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDocumento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Documento(
                        rs.getInt("id_documento"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("año_publicacion"),
                        rs.getString("tipo_documento"),
                        rs.getDouble("precio_compra"),
                        rs.getDouble("precio_alquiler"),
                        rs.getBoolean("disponible")
                );
            } else {
                return null;
            }
        }
    }

    public static void actualizarDocumento(Connection conn, Documento doc) throws SQLException {
        String sql = "UPDATE documento SET titulo = ?, autor = ?, año_publicacion = ?, tipo_documento = ?, precio_compra = ?, precio_alquiler = ?, disponible = ? WHERE id_documento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doc.getTitulo());
            stmt.setString(2, doc.getAutor());
            stmt.setInt(3, doc.getAñoPublicacion());
            stmt.setString(4, doc.getTipoDocumento());
            stmt.setDouble(5, doc.getPrecioCompra());
            stmt.setDouble(6, doc.getPrecioAlquiler());
            stmt.setBoolean(7, doc.isDisponible());
            stmt.setInt(8, doc.getIdDocumento());
            stmt.executeUpdate();
        }
    }

    public static void eliminarDocumento(Connection conn, int idDocumento) throws SQLException {
        String sql = "DELETE FROM documento WHERE id_documento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDocumento);
            stmt.executeUpdate();
        }
    }

    public static List<Documento> listarDocumentos(Connection conn) throws SQLException {
        List<Documento> documentos = new ArrayList<>();
        String sql = "SELECT * FROM documento";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                documentos.add(new Documento(
                        rs.getInt("id_documento"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("año_publicacion"),
                        rs.getString("tipo_documento"),
                        rs.getDouble("precio_compra"),
                        rs.getDouble("precio_alquiler"),
                        rs.getBoolean("disponible")
                ));
            }
        }
        return documentos;
    }
}
