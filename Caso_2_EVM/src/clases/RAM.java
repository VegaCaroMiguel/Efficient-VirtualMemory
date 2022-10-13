package clases;

import java.util.ArrayList;
import java.util.HashMap;

public class RAM {

    private static HashMap<Integer, Integer> ram = new HashMap<Integer, Integer>();
    private static HashMap<Integer, Integer> bits = new HashMap<Integer, Integer>();
    private Integer n;
    private static Integer actDir;
    private static Integer ultModDir;
    private static ArrayList<Integer> referenciadas = new ArrayList<Integer>();

    public RAM(Integer pN) {
        this.n = pN;
        for (int i = 0; i < this.n; i++) {
            ram.put(i, null);
            bits.put(i, 0);
        }
    }

    public HashMap<Integer, Integer> getHashRAM() {
        return ram;
    }

    public HashMap<Integer, Integer> getHashBITS() {
        return bits;
    }
    
    public void getUltDir() {
        // for (int i = 0; i < referenciadas.size(); i++) {
        //     System.out.println("ults: " + referenciadas.get(i));
        // }
        System.out.println("ultima: " + ultModDir);
    }

    public Integer getAntDir() {
        return actDir;
    }

    public void setHashBITS(HashMap<Integer, Integer> map) {
        bits = map;
    }

    public void setActDir(Integer dir) {
        actDir = dir;
    }

    public void loopRAM() {
        for (int i = 0; i < this.n; i++) {
            System.out.println("RAM[" + i + "] = " + ram.get(i));
        }
    }

    public void loopBITS() {
        for (int i = 0; i < this.n; i++) {
            System.out.println("\nBITS[" + i + "] = " + Integer.toBinaryString(bits.get(i)));
        }
    }

    public synchronized void envejecimiento() {
        for (int i = 0; i < this.n; i++) {
            Integer valor = bits.get(i);
            valor = valor << 1;
            if (actDir != null) {
                if (referenciadas.contains(actDir) && (actDir).equals(i)) {
                    valor = valor ^ 1;
                }
            }
            bits.put(i, valor);
        }
    }

    private synchronized Boolean espacio() {
        Boolean lleno = false;
        for (int i = 0; i < this.n; i++) {
            if (ram.get(i) == null) {
                lleno = true;
            }
        }

        return lleno;
    }

    public synchronized void actualizar(Integer dir) {       
        Boolean hayEspacio = espacio();
        if (hayEspacio) {
            for (int i = 0; i < this.n; i++) {
                if (ram.get(i) == null) {
                    ram.put(i, dir);
                    referenciadas.add(dir);
                    ultModDir = dir;
                    break;
                }
            }
        }
        else {
            Integer menor = Integer.MAX_VALUE;
            Integer indice = 0;
            for (int i = 0; i < this.n; i++) {
                if (bits.get(i) < menor) {
                    menor = bits.get(i); // busca cuál es el menor. El Integer menor es el más viejo.
                    indice = i;
                }
            }
            ram.put(indice, dir); // actualiza el valor del elemento eliminado.
            referenciadas.add(dir);
            ultModDir = dir;
        }
    }
        
}
