package interfaz;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Login {
    //objeto de lectura de teclado
    private static final BufferedReader entrada = new  BufferedReader(new InputStreamReader(System.in));
    //lista con el path de usuarios
    private static final String PROPERTY_PATH = "./db/usuarios.txt";

    //requiere que se ingrese un usuario y contraseña para validarlo con el metodo validar Usuario
    public void menuInicio() throws Exception{
        String user,pssw;
        System.out.println("Sistema De Administracion De Citas");
        do {
            System.out.println("Ingrese su usuario");
            user=entrada.readLine();
            System.out.println("Ingrese su contraseña");
            pssw = entrada.readLine();
        }while (!validarUsuario(user,pssw));
    }
    //si el usuario y la contraseña son correctos retorna verdadero caso contrario retorna falso
    public boolean validarUsuario(String user,String pssw) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream(PROPERTY_PATH));
        try {
            return properties.get(user).equals(pssw);
        }catch (NullPointerException e){
            System.out.println("\nUsuario o Contraseña Incorrecto\n");
            return false;
        }
    }
}
