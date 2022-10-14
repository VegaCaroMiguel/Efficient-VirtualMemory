import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class TLB {

    private static HashMap<Integer, Integer> tlb = new HashMap<Integer, Integer>();
    private static Queue<Integer> fifo = new LinkedList<>();
    private Integer n;

    public TLB(Integer pN) {
        this.n = pN;
        System.out.println(this.n);
        for (int i = 0; i < this.n; i++) {
            tlb.put(i, null);
        }
    }

    public HashMap<Integer, Integer> getHashTLB() {
        return tlb;
    }

    public void loopTLB() {
        for (int i = 0; i < this.n; i++) {
            System.out.println("TLB[" + i + "] = " + tlb.get(i));
        }
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

    /**
     * Actualiza la TLB, si hay espacio, agrega la dirección de la página a la TLB,
     * sino, sigue el algoritmo de reemplazo FIFO.
     * 
     * @param dir
     * @param estadoRAM : true si RAM tiene espacio, falso si no.
     * @param dirVieja
     */
    public synchronized void actualizar(Integer dir, Boolean estadoRAM, Integer dirVieja) {
        Boolean hayEspacio = espacio();
        if (!estadoRAM) {
            for (int i = 0; i < fifo.size(); i++) {
                if (fifo.contains(dirVieja)) {
                    //fifo.poll();
                    fifo.remove(dirVieja);
                }
            }
            tlb.remove(dirVieja);
        }
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
