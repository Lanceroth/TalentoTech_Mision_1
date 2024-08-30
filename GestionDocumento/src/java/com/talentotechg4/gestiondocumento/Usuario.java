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

        public void registrar() {
            System.out.println("Registrando usuario: " + nombre);
        }

        public boolean iniciarSesion() {
            System.out.println("Iniciando sesión para: " + email);
            return false;
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
    }

