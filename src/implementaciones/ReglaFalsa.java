/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementaciones;

import metodosnumericos.MetodoNumerico;

/**
 *
 * @author josh
 */
public class ReglaFalsa extends MetodoNumerico {

    private double inferior;
    private double superior;
    private double epsilon;
    private int limite;
    
    @Override
    public void leerParametros() {
        int grado = (int) leerNumero("¿De qué grado es la ecuacion?", true);
        leerPolinomio(grado); 
        inferior = leerNumero("¿Cuál el límite inferior?", false);
        superior = leerNumero("¿Cuál el límite superior?", false);
        limite =  (int) leerNumero("¿Cuál es el número máximo de iteraciones?", true);
        epsilon = 0.001;
    }

    @Override
    public void calcular() {
        try {
            System.out.println("xa"+tab+tab+"xb"+tab+tab+"f(xa)"+tab+tab+"f(xb)"+tab+"nueva X"+tab+"error");
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
        double x=inferior;
        double y;
        double ya=evaluarFuncion(inferior);
        double yb=evaluarFuncion(superior); 
        
        for(int i=0; i<limite; i++) {
            double numerador = ya*(xb-xa);
            double denominador = yb-ya;
            
            if (denominador == 0){
               throw new Exception("No esposible obtener la raíz");
            }
            
            x = xa - numerador / denominador;
            y = evaluarFuncion(x);
            
            if (xa*y<epsilon){                
                return x;
            }
            
            System.out.println(
                    formato.format(xa)+tab+
                    formato.format(xb)+tab+
                    formato.format(ya)+tab+
                    formato.format(yb)+tab+
                    formato.format(x)+tab+
                    formato.format(xa*y)+tab);
            
            if (ya*y<0) {
                superior=x;
            } else {
                inferior=x;
            }
        }
        
        throw new Exception("Límite de iteracines alcanzado");
    }
}
