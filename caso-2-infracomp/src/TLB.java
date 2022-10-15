import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TLB {

    private static HashMap<Integer, Integer> tlb = new HashMap<Integer, Integer>();
    //private static Queue<Integer> fifo = new LinkedList<>();
    private static List<Integer> fifo = new ArrayList<Integer>();
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

    /**
     * Revisa si la TLB tiene espacio o no.
     * 
     * @return hayEspacio : false si la TLB esta llena, true si no lo esta.
     */
    private synchronized Boolean espacio() {
        Boolean hayEspacio = false;
        for (int i = 0; i < this.n; i++) {
            if (tlb.get(i) == null) {
                hayEspacio = true;
            }
        }

        return hayEspacio;
    }

    /**
     * Actualiza la TLB. Si hay espacio, agrega la dirección de la página a la TLB,
     * sino, sigue el algoritmo de reemplazo FIFO.
     * El "if (!estadoRAM)", cuando la RAM no tiene espacio y reemplaza una página,
     * se saca la pagina que se reemplazó en la RAM de la TLB y de FIFO
     * (ya sé que es no es buena práctica dejar el mismo if dos veces en la misma función,
     * pero así sincroniza la TLB y FIFO con la RAM bien).
     * 
     * @param dir : dirección actual que se quiere cargar.
     * @param estadoRAM : true si RAM tiene espacio, falso si no.
     * @param dirVieja : dirección de la página que se acaba de reemplazar en RAM (si es el caso).
     */
    public synchronized void actualizar(Integer dir, Boolean estadoRAM, Integer dirVieja) {
        Boolean hayEspacio = espacio();
        Integer cabeza = null;
        if (hayEspacio) {
            if (!estadoRAM) {
                if (fifo.contains(dirVieja)) {
                    int index = fifo.indexOf(dirVieja);
                    cabeza = fifo.remove(index);
                }
                tlb.remove(dirVieja);
            }
            for (int i = 0; i < this.n; i++) {
                if (tlb.get(i) == null) {
                    tlb.put(i, dir);
                    fifo.add(i);
                    break;
                }
            }
        }
        else {
            if (!estadoRAM) {
                if (fifo.contains(dirVieja)) {
                    int index = fifo.indexOf(dirVieja);
                    cabeza = fifo.remove(index);
                }
                tlb.remove(dirVieja);
            }
            else { 
                cabeza = fifo.remove(0); // ver y eliminar el elemento del tope de la linked list.
            }
            tlb.put(cabeza, dir); // actualiza el valor del elemento eliminado.
            fifo.add(cabeza); // vuelve a meter la dirección a la cola.
        }

    }

}
