package clases;

import java.util.HashMap;

public class Envejecimiento extends Thread {
    
    private HashMap<Integer, Integer> bits = new HashMap<Integer, Integer>();

    public Envejecimiento(Integer pN) {}

    public void envejecimiento(RAM ram) {
        HashMap<Integer, Integer> bitsram = ram.getHashBITS();
        for (Integer value: bitsram.values()) {
            if (value != null) {
                value = value >> 1;
            }
        }
    }


}
