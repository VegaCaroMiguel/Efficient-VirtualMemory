
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class TLB {

    private static HashMap<Integer, Integer> tlb = new HashMap<Integer, Integer>();
    private static Queue<Integer> fifo = new LinkedList<>();
    private Integer n;

    public TLB(Integer pN) {
        this.n = pN;
        for (int i = 0; i < this.n; i++) {
            tlb.put(i, null);
        }
    }

    public HashMap<Integer, Integer> getHashTLB() {
        return tlb;
    }

    private synchronized Boolean espacio() {
        Boolean lleno = false;
        for (int i = 0; i < this.n; i++) {
            if (tlb.get(i) == null) {
                lleno = true;
            }
        }

        return lleno;
    }

    public synchronized void actualizar(Integer dir) {
        Boolean hayEspacio = espacio();
        if (hayEspacio) {
            for (int i = 0; i < this.n; i++) {
                if (tlb.get(i) == null) {
                    tlb.put(i, dir);
                    fifo.add(i);
                    break;
                }
            }
        }
        else {
            Integer cabeza = fifo.poll(); // ver y eliminar el elemento del tope de la linked list.
            tlb.put(cabeza, dir); // actualiza el valor del elemento eliminado.
            fifo.add(cabeza); // vuelve a meter la dirección a la cola.
        }

    }
}
