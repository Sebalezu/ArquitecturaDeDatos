package presentacion;

import negocio.servicios.ReporteService;
import negocio.servicios.SolicitudService;
import negocio.servicios.TipoSolicitudService;
import negocio.servicios.UsuarioService;
import datos.repositorios.interfaces.IUsuarioRepository;
import dominio.Solicitud;
import dominio.Usuario;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private UsuarioService usuarioService;
    private SolicitudService solicitudService;
    private ReporteService reporteService;
    private TipoSolicitudService tipoSolicitudService;
    private IUsuarioRepository usuarioRepo;
    private String rolActual;

    public Menu(UsuarioService usuarioService, SolicitudService solicitudService,
                ReporteService reporteService, TipoSolicitudService tipoSolicitudService,
                IUsuarioRepository usuarioRepo) {
        this.scanner = new Scanner(System.in);
        this.usuarioService = usuarioService;
        this.solicitudService = solicitudService;
        this.reporteService = reporteService;
        this.tipoSolicitudService = tipoSolicitudService;
        this.usuarioRepo = usuarioRepo;
    }

    public void iniciar() {
        System.out.println("=== EXPRESS - Sistema de Gestión de Solicitudes ===");
        System.out.print("Ingrese su correo para iniciar sesión (o presione Enter para registrar usuario primero): ");
        String correo = scanner.nextLine();

        Usuario usuarioActual = null;

        if (!correo.isBlank()) {
            usuarioActual = usuarioRepo.buscarPorCorreo(correo);
            if (usuarioActual == null) {
                System.out.println("No se encontró un usuario con ese correo. Debe registrarse primero.");
            } else {
                System.out.println("Bienvenido, " + usuarioActual.getNombre() + " (" + usuarioActual.getRol() + ")");
            }
        }

        if (usuarioActual == null) {
            this.rolActual = "SOLICITANTE";
            menuSolicitante();
        } else if (usuarioActual.getRol().equals("FUNCIONARIO")) {
            this.rolActual = "FUNCIONARIO";
            menuFuncionario();
        } else {
            this.rolActual = "SOLICITANTE";
            menuSolicitante();
        }
    }

    private void menuFuncionario() {
        int opcion = -1;

        while (opcion != 7) {
            System.out.println("\n=== MENÚ FUNCIONARIO ===");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Registrar tipo de solicitud");
            System.out.println("3. Crear solicitud");
            System.out.println("4. Cambiar estado de solicitud");
            System.out.println("5. Consultar solicitudes por estado");
            System.out.println("6. Generar reporte");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1: registrarUsuario(); break;
                case 2: registrarTipoSolicitud(); break;
                case 3: crearSolicitud(); break;
                case 4: cambiarEstado(); break;
                case 5: consultarPorEstado(); break;
                case 6: generarReporte(); break;
                case 7: System.out.println("Saliendo del sistema..."); break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private void menuSolicitante() {
        int opcion = -1;

        while (opcion != 5) {
            System.out.println("\n=== MENÚ SOLICITANTE ===");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Crear solicitud");
            System.out.println("3. Consultar solicitudes por estado");
            System.out.println("4. Generar reporte");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1: registrarUsuario(); break;
                case 2: crearSolicitud(); break;
                case 3: consultarPorEstado(); break;
                case 4: generarReporte(); break;
                case 5: System.out.println("Saliendo del sistema..."); break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private void registrarUsuario() {
        System.out.print("ID: ");
        int id = leerEntero();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Rol (SOLICITANTE/FUNCIONARIO): ");
        String rol = scanner.nextLine();

        usuarioService.registrarUsuario(id, nombre, correo, telefono, rol, rolActual);
    }

    private void registrarTipoSolicitud() {
        System.out.print("ID: ");
        int id = leerEntero();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Tiempo estimado (días): ");
        int tiempoEstimadoDias = leerEntero();

        tipoSolicitudService.registrarTipoSolicitud(id, nombre, descripcion, tiempoEstimadoDias);
    }

    private void crearSolicitud() {
        System.out.print("ID de la solicitud: ");
        int id = leerEntero();
        System.out.print("ID del usuario: ");
        int idUsuario = leerEntero();
        System.out.print("ID del tipo de solicitud: ");
        int idTipo = leerEntero();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        solicitudService.registrarSolicitud(id, idUsuario, idTipo, descripcion);
    }

    private void cambiarEstado() {
        System.out.print("ID de la solicitud: ");
        int id = leerEntero();
        System.out.print("Nuevo estado (CREADA, EN_REVISION, APROBADA, RECHAZADA, CERRADA): ");
        String estado = scanner.nextLine();

        solicitudService.cambiarEstadoSolicitud(id, estado);
    }

    private void consultarPorEstado() {
        System.out.print("Estado a consultar: ");
        String estado = scanner.nextLine();

        List<Solicitud> resultado = solicitudService.consultarSolicitudesPorEstado(estado);

        if (resultado.isEmpty()) {
            System.out.println("No hay solicitudes con ese estado.");
        } else {
            for (Solicitud s : resultado) {
                System.out.println(s);
            }
        }
    }

    private void generarReporte() {
        List<Solicitud> todas = solicitudService.obtenerTodas();
        reporteService.generarReporteEstadistico(todas);
    }

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}