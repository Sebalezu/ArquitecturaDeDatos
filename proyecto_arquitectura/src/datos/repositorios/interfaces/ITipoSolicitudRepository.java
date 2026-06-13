package datos.repositorios.interfaces;

import dominio.TipoSolicitud;
import java.util.List;

public interface ITipoSolicitudRepository {
    void guardar(TipoSolicitud tipo);
    TipoSolicitud buscarPorId(int id);
    List<TipoSolicitud> obtenerTodos();
}