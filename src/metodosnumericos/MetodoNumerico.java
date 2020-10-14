package metodosnumericos;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author Josh
 */
public abstract class MetodoNumerico {
    
    protected double[] funcion; // Arreglo que guarda coheficientes de la funcion C0 + C1x + C2x^2 + C3x^3 ...
    protected String tab="\t"; // Caracter para generar tablas
    protected DecimalFormat formato = new DecimalFormat("00000.00000"); // Con el que podemos imprimir vairables con muchos decimales solamente mostrando los necesarios del punto
            
    public abstract void leerParametros();
    public abstract void calcular();
    public abstract String nombre();
    
    public void leerPolinomio(int grado) {
        this.funcion = new double[grado+1]; // Agregamos 1 por la constante C0
        String ejemplo = generarEjemplo(grado);
        System.out.println("Intruduce los coheficientes de la ecuación "+ejemplo+" en orden comenzando por la constante.");
        
        for(int i=0; i<=grado; i++) {
            double coheficiente = leerNumero("C"+i, false);
            this.funcion[i] = coheficiente;
        }
    }
    
    public double leerNumero(String mensaje, boolean checarPositivo) {
        System.out.println(mensaje);
        Scanner scanner = new Scanner(System.in);
        double lectura = 0d;
        
        while(true) {
            try {
                System.out.print(">> ");
                lectura = scanner.nextDouble();
            } catch(Exception e) {
                System.out.println("Favor de introducir un número.");
            }
            
            if(checarPositivo && lectura <= 0) {
                System.out.println("El número introducido debe ser un valor entero positivo.");
            } else {
                break;
            }
        }
        
        return lectura;
    }
    
    public double evaluarFuncion(double x) { // Pasamos el valor de x
        double resultado = 0d;
        
        for(int termino=0; termino<this.funcion.length; termino++) {
            resultado += this.funcion[termino] * Math.pow(x, termino); // Multiplicamos termino por x elevado al termino correspondiente
        }
        
        return resultado;
    }
    
    public double evaluarDerivada(double x) { // Pasamos el valor de x
        double resultado = 0d;
        
        for(int termino=1; termino<this.funcion.length; termino++) { // Comenzamos lectura desde el segundo elemento porque la derivada de una constante es cero
            resultado += this.funcion[termino] * termino * Math.pow(x, termino-1); // La derivada de un termino es = coheficiente por el termino por x elevado al (termino-1)
        }
        
        return resultado;
    }

    private String generarEjemplo(int grado) {
        StringBuilder sb = new StringBuilder("C0 + ");
        for(int i=1; i<=grado; i++) {
            sb.append("C").append(i);
            sb.append("x^").append(i);
            if(i<grado) {
                sb.append(" + ");
            } 
        }
        return sb.toString();
    }
}