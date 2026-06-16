package servicios;

import dominio.Solicitud;
import java.util.List;

public class ReporteService {

    public ReporteService() {
    }

    public void generarReporteEstadistico(List<Solicitud> listaSolicitudes) {
        System.out.println("REPORTE DE SOLICITUDES");

        if (listaSolicitudes == null || listaSolicitudes.size() == 0) {
            System.out.println("No hay solicitudes registradas.");
            return;
        }

        int creadas = 0;
        int enRevision = 0;
        int aprobadas = 0;
        int rechazadas = 0;
        int cerradas = 0;
        int total = listaSolicitudes.size();

        for (int i = 0; i < total; i++) {
            Solicitud solicitud = listaSolicitudes.get(i);
            String estado = solicitud.getEstado();

            if (estado != null) {
                if (estado.equals("CREADA")) {
                    creadas++;
                } else if (estado.equals("EN_REVISION")) {
                    enRevision++;
                } else if (estado.equals("APROBADA")) {
                    aprobadas++;
                } else if (estado.equals("RECHAZADA")) {
                    rechazadas++;
                } else if (estado.equals("CERRADA")) {
                    cerradas++;
                }
            }
        }

        System.out.println("Total evaluadas: " + total);
        System.out.println("Creadas: " + creadas);
        System.out.println("En Revision: " + enRevision);
        System.out.println("Aprobadas: " + aprobadas);
        System.out.println("Rechazadas: " + rechazadas);
        System.out.println("Cerradas: " + cerradas);
    }
}