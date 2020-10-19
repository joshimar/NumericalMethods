package general;

import util.Term;
import static util.Constants.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.PropertiesReader;

/**
 *
 * @author josh.isc@hotmail.com
 */
public abstract class NumericalMethod {
    private String expression;
    private Map<Integer, Double> coefficents; // exponent -> coefficent
    protected List<Term> equation;
    protected int iterationsLimit;
    protected int currentIteration;
    
    public abstract void readInputValues(PropertiesReader reader) throws Exception;
    public abstract double compute() throws Exception;
    public abstract String name();
    
    public void readExpression(String expression) throws Exception {
        equation = new ArrayList<>();
        this.expression = expression;
        if(!validateEquation()) {
            throw new Exception(expression+" is not a valid equation. \nThe expression must be in the form "+generateExample(3)+" where Cn are the constants/coefficients.");
        }
        equation.sort((a, b) -> {
            return (int) (a.power - b.power);
        });
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name()).append(" : ");
        if(equation == null) {
            return sb.toString();
        }
        
        for(int i=0; i<equation.size(); i++) {
            Term term = equation.get(i);
            
            if(term.coefficient == 0.0) {
                continue;
            }
            
            if(term.coefficient > 0.0 && i>0) {
                sb.append(POSITIVE).append(SPACE);
            }
            
            if(term.coefficient != 1.0) {
                sb.append(term.coefficient);
            }
            
            if(term.power > 0.0) {
                sb.append(Term.variable);
            }
            if(term.power > 1.0) {
                sb.append(POWER).append(term.power);
            }
            
            if(i<equation.size()-1) {
                sb.append(SPACE);
            }
        }
        return sb.toString().trim();
    }
    
    public double evaluateFunction(double value) throws Exception {
        if(equation == null) {
            throw new Exception("There's no currently an equation to evaluate, please read an expression first.");
        }
        
        double result = 0d;
        for(Term term : equation) {
            result += term.coefficient * Math.pow(value, term.power);
        }
        return result;
    }
    
    public double evaluateDerivative(double value) throws Exception {
        if(equation == null) {
            throw new Exception("There's no currently an equation to evaluate, please read an expression first.");
        }
        
        double result = 0d;
        for(Term term : equation) {
            result += term.coefficient * term.power * Math.pow(value, term.power-1);
        }
        return result;
    }
    
    public void readIterationsLimit(PropertiesReader reader) {
        iterationsLimit = Math.abs(reader.getInt(ITERATIONS_LIMIT_PROPERTY));
    }
    
    protected double getCoefficent(double exponent) {
        return coefficents.getOrDefault(exponent, 0d);
    }

    private String generateExample(int grado) {
        StringBuilder sb = new StringBuilder("C0 + ");
        for(int i=1; i<=grado; i++) {
            sb.append("C").append(i);
            sb.append("x^").append(i);
            if(i<grado) {
                sb.append(" + ");
            } 
        }
        
        return sb.toString();
    }
    
    private int getSign(char curr) {
        switch(curr) {
            case NEGATIVE: return -1;
            case POSITIVE: return 1;
            default: return 0;
        }
    }

    private String prepareToParse() {
        String result = expression.replace(" ", ""); // We don't need spaces
        if(!result.startsWith(NEGATIVE+"") && !result.startsWith(POSITIVE+"")) {
            result = "+"+result; // Make sure we start with a sign for proper parsing
        }
        return result.toLowerCase();
    }

    private boolean validateEquation() throws Exception {
        char[] expression = prepareToParse().toCharArray();
        
        for(int i=0; i<expression.length; i++) {
            char curr = expression[i];
            int sign = getSign(curr);
            
            if(sign == 0) {
                return false;
            }
            
            Term term = new Term();
            i = getTerm(i+1, expression, term)-1; // i will be forwarded to the position of the next sign
            
            if(i < 0) {
                return false;
            }
            
            term.coefficient*=sign;
            equation.add(term);
        }
      
        coefficents = new HashMap<>();
        for(Term term : equation) {
            coefficents.put(term.power, coefficents.getOrDefault(term.power, 0d) + term.coefficient); // Simplify polynomial
        }
        
        return true;
    }
    
    /**
     * Parse a term in the polynomial and load it in memory
     * @param i Position where the term to parse starts
     * @param expression The expression where we are parsing it from 
     * @param term Term that will fill the values into
     * @return Last position after finishing the parsing current term
     */
    private int getTerm(int i, char[] expression, Term term) {
        StringBuilder sb = new StringBuilder("");
        double coefficient = 1d;
        int power = 0;
        int size = expression.length;
        
        while(i<size && expression[i] != Term.variable) { // We need to read all the numbers before the next appearance of the variable
            sb.append(expression[i++]);
        }
        
        try {
            String tmp = sb.toString();
            if(tmp.length() > 0) { // Parse coefficient in case we have it
                coefficient = Double.parseDouble(tmp);
            }
        } catch (Exception e) { 
            return -1;
        }
        
        if(i>=size || getSign(expression[i]) != 0) { // Check if it's a constant
            term.coefficient = coefficient;
            term.power = 0;
            return i;
        }
        
        if(i>=size || expression[i++] != Term.variable) { // Make sure we have the variable
            return -1;
        }
        
        if(i>=size || expression[i] != POWER) { // There's no exponent
            term.coefficient = coefficient;
            term.power = 1;
            return i;
        }
        
        // We have the power character 
        sb = new StringBuilder("");
        while(++i<size && getSign(expression[i]) == 0) { // We need to read all the numbers before the next appearance of the sign
            sb.append(expression[i]);
        }
        
        try {
            String exponent = sb.toString();
            if(exponent.length() > 0) { // Parse power in case we have it
                power = (int) Double.parseDouble(exponent); // We are only working with integer powers
            } else {
                power = 1; // There's not an exponent
            }
        } catch (Exception e) { 
            return -1;
        }
        
        term.coefficient = coefficient;
        term.power = power;
        
        return i;
    }
}