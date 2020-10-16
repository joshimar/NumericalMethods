package implementaciones;

import metodosnumericos.MetodoNumerico;

/**
 * @author Josh
 */
public class NewtonRaphson extends MetodoNumerico {
    
    private double valorInicial;
    private int limite;

    @Override
    public void leerParametros() {
//        int grado = (int) leerNumero("¿De qué grado es la ecuacion?", true);
//        leerPolinomio(grado);
        opcionSeleccionada =  (int) leerNumero(opciones, true);
        this.valorInicial = leerNumero("¿Cuál es el valor inicial?", false);
        this.limite = (int) leerNumero("Cuantas interacciones quieres?", true);
    }

    @Override
    public void calcular() {
        try {
            System.out.println();
            System.out.println("x"+tab+tab+"f(x)"+tab+tab+"f'(x)"+tab+tab+tab+"error");
            float resultado = (float) this.calcularRaiz();
            System.out.println();
//            System.out.println("La raíz encontrada es: "+resultado);
//            System.out.println();
        } catch(Exception e) {
            //System.out.println(e.getMessage());
        }
    }
    
    @Override
    public String nombre() {
       return "Newton Raphson";
    }
    
    private double calcularRaiz() throws Exception {
        double x = this.valorInicial;
        double relacion; 
        int cuenta = 0;
        double error = Double.MAX_VALUE;
        double anterior;
        
        while (error >= epsilon && cuenta<limite) { //mientras no eh llegado al error disponible puedo seguir
            // Newton Raphson : xi+1 = xi - funcion(xi) / derivada(xi)
            double derivada = evaluarDerivada(x);
            double fx = evaluarFuncion(x);
            anterior = fx;
            
            String errorTexto = error == Double.MAX_VALUE ? "" : formato.format(error);
            
            System.out.println(
                    formato.format(x)+tab+
                    formato.format(fx)+tab+
                    formato.format(derivada)+tab+
                    errorTexto);
            
            if(derivada == 0) {
                throw new Exception("La raíz the función no puede ser encontrada, la derivada evaluada en "+x+" es cero.");
            }
                
            anterior = x;
            
            relacion = fx / derivada; 
            x = x - relacion; 
            
            error = x - (anterior / x);
            
            cuenta++;
        }
        
        return x; 
    }
    
}