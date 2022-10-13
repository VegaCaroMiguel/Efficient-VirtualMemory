
import java.util.ArrayList;
import java.util.HashMap;

public class Envejecimiento extends Thread {
    
    private RAM ram;
    private Integer ultModDir;
    private ArrayList<Integer> direcciones;

    public Envejecimiento(Integer pN, RAM pRam, ArrayList<Integer> pDirecciones) {
        this.ram = pRam;
        this.direcciones = pDirecciones;
    }

    public void envejecimiento() {
        this.ram.envejecimiento();

    }

    public void run() {
        for (int i = 0; i < this.direcciones.size(); i++) {
            try {
                sleep(1);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            envejecimiento();
        }
    }


}
