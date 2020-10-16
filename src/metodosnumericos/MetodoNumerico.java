package metodosnumericos;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author Josh
 */
public abstract class MetodoNumerico {
    
    protected double epsilon = 0.000000001; // Tolerancia de 9 decimales (estricta)
    protected double[] funcion; // Arreglo que guarda coheficientes de la funcion C0 + C1x + C2x^2 + C3x^3 ...
    protected String tab="\t"; // Caracter para generar tablas
    protected DecimalFormat formato = new DecimalFormat("00000.000000000"); // Con el que podemos imprimir vairables con muchos decimales solamente mostrando los necesarios del punto
    protected int opcionSeleccionada; // ecuacion        
    protected String opciones = "¿Qué ecuacion quieres usar?\n (1) ecuacion1 \n (2) ecuacion2 \n (3) ecuacion3";
        
    public abstract void leerParametros();
    public abstract void calcular();
    public abstract String nombre();
    
    public void leerPolinomio(int grado) {
        this.funcion = new double[grado+1]; // Agregamos 1 por la constante C0
        
        String ejemplo = generarEjemplo(grado);
        
        while(true) {
            System.out.println("Intruduce los coheficientes de la ecuación "+ejemplo+" en orden comenzando por la constante C0 utilizando espacios para separar cada uno.");
            try {
                leerCoheficientes();
                break;
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
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
    
    private void leerCoheficientes() throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        String[] lectura = null;
        
        try {
            System.out.print(">> ");
            lectura = scanner.nextLine().trim().split(" ");
            
            if(lectura.length != funcion.length) {
                throw new Exception("Debes introducir exactamente "+funcion.length+" coheficientes.");
            }
            
            for(int i=0; i<funcion.length; i++) {
                funcion[i] = Double.parseDouble(lectura[i]);
            }
        } catch(Exception e) {
            throw new Exception("Favor de introducir solo números separados por un espacio." + e.getMessage());
        }
        
        System.out.println("La ecuación introducida es: "+mostrarEcuacion());
    }
    
    public double evaluarFuncion(double x) { // Pasamos el valor de x
        return evaluarFuncionEstatica(x);
//        double resultado = 0d;
//        
//        for(int termino=0; termino<this.funcion.length; termino++) {
//            resultado += this.funcion[termino] * Math.pow(x, termino); // Multiplicamos termino por x elevado al termino correspondiente
//        }
//        
//        return resultado;
    }
    
    public double evaluarDerivada(double x) { // Pasamos el valor de x
        return evaluarDerivadaEstatica(x);
//        double resultado = 0d;
//        
//        for(int termino=1; termino<this.funcion.length; termino++) { // Comenzamos lectura desde el segundo elemento porque la derivada de una constante es cero
//            resultado += this.funcion[termino] * termino * Math.pow(x, termino-1); // La derivada de un termino es = coheficiente por el termino por x elevado al (termino-1)
//        }
//        
//        return resultado;
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
    
    private String mostrarEcuacion() {
        StringBuilder sb = new StringBuilder();
        sb.append(funcion[0]).append(" + ");
        for(int i=1; i<funcion.length; i++) {
            sb.append(funcion[i]).append("x");
            if(i>1) {
                sb.append("^").append(i);
            }
            if(i<funcion.length-1) {
                sb.append(" + ");
            } 
        }
        return sb.toString();
    }
    
    private double evaluarFuncionEstatica(double x) {
        // if(opcionSeleccionada == 1) ... else // regresas la ecuacion dependiendo del numero (opcion) seleccionadx
        return Math.pow(x, 3) + 4*Math.pow(x, 2) - 10; // x^3 + 4x^2 - 10 
//        return Math.cos(x); // Cos(x)
//        return Math.exp(-x) - Math.log(x); // e^(-x) - ln(x)
    }
    
    private double evaluarDerivadaEstatica(double x) {
         return 3*Math.pow(x, 2) + 8*x;  // Bisección
//        return -Math.exp(-x) - 1/x; // Newton Rapshon
    }
}