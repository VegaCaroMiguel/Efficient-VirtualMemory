import java.util.ArrayList;

public class Envejecimiento extends Thread {
    
    private RAM ram;
    private ArrayList<Integer> direcciones;
    public static Boolean env = true;

    public Envejecimiento(Integer pN, RAM pRam, ArrayList<Integer> pDirecciones) {
        this.ram = pRam;
        this.direcciones = pDirecciones;
    }

    /**
     * Llama la función "envejecimiento" de la RAM.
     */
    public void envejecimiento() {
        this.ram.envejecimiento();
    }

    public void run() {
        while (env) {
            envejecimiento();
            try {
                sleep(1);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
