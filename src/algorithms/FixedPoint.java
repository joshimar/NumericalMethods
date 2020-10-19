package algorithms;

import static util.Constants.*;
import general.NumericalMethod;
import util.PropertiesReader;

/**
 *
 * @author josh.isc@hotmail.com
 */
public class FixedPoint extends NumericalMethod {

    private double initialValue; 
    private double epsilon; 
    
    @Override
    public void readInputValues(PropertiesReader reader) {
        initialValue = reader.getDouble(INITIAL_VALUE_PROPERTY);
        epsilon = Math.abs(reader.getDouble(EPSILON_PROPERTY));
    }

    @Override
    public double compute() throws Exception {
        printHeaders();
        return runFixedPoint();
    }

    @Override
    public String name() {
        return "Fixed Point";
    }
    
    private double runFixedPoint() throws Exception {
        currentIteration = 0;
        double x = initialValue;
        while(currentIteration++ < iterationsLimit) {
            double gx = g(x);
            double newP = gx-x;
            
            printStep(x, gx, evaluateFunction(x));
           
            if(Math.abs(newP) < epsilon) {
                return gx;
            }
            x=gx;
        }
        throw new Exception(notFoundMessage);
    }
    
    /**
     * Example of g(x) for an equation of third degree.
     * 
     * Solution:
     * 
     * C0 + C1x + C2x^2 + C3x^3 = 0
     * C3x^3 = -C0 -C1x -C2x^2 
     * x^3 = -(C0 + C1x + C2X^2) / C3
     * g(x) = thirdRoot((C0 + C1x + C2X^2) / -C3) 
     * 
     * Therefore we can say that g(x) generalized for a polynomial is:
     * 
     * g(x) = degreethRoot(-(C0 + C1x + C2x^2 + ... C(degree-1)x^(degree-1))/C-degree)
     * 
     * @param x Value of x to be used in the evaluation of g.
     * @return
     * @throws Exception 
     */
    private double g(double x) throws Exception {
        int degree = equation.get(equation.size()-1).power;
        double division = 0;
        for(int i=0; i<degree; i++) {
            division += getCoefficent(i)*Math.pow(x, i);
        }
        division /= -getCoefficent(degree);
        if(division < 0) {
            throw new Exception(notFoundMessage);
        }
        return Math.pow(division, 1.0/(float)degree);
    }
    
    private void printStep(double initialValue, double gp, double newP) {
        System.out.println(formatter.format(gp)+tab+
            formatter.format(initialValue)+tab+
            formatter.format(newP)+tab);
    }

    private void printHeaders() {
        System.out.println("x"+tab+tab+"g(x)"+tab+tab+"f(x)");
    }
}
