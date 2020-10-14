package metodosnumericos;

import implementaciones.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    PuntoFijo.class);
    
    // Constructor vacío
    public Menu() {
        cargarAlgoritmos();
    }
    
    // Punto de entrada
    public static void main(String[] args) {
        Menu menu = new Menu();
        int algoritmoSeleccionado = 2;
        menu.ejecutarAlgoritmo(algoritmoSeleccionado);
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
}