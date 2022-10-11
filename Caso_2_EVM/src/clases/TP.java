package clases;

import java.util.HashMap;

public class TP {
    
    private HashMap<Integer, Boolean> tp = new HashMap<Integer, Boolean>();
    // true es que está en RAM, false es que está en disco.

    public TP(Integer pN) {

        for (int i = 0; i < pN; i++) {
            tp.put(i, false);
        }
    }

    public HashMap<Integer, Boolean> getHashTP() {
        return this.tp;
    }

    public void actualizar(Integer dir) {
        this.tp.put(dir, true);
    }

}
