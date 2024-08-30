package java.com.talentotechg4.gestiondocumento;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;


 class Documento {
        protected int idDocumento;
        protected String titulo;
        protected String autor;
        protected int añoPublicacion;
        protected String tipoDocumento;
        protected double precioCompra;
        protected double precioAlquiler;
        protected boolean disponible;

        public void actualizar() {
            String nuevoTitulo = "Nuevo titulo";
            this.titulo = nuevoTitulo;
            String nuevoAutor = "Nuyevo autor";
            this.autor = nuevoAutor;
            int nuevoAñoPublicacion = 0;
            this.añoPublicacion = nuevoAñoPublicacion;
            String nuevoTipoDocumento = "Nuevo tipo de documento";
            this.tipoDocumento = nuevoTipoDocumento;
            double nuevoPrecioCompra = 0;
            this.precioCompra = nuevoPrecioCompra;
            double nuevoPrecioAlquiler = 0;
            this.precioAlquiler = nuevoPrecioAlquiler;
            System.out.println("Documento actualizado: " + titulo);
        }

        public void eliminar() {
            this.disponible = false; // Cambia el estado del documento a no disponible
            System.out.println("Documento eliminado: " + titulo);
        }

        public boolean consultarDisponibilidad() {
            return disponible;
        }

        public String getTitulo() {
            return "Titulo";
        }
    }

    class LibroElectronico extends Documento {
        private String formato;
        private double tamañoArchivo;
    }

    class Revista extends Documento {
        private String formato;
        private double tamañoArchivo;
    }

    class Iconografico extends Documento {
        private String formato;
        private double tamañoArchivo;
    }

    class Monografia extends Documento {
        private String formato;
        private double tamañoArchivo;
    }

    class Ensayo extends Documento {
        private String formato;
        private double tamañoArchivo;
    }

    class Investigacion extends Documento {
        private String formato;
        private double tamañoArchivo;
    }
