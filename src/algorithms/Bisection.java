package algorithms;

import static util.Constants.*;
import general.NumericalMethod;
import util.PropertiesReader;

/**
 *
 * @author josh.isc@hotmail.com
 */
public class Bisection extends NumericalMethod {

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
        System.out.println("a"+tab+tab+"b"+tab+tab+"c"+tab+tab+"f(a)"+tab+tab+"f(b)"+tab+tab+"f(c)");
        currentIteration = 0;
        return runBisection();
    }
    
    @Override
    public String name() {
       return "Bisection";
    }
    
    private double runBisection() throws Exception {
        if(currentIteration++ >= iterationsLimit) {
            throw new Exception(notFoundMessage);
        }
        
        double c = lowerLimit + (upperLimit - lowerLimit) / 2; // Get center of the current interval
        double fc = evaluateFunction(c); 
        double fl = evaluateFunction(lowerLimit);
        double fu = evaluateFunction(upperLimit);
        
        if(Math.abs(fc) < epsilon) {
            return c;
        } 
        
        printStep(lowerLimit, upperLimit, c, fl, fu, fc);
        
        if(upperLimit - lowerLimit < epsilon) { 
            throw new Exception(notFoundMessage);
        }
        
        if(Math.signum(fl) == Math.signum(fc)) {
            lowerLimit = c; // Keep searching on the right half
            return runBisection();
        } else if(Math.signum(fc) == Math.signum(fu))  {
            upperLimit = c; // Keep searching on the left half
            return runBisection(); 
        }
        
        throw new Exception(notFoundMessage);
    }

    private void printStep(double lowerLimit, double upperLimit, double c, double fl, double fu, double fc) {
        System.out.println(formatter.format(lowerLimit)+tab+
                formatter.format(upperLimit)+tab+
                formatter.format(c)+tab+
                formatter.format(fl)+tab+
                formatter.format(fu)+tab+
                formatter.format(fc));
    }
}