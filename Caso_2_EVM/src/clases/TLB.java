package clases;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class TLB {

    private HashMap<Integer, Integer> tlb = new HashMap<Integer, Integer>();
    private Queue<Integer> fifo = new LinkedList<>();
    private Integer n;

    public TLB(Integer pN) {
        this.n = pN;
        for (int i = 0; i < this.n; i++) {
            tlb.put(i, null);
        }
    }

    public HashMap<Integer, Integer> getHashTLB() {
        return this.tlb;
    }

    private Boolean espacio() {
        Boolean lleno = false;
        for (int i = 0; i < this.n; i++) {
            if (this.tlb.get(i) == null) {
                lleno = true;
            }
        }

        return lleno;
    }

    public void actualizar(Integer dir) {
        Random random = new Random();

        Boolean hayEspacio = espacio();
        if (hayEspacio) {
            Boolean centinela = true;
            Integer i = 0;
            while (i < this.n || centinela) {
                Integer rand = random.nextInt((this.n - 0) + 1);
                if (this.tlb.get(rand) == null) {
                    this.tlb.put(rand, dir);
                    this.fifo.add(rand);
                    centinela = false;
                }
                i++;
            }
        }
        else {
            Integer cabeza = this.fifo.poll(); // ver y eliminar el elemento del tope de la linked list.
            this.tlb.put(cabeza, dir); // actualiza el valor del elemento eliminado.
            this.fifo.add(cabeza); // vuelve a meter la dirección a la cola.
        }



    }

}
