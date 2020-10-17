package algorithms;

import static util.Constants.*;
import general.NumericalMethod;
import util.PropertiesReader;

/**
 *
 * @author josh.isc@hotmail.com
 */
public class NewtonRaphson extends NumericalMethod {
    
    private double epsilon;
    private double initialValue;

    @Override
    public void readInputValues(PropertiesReader reader) {
        initialValue = reader.getDouble(INITIAL_VALUE_PROPERTY);
        epsilon = Math.abs(reader.getDouble(EPSILON_PROPERTY));
    }

    @Override
    public double compute() throws Exception {
        System.out.println();
        System.out.println("x"+tab+tab+"f(x)"+tab+tab+"f'(x)");
        return this.runNewtonRaphson();
    }
    
    @Override
    public String name() {
       return "Newton Raphson";
    }
    
    private double runNewtonRaphson() throws Exception {
        double x = initialValue;
        double error = Double.MAX_VALUE; // Start with the highest error possible 
        currentIteration = 0;
        
        while (error >= epsilon && currentIteration++ < iterationsLimit) { 
            double derivative = evaluateDerivative(x);
            double fx = evaluateFunction(x);
            
            printStep(x, fx, derivative);
            
            if(derivative == 0) {
                throw new Exception("Root not found, the derivative of "+this.toString()+" for x="+x+" is zero.");
            }
            
            double division = fx / derivative;
            x = x - division; // Newton Raphson : xi+1 = xi - f(xi) / f'(xi)
            
            error = Math.abs(division);
        }
        
        return Math.round(x * 100.0) / 100.0; 
    }

    private void printStep(double x, double fx, double derivative) {
        System.out.println(
            formatter.format(x)+tab+
            formatter.format(fx)+tab+
            formatter.format(derivative)
        );
    }
}