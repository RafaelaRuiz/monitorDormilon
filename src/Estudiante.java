import java.util.Random;
public class Estudiante extends Thread{
    private String nombre;

    public Estudiante(String nombre){
        this.nombre = nombre;
    }

    public void run(){
        while (true) {
            try {
                System.out.println("Estudiante " + nombre + " esta programando");
                //Se simula el tiempo programando
                Thread.sleep(new Random().nextInt(2000));
                System.out.println("Estudiante " + nombre + " necesita ayuda del monitor");
                //Seccion critica
                //Se quiere sentar en una silla
                MonitorDormilon.mutex.acquire();
                //Si hay sillas disponibles
                if(MonitorDormilon.sillasDisponibles.tryAcquire()){
                    //Se libera una silla
                    MonitorDormilon.colaEstudiantes.add(nombre);
                    MonitorDormilon.mutex.release();
                    System.out.println("Estudiante " + nombre + " se sienta en una silla");
                    //Se avisa que hay un estudiante esperando
                    MonitorDormilon.estudiantesEsperando.release();
                    //Se espera a que el monitor lo atienda
                    MonitorDormilon.monitorDisponible.acquire();
                    System.out.println("Estudiante " + nombre + " recibe ayuda del monitor");
                }else{
                    //Si no hay sillas disponibles
                    MonitorDormilon.mutex.release();
                    System.out.println("Estudiante " + nombre + " no encontro silla disponible");
                    System.out.println("Estudiante " + nombre + " fue a programar en sala");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } 
    }
}
