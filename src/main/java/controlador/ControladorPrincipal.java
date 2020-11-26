package controlador;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ControladorPrincipal {
    public static void pideDatos(){
        Semaphore semaforo = new Semaphore(1);
        Scanner sc = new Scanner(System.in);
        boolean valido = true;
        int registros=0;
        int hilos=0;
        while (valido) {
            System.out.println("Escribe los registros: ");
            try {
                registros = sc.nextInt();
                valido = false;
            } catch (InputMismatchException a) {
                System.out.println("Escribe un numero por favor");
                sc.next();
            }
        }
        valido = true;
        while (valido) {
            System.out.println("Escribe los hilos que quieres utilizar: ");
            try {
                hilos = sc.nextInt();
                valido = false;
            } catch (InputMismatchException a) {
                System.out.println("Escribe un numero por favor");
                sc.next();
            }
        }
        if (registros<hilos){
            System.out.println("el numero de hilos no puede ser mayor que el de hilos");
            pideDatos();
        }

        int resto= registros%hilos;
        System.out.println(resto);
        int rango=registros/hilos;
        System.out.println(rango);
        int numInicial=1;
        int numFinal=rango;
        for(int x=1;x<=hilos;x++){
            if(resto>=1 && x == hilos && registros>hilos){
                numFinal+=resto;
            }
            EnviarDatos enviar = new EnviarDatos(numInicial,numFinal,semaforo);
            enviar.start();
            numInicial+=rango;
            numFinal+=rango;
        }
    }
}
