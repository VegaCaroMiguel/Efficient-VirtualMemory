package clases;

import java.util.HashMap;

public class Envejecimiento extends Thread {
    
    private HashMap<Integer, Integer> bits;

    public Envejecimiento(Integer pN, HashMap<Integer, Integer> pBits) {
        this.bits = pBits;
    }

    public void envejecimiento() {
        for (Integer valor: this.bits.values()) {
            valor = valor >> 1;
            // ver si está en RAM. Si está se agrega un 1 cuando se corre, sino un 0.
        }
    }

    public void run() {
        envejecimiento();
    }


}
