package com.talentotechg4.gestiondocumento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/BD_SISTEMA_GESTION_DOCUMENTAL";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            if (conn != null) {
                System.out.println("Conexión exitosa a la base de datos!");

                GestionDocumentoServices gestionDocumento = new GestionDocumentoServices();
                Inventario inventario = new Inventario(1); // Supongamos que el ID del inventario es 1
                Autenticacion autenticacion = new Autenticacion();
                Scanner scanner = new Scanner(System.in);

                boolean running = true;
                boolean sessionActive = false;
                Usuario usuarioActual = null;

                while (running) {
                    if (!sessionActive) {
                        handleAuthentication(conn, scanner, autenticacion);
                        sessionActive = autenticacion.getUsuarioActual() != null;
                        usuarioActual = autenticacion.getUsuarioActual();
                    } else {
                        handleMainMenu(conn, scanner, gestionDocumento, inventario);
                        sessionActive = false; // Opcionalmente cerrar sesión después de cada operación
                    }
                }
                scanner.close();
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    private static void handleAuthentication(Connection conn, Scanner scanner, Autenticacion autenticacion) {
        System.out.println("Por favor, inicie sesión o registre una nueva cuenta:");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrar nuevo usuario");
        System.out.println("3. Salir");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        try {
            switch (opcion) {
                case 1:
                    loginUser(conn, scanner, autenticacion);
                    break;
                case 2:
                    registrarUsuario(conn);
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    System.exit(0); // Terminar el programa
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
                    break;
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar iniciar sesión o registrar usuario: " + e.getMessage());
        }
    }

    private static void loginUser(Connection conn, Scanner scanner, Autenticacion autenticacion) throws SQLException {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();

        if (autenticacion.iniciarSesion(conn, email, contraseña)) {
            System.out.println("Inicio de sesión exitoso. Bienvenido, " + autenticacion.getUsuarioActual().getNombre() + "!");
        } else {
            System.out.println("Email o contraseña incorrectos.");
        }
    }

    private static void handleMainMenu(Connection conn, Scanner scanner, GestionDocumentoServices gestionDocumento, Inventario inventario) {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Insertar Documento");
        System.out.println("2. Leer Documento");
        System.out.println("3. Actualizar Documento");
        System.out.println("4. Eliminar Documento");
        System.out.println("5. Listar Documentos");
        System.out.println("6. Añadir Documento al Inventario");
        System.out.println("7. Actualizar Inventario");
        System.out.println("8. Consultar Inventario");
        System.out.println("9. Registrar Venta");
        System.out.println("10. Consultar Histórico de Ventas");
        System.out.println("11. Registrar Alquiler");
        System.out.println("12. Consultar Histórico de Alquileres");
        System.out.println("13. Cerrar Sesión");
        System.out.println("14. Salir");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        try {
            switch (opcion) {
                case 1:
                    gestionDocumento.insertarDocumento(conn);
                    break;
                case 2:
                    gestionDocumento.leerDocumento(conn);
                    break;
                case 3:
                    gestionDocumento.actualizarDocumento(conn);
                    break;
                case 4:
                    gestionDocumento.eliminarDocumento(conn);
                    break;
                case 5:
                    gestionDocumento.listarDocumentos(conn);
                    break;
                case 6:
                    agregarDocumentoInventario(conn, scanner, inventario);
                    break;
                case 7:
                    actualizarInventario(conn, scanner, inventario);
                    break;
                case 8:
                    consultarInventario(conn, inventario);
                    break;
                case 9:
                    registrarVenta(conn, scanner);
                    break;
                case 10:
                    consultarHistoricoVentas(conn);
                    break;
                case 11:
                    registrarAlquiler(conn, scanner);
                    break;
                case 12:
                    consultarHistoricoAlquileres(conn);
                    break;
                case 13:
                    System.out.println("Sesión cerrada.");
                    break;
                case 14:
                    System.out.println("Saliendo...");
                    System.exit(0); // Terminar el programa
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
                    break;
            }
        } catch (SQLException e) {
            System.out.println("Se produjo un error al realizar la operación: " + e.getMessage());
        }
    }

    private static void agregarDocumentoInventario(Connection conn, Scanner scanner, Inventario inventario) throws SQLException {
        System.out.print("Ingrese el ID del documento: ");
        int docId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        Documento documento = Documento.leerDocumento(conn, docId);
        if (documento != null) {
            System.out.print("Ingrese la cantidad a añadir: ");
            int cantidadDisponible = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            inventario.agregarDocumento(conn, documento, cantidadDisponible);
            System.out.println("Documento añadido al inventario.");
        } else {
            System.out.println("Documento no encontrado.");
        }
    }

    private static void actualizarInventario(Connection conn, Scanner scanner, Inventario inventario) throws SQLException {
        System.out.print("Ingrese el ID del documento: ");
        int docId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        Documento documento = Documento.leerDocumento(conn, docId);
        if (documento != null) {
            System.out.print("Ingrese la nueva cantidad: ");
            int nuevaCantidad = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            inventario.actualizarInventario(conn, documento, nuevaCantidad);
            System.out.println("Inventario actualizado.");
        } else {
            System.out.println("Documento no encontrado.");
        }
    }

    private static void consultarInventario(Connection conn, Inventario inventario) throws SQLException {
        List<Documento> documentosInventario = inventario.consultarInventario(conn);
        if (documentosInventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            for (Documento doc : documentosInventario) {
                System.out.println("ID: " + doc.getIdDocumento());
                System.out.println("Título: " + doc.getTitulo());
                System.out.println("Autor: " + doc.getAutor());
                System.out.println("Año de publicación: " + doc.getAñoPublicacion());
                System.out.println("Tipo de documento: " + doc.getTipoDocumento());
                System.out.println("Precio de compra: " + doc.getPrecioCompra());
                System.out.println("Precio de alquiler: " + doc.getPrecioAlquiler());
                System.out.println("Disponible: " + doc.isDisponible());
                System.out.println("Cantidad disponible en inventario: " + inventario.getInventarioPorDocumento().get(doc));
                System.out.println("-------------");
            }
        }
    }

    private static void registrarVenta(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Ingrese el ID del usuario: ");
        int idUsuarioVenta = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        Usuario usuarioVenta = Usuario.leerUsuario(conn, idUsuarioVenta);
        if (usuarioVenta != null) {
            System.out.print("Ingrese el ID del documento: ");
            int idDocumentoVenta = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            Documento documentoVenta = Documento.leerDocumento(conn, idDocumentoVenta);
            if (documentoVenta != null) {
                System.out.print("Ingrese el precio total de la venta: ");
                double precioTotalVenta = scanner.nextDouble();
                scanner.nextLine(); // Consumir el salto de línea

                Venta venta = new Venta(0, new java.sql.Date(new Date().getTime()), usuarioVenta, documentoVenta, precioTotalVenta);
                venta.registrarVenta(conn);
                System.out.println("Venta registrada con éxito.");
            } else {
                System.out.println("Documento no encontrado.");
            }
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private static void consultarHistoricoVentas(Connection conn) throws SQLException {
        List<Venta> ventas = Venta.consultarHistoricoVentas(conn);
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            for (Venta v : ventas) {
                System.out.println("ID: " + v.getIdVenta());
                System.out.println("Fecha: " + v.getFechaVenta());
                System.out.println("Usuario: " + v.getUsuario().getNombre());
                System.out.println("Documento: " + v.getDocumento().getTitulo());
                System.out.println("Precio Total: " + v.getPrecioTotal());
                System.out.println("-------------");
            }
        }
    }

    private static void registrarAlquiler(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Ingrese el ID del usuario: ");
        int idUsuarioAlquiler = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        Usuario usuarioAlquiler = Usuario.leerUsuario(conn, idUsuarioAlquiler);
        if (usuarioAlquiler != null) {
            System.out.print("Ingrese el ID del documento: ");
            int idDocumentoAlquiler = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            Documento documentoAlquiler = Documento.leerDocumento(conn, idDocumentoAlquiler);
            if (documentoAlquiler != null) {
                System.out.print("Ingrese la fecha de devolución (formato YYYY-MM-DD): ");
                String fechaDevolucionStr = scanner.nextLine();
                Date fechaDevolucion = java.sql.Date.valueOf(fechaDevolucionStr);
                System.out.print("Ingrese el precio total del alquiler: ");
                double precioTotalAlquiler = scanner.nextDouble();
                scanner.nextLine(); // Consumir el salto de línea

                Alquiler alquiler = new Alquiler(0, new java.sql.Date(new Date().getTime()), fechaDevolucion, usuarioAlquiler, documentoAlquiler, precioTotalAlquiler);
                alquiler.registrarAlquiler(conn);
                System.out.println("Alquiler registrado con éxito.");
            } else {
                System.out.println("Documento no encontrado.");
            }
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private static void consultarHistoricoAlquileres(Connection conn) throws SQLException {
        List<Alquiler> alquileres = Alquiler.consultarHistoricoAlquileres(conn);
        if (alquileres.isEmpty()) {
            System.out.println("No hay alquileres registrados.");
        } else {
            for (Alquiler a : alquileres) {
                System.out.println("ID: " + a.getIdAlquiler());
                System.out.println("Fecha Alquiler: " + a.getFechaAlquiler());
                System.out.println("Fecha Devolución: " + a.getFechaDevolucion());
                System.out.println("Usuario: " + a.getUsuario().getNombre());
                System.out.println("Documento: " + a.getDocumento().getTitulo());
                System.out.println("Precio Total: " + a.getPrecioTotal());
                System.out.println("-------------");
            }
        }
    }

    private static void registrarUsuario(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese los datos del nuevo usuario:");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();
        System.out.print("Tipo de usuario: ");
        String tipoUsuario = scanner.nextLine();

        Usuario nuevoUsuario = new Usuario(nombre, email, contraseña, tipoUsuario);
        try {
            nuevoUsuario.registrar(conn);
            System.out.println("Usuario registrado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
    }
}
