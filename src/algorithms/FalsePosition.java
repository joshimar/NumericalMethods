package algorithms;

import static util.Constants.*;
import general.NumericalMethod;
import util.PropertiesReader;

/**
 *
 * @author josh.isc@hotmail.com
 */
public class FalsePosition extends NumericalMethod {

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
        return runFalseRule();
    }

    @Override
    public String name() {
        return "False Position";
    }
    
    private double runFalseRule() throws Exception {
        double a=lowerLimit, b=upperLimit;
        double x, y;
        double ya=evaluateFunction(a);
        double yb=evaluateFunction(b); 
        
        printHeaders();
        
        for(int i=0; i<iterationsLimit; i++) {
            double dividend = ya*(b-a);
            double divisor = yb-ya;
            
            if (divisor == 0){
               throw new Exception(notFoundMessage);
            }
            
            x = a - dividend / divisor;
            y = evaluateFunction(x);
            
            printStep(a, b, ya, yb, x, y);
            
            if (Math.abs(y) < epsilon){                
                return x;
            }
            
            if (ya*y < 0) {
                b=x;
            } else {
                a=x;
            }
        }
        
        throw new Exception(notFoundMessage);
    }

    private void printStep(double a, double b, double ya, double yb, double c, double fc) {
        System.out.println(formatter.format(a)+tab+
            formatter.format(b)+tab+
            formatter.format(ya)+tab+
            formatter.format(yb)+tab+
            formatter.format(c)+tab+
            formatter.format(fc)
        );
    }

    private void printHeaders() {
        System.out.println("a"+tab+tab+"b"+tab+tab+"f(a)"+tab+tab+"f(b)"+tab+tab+"c"+tab+tab+"f(c)");
    }
}
