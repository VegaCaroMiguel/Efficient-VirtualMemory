package clases;

import java.util.ArrayList;

public class Referencias extends Thread {

    private TLB tlb;
    private RAM ram;
    private TP tp;
    private ArrayList<Integer> direcciones;

    public Referencias(TLB pTlb, RAM pRam, TP pTp, ArrayList<Integer> pDirecciones) {
        this.tlb = pTlb;
        this.ram = pRam;
        this.tp = pTp;
        this.direcciones = pDirecciones;
    }

    public void validarReferencias(Integer direccion) {
        Boolean estaTLB = this.tlb.getHashTLB().containsValue(direccion);
        if (estaTLB) {
            // sumar tiempo traducción TLB
        }
        else {
            Boolean estaTP = this.tp.getHashTP().get(direccion);
            if (estaTP) {
                // sumar tiempo carga TLB
                this.tlb.actualizar(direccion); // ¡actualizar es algoritmo FIFO!
            }
            else {
                System.out.println("Fallo de página.");
                // sumar tiempo carga TP
                this.tp.actualizar(direccion);
                // sumar tiempo carga RAM
                this.ram.actualizar(direccion);
                // sumar tiempo carga TLB
                this.tlb.actualizar(direccion); // ¡actualizar es algoritmo FIFO!
            }
        }
    }

    public void run() {
        for (int i = 0; i < this.direcciones.size(); i++) {
            try {
                sleep(2);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            validarReferencias(this.direcciones.get(i));
        }
    }
    
}
