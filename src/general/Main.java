package general;

import static util.Constants.*;
import algorithms.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.PropertiesReader;

/**
 *
 * @author josh.isc@hotmail.com
 */
public class Main {

    // Map: algorithm-name -> algoritm instance
    private Map<String, NumericalMethod> instances;
    // Algorithms available:
    private List<Class<? extends NumericalMethod>> algorithms = Arrays.asList(
                    NewtonRaphson.class, 
                    Bisection.class,
                    FalsePosition.class,
                    FixedPoint.class);
    
    private PropertiesReader reader;
    
    public Main(String propertiesFile) throws Exception {
        loadInstances();
        reader = new PropertiesReader(propertiesFile);
        reader.loadProperties();
    }
    
    public static void main(String[] args) {
        if(args.length < 1) {
            throw new IllegalArgumentException("You must specify the properties file.");
        }
        try {
            Main main = new Main(args[0]);
            double result = main.run();
            System.out.println("Root found at x="+result);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private double run() throws Exception {
        String targetAlgorithm = reader.getString(METHOD_PROPERTY).replace(" ", "").toLowerCase();
        NumericalMethod algorithm = instances.get(targetAlgorithm);
        if(algorithm == null) {
            throw new Exception("Algorithm "+reader.getString(METHOD_PROPERTY).trim()+" not supported.");
        }
        String expression = reader.getString(POLYNOMIAL_PROPERTY);
        algorithm.readExpression(expression);
        algorithm.readIterationsLimit(reader);
        algorithm.readInputValues(reader);
        System.out.println(algorithm.toString());
        return algorithm.compute();
    }
    
    private void loadInstances() {
        instances = new HashMap<>();
        for(Class<? extends NumericalMethod> algorithm : algorithms) {
            try {
                NumericalMethod instance = algorithm.newInstance();
                instances.put(instance.name().replace(" ", "").toLowerCase(), instance);
            } catch (Exception e) { 
                System.out.println("Algorithm "+algorithm.getSimpleName()+" could not be loaded. "+e.getMessage());
            } 
        }
    }
}