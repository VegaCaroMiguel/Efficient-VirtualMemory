package clases;

import java.util.Scanner;
import java.io.*;

public class App {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        System.out.println("Ingrese el numero de entradas de la TLB");
		int numEntTlb = sc.nextInt();
		System.out.println("Ingrese el numero de marcos de pagina en RAM");
		int numMarcosPag = sc.nextInt();
		System.out.println("Ingrese el nombre del archivo");
		String archivo = sc.nextLine();

        // lee un archivo de texto y lo guarda en un arreglo de enteros
        Integer[] direcciones = new Integer[64];
        File file = new File("./lib/" + archivo);
        BufferedReader br = new BufferedReader(new FileReader(file));
        
        for (int i = 0; i < direcciones.length; i++) {
            direcciones[i] = Integer.parseInt(br.readLine());
        }

        TLB tlb = new TLB(numEntTlb);
        RAM ram = new RAM(numMarcosPag);
        TP tp = new TP(64);
        Envejecimiento envejecimiento = new Envejecimiento(numMarcosPag, ram);
        Referencias referencias = new Referencias(tlb, ram, tp);

        referencias.start();
        
        for (int i = 0; i < direcciones.length; i++) {
            referencias.setDireccion(direcciones[i]);
        }
    }


    
}
