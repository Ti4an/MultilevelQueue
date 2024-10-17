/**
 * @author Sebastian Bucheli Miranda
 * @version 1.0
 * Clase que representa un proceso en el sistema de planificación
 */
public class Proceso {
    /** Nombre o etiqueta que identifica un proceso*/
    String etiqueta;
    /** Tiempo de ejecución total que requiere el proceso */
    int burstTime;
    /** Tiempo en el que el proceso llega al sistema */
    int arrivalTime;
    /** Cola a la que pertenece el proceso */
    int queue;
    /** Prioridad del proceso */
    int priority;

    /** Tiempo de espera que del proceso antes de su ejecución */
    int waitingTime = 0;
    /** Tiempo en el que se completa la ejecución del proceso */
    int completionTime = 0;
    /** Tiempo de respuesta del proceso */
    int responseTime = -1;
    /** Tiempo total desde que el proceso llega hasta que se completa */
    int turnaroundTime = 0;
    /** Tiempo restante de ejecución para el proceso */
    int remainingTime;

    /**
     * Constructor de la clase Proceso
     *
     * Inicializa un nuevo proceso con los atributos especificados
     *
     * @param etiqueta
     * @param burstTime
     * @param arrivalTime
     * @param queue
     * @param priority
     */
    public Proceso(String etiqueta, int burstTime, int arrivalTime, int queue, int priority) {
        this.etiqueta = etiqueta;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.queue = queue;
        this.priority = priority;
        this.remainingTime = burstTime;
    }
}
