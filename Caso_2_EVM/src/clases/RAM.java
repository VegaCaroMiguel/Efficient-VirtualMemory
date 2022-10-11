package clases;

import java.util.HashMap;
import java.util.Random;

public class RAM {

    private HashMap<Integer, Integer> ram = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> bits = new HashMap<Integer, Integer>();
    private Integer n;

    public RAM(Integer pN) {

        this.n = pN;

        for (int i = 0; i < pN; i++) {
            ram.put(i, null);
            bits.put(i, null);
        }
    }

    public HashMap<Integer, Integer> getHashBITS() {
        return this.bits;
    }

    private Boolean espacio() {
        Boolean lleno = false;
        for (int i = 0; i < this.n; i++) {
            if (this.ram.get(i) == null) {
                lleno = true;
            }
        }

        return lleno;
    }

    public void actualizar(Integer direccion) {
        Random random = new Random();
        
        Boolean lleno = espacio();
        if (lleno) {
            Boolean centinela = true;
            Integer i = 0;
            while (i < this.n || centinela) {
                Integer rand = random.nextInt((this.n - 0) + 1);
                if (this.ram.get(rand) == null) {
                    this.ram.put(rand, direccion);
                    centinela = false;
                }
                i++;
            }
        }
        else {
            Integer menor = Integer.MAX_VALUE;
            Integer indice = 0;
            for (int i = 0; i < this.n; i++) {
                if (this.bits.get(i) < menor) {
                    menor = this.bits.get(i); // busca cuál es el menor. El Integer menor es el más viejo.
                    indice = i;
                }
            }
            this.ram.put(indice, direccion); // actualiza el valor del elemento eliminado.
        }
    }
        
}
