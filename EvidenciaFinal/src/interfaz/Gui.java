package interfaz;

public class Gui {

    public static void main(String[] args) throws Exception{
        //crea objetos para la secuencia de los metodos y clases
        OnCreate onCreate = new OnCreate();
        Login login = new Login();
        Menu menu = new Menu();
        onCreate.validarArchivos();
        login.menuInicio();
        menu.menuPrincipal();
    }
}
