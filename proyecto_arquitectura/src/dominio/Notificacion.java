package dominio;

import java.time.LocalDate;

public class Notificacion {
    private int id;
    private Solicitud solicitud;
    private String mensaje;
    private LocalDate fecha;
    private String estadoSolicitud;

    public Notificacion(int id, Solicitud solicitud, String mensaje, LocalDate fecha, String estadoSolicitud) {
        this.id = id;
        this.solicitud = solicitud;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.estadoSolicitud = estadoSolicitud;
    }

    public int getId() { return id; }
    public Solicitud getSolicitud() { return solicitud; }
    public String getMensaje() { return mensaje; }
    public LocalDate getFecha() { return fecha; }
    public String getEstadoSolicitud() { return estadoSolicitud; }

    @Override
    public String toString() {
        return "[Notificacion] " + mensaje + " | Estado: " + estadoSolicitud + " | Fecha: " + fecha;
    }
}