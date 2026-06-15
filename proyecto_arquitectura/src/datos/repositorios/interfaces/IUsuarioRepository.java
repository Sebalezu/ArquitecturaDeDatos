package datos.repositorios.interfaces;

import dominio.Usuario;
import java.util.List;

public interface IUsuarioRepository {
    void guardar(Usuario usuario);
    Usuario buscarPorId(int id);
    Usuario buscarPorCorreo(String correo);
    List<Usuario> obtenerTodos();
}