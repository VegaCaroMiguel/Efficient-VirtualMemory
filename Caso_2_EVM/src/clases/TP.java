package clases;

import java.util.HashMap;

public class TP {
    
    private static HashMap<Integer, Boolean> tp = new HashMap<Integer, Boolean>();
    // true es que está en RAM, false es que está en disco.
    private Integer n;

    public TP(Integer pN) {
        this.n = pN;
        for (int i = 0; i < this.n; i++) {
            tp.put(i, false);
        }
    }

    public HashMap<Integer, Boolean> getHashTP() {
        return tp;
    }

    public synchronized void actualizar(Integer dir) {
        tp.put(dir, true);
    }

}
