package interfaz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OnCreate {
    //lista con los paths de direcciones para el manejo de archivos
    private static final List<String> PROPERTY_PATH = new ArrayList<String>(){{add("./db/usuarios.txt");
    add("./db/pacientes.txt");add("./db/doctores.txt");add("./db/citas.txt");
    add("./db/agenda.txt");}};

    //valida si se encuentra los archivos de recursos si no se encuentar los crea llamando al metodo crearResources
    public void validarArchivos()throws Exception {
        File resources = new File("./db");
        if (!resources.exists()) {
            if (crearResources(resources))System.out.println("Se Crearon Los Recursos");
        }
        else System.out.println("Cargando Recursos");
        //crea el usuario inicial
        usuarioInicial();
        //crea id's iniciales para cada archivo
        idPrueba();
    }

    //crea la carpeta de db y los archivos de la carpeta
    public boolean crearResources(File resources) throws Exception{
        boolean result= resources.mkdirs();
        for (String file:PROPERTY_PATH){
            resources = new File(file);
            if (!resources.exists())
                result = resources.createNewFile();
        }
        return result;
    }
    //agrega un usuario inicial
    public void usuarioInicial() throws Exception{
        Properties properties = new Properties();
        properties.load(new FileInputStream(PROPERTY_PATH.get(0)));
        if (properties.get("admin")==null){
                properties.setProperty("admin","1234");
                properties.store(new FileOutputStream(PROPERTY_PATH.get(0)),"Usuario Inicial");
        }
    }
    //crea usuarios iniciales con identificadores para cada archivo si no existe ya algun registro en cada uno
    private void idPrueba() throws Exception {
        List<String> list = PROPERTY_PATH;
        List<String> id = new ArrayList<String>(){{add("p0");add("d0");add("c0");add("r0");}};
        list.remove(0);
        Properties properties = new Properties();
        for (String l : list){
            properties.load(new FileInputStream(l));
            if (properties.isEmpty()){
                properties.setProperty(id.get(0),"prueba");
                id.remove(0);
                properties.store(new FileOutputStream(l),"Registro Inicial");
                properties.clear();
            }
        }
        list.clear();
        id.clear();
    }
}
