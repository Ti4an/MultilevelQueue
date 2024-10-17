import java.io.*;
import java.util.*;

/**
 * @author Sebastian Bucheli Miranda
 * @version 1.0
 * La clase MLQScheduler se encarga de simular la planificación de procesos en un
 * sistema de colas multinivel
 */
public class MLQScheduler {
    /** Lista que almacena los procesos leídos del archivo */
    List<Proceso> procesos = new ArrayList<>();
    /** Cola 1: Procesos asignados a la primera cola (RR(3))*/
    Queue<Proceso> cola1 = new LinkedList<>(); // RR(3)
    /** Cola 2: Procesos asignados a la segunda cola (RR(5))*/
    Queue<Proceso> cola2 = new LinkedList<>(); // RR(5)
    /** Cola 3: Procesos asignados a la tercera cola (FCFS)*/
    Queue<Proceso> cola3 = new LinkedList<>(); // FCFS

    /**
     * Lee los procesos desde un archivo de entrada y los almacena en la lista de procesos
     * @param archivoEntrada
     */
    public void leerProcesos(String archivoEntrada) throws IOException {
        BufferedReader archivo = new BufferedReader(new FileReader(archivoEntrada));
        String linea;
        linea = archivo.readLine();

        while ((linea = archivo.readLine()) != null) {
            if (linea.trim().isEmpty() || linea.startsWith("#")) {
                continue;
            }
            String[] datos = linea.split(";");
            String etiqueta = datos[0].trim();
            int burst_time = Integer.parseInt(datos[1].trim());
            int arrival_time = Integer.parseInt(datos[2].trim());
            int queue = Integer.parseInt(datos[3].trim());
            int priority = Integer.parseInt(datos[4].trim());

            Proceso proceso = new Proceso(etiqueta, burst_time, arrival_time, queue, priority);
            procesos.add(proceso);
        }
        archivo.close();
    }

    /**
     * Asigna los procesos a sus respectivas colas según el valor de su atributo "queue"
     */
    public void asignarColas() {
        for (Proceso lista_procesos : procesos) {
            switch (lista_procesos.queue) {
                case 1:
                    cola1.add(lista_procesos);
                    break;
                case 2:
                    cola2.add(lista_procesos);
                    break;
                case 3:
                    cola3.add(lista_procesos);
                    break;
            }
        }
    }

    /**
     * Ejecuta los procesos en las tres colas según sus respectivos algoritmos de planificación
     */
    public void ejecutarColas() {
        int tiempoActual = 0;
        tiempoActual = ejecutarRoundRobin(cola1, tiempoActual, 3);
        tiempoActual = ejecutarRoundRobin(cola2, tiempoActual, 5);
        ejecutarFCFS(cola3, tiempoActual);
    }

    /**
     * Ejecuta los procesos de una cola utilizando el algoritmo Round Robin
     *
     * @param cola
     * @param tiempoActual
     * @param quantum
     * @return El tiempo actualizado después de ejecutar los procesos en la cola
     */
    private int ejecutarRoundRobin(Queue<Proceso> cola, int tiempoActual, int quantum) {
        while (!cola.isEmpty()) {
            Proceso p = cola.poll();
            if (p.responseTime == -1) {
                p.responseTime = tiempoActual - p.arrivalTime;
            }
            if (p.remainingTime <= quantum) {
                tiempoActual += p.remainingTime;
                p.remainingTime = 0;
                p.completionTime = tiempoActual;
                p.turnaroundTime = p.completionTime - p.arrivalTime;
                p.waitingTime = p.turnaroundTime - p.burstTime;
            } else {
                tiempoActual += quantum;
                p.remainingTime -= quantum;
                cola.add(p);
            }
        }
        return tiempoActual;
    }
    /**
     * Ejecuta los procesos de una cola utilizando el algoritmo First-Come First-Served
     *
     * @param cola
     * @param tiempoActual
     */
    private void ejecutarFCFS(Queue<Proceso> cola, int tiempoActual) {
        for (Proceso p : cola) {
            if (p.arrivalTime > tiempoActual) {
                tiempoActual = p.arrivalTime;
            }
            if (p.responseTime == -1) {
                p.responseTime = tiempoActual - p.arrivalTime;
            }
            p.waitingTime = tiempoActual - p.arrivalTime;
            p.completionTime = tiempoActual + p.burstTime;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            tiempoActual += p.burstTime;
        }
    }

    /**
     * Genera un archivo de salida con los resultados de la simulación
     * @param archivoSalida
     */
    public void generarArchivoSalida(String archivoSalida) throws IOException {
        BufferedWriter archivo_salida = new BufferedWriter(new FileWriter(archivoSalida));
        archivo_salida.write("# archivo: "+ archivoSalida+"\n");
        archivo_salida.write("# etiqueta; BT; AT; Q; Pr; WT; CT; RT; TAT\n");
        double totalWT = 0, totalCT = 0, totalRT = 0, totalTAT = 0;

        for (Proceso p : procesos) {
            archivo_salida.write(String.format("%s; %d; %d; %d; %d; %d; %d; %d; %d\n",
                    p.etiqueta, p.burstTime, p.arrivalTime, p.queue, p.priority,
                    p.waitingTime, p.completionTime, p.responseTime, p.turnaroundTime));
            totalWT += p.waitingTime;
            totalCT += p.completionTime;
            totalRT += p.responseTime;
            totalTAT += p.turnaroundTime;
        }
        int numProcesos = procesos.size();
        archivo_salida.write(String.format("WT=%.1f; CT=%.1f; RT=%.1f; TAT=%.1f;\n",
                totalWT/numProcesos, totalCT/numProcesos, totalRT/numProcesos, totalTAT/numProcesos));
        archivo_salida.close();
    }
}
