package datos.repositorios.impl;

import datos.conexion.ConexionDB;
import datos.repositorios.interfaces.ITipoSolicitudRepository;
import dominio.TipoSolicitud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoSolicitudRepositoryImpl implements ITipoSolicitudRepository {

    @Override
    public void guardar(TipoSolicitud tipo) {
        String sql = "INSERT INTO tipos_solicitud (nombre, descripcion, tiempo_estimado_dias) VALUES (?, ?, ?)";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tipo.getNombre());
            ps.setString(2, tipo.getDescripcion());
            ps.setInt(3, tipo.getTiempoEstimadoDias());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar tipo de solicitud: " + e.getMessage());
        }
    }

    @Override
    public TipoSolicitud buscarPorId(int id) {
        String sql = "SELECT * FROM tipos_solicitud WHERE id = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearTipo(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar tipo de solicitud: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<TipoSolicitud> obtenerTodos() {
        List<TipoSolicitud> tipos = new ArrayList<>();
        String sql = "SELECT * FROM tipos_solicitud";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tipos.add(mapearTipo(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener tipos de solicitud: " + e.getMessage());
        }
        return tipos;
    }

    private TipoSolicitud mapearTipo(ResultSet rs) throws SQLException {
        return new TipoSolicitud(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("descripcion"),
            rs.getInt("tiempo_estimado_dias")
        );
    }
}