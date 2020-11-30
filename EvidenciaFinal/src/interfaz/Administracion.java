package interfaz;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Administracion {
    //objeto de lectura del teclado
    private static final BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
    //lista de los path de direcciones
    private static final List<String> PROPERTY_PATH = new ArrayList<String>(){{add("./db/doctores.txt");
        add("./db/pacientes.txt");add("./db/citas.txt");add("./db/agenda.txt");}};
    //listas para validar nombres y el id de las citas
    private static final List<String> nombres = new ArrayList<>();
    private static final List<String> citasArray = new ArrayList<>();

    //metodo para agregar doctores a su archivo correspondente
    public void doctores() throws Exception{
        int id;
        String nombre,especialidad;
        Properties properties  = new Properties();
        properties.load(new FileInputStream(PROPERTY_PATH.get(0)));
        System.out.println("Ingrese El Nombre Completo Del Doctor");
        nombre = entrada.readLine();
        System.out.println("Ingrese La Especialidad Del Doctor");
        especialidad  = entrada.readLine();
        id = obtenerId(properties,PROPERTY_PATH.get(0));
        properties.remove("d0");
        properties.setProperty("d"+id,nombre.trim()+","+especialidad);
        properties.store(new FileOutputStream(PROPERTY_PATH.get(0)),null);
        System.out.println("Se Dio De Alta Correctamente Al Doctor "+nombre);
    }
    //metodo para agregar pacientes a su archivo correspondiente
    public void pacientes() throws Exception{
        int id;
        String nombre;
        Properties properties  = new Properties();
        properties.load(new FileInputStream(PROPERTY_PATH.get(1)));
        System.out.println("Ingrese El Nombre Completo Del Doctor");
        nombre = entrada.readLine();
        id = obtenerId(properties,PROPERTY_PATH.get(1));
        properties.remove("p0");
        properties.setProperty("p"+id,nombre.trim());
        properties.store(new FileOutputStream(PROPERTY_PATH.get(1)),null);
        System.out.println("Se Dio De Alta Correctamente Al Paciente "+nombre);
    }
    //metodo para agregar citas a su archivo correspondiente
    public void citas() throws Exception{
        int id;
        String fecha,hora,motivo;
        Properties properties  = new Properties();
        properties.load(new FileInputStream(PROPERTY_PATH.get(2)));
        System.out.println("Ingrese La Fecha De Su Cita");
        fecha = entrada.readLine();
        System.out.println("Ingrese La Hora De Su Cita");
        hora  = entrada.readLine();
        System.out.println("Ingrese El Motivo De Su Cita");
        motivo  = entrada.readLine();
        id = obtenerId(properties,PROPERTY_PATH.get(2));
        properties.remove("c0");
        properties.setProperty("c"+id, fecha+ " " + hora + "," +motivo);
        properties.store(new FileOutputStream(PROPERTY_PATH.get(2)),null);
        System.out.println("Se Agrego Correctamente La Cita\nNumero De Cita: c"+id);

    }
    //metodo que genera confirma una cita con los datos existentes de los demas archivos
    public void relacion() throws Exception{
        int id;
        String doctor,paciente,cita;
        Properties pdoctor  = new Properties();
        pdoctor.load(new FileInputStream(PROPERTY_PATH.get(0)));
        Properties ppaciente  = new Properties();
        ppaciente.load(new FileInputStream(PROPERTY_PATH.get(1)));
        Properties pcita  = new Properties();
        pcita.load(new FileInputStream(PROPERTY_PATH.get(2)));
        Properties properties  = new Properties();
        properties.load(new FileInputStream(PROPERTY_PATH.get(3)));
        do {
            System.out.println("Ingrese El Nombre Del Doctor");
            mostrarPropiedades(pdoctor,PROPERTY_PATH.get(0));
            doctor = entrada.readLine();
        }while (!validarNombre(doctor));
        nombres.clear();
        do {
            System.out.println("Ingrese El Nombre Del Cliente");
            mostrarPropiedades(ppaciente,PROPERTY_PATH.get(1));
            paciente = entrada.readLine();
        }while (!validarNombre(paciente));
        nombres.clear();
        citasArray.clear();
        do {
            System.out.println("Ingrese El Numero De Cita");
            mostrarPropiedades(pcita,PROPERTY_PATH.get(2));
            cita = entrada.readLine();
        }while (!validarCita(cita));
        nombres.clear();
        citasArray.clear();
        id = obtenerId(properties,PROPERTY_PATH.get(3));
        properties.remove("r0");
        String[] doct = doctor.split(",");
        String cits = pcita.getProperty(cita);
        String[] cts = cits.split(",");
        properties.setProperty("r"+id,doct[0]+","+paciente + "," + cts[0]);
        properties.store(new FileOutputStream(PROPERTY_PATH.get(3)),null);
        System.out.println("Se Confirmo Correctamente La Cita\nNumero De Cita Confirmada: r"+id);
    }
    //metodo que retorna el id siguiente para el control de id de los archivos
    public int obtenerId(Properties properties,String path) throws Exception{
        properties.load(new FileInputStream(path));
        Object[] objects = properties.keySet().toArray();
        List<String> list = new ArrayList<>();
        for (Object o: objects) {
            list.add(o.toString());
        }
        Collections.sort(list);
        return Integer.parseInt(list.get(list.size()-1).substring(1))+1;
    }
    //metodo que muestra las citas confirmadas del archivo agenda
    public void mostrarCitas()throws Exception{
        StringBuilder stringBuilder= new StringBuilder();
        Properties properties  = new Properties();
        properties.load(new FileInputStream(PROPERTY_PATH.get(3)));
        Object[] objects = properties.keySet().toArray();
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for (Object o: objects) {
            list.add(o.toString());
        }
        Collections.sort(list);
        for (String l : list) {
            String[] splits =properties.getProperty(l).split(",");
            for (int i=0;i<splits.length;i++){
                if (i==0){
                    stringBuilder.append("Doctor: ").append(splits[i]);
                }
                if (i==1){
                    stringBuilder.append(" Paciente: ").append(splits[i]);
                }
                if (i==2){
                    stringBuilder.append(" Cita: ").append(splits[i]);
                }
            }
            list2.add(stringBuilder.toString());
            stringBuilder.setLength(0);
        }
        IntStream.range(0, list2.size()).mapToObj(i -> list.get(i) + " " + list2.get(i)).forEach(System.out::println);
    }
    //metodo que muestra las propiedades de los archivos doctores pacientes y citas
    public void mostrarPropiedades(Properties properties,String path) throws Exception{
        properties.load(new FileInputStream(path));
        Object[] objects = properties.keySet().toArray();
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for (Object o: objects) {
            list.add(o.toString());
        }
        Collections.sort(list);
        for (String l:list) {
            citasArray.add(l);
            String[] splits = properties.getProperty(l).split(",");
            nombres.add(splits[0]);
        }
        list.forEach((l)->list2.add(l+" "+properties.get(l)));
        list2.forEach(System.out::println);
    }
    //metodo que valida el nombre ingresado en el metodo relacion exista en el archivo correspondiente
    public boolean validarNombre(String nombre){
        for (String l:nombres) {
            if (l.equals(nombre))
                return true;
        }
        System.out.println("Nombre Invalido");
        return false;
    }
    //metodo que valida la cita ingresada en el metodo relacion exista en el archivo correspondiente
    public boolean validarCita(String cita){
        for (String l: citasArray) {
            if (l.equals(cita))
                return true;
        }
        System.out.println("Cita Invalida");
        return false;
    }

}