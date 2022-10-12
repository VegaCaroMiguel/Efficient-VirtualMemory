package clases;

public class Referencias extends Thread {

    private TLB tlb;
    private RAM ram;
    private TP tp;
    private Integer direccion;

    public Referencias(TLB pTlb, RAM pRam, TP pTp) {
        this.tlb = pTlb;
        this.ram = pRam;
        this.tp = pTp;
    }

    public void setDireccion(Integer pDir) {
        this.direccion = pDir;
    }

    public void validarReferencias() {
        try {
            sleep(2);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        Boolean estaTLB = this.tlb.getHashTLB().containsValue(this.direccion);
        if (estaTLB) {
            // sumar tiempo traducción TLB
        }
        else {
            Boolean estaTP = this.tp.getHashTP().get(this.direccion);
            if (estaTP) {
                // sumar tiempo carga TLB
                this.tlb.actualizar(this.direccion); // ¡actualizar es algoritmo FIFO!
            }
            else {
                System.out.println("Fallo de página.");
                // sumar tiempo carga TP
                this.tp.actualizar(this.direccion);
                // sumar tiempo carga RAM
                this.ram.actualizar(this.direccion);
                // sumar tiempo carga TLB
                this.tlb.actualizar(this.direccion); // ¡actualizar es algoritmo FIFO!
            }
        }
    }

    public void run() {
        validarReferencias();
    }
    
}
