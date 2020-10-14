package metodosnumericos;

import implementaciones.Biseccion;
import implementaciones.NewtonRaphson;
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
    private List<Class<? extends MetodoNumerico>> algoritmos;
    
    public Menu() {
        cargarAlgoritmos();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Menu menu = new Menu();
        int algoritmoSeleccionado = 0;
        menu.ejecutarAlgoritmo(algoritmoSeleccionado);
    }
    
    public void ejecutarAlgoritmo(int algoritmoSeleccionado) {
        MetodoNumerico algoritmo = instancias.get(algoritmoSeleccionado);
        System.out.println(algoritmo.nombre());
        algoritmo.leerParametros();
        algoritmo.calcular();
    }
    
    private void cargarAlgoritmos() {
        // Cuando implementamos un nuevo algoritmo, lo registramos aquí
            algoritmos = Arrays.asList(
                    NewtonRaphson.class, 
                    Biseccion.class);
            
            opciones = new ArrayList<>();
            
        try {
            int posicion=0;
            instancias = new HashMap<>();
            for(Class<? extends MetodoNumerico> algoritmo : algoritmos) {
                instancias.put(posicion, algoritmo.newInstance());
                opciones.add(instancias.get(posicion).nombre());
                posicion++;
            }
        } catch (Exception e) { } 
    }
    
}