import java.util.ArrayList;

public class Referencias extends Thread {

    private TLB tlb;
    private RAM ram;
    private TP tp;
    private ArrayList<Integer> direcciones;
    public static ArrayList<Integer> referenciadas = new ArrayList<Integer>();

    // Variables cambiantes de tiempo (ns):
    private Long tempTrad = Long.valueOf(0);
    private Long tempCarga = Long.valueOf(0);
    private Integer numFallosPagina = 0;
    
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
        this.direcciones = pDirecciones; // mark down
    }

    public void validarReferencias(Integer direccion) {
        synchronized(referenciadas) {
            //System.out.println("Referencias");
            Boolean estaTLB = this.tlb.getHashTLB().containsValue(direccion);
            if (estaTLB) {
                //System.out.println("Esta en TLB");
                this.tempTrad += this.tempTradTLB;
                this.tempCarga += this.tempTradTLB;
            }
            else {
                //System.out.println("No esta en TLB");
                Boolean estaTP = this.tp.getHashTP().get(direccion);
                Boolean estaRAM = this.ram.getHashRAM().containsValue(direccion);
                
                if (estaTP && estaRAM) {
                    this.tempTrad += this.tempTradTP;
                    this.tempCarga += this.tempTradTP;

                    this.tlb.actualizar(direccion, true, null); // ¡actualizar es algoritmo FIFO!
                    
                    this.tempTrad += this.tempTradPag;
                    this.tempCarga += this.tempTradPag;
                }
                else {
                    //System.out.println("Fallo de página");
                    this.numFallosPagina++;
                    Boolean hayEspacioRAM = this.ram.espacio();
                    Integer dirVieja = this.ram.actualizar(direccion);
                    //System.out.println("No esta en RAM");
                    this.tempTrad += this.tempFalloPag;
                    this.tempCarga += this.tempFalloPag;

                    this.tp.actualizar(direccion, hayEspacioRAM, dirVieja);
                    
                    this.tlb.actualizar(direccion, hayEspacioRAM, dirVieja); // ¡actualizar es algoritmo FIFO!
                    
                    this.tempCarga += this.tempArregloFallPag;
                }
            }
            referenciadas.add(direccion);
            //this.ram.setReferenciadas(referenciadas);
        }
    }

    public void run() {
        for (int i = 0; i < this.direcciones.size(); i++) {
            validarReferencias(this.direcciones.get(i));
            //System.out.println(i);
            try {
                sleep(2);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Envejecimiento.env = false;
        System.out.println("Fin de ejecución.");
        System.out.println("Fallos de página: " + this.numFallosPagina);
        System.out.println("Tiempo de traducción: " + this.tempTrad + " ns");
        System.out.println("Tiempo de carga: " + this.tempCarga + " ns");
        //this.ram.loopRAM();
        //System.out.println("\n");
        //this.ram.loopBITS();
        //System.out.println("\n");
        //this.tlb.loopTLB();
        //System.out.println("\n");
        //this.tp.loopTP();
    }
    
}