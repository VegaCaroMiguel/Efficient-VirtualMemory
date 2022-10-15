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
     * Ejecuta el algoritmo de envejecimiento.
     * Recorre la tabla de Hash de bits y hace un corrimiento a la derecha
     * de cada secuencia de bits. El menor valor en Integer que se encuentre
     * es el que se debe sacar en caso de que no haya espacio en RAM.
     */
    public void envejecimiento() {
        synchronized(Referencias.referenciadas) {
            //System.out.println("Envejecimiento");
            for (int i = 0; i < this.n; i++) {
                Integer valor = bits.get(i);
                valor = valor >> 1;
                if (Referencias.referenciadas.contains(i) && Integer.toBinaryString(valor).length() < 32) {
                    //valor = valor ^ 1;
                    //String s = "1" + Integer.toBinaryString(valor);
                    //valor = Integer.parseInt(s, 2);
                    valor = valor + (int) Math.pow(2, 32); // 2^30 es sumarle 1 a la izquierda.
                }
                bits.put(i, valor);
            }
            Referencias.referenciadas.clear();
        }
    }

    /**
     * Revisa si la RAM tiene espacio o no.
     * 
     * @return hayEspacio : false si la TLB esta llena, true si no lo esta.
     */
    public Boolean espacio() {
        Boolean lleno = false;
        for (int i = 0; i < this.n; i++) {
            if (ram.get(i) == null) {
                lleno = true;
            }
        }

        return lleno;
    }

    /**
     * Actualiza la RAM. Si hay espacio, agrega la dirección de la página a la RAM,
     * sino, busca el menor valor en la tabla de Hash de bits y lo reemplaza por el nuevo.
     * 
     * @param dir : dirección actual que se quiere cargar.
     * @return dirVieja : dirección que se reemplazó.
     */
    public synchronized Integer actualizar(Integer dir) {      
        Integer dirVieja = null; 
        Boolean hayEspacio = espacio();
        if (hayEspacio) {
            for (int i = 0; i < this.n; i++) {
                if (ram.get(i) == null) {
                    ram.put(i, dir);
                    bits.put(i, 0);
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
                    menor = bits.get(i); // busca cuál es el menor. El Integer menor es el menos usado hasta es momento.
                    indice = i; // guarda el índice del menor.
                }
            }
            dirVieja = ram.get(indice);
            ram.put(indice, dir); // actualiza el valor del elemento eliminado.
            bits.put(indice, 0); // actualiza y reinicia el bitstring del elemento eliminado.
        }

        return dirVieja;
    }
        
}
