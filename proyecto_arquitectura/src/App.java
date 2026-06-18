import datos.conexion.ConexionDB;
import datos.repositorios.impl.SolicitudRepositoryImpl;
import datos.repositorios.impl.TipoSolicitudRepositoryImpl;
import datos.repositorios.impl.UsuarioRepositoryImpl;
import datos.repositorios.interfaces.ISolicitudRepository;
import datos.repositorios.interfaces.ITipoSolicitudRepository;
import datos.repositorios.interfaces.IUsuarioRepository;
import negocio.servicios.ReporteService;
import negocio.servicios.SolicitudService;
import negocio.servicios.TipoSolicitudService;
import negocio.servicios.UsuarioService;
import presentacion.Menu;

public class App {
    public static void main(String[] args) throws Exception {

        try {
            ConexionDB.obtenerConexion();
            System.out.println("Conexión a la base de datos exitosa.");
        } catch (Exception e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return;
        }

        IUsuarioRepository usuarioRepo = new UsuarioRepositoryImpl();
        ITipoSolicitudRepository tipoSolicitudRepo = new TipoSolicitudRepositoryImpl();
        ISolicitudRepository solicitudRepo = new SolicitudRepositoryImpl();

        UsuarioService usuarioService = new UsuarioService(usuarioRepo);
        SolicitudService solicitudService = new SolicitudService(usuarioRepo, tipoSolicitudRepo, solicitudRepo);
        ReporteService reporteService = new ReporteService();
        TipoSolicitudService tipoSolicitudService = new TipoSolicitudService(tipoSolicitudRepo);

        Menu menu = new Menu(usuarioService, solicitudService, reporteService, tipoSolicitudService, usuarioRepo);
        menu.iniciar();
    }
}