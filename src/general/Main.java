package general;

import static util.Constants.*;
import algorithms.Bisection;
import algorithms.NewtonRaphson;
import algorithms.FixedPoint;
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
    private Map<String, NumericMethod> instances;
    // Algorithms available:
    private List<Class<? extends NumericMethod>> algorithms = Arrays.asList(NewtonRaphson.class, 
                    Bisection.class,
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
            System.out.println("The root found is: "+result);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private double run() throws Exception {
        String targetAlgorithm = reader.getString(METHOD_PROPERTY);
        NumericMethod algorithm = instances.get(targetAlgorithm);
        System.out.println(algorithm.name());
        String expression = reader.getString(POLYNOMIAL_PROPERTY);
        algorithm.readExpression(expression);
        algorithm.readIterationsLimit(reader);
        algorithm.readInputValues(reader);
        return algorithm.compute();
    }
    
    private void loadInstances() {
        instances = new HashMap<>();
        for(Class<? extends NumericMethod> algorithm : algorithms) {
            try {
                NumericMethod instance = algorithm.newInstance();
                instances.put(instance.name(), instance);
            } catch (Exception e) { 
                System.out.println("Algorithm "+algorithm.getSimpleName()+" could not be loaded. "+e.getMessage());
            } 
        }
    }
}