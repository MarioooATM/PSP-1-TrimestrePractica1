package vista;

import com.github.javafaker.Faker;
import controlador.ControladorPrincipal;
import controlador.EnviarDatos;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main (String [] args)  {
        ControladorPrincipal cp = new ControladorPrincipal();
        cp.pideDatos();
    }

}
