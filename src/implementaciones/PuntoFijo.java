package implementaciones;

import metodosnumericos.MetodoNumerico;

/**
 *
 * @author Josh
 */
public class PuntoFijo extends MetodoNumerico {

    private double p0; // Punto inicial
    private double epsilon; // Tolerancia
    private int max; // Número máximo de iteraciones
    private int grado ; // Problema definido para una ecuación de segundo o tercer grado
    
    @Override
    public void leerParametros() {
        leerPolinomio(grado); 
        p0 = leerNumero("¿Cuál el punto inicial?", false);
        max =  (int) leerNumero("¿Cuál es el número máximo de iteraciones?", true);
        grado =  (int) leerNumero("¿Cuál es el grado de la ecuacion? solamente puede ser 2 o 3", true);
        epsilon = 0.001;//leerNumero("¿Cuál es el epsilon a utilizar? Puede ser 0.001", true);
    }

    @Override
    public void calcular() {
        try {
            System.out.println();
            System.out.println("p"+tab+tab+"p0"+tab+tab+"evaluación");
            float resultado = (float) calcularRaiz();
            System.out.println();
            System.out.println("La raíz encontrada es: "+resultado);
            System.out.println();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String nombre() {
        return "Punto Fijo";
    }
    
    private double calcularRaiz() throws Exception {
        for(int i=0; i<max; i++) {
            double p = g(p0);
            double evaluacion = p-p0;
            
            System.out.println(
                    formato.format(p)+tab+
                    formato.format(p0)+tab+
                    formato.format(evaluacion)+tab);
            
            if(Math.abs(evaluacion) < epsilon) {
                return p;
            }
            p0=p;
        }
        throw new Exception("La raíz no pudo ser encontrada.");
    }
    
    // Función iteradora
    private double g(double punto) throws Exception {
        switch(grado){
            case 2: return g2(punto);
            default: return g3(punto);
        }
    }
    
    private double g2(double punto) throws Exception {
        
        double numerador = -funcion[0];
        double denominador = funcion[2]*punto + funcion[1];
        
        if(funcion[3] == 0 || denominador == 0) {
            throw new Exception("La raíz no pudo ser encontrada.");
        }
        
        return numerador / denominador;
    }
    
    // Función iteradora para una ecuación de tercer grado.
    // C0 + C1x + C2x^2 + C3x^3
    // x^2(C3x + C2) = -(C0 + C1x)
    // x^2 = -(C0 + C1x) / (C3x + C2)
    // x = raízCuadrada(-(C0 + C1x) / (C3x + C2)) <- esta será la función iteradora g(x)
    // g(x) = raízCuadrada(-(C0 + C1x) / (C3x + C2)) 
    private double g3(double punto) throws Exception {
        
        double numerador = -(funcion[0] + funcion[1]*punto);
        double denominador = funcion[3]*punto + funcion[2];
        
        if(funcion[3] == 0 || denominador == 0) {
            throw new Exception("La raíz no pudo ser encontrada.");
        }
        
        return Math.sqrt(numerador / denominador);
    }
}
