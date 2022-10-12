package clases;

import java.util.ArrayList;
import java.util.Date;

public class Referencias extends Thread {

    private TLB tlb;
    private RAM ram;
    private TP tp;
    private ArrayList<Integer> direcciones;

    // Variables cambiantes de tiempo (ns):
    private Integer tempTrad = 0;
    private Integer tempCarga = 0;
    
    // Constantes de tiempo (ns):
    private final Integer tempTradTLB = 2;
    private final Integer tempTradTP = 30;
    private final Integer tempFalloPag = tempTradTP * 2;
    private final Integer tempTradPag = 30;
    private final Integer tempArregloFallPag = 10000000;


    public Referencias(TLB pTlb, RAM pRam, TP pTp, ArrayList<Integer> pDirecciones) {
        this.tlb = pTlb;
        this.ram = pRam;
        this.tp = pTp;
        this.direcciones = pDirecciones;
    }

    public void validarReferencias(Integer direccion) {
        Boolean estaTLB = this.tlb.getHashTLB().containsValue(direccion);
        // sumar tiempo traducción TLB
        this.tempTrad += this.tempTradTLB;
        if (estaTLB) {
            //System.out.println("traduce TLB");
        }
        else {
            Boolean estaTP = this.tp.getHashTP().get(direccion);
            this.tempTrad += this.tempTradTP;
            if (estaTP) {
                // sumar tiempo carga TLB
                //System.out.println("carga TLB");
                this.tlb.actualizar(direccion); // ¡actualizar es algoritmo FIFO!
                // sumar tiempo traducción RAM
                this.tempTrad += this.tempTradPag;
                //System.out.println("traduce RAM");
            }
            else {
                this.tempTrad += this.tempFalloPag;
                //System.out.println("Fallo de página.");
                // sumar tiempo carga TP
                //System.out.println("carga TP");
                this.tp.actualizar(direccion);
                // sumar tiempo carga RAM
                //System.out.println("carga RAM");
                this.ram.actualizar(direccion);
                // sumar tiempo carga TLB
                //System.out.println("carga TLB");
                this.tlb.actualizar(direccion); // ¡actualizar es algoritmo FIFO!
                this.tempCarga += this.tempArregloFallPag;
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
        System.out.println("Fin de ejecución.");
        System.out.println("Tiempo de traducción: " + this.tempTrad + "ns");
        System.out.println("Tiempo de carga: " + this.tempCarga + "ns");
    }
    
}
