package interfaz;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Menu {
    //objeto de lectura del teclado
    private static final BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
    //menu principal de la aplicacion
    public void menuPrincipal()throws Exception{
        boolean again;
        do {
            int opcion = 0;
            //repite el menu si no ingresa un numero del 1 al 6 o marca numero invalido siescribe algo que no sea un numero
            do try {
                System.out.println("Seleccione La Accion Que Desea Realizar\n" +
                        "1.- Dar de alta doctores.\n" +
                        "2.- Dar de alta pacientes.\n" +
                        "3.- Crear una cita con fecha y hora.\n" +
                        "4.- Relacionar una cita con un doctor y un paciente\n" +
                        "5.- Mostrar citas Confirmadas\n" +
                        "6.- Salir");
                opcion = Integer.parseInt(entrada.readLine());
            } catch (Exception e){
                System.out.println("Numero invalido");
            } while (validarOpcion(opcion)==0);
            again = casos(opcion);
        }while (again);
    }
    //manda a llamar a los metodos de cada uno de los caso del 1 al 5 retorna 5 y el 6 retorna falso para salir de la aplicacion
    public int validarOpcion(int opcion){
        return (opcion <1 | opcion>6)?0:opcion;
    }
    public boolean casos(int opcion) throws Exception{
        Administracion administracion = new Administracion();
        switch (opcion){
            case 1:
                administracion.doctores();
                break;
            case 2:
                administracion.pacientes();
                break;
            case 3:
                administracion.citas();
                break;
            case 4:
                administracion.relacion();
                break;
            case 5:
                administracion.mostrarCitas();
                break;
            case 6:
                return false;
        }
        return true;
    }
}
