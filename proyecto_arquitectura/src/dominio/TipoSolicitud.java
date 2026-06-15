package dominio;

public class TipoSolicitud {
    private int id;
    private String nombre;
    private String descripcion;
    private int tiempoEstimadoDias;

    public TipoSolicitud(int id, String nombre, String descripcion, int tiempoEstimadoDias) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tiempoEstimadoDias = tiempoEstimadoDias;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public int getTiempoEstimadoDias() { return tiempoEstimadoDias; }

    @Override
    public String toString() {
        return "TipoSolicitud{id=" + id + ", nombre=" + nombre + ", dias=" + tiempoEstimadoDias + "}";
    }
}