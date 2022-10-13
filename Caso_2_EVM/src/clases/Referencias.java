import java.util.ArrayList;

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
        if (estaTLB) {
            this.tempTrad += this.tempTradTLB;
        }
        else {
            Boolean estaTP = this.tp.estadoTabla();
            
            this.tempTrad += this.tempTradTP; 
            
            if (estaTP) {
                this.tlb.actualizar(direccion); // ¡actualizar es algoritmo FIFO!
                
                this.tempTrad += this.tempTradPag;
            }
            else {
                this.tempTrad += this.tempFalloPag;

                this.tp.actualizar(direccion);
                
                this.ram.actualizar(direccion);
                
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
            this.ram.setActDir(direcciones.get(i));
            validarReferencias(this.direcciones.get(i));
            System.out.println("ant: " + this.ram.getAntDir());
            this.ram.getUltDir();
        }
        System.out.println("Fin de ejecución.");
        System.out.println("Tiempo de traducción: " + this.tempTrad + " ns");
        System.out.println("Tiempo de carga: " + this.tempCarga + " ns");
        this.ram.loopRAM();
        this.ram.loopBITS();
    }
    
}
