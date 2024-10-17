import java.io.IOException;
/**
 * @author Sebastian Bucheli Miranda
 * @version 1.0
 *
 * La clase Main contiene el método principal (main) que orquesta la ejecución de
 * múltiples simulaciones utilizando la clase MLQSimulator
 */
public class Main {
    public static void main(String[] args) throws IOException {
        MLQSimulator simulator1 = new MLQSimulator();
        MLQSimulator simulator2 = new MLQSimulator();
        MLQSimulator simulator3 = new MLQSimulator();

        // Simulación 1
        String archivoEntrada1 = "src/main/resources/entradas/mlq001.txt";
        String archivoSalida1 = "src/main/resources/salidas/salida_mlq001.txt";
        simulator1.mlq_simulator(archivoEntrada1,archivoSalida1);

        // Simulación 2
        String archivoEntrada2 = "src/main/resources/entradas/mlq002.txt";
        String archivoSalida2 = "src/main/resources/salidas/salida_mlq002.txt";
        simulator2.mlq_simulator(archivoEntrada2,archivoSalida2);

        // Simulación 3
        String archivoEntrada3 = "src/main/resources/entradas/mlq003.txt";
        String archivoSalida3 = "src/main/resources/salidas/salida_mlq003.txt";
        simulator3.mlq_simulator(archivoEntrada3,archivoSalida3);


    }
}

