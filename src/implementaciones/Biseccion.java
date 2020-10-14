package implementaciones;

import metodosnumericos.MetodoNumerico;

/**
 *
 * @author Josh
 */
public class Biseccion extends MetodoNumerico {

    private double limiteIzquierda;
    private double limiteDerecha;
    private double epsilon;
    
    @Override
    public void leerParametros() {
        leerEcuacion();
        limiteIzquierda = leerNumero("¿Cuál el límite izquierdo inicial?", false);
        limiteDerecha = leerNumero("¿Cuál el límite derecho inicial?", false);
        epsilon = leerNumero("¿Cuál es el epsilon a utilizar? Puede ser 0.001", true);
    }

    @Override
    public void calcular() {
        try {
            System.out.println();
            System.out.println("a"+tab+tab+"b"+tab+tab+"c"+tab+tab+"f(a)"+tab+tab+"f(b)"+tab+tab+"f(c)");
            float resultado = (float) buscar();
            System.out.println();
            System.out.println("La raíz encontrada es: "+resultado);
            System.out.println();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public String nombre() {
       return "Bisección (búsqueda binaria)";
    }
    
    private double buscar() throws Exception {
        
        double c = (limiteDerecha+limiteIzquierda) / 2; // Calcular punto medio entre limites
        double fc = evaluarFuncion(c); // Evaluar funcion para el punto medio
        double fi = evaluarFuncion(limiteIzquierda);
        double fd = evaluarFuncion(limiteDerecha);
            
        if(fc == 0.0) {
            return c; // Cuando la funcion evaluada f(c) es cero, ya hemos encontrado la raiz
        }
        
        if(Math.abs(fc) < epsilon) {
            return c; // Si el valor de c es muy pequeño, podemos considerar que hemos ebcibtrado la raíz con un valor muy aproximado
        } 
        
        System.out.println(
                formato.format(limiteIzquierda)+tab+
                formato.format(limiteDerecha)+tab+
                formato.format(c)+tab+
                formato.format(fi)+tab+
                formato.format(fd)+tab+
                formato.format(fc));
        
        if(limiteDerecha - limiteIzquierda < epsilon) { // Ya hemos igualado ambos límites sin poder haver encontrado la raíz
            throw new Exception("No es posible obtener la raiz.");
        }
        
        if(Math.signum(fc) == Math.signum(fi)) {
            limiteIzquierda = c; // Descartamos la mitad izquierda
            return buscar(); // Continuamos la búsqueda sobre la mitad derecha
        } else if(Math.signum(fc) == Math.signum(fd))  {
            limiteDerecha = c; // Descartamos la mitad derecha
            return buscar(); // Continuamos la búsqueda sobre la mitad izquierds
        }
        
        throw new Exception("No es posible obtener la raiz.");
    }
}