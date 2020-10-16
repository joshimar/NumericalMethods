/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementaciones;

import metodosnumericos.MetodoNumerico;

/**
 *
 * @author dell
 */
public class ReglaFalsa extends MetodoNumerico {

    private double inferior;
    private double superior;
    private double epsilon;
    private int limite;
    private int funcionDestino;
    
    @Override
    public void leerParametros() {
//        funcionDestino =  (int) leerNumero("¿Qué función quieres utilizar? \n(1) polinomio \n(2) logarítmica", true);
//        if(funcionDestino == 1) {
//            int grado = (int) leerNumero("¿De qué grado es la ecuacion?", true);
//            leerPolinomio(grado); 
//        }
        inferior = leerNumero("¿Cuál el límite inferior?", false);
        superior = leerNumero("¿Cuál el límite superior?", false);
        limite =  (int) leerNumero("¿Cuál es el número máximo de iteraciones?", true);
        epsilon = 0.001;
    }

    @Override
    public void calcular() {
        try {
            System.out.println("xa"+tab+tab+"xb"+tab+tab+"xc"+tab+tab+"f(xa)"+tab+tab+"f(xb)"+tab+tab+"f(c)"+tab+tab+"error");
            double raiz = calcularRaiz();
            System.out.println("La raíz encontrada es: "+raiz);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String nombre() {
        return "Regla Falsa";
    }
    
    private double calcularRaiz() throws Exception {
        double xa=inferior, xb=superior;
        double xc=inferior;
        double yc;
        double ya;
        double yb; 
        
        for(int i=0; i<limite; i++) {
            
            ya=evaluarFuncion(xa);
            yb=evaluarFuncion(xb);
            xc = obtenerCentro(xa, xb, ya, yb);
            yc = evaluarFuncion(xc);
            
            double error = xa*yc;
            
            System.out.println(formato.format(xa)+tab+
                    formato.format(xb)+tab+
                    formato.format(xc)+tab+
                    formato.format(ya)+tab+
                    formato.format(yb)+tab+
                    formato.format(yc)+tab+
                    formato.format(error)+tab);
            
            if (error < epsilon){                
                return xc;
            }
            
            if(ya*yc > 0 ) {
                xa = xc; // la raíz está entre xc y xb (descartamos mitad izquierda)
            } else {
                xb = xc; // la raíz está entre xa y xc (descartamos mitad derecha)
            }
        }
        
        throw new Exception("Límite de iteracines alcanzado");
    }
    
    private double obtenerCentro(double xa, double xb, double ya, double yb) throws Exception {
        double numerador = ya*(xb-xa);
        double denominador = yb-ya;
            
        if (denominador == 0){
           throw new Exception("No esposible obtener la raíz");
        }
        
        return xa - (numerador / denominador);
    }
    
    private double evaluarFuncionInterna(double x) {
        if(funcionDestino == 1) {
            return evaluarFuncion(x); // Polinomio
        } else {
            return Math.exp(-x) - Math.log(x); // Función logarítmica
        }
    }
}
