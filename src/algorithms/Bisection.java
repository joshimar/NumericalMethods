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
    private double previous;
    
    @Override
    public void readInputValues(PropertiesReader reader)  {
        epsilon = Math.abs(reader.getDouble(EPSILON_PROPERTY));
        lowerLimit = reader.getDouble(LOWER_LIMIT_PROPERTY);
        upperLimit = reader.getDouble(UPPER_LIMIT_PROPERTY);
    }

    @Override
    public double compute() throws Exception {
        currentIteration = 0; // Start iteration count here since the bisection method is recursive
        previous = Double.POSITIVE_INFINITY; // No previous evaluation, thus set to infinity
        printHeaders();
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
        double error = Math.abs(previous - c);
        previous = c;
        
        if(error < epsilon) {
            return c;
        } 
        
        double fc = evaluateFunction(c); 
        double fl = evaluateFunction(lowerLimit);
        double fu = evaluateFunction(upperLimit);
        
        printStep(lowerLimit, upperLimit, c, fl, fu, fc, error);
        
        if(Math.signum(fl) == Math.signum(fc)) {
            lowerLimit = c; // Keep searching on the right half
            return runBisection();
        } else if(Math.signum(fc) == Math.signum(fu))  {
            upperLimit = c; // Keep searching on the left half
            return runBisection(); 
        }
        
        throw new Exception(notFoundMessage);
    }

    private void printStep(double lowerLimit, double upperLimit, double c, double fl, double fu, double fc, double error) {
        System.out.println(formatter.format(lowerLimit)+tab+
                formatter.format(upperLimit)+tab+
                formatter.format(c)+tab+
                formatter.format(fl)+tab+
                formatter.format(fu)+tab+
                formatter.format(fc)+tab+
                (error == Double.POSITIVE_INFINITY ? "-" : formatter.format(error))
        );
    }

    private void printHeaders() {
        System.out.println("a"+tab+tab+"b"+tab+tab+"c"+tab+tab+"f(a)"+tab+tab+"f(b)"+tab+tab+"f(c)"+tab+tab+"error");
    }
}