import java.io.IOException;

/**
 * @author Sebastian Bucheli Miranda
 * @version 1.0
 * La clase MLQSimulator es responsable de orquestar la simulación de la planificación
 */
public class MLQSimulator {
    /** Instancia de la clase MLQScheduler que gestiona los procesos */
    MLQScheduler scheduler = new MLQScheduler();

    /**
     * Método que simula la planificación de procesos con colas multinivel
     * @param ruta_entrada
     * @param ruta_salida
     */
    public void mlq_simulator(String ruta_entrada, String ruta_salida) throws IOException {
        scheduler.leerProcesos(ruta_entrada);
        scheduler.asignarColas();
        scheduler.ejecutarColas();
        scheduler.generarArchivoSalida(ruta_salida);
    }
}
