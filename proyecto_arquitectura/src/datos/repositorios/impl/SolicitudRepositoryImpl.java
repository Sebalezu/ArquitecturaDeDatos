package datos.repositorios.impl;

import datos.conexion.ConexionDB;
import datos.repositorios.interfaces.ISolicitudRepository;
import dominio.Solicitud;
import dominio.TipoSolicitud;
import dominio.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolicitudRepositoryImpl implements ISolicitudRepository {

    @Override
    public void guardar(Solicitud solicitud) {
        String sql = "INSERT INTO solicitudes (usuario_id, tipo_solicitud_id, descripcion, fecha_creacion, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, solicitud.getUsuario().getId());
            ps.setInt(2, solicitud.getTipoSolicitud().getId());
            ps.setString(3, solicitud.getDescripcion());
            ps.setDate(4, java.sql.Date.valueOf(solicitud.getFechaCreacion()));
            ps.setString(5, solicitud.getEstado());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar solicitud: " + e.getMessage());
        }
    }

    @Override
    public Solicitud buscarPorId(int id) {
        String sql = "SELECT s.*, u.nombre AS u_nombre, u.correo AS u_correo, u.telefono AS u_telefono, u.rol AS u_rol, " +
                     "t.nombre AS t_nombre, t.descripcion AS t_descripcion, t.tiempo_estimado_dias AS t_dias " +
                     "FROM solicitudes s " +
                     "JOIN usuarios u ON s.usuario_id = u.id " +
                     "JOIN tipos_solicitud t ON s.tipo_solicitud_id = t.id " +
                     "WHERE s.id = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearSolicitud(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar solicitud por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Solicitud> obtenerTodas() {
        List<Solicitud> solicitudes = new ArrayList<>();
        String sql = "SELECT s.*, u.nombre AS u_nombre, u.correo AS u_correo, u.telefono AS u_telefono, u.rol AS u_rol, " +
                     "t.nombre AS t_nombre, t.descripcion AS t_descripcion, t.tiempo_estimado_dias AS t_dias " +
                     "FROM solicitudes s " +
                     "JOIN usuarios u ON s.usuario_id = u.id " +
                     "JOIN tipos_solicitud t ON s.tipo_solicitud_id = t.id";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                solicitudes.add(mapearSolicitud(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener solicitudes: " + e.getMessage());
        }
        return solicitudes;
    }

    @Override
    public List<Solicitud> obtenerPorEstado(String estado) {
        List<Solicitud> solicitudes = new ArrayList<>();
        String sql = "SELECT s.*, u.nombre AS u_nombre, u.correo AS u_correo, u.telefono AS u_telefono, u.rol AS u_rol, " +
                     "t.nombre AS t_nombre, t.descripcion AS t_descripcion, t.tiempo_estimado_dias AS t_dias " +
                     "FROM solicitudes s " +
                     "JOIN usuarios u ON s.usuario_id = u.id " +
                     "JOIN tipos_solicitud t ON s.tipo_solicitud_id = t.id " +
                     "WHERE s.estado = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                solicitudes.add(mapearSolicitud(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener solicitudes por estado: " + e.getMessage());
        }
        return solicitudes;
    }

    @Override
    public void actualizarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE solicitudes SET estado = ? WHERE id = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar estado de solicitud: " + e.getMessage());
        }
    }

    private Solicitud mapearSolicitud(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario(
            rs.getInt("usuario_id"),
            rs.getString("u_nombre"),
            rs.getString("u_correo"),
            rs.getString("u_telefono"),
            rs.getString("u_rol")
        );

        TipoSolicitud tipo = new TipoSolicitud(
            rs.getInt("tipo_solicitud_id"),
            rs.getString("t_nombre"),
            rs.getString("t_descripcion"),
            rs.getInt("t_dias")
        );

        LocalDate fecha = rs.getDate("fecha_creacion").toLocalDate();

        return new Solicitud.Builder()
                .conId(rs.getInt("id"))
                .conUsuario(usuario)
                .conTipo(tipo)
                .conDescripcion(rs.getString("descripcion"))
                .conFechaCreacion(fecha)
                .conEstado(rs.getString("estado"))
                .build();
    }
}