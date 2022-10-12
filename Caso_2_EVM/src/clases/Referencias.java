package clases;

public class Referencias extends Thread {

    private TLB tlb;
    private RAM ram;
    private TP tp;
    private Envejecimiento env;

    public Referencias(TLB pTlb, RAM pRam, TP pTp, Envejecimiento pEnv) {
        this.tlb = pTlb;
        this.ram = pRam;
        this.tp = pTp;
        this.env = pEnv;
    }

    public synchronized void validarReferencias(Integer direccion) {
        Boolean estaTLB = this.tlb.getHashTLB().containsValue(direccion);
        if (estaTLB) {           
        }
        else {
            Boolean estaTP = this.tp.getHashTP().get(direccion);
            if (estaTP) {
                this.tlb.actualizar(direccion); // ¡actualizar es algoritmo FIFO!
            }
            else {
                System.out.println("Fallo de página.");
                this.tp.actualizar(direccion);
                this.ram.actualizar(direccion);
                this.tlb.actualizar(direccion); // ¡actualizar es algoritmo FIFO!
            }
        }
    }

    public void run() {
        /*
         * if está en TLB
         *  todo bien
         * else
         *  ver en TP si está en RAM
         *  if está en RAM
         *    actualizar TLB
         *  else
         *    fallo de página
         *    actualizar TP
         *    actualizar RAM
         *    actualizar TLB
         */
    }
    
}
