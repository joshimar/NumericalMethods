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
        System.out.println();
        System.out.println("gp"+tab+tab+"p0"+tab+tab+"evaluación");
        return runFixedPoint();
    }

    @Override
    public String name() {
        return "Fixed Point";
    }
    
    private double runFixedPoint() throws Exception {
        currentIteration = 0;
        while(currentIteration++ < iterationsLimit) {
            double gp = g(initialValue);
            double newP = gp-initialValue;
            
            printStep(initialValue, gp, newP);
           
            if(Math.abs(newP) < epsilon) {
                return gp;
            }
            initialValue=gp;
        }
        throw new Exception(notFoundMessage);
    }
    
    // Función iteradora
    private double g(double x) throws Exception {
        int degree = equation.get(equation.size()-1).power;
        if(degree != 2 && degree != 3) {
            throw new Exception("g(x) for f(x)="+toString()+" is not currently supported by "+name()+" please implement its g(x) manually.");
        }
        switch(degree) {
            case 2: return g2(x);
            case 3: return g3(x);
            default: return customG(x);
        }
    }
    
    /**
     * g(x) for an equation of third degree.
     * 
     * Solution:
     * 
     * C0 + C1x + C2x^2 + C3x^3
     * x^2(C3x + C2) = -(C0 + C1x)
     * x^2 = -(C0 + C1x) / (C3x + C2)
     * x = squaredRoot(-(C0 + C1x) / (C3x + C2)) 
     * g(x) = squaredRoot(-(C0 + C1x) / (C3x + C2)) 
     * 
     * @param x value of of independent variable to perform the evaluation.
     * @return Result of the function evaluated for the given x value.
     * @throws Exception 
     */
    private double g3(double x) throws Exception {
        double C0 = getCoefficent(0);
        double C1 = getCoefficent(1);
        double C2 = getCoefficent(2);
        double C3 = getCoefficent(3);
        
        double numerator = -(C0+ C1*x);
        double denominator = C3*x + C2;
        
        if(C3 == 0 || denominator == 0) { // C3 cannot be zero since we are using that term (C3x^3) to find the value of g(x).
            throw new Exception(notFoundMessage);
        }
        
        return Math.sqrt(numerator / denominator);
    }
    
    /**
     * g(x) for an equation of second degree.
     * 
     * Solution:
     * 
     * C0 + C1x + C2x^2
     * x(C2x + C1) = -C0
     * x = -C0 / (C2x + C1)
     * g(x) = -C0 / (C2x + C1)
     * 
     * @param x value of of independent variable to perform the evaluation.
     * @return Result of the function evaluated for the given x value.
     * @throws Exception 
     */
    private double g2(double x) throws Exception {
        double C0 = getCoefficent(0);
        double C1 = getCoefficent(1);
        double C2 = getCoefficent(2);
        
        double numerator = -C0;
        double denominator = C2*x + C1;
        
        if(C2 == 0 || denominator == 0) { // C2 cannot be zero since we are using that term (C2x^2) to find the value of g(x).
            throw new Exception(notFoundMessage);
        }
        
        return Math.sqrt(numerator / denominator);
    }

    private double customG(double x) {
        throw new UnsupportedOperationException("This g(x) is not supported yet. Please write the implementation.");
    }
    
    private void printStep(double initialValue, double gp, double newP) {
        System.out.println(formatter.format(gp)+tab+
            formatter.format(initialValue)+tab+
            formatter.format(newP)+tab);
    }
}
