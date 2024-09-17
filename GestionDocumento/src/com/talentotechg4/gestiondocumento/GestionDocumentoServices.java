package com.talentotechg4.gestiondocumento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GestionDocumentoServices {

    private Scanner in = new Scanner(System.in);

    public void insertarDocumento(Connection conn) throws SQLException {
        // Captura de los detalles del documento
        System.out.println("Ingrese el ID del documento (0 si es nuevo): ");
        int idDocumento = in.nextInt();
        in.nextLine(); // Consumir el salto de línea

        System.out.println("Ingrese el título del documento: ");
        String titulo = in.nextLine();

        System.out.println("Ingrese el autor del documento: ");
        String autor = in.nextLine();

        System.out.println("Ingrese el año de publicación del documento: ");
        int añoPublicacion = in.nextInt();
        in.nextLine(); // Consumir el salto de línea

        System.out.println("Ingrese el tipo de documento: ");
        String tipoDocumento = in.nextLine();

        System.out.println("Ingrese el precio de compra del documento: ");
        double precioCompra = in.nextDouble();

        System.out.println("Ingrese el precio de alquiler del documento: ");
        double precioAlquiler = in.nextDouble();
        in.nextLine(); // Consumir el salto de línea

        System.out.println("Ingrese si está disponible (1 para sí, 0 para no): ");
        boolean disponible = in.nextInt() == 1;

        Documento documento = new Documento(idDocumento, titulo, autor, añoPublicacion, tipoDocumento, precioCompra, precioAlquiler, disponible);

        if (idDocumento == 0) {
            Documento.crearDocumento(conn, documento);
            System.out.println("Documento creado con éxito.");
        } else {
            Documento.actualizarDocumento(conn, documento);
            System.out.println("Documento actualizado con éxito.");
        }
    }

    public void leerDocumento(Connection conn) throws SQLException {
        System.out.println("Ingrese el ID del documento a consultar: ");
        int idDocumento = in.nextInt();
        in.nextLine(); // Consumir el salto de línea

        Documento documento = Documento.leerDocumento(conn, idDocumento);
        if (documento != null) {
            System.out.println("Documento encontrado: ");
            System.out.println("ID: " + documento.getIdDocumento());
            System.out.println("Título: " + documento.getTitulo());
            System.out.println("Autor: " + documento.getAutor());
            System.out.println("Año de publicación: " + documento.getAñoPublicacion());
            System.out.println("Tipo de documento: " + documento.getTipoDocumento());
            System.out.println("Precio de compra: " + documento.getPrecioCompra());
            System.out.println("Precio de alquiler: " + documento.getPrecioAlquiler());
            System.out.println("Disponible: " + documento.isDisponible());
        } else {
            System.out.println("Documento no encontrado.");
        }
    }

    public void eliminarDocumento(Connection conn) throws SQLException {
        System.out.println("Ingrese el ID del documento a eliminar: ");
        int idDocumento = in.nextInt();
        in.nextLine(); // Consumir el salto de línea

        Documento.eliminarDocumento(conn, idDocumento);
        System.out.println("Documento eliminado con éxito.");
    }

    public void listarDocumentos(Connection conn) throws SQLException {
        List<Documento> documentos = Documento.listarDocumentos(conn);
        if (documentos.isEmpty()) {
            System.out.println("No hay documentos en el sistema.");
        } else {
            for (Documento doc : documentos) {
                System.out.println("ID: " + doc.getIdDocumento());
                System.out.println("Título: " + doc.getTitulo());
                System.out.println("Autor: " + doc.getAutor());
                System.out.println("Año de publicación: " + doc.getAñoPublicacion());
                System.out.println("Tipo de documento: " + doc.getTipoDocumento());
                System.out.println("Precio de compra: " + doc.getPrecioCompra());
                System.out.println("Precio de alquiler: " + doc.getPrecioAlquiler());
                System.out.println("Disponible: " + doc.isDisponible());
                System.out.println("-------------");
            }
        }
    }

    public void actualizarDocumento(Connection conn) throws SQLException {
        if (conn == null) {
            System.out.println("No se pudo establecer la conexión.");
            return;
        }

        // Solicitar el ID del documento a actualizar
        System.out.print("Ingrese el ID del documento a actualizar: ");
        int idDocumento = in.nextInt();
        in.nextLine(); // Consumir el salto de línea

        // Consultar el documento existente
        Documento documento = Documento.leerDocumento(conn, idDocumento);
        if (documento == null) {
            System.out.println("Documento no encontrado.");
            return;
        }

        // Solicitar los nuevos datos
        System.out.print("Ingrese el nuevo título (dejar vacío para no cambiar): ");
        String nuevoTitulo = in.nextLine();
        System.out.print("Ingrese el nuevo autor (dejar vacío para no cambiar): ");
        String nuevoAutor = in.nextLine();
        System.out.print("Ingrese el nuevo año de publicación (0 para no cambiar): ");
        int nuevoAnoPublicacion = in.nextInt();
        in.nextLine(); // Consumir el salto de línea
        System.out.print("Ingrese el nuevo tipo de documento (dejar vacío para no cambiar): ");
        String nuevoTipoDocumento = in.nextLine();
        System.out.print("Ingrese el nuevo precio de compra (0 para no cambiar): ");
        double nuevoPrecioCompra = in.nextDouble();
        System.out.print("Ingrese el nuevo precio de alquiler (0 para no cambiar): ");
        double nuevoPrecioAlquiler = in.nextDouble();
        System.out.print("Ingrese la nueva disponibilidad (1 para sí, 0 para no): ");
        boolean nuevaDisponibilidad = in.nextInt() == 1;

        // Actualizar el documento en la base de datos
        String sql = "UPDATE documento SET titulo = ?, autor = ?, año_publicacion = ?, tipo_documento = ?, precio_compra = ?, precio_alquiler = ?, disponible = ? WHERE id_documento = ?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, nuevoTitulo.isEmpty() ? documento.getTitulo() : nuevoTitulo);
            stm.setString(2, nuevoAutor.isEmpty() ? documento.getAutor() : nuevoAutor);
            stm.setInt(3, nuevoAnoPublicacion == 0 ? documento.getAñoPublicacion() : nuevoAnoPublicacion);
            stm.setString(4, nuevoTipoDocumento.isEmpty() ? documento.getTipoDocumento() : nuevoTipoDocumento);
            stm.setDouble(5, nuevoPrecioCompra == 0 ? documento.getPrecioCompra() : nuevoPrecioCompra);
            stm.setDouble(6, nuevoPrecioAlquiler == 0 ? documento.getPrecioAlquiler() : nuevoPrecioAlquiler);
            stm.setBoolean(7, nuevaDisponibilidad); // En caso de no cambiar, se actualiza con el valor ingresado
            stm.setInt(8, idDocumento);

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Documento actualizado con éxito.");
            } else {
                System.out.println("No se actualizó ningún documento. Verifique el ID.");
            }
        }
    }
}

