package clases;

import java.util.HashMap;

public class TP {
    
    private HashMap<Integer, Integer> tp = new HashMap<Integer, Integer>();

    public TP(Integer pN) {

        for (int i = 0; i < pN; i++) {
            tp.put(i, null);
        }
    }

}
