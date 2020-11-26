package controlador;

import com.github.javafaker.Faker;
import modelo.Usuario;

import java.sql.*;
import java.util.concurrent.Semaphore;

public class EnviarDatos extends Thread{

    private int numInicial;
    private int numFinal;
    private Semaphore semaforo;
    public EnviarDatos(int numInicial, int numFinal, Semaphore semaforo){
        this.numInicial= numInicial;
        this.numFinal=numFinal;
        this.semaforo= semaforo;
    }
    public void run(){
        calculo();
    }

    private void calculo(){
        int x=0;
        Usuario datos;
        Faker javaFaker;
        for (x=numInicial;x<=numFinal;x++) {
            try {
                semaforo.acquire();
                datos = new Usuario();
                javaFaker = new Faker();
                datos.setCorreo(javaFaker.bothify("??????###@gmail.com"));
                System.out.println(datos.getCorreo() + " " + Thread.currentThread().getName());
                datos.setSueldo((int) (Math.random() * 1000 + 10));
                System.out.println(datos.getSueldo() + " " + Thread.currentThread().getName());
                conectarBBDD(datos);
                semaforo.release();
            } catch (InterruptedException d) {
                System.out.println("Ha ocurrido un problema al crear los datos de insercion");
            }
        }
    }

    private void conectarBBDD(Usuario datos){
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BBDD_PSP_1","DAM2020_PSP","DAM2020_PSP");
            Statement consulta = conexion.createStatement();
            consulta.executeUpdate("INSERT INTO empleados (EMAIL,INGRESOS)\n" +
                    "VALUES ('"+datos.getCorreo()+"', "+datos.getSueldo()+")");
            System.out.println(Thread.currentThread().getName()+" He añadido 1");
            conexion.close();
        } catch (SQLException throwables) {
            System.out.println("Ha ocurrido un problema con la insercion de datos");
        }
    }

}
