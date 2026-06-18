package negocio.servicios;

import datos.repositorios.interfaces.ISolicitudRepository;
import datos.repositorios.interfaces.ITipoSolicitudRepository;
import datos.repositorios.interfaces.IUsuarioRepository;
import dominio.Notificacion;
import dominio.Solicitud;
import dominio.TipoSolicitud;
import dominio.Usuario;

import java.time.LocalDate;
import java.util.List;

public class SolicitudService {
    private IUsuarioRepository usuarioRepo;
    private ITipoSolicitudRepository tipoSolicitudRepo;
    private ISolicitudRepository solicitudRepo;
    private int contadorNotificaciones = 1;

    public SolicitudService(IUsuarioRepository usuarioRepo, ITipoSolicitudRepository tipoSolicitudRepo, ISolicitudRepository solicitudRepo) {
        this.usuarioRepo = usuarioRepo;
        this.tipoSolicitudRepo = tipoSolicitudRepo;
        this.solicitudRepo = solicitudRepo;
    }

    public void registrarSolicitud(int id, int idUsuario, int idTipoSolicitud, String descripcion) {
        if (descripcion == null || descripcion.length() == 0) {
            System.out.println("Error: La descripcion no puede estar vacia.");
            return;
        }

        Usuario usuario = usuarioRepo.buscarPorId(idUsuario);
        if (usuario == null) {
            System.out.println("Error: El usuario no existe.");
            return;
        }

        TipoSolicitud tipo = tipoSolicitudRepo.buscarPorId(idTipoSolicitud);
        if (tipo == null) {
            System.out.println("Error: El tipo de solicitud no existe.");
            return;
        }

        try {
           Solicitud nuevaSolicitud = new Solicitud.Builder()
                                        .conId(id)
                                        .conUsuario(usuario)
                                        .conTipo(tipo)
                                        .conDescripcion(descripcion)
                                        .conEstado("CREADA")
                                        .build();

            solicitudRepo.guardar(nuevaSolicitud);

            System.out.println("Solicitud creada con éxito usando el Builder.");
            System.out.println(nuevaSolicitud.toString());

            generarNotificacion(nuevaSolicitud, "Su solicitud ha sido creada exitosamente.");

        } catch (IllegalStateException e) {
            System.out.println("Error al construir la solicitud: " + e.getMessage());
        }
    }

    public void cambiarEstadoSolicitud(int idSolicitud, String nuevoEstado) {
        Solicitud solicitud = solicitudRepo.buscarPorId(idSolicitud);

        if (solicitud == null) {
            System.out.println("Error: La solicitud no existe.");
            return;
        }

        try {
            solicitud.cambiarEstado(nuevoEstado);
            solicitudRepo.actualizarEstado(idSolicitud, nuevoEstado);
            System.out.println("Estado actualizado correctamente a: " + nuevoEstado);

            generarNotificacion(solicitud, "El estado de su solicitud ha cambiado a " + nuevoEstado + ".");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void generarNotificacion(Solicitud solicitud, String mensaje) {
        Notificacion notificacion = new Notificacion(
            contadorNotificaciones++,
            solicitud,
            mensaje,
            LocalDate.now(),
            solicitud.getEstado()
        );

        System.out.println(notificacion.toString());
    }

    public List<Solicitud> consultarSolicitudesPorEstado(String estado) {
        return solicitudRepo.obtenerPorEstado(estado);
    }

    public List<Solicitud> obtenerTodas() {
        return solicitudRepo.obtenerTodas();
    }
}