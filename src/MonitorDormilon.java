import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class MonitorDormilon {
    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore sillasDisponibles = new Semaphore(3);
    public static Semaphore monitorDisponible = new Semaphore(0);
    public static Semaphore estudiantesEsperando = new Semaphore(0);
    public static Queue<String> colaEstudiantes = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Monitor monitor = new Monitor();
        monitor.start();

        System.out.println("Ingrese el número de estudiantes: ");
        int numEstudiantes = scanner.nextInt();
        scanner.nextLine();

        Estudiante[] estudiantes = new Estudiante[numEstudiantes];
        for (int i = 0; i < numEstudiantes; i++) {
            System.out.println("Ingrese el nombre del estudiante " + (i + 1) + ": ");
            String nombre = scanner.nextLine();
            estudiantes[i] = new Estudiante(nombre);
        }

        // Iniciar los hilos de los estudiantes después de crearlos
        for (Estudiante estudiante : estudiantes) {
            estudiante.start();
        }

        scanner.close();
    }
}
