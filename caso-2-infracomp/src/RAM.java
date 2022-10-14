import java.util.ArrayList;
import java.util.HashMap;

public class RAM {

    private static HashMap<Integer, Integer> ram = new HashMap<Integer, Integer>();
    private static HashMap<Integer, Integer> bits = new HashMap<Integer, Integer>();
    private Integer n;
    private static Integer actDir;
    private static Integer ultModDir;
    //private static ArrayList<Integer> referenciadas = new ArrayList<Integer>();

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
        for (int i = 0; i < ram.size(); i++) {
            System.out.println("RAM[" + i + "] = " + ram.get(i));
        }
    }

    public void loopBITS() {
        for (int i = 0; i < bits.size(); i++) {
            if (bits.get(i) != null) {
                System.out.println("BITS[" + i + "] = " + Integer.toBinaryString(bits.get(i)));
            }
        }
    }

    /**
     * Recorre el HashMap de bits y retorna el indice de la posicion con el bitstring mas largo.
     * 
     * @return the espacio
     */
    public Integer longestBitString() {
        Integer max = 0;
        for (int i = 0; i < this.n; i++) {
            String bitsString = Integer.toBinaryString(bits.get(i));
            if (bitsString.length() > max) {
                max = bits.get(i);
            }
        }
        return max;
    }

    /**
     * Recorre y modifica el HashMap de bits, asegurándose de que todas las cadenas sean de la misma longitud.
     * Esto, para que la evaluación de mayor y menor sea cierta.
     */
    public synchronized void tamanioIgual() {
        Integer max = longestBitString();
        for (int i = 0; i < bits.size(); i++) {
            Integer valor = bits.get(i);
            int j = 0;
            Integer faltante = max - (Integer.toBinaryString(valor).length());
            if (faltante != 0 && valor != 0) {
                while (j < faltante) {
                    valor = valor << 1;
                    j++;
                }
                bits.put(i, valor);
            }
        }
    }

    public void envejecimiento() {
        synchronized(Referencias.referenciadas) {
            //System.out.println("Envejecimiento");
            for (int i = 0; i < this.n; i++) {
                Integer valor = bits.get(i);
                valor = valor >> 1;
                if (Referencias.referenciadas.contains(i)) {
                    valor = valor ^ 1;
                }
                bits.put(i, valor);
            }
            Referencias.referenciadas.clear();
        }
    }

    public Boolean espacio() {
        Boolean lleno = false;
        for (int i = 0; i < this.n; i++) {
            if (ram.get(i) == null) {
                lleno = true;
            }
        }

        return lleno;
    }

    public synchronized Integer actualizar(Integer dir) {      
        Integer dirVieja = null; 
        Boolean hayEspacio = espacio();
        if (hayEspacio) {
            for (int i = 0; i < this.n; i++) {
                if (ram.get(i) == null) {
                    ram.put(i, dir);
                    bits.put(dir, 0);
                    ultModDir = dir;
                    break;
                }
            }
        }
        else {
            //tamanioIgual();
            Integer menor = Integer.MAX_VALUE;
            Integer indice = 0;
            for (int i = 0; i < this.n; i++) {
                if (bits.get(i) < menor) {
                    menor = bits.get(i); // busca cuál es el menor. El Integer menor es el menos usado hasta es momento.
                    indice = i;
                }
            }
            dirVieja = ram.get(indice);
            ram.put(indice, dir); // actualiza el valor del elemento eliminado.
            bits.put(dir, 0); // actualiza y reinicia el bitstring del elemento eliminado.
        }

        return dirVieja;
    }
        
}
