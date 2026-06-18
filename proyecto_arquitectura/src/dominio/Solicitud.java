package dominio;

import java.time.LocalDate;

public class Solicitud {
    private int id;
    private Usuario usuario;
    private TipoSolicitud tipoSolicitud;
    private String descripcion;
    private LocalDate fechaCreacion;
    private String estado;

    private Solicitud() {}

    public void cambiarEstado(String nuevoEstado) {
        String[] estadosValidos = {"CREADA", "EN_REVISION", "APROBADA", "RECHAZADA", "CERRADA"};
        boolean esValido = false;
        for (String e : estadosValidos) {
            if (e.equals(nuevoEstado)) {
                esValido = true;
                break;
            }
        }
        if (!esValido) {
            throw new IllegalArgumentException("Estado no válido: " + nuevoEstado);
        }

        if (!transicionPermitida(this.estado, nuevoEstado)) {
            throw new IllegalArgumentException("No se permite cambiar de " + this.estado + " a " + nuevoEstado + ".");
        }

        this.estado = nuevoEstado;
    }

    private boolean transicionPermitida(String estadoActual, String estadoNuevo) {
        switch (estadoActual) {
            case "CREADA":
                return estadoNuevo.equals("EN_REVISION");
            case "EN_REVISION":
                return estadoNuevo.equals("APROBADA") || estadoNuevo.equals("RECHAZADA");
            case "APROBADA":
                return estadoNuevo.equals("CERRADA");
            case "RECHAZADA":
                return estadoNuevo.equals("CERRADA");
            case "CERRADA":
                return false;
            default:
                return false;
        }
    }

    public int getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public TipoSolicitud getTipoSolicitud() { return tipoSolicitud; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public String getEstado() { return estado; }

    @Override
    public String toString() {
        return "Solicitud{id=" + id + ", estado=" + estado + ", usuario=" + usuario.getNombre() + "}";
    }

    public static class Builder {
        private int id;
        private Usuario usuario;
        private TipoSolicitud tipoSolicitud;
        private String descripcion;
        private LocalDate fechaCreacion;
        private String estado;

        public Builder conId(int id) {
            this.id = id;
            return this;
        }

        public Builder conUsuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Builder conTipo(TipoSolicitud tipoSolicitud) {
            this.tipoSolicitud = tipoSolicitud;
            return this;
        }

        public Builder conDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder conFechaCreacion(LocalDate fecha) {
            this.fechaCreacion = fecha;
            return this;
        }

        public Builder conEstado(String estado) {
            this.estado = estado;
            return this;
        }

        public Solicitud build() {
            if (usuario == null)
                throw new IllegalStateException("La solicitud debe tener un usuario.");
            if (descripcion == null || descripcion.isBlank())
                throw new IllegalStateException("Solicitud vacía, es importante completar la información.");
            if (tipoSolicitud == null)
                throw new IllegalStateException("Debe especificar un tipo de solicitud.");

            Solicitud s = new Solicitud();
            s.id = this.id;
            s.usuario = this.usuario;
            s.tipoSolicitud = this.tipoSolicitud;
            s.descripcion = this.descripcion;
            s.fechaCreacion = (this.fechaCreacion != null) ? this.fechaCreacion : LocalDate.now();
            s.estado = (this.estado != null) ? this.estado : "CREADA";
            return s;
        }
    }
}