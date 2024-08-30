package java.com.talentotechg4.gestiondocumento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventario {
    //Atributos
    private int idInventario;
    private int cantidadDisponible;
    private Map<Documento, Integer> inventarioPorDocumento;


    // constructor
    public Inventario(int idInventario) {
        this.idInventario = idInventario;
        this.cantidadDisponible = 0;
        this.inventarioPorDocumento = new HashMap<>();
    }

    //Metodo Actualizar inventario

    public void actualizarInventario(Documento documento, int cantidad) {
        int cantidadActual = inventarioPorDocumento.getOrDefault(documento, 0);
        int nuevacantidad = cantidad + cantidadActual;
        if (nuevacantidad >= 0) {
            inventarioPorDocumento.put(documento, nuevacantidad);
            cantidadDisponible += nuevacantidad; // actualizamos la cantidad total disponible
        } else {
            throw new IllegalArgumentException("Cantidad no puede ser menor que 0");
        }
    }

    //Metodo consultarInventario

    public List<Documento> consultarInventario() {
        return new ArrayList<>(inventarioPorDocumento.keySet());
    }

    // Otros métodos
    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void agregarDocumento(Documento documento) {
        actualizarInventario(documento, 1);
    }

    public void eliminarDocumento(Documento documento) {
        actualizarInventario(documento, -1);
    }
}
