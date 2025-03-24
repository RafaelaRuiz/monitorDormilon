import java.util.Random;

public class Monitor extends Thread{
    public void run(){
        while (true) {
            try {
                System.out.println("Monitor esta dormido");
                //Espera que un estudiante lo despierte
                MonitorDormilon.estudiantesEsperando.acquire();
                System.out.println("Monitor se despierta");
                //Seccion critica
                //Se atiende a un estudiante
                MonitorDormilon.mutex.acquire();
                String estudianteAtendido = MonitorDormilon.colaEstudiantes.poll();
                System.out.println("Monitor atiende a estudiante: " + estudianteAtendido);
                MonitorDormilon.sillasDisponibles.release(); //Se libera una silla al atender
                MonitorDormilon.mutex.release(); 

                //Se simula el tiempo atendiendo
                Thread.sleep(new Random().nextInt(2000));
                System.out.println("Monitor termina de atender a estudiante: " + estudianteAtendido);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } 
    }
    
}
