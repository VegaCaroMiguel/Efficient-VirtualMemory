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

    public validarReferencias(Integer direccion) {
        if (this.tlb.contiene(direccion)) {
            // se imprime el tiempo de busqueda.
            env.envejecimiento(this.ram);            
        }
        else {
            Boolean esta = this.tp.getHashTP().get(direccion);
            if (esta) {
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
