package clases;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class TLB {

    private HashMap<Integer, Integer> tlb = new HashMap<Integer, Integer>();
    private Queue<Integer> fifo = new LinkedList<>();

    public TLB(Integer pN) {

        for (int i = 0; i < pN; i++) {
            tlb.put(i, null);
        }
    }

}
