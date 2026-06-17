package datos.repositorios.interfaces;

import dominio.Solicitud;
import java.util.List;

public interface ISolicitudRepository {
    void guardar(Solicitud solicitud);
    Solicitud buscarPorId(int id);
    List<Solicitud> obtenerTodas();
    List<Solicitud> obtenerPorEstado(String estado);
    void actualizarEstado(int id, String nuevoEstado);
}