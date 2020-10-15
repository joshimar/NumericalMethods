package metodosnumericos;

import implementaciones.*;//importe implementaciones de cada metodo 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Josh
 */
public class Menu {

    private List<String> opciones;
    private Map<Integer, MetodoNumerico> instancias;
    private List<Class<? extends MetodoNumerico>> algoritmos = Arrays.asList(
    // Cuando implementamos un nuevo algoritmo, lo registramos aquí:
                    NewtonRaphson.class, 
                    Biseccion.class,
                    PuntoFijo.class,
                    ReglaFalsa.class);
    
    // Constructor vacío
    public Menu() {
        cargarAlgoritmos();
    }
    
    // Punto de entrada
    public static void main(String[] args) {
        Menu menu = new Menu();
        while(true){
            int algoritmoSeleccionado = menu.leerOpcion();
            menu.ejecutarAlgoritmo(algoritmoSeleccionado);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Si deseas continuar presiona enter para parar introduce la lentra N");
            String respuesta=scanner.nextLine();
            
            if("N".equals(respuesta.toUpperCase())){
                break;
            }
        }
    }
    
    private void ejecutarAlgoritmo(int algoritmoSeleccionado) {
        MetodoNumerico algoritmo = instancias.get(algoritmoSeleccionado);
        System.out.println(algoritmo.nombre());
        algoritmo.leerParametros();
        algoritmo.calcular();
    }
    
    private void cargarAlgoritmos() {
        opciones = new ArrayList<>();
        instancias = new HashMap<>();
        int posicion=0;
        for(Class<? extends MetodoNumerico> algoritmo : algoritmos) {
            try {
                instancias.put(posicion, algoritmo.newInstance());
            } catch (Exception e) { 
                System.out.println("No se pudo cargar algoritmo "+algoritmo.getSimpleName()+". "+e.getMessage());
            } 
            opciones.add(instancias.get(posicion++).nombre());
        }
    }
    
    public int leerOpcion() {// declaro un metodo publico que me regresa un tipo de dato entero
                 
        System.out.println("Intruce el numero del algoritmo que quieres correr");
        System.out.println("Lista de opciones:");
                
        for(int i = 0; i<algoritmos.size(); i++) {
            System.out.println("("+(i+1)+") "+instancias.get(i).nombre());//mostrarle al ususario las opciones 
        }
        
        int seleccion = 0;
        boolean inputValido = false;
        while(!inputValido) {
            try {
                System.out.print(">> ");//intruduce el dato el usuario
                Scanner scanner = new Scanner(System.in);//Crear un objeto para leer desde la consola utilizando un construcctor
                seleccion = scanner.nextInt();//cuando utilizo un metodo siempre programar con camelcase (estandar de camello)
                
                if(seleccion<1 || seleccion > algoritmos.size()) {//esta líena reviza si el usuario introduce un valor invalido
                    System.out.println(seleccion+" es un valor erroneo, intenta otra vez ");  
                } else { 
                    inputValido = true;
                }
            } catch(InputMismatchException e) {//bloque de codigo que nos recupera del error utilizando manejo de exepciones.
                System.out.println("Favor de introducir un numero");
            }  
        }
        
        return seleccion-1;//final del metodo // lo que ponga el usuario menos 1 para mapear correctamente con el arreglo opciones.
    }  
}