package util;

/**
 *
 * @author josh.isc@hotmail.com
 */
public class Term {
    
    public static final char variable = 'x'; // x is usually used as the variable for the equation
    public double coefficient;
    public int power; // only supporting integer exponents
    
    public Term() {
        coefficient = 1;
        power = 0;
    }
    
    public Term(double coefficient, int power) {
        this.coefficient = coefficient;
        this.power = power;
    }
}
