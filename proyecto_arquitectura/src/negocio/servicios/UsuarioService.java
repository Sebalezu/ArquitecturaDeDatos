package negocio.servicios;

import datos.repositorios.interfaces.IUsuarioRepository;
import dominio.Usuario;

public class UsuarioService {
    private IUsuarioRepository usuarioRepo;

    public UsuarioService(IUsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public void registrarUsuario(int id, String nombre, String correo, String telefono, String rol) {
    if (nombre == null || nombre.length() == 0 || correo == null || correo.length() == 0) {
        System.out.println("Error: El nombre y el correo son obligatorios.");
        return;
    }

    if (!correo.contains("@")) {
        System.out.println("Error: El correo electrónico es inválido.");
        return;
    }

    if (!rol.equals("SOLICITANTE") && !rol.equals("FUNCIONARIO")) {
        System.out.println("Error: El rol debe ser SOLICITANTE o FUNCIONARIO.");
        return;
    }

    if (usuarioRepo.buscarPorId(id) != null) {
        System.out.println("Error: Ya existe un usuario registrado con ese ID.");
        return;
    }

    if (usuarioRepo.buscarPorCorreo(correo) != null) {
        System.out.println("Error: El correo ya se encuentra registrado.");
        return;
    }

    Usuario nuevoUsuario = new Usuario(id, nombre, correo, telefono, rol);
    usuarioRepo.guardar(nuevoUsuario);
    System.out.println("Usuario registrado exitosamente: " + nuevoUsuario.getNombre());
}
}