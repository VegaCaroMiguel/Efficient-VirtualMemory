package clases;

import java.util.HashMap;

public class Envejecimiento extends Thread {
    
    private RAM ram;

    public Envejecimiento(Integer pN, RAM pRam) {
        this.ram = pRam;
    }

    public void envejecimiento() {
        this.ram.envejecimiento();
    }

    public void run() {
        while (true) {
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
