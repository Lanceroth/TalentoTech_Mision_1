package java.com.talentotechg4.gestiondocumento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venta {
    private int idVenta;
    private Date fechaVenta;
    private String usuario;
    private String documento;
    private double precioTotal;

    private static List<Venta> ventas = new ArrayList<>();

    // Constructor
    public Venta(int idVenta, Date fechaVenta, String usuario, String documento, double precioTotal) {
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }


    public void registrarVenta() {
        ventas.add(this);
        System.out.println("Venta registrada con éxito: " + this);
    }


    public static List<Venta> consultarHistoricoVentas() {
        return ventas;
    }
}