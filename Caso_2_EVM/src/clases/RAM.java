package clases;

import java.util.HashMap;

public class RAM {

    private HashMap<Integer, Integer> ram = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> bits = new HashMap<Integer, Integer>();

    public RAM(Integer pN) {

        for (int i = 0; i < pN; i++) {
            ram.put(i, null);
            bits.put(i, null);
        }
    }

    public HashMap<Integer, Integer> getHashBITS() {
        return this.bits;
    }
        
}
