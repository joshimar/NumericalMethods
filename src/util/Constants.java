package util;

import java.text.DecimalFormat;

/**
 *
 * @author josh.isc@hotmail.com
 */
public class Constants {
    public static final String notFoundMessage = "Root not found.";
    public static final DecimalFormat formatter = new DecimalFormat("00000.00000"); 
    public static final char tab='\t'; 
    public static final char NEW_LINE='\n';
    public static final char NEGATIVE = '-';
    public static final char POSITIVE = '+';
    public static final char POWER = '^';
    public static final char SPACE = ' ';
    public static final String EQUALS = "=";
    public static final String COMMENT = "#";
    public static final String POLYNOMIAL_PROPERTY = "polynomial";
    public static final String METHOD_PROPERTY = "method";
    public static final String INITIAL_VALUE_PROPERTY = "initial_value";
    public static final String ITERATIONS_LIMIT_PROPERTY = "iterations_limit";
    public static final String LOWER_LIMIT_PROPERTY = "lower_limit";
    public static final String UPPER_LIMIT_PROPERTY = "upper_limit";
    public static final String EPSILON_PROPERTY = "epsilon";
}
