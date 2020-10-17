package algorithms;

import static util.Constants.*;
import general.NumericalMethod;
import util.PropertiesReader;

/**
 *
 * @author josh.isc@hotmail.com
 */
public class FalseRule extends NumericalMethod {

    private double lowerLimit;
    private double upperLimit;
    private double epsilon;
    
    @Override
    public void readInputValues(PropertiesReader reader)  {
        epsilon = Math.abs(reader.getDouble(EPSILON_PROPERTY));
        lowerLimit = reader.getDouble(LOWER_LIMIT_PROPERTY);
        upperLimit = reader.getDouble(UPPER_LIMIT_PROPERTY);
    }

    @Override
    public double compute() throws Exception {
        System.out.println();
        System.out.println("xa"+tab+tab+"xb"+tab+tab+"f(xa)"+tab+tab+"f(xb)"+tab+"nueva X"+tab+"error");
        return runFalseRule();
    }

    @Override
    public String name() {
        return "Regla Falsa";
    }
    
    private double runFalseRule() throws Exception {
        double xa=lowerLimit, xb=upperLimit;
        double x=lowerLimit;
        double y;
        double ya=evaluateFunction(lowerLimit);
        double yb=evaluateFunction(upperLimit); 
        
        for(int i=0; i<iterationsLimit; i++) {
            double numerador = ya*(xb-xa);
            double denominador = yb-ya;
            
            if (denominador == 0){
               throw new Exception(notFoundMessage);
            }
            
            x = xa - numerador / denominador;
            y = evaluateFunction(x);
            
            if (xa*y<epsilon){                
                return x;
            }
            
            printStep(xa, xb, ya, yb, x);
            
            if (ya*y<0) {
                upperLimit=x;
            } else {
                lowerLimit=x;
            }
        }
        
        throw new Exception(notFoundMessage);
    }

    private void printStep(double xa, double xb, double ya, double yb, double x) {
        System.out.println(
            formatter.format(xa)+tab+
            formatter.format(xb)+tab+
            formatter.format(ya)+tab+
            formatter.format(yb)+tab+
            formatter.format(x)+tab
        );
    }
}
