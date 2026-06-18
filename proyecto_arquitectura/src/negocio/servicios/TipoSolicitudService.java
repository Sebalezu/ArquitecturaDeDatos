package negocio.servicios;

import datos.repositorios.interfaces.ITipoSolicitudRepository;
import dominio.TipoSolicitud;

public class TipoSolicitudService {
    private ITipoSolicitudRepository tipoSolicitudRepo;

    public TipoSolicitudService(ITipoSolicitudRepository tipoSolicitudRepo) {
        this.tipoSolicitudRepo = tipoSolicitudRepo;
    }

    public void registrarTipoSolicitud(int id, String nombre, String descripcion, int tiempoEstimadoDias) {
        if (nombre == null || nombre.length() == 0) {
            System.out.println("Error: El nombre del tipo de solicitud es obligatorio.");
            return;
        }

        if (tiempoEstimadoDias <= 0) {
            System.out.println("Error: El tiempo estimado debe ser mayor a cero.");
            return;
        }

        if (tipoSolicitudRepo.buscarPorId(id) != null) {
            System.out.println("Error: Ya existe un tipo de solicitud con ese ID.");
            return;
        }

        TipoSolicitud nuevoTipo = new TipoSolicitud(id, nombre, descripcion, tiempoEstimadoDias);
        tipoSolicitudRepo.guardar(nuevoTipo);
        System.out.println("Tipo de solicitud registrado exitosamente: " + nuevoTipo.getNombre());
    }
}