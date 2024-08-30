package java.com.talentotechg4.gestiondocumento;
import java.util.Date;
import java.util.List;

public class Alquiler {

    private int idAlquiler;
    private Date fechaAlquiler;
    private Date fechaDevolucion;
    private Usuario usuario;
    private Documento documento;
    private double precioTotal;


    public Alquiler(int idAlquiler, Date fechaAlquiler, Date fechaDevolucion, Usuario usuario, Documento documento, double precioTotal) {
        this.idAlquiler = idAlquiler;
        this.fechaAlquiler = fechaAlquiler;
        this.fechaDevolucion = fechaDevolucion;
        this.usuario = usuario;
        this.documento = documento;
        this.precioTotal = precioTotal;
    }

    // Getters and Setters
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

    // Métodos
    public void registrarAlquiler() {
    }

    public List<Alquiler> consultarHistoricoAlquileres() {

        return null;
    }
}