# NumericalMethods
Java examples of Numerical Methods to calculate the root of a function. 

The available algorithms are:

1. Newton Raphson
2. Bisection (Binary Search)
3. Fixed Point
4. False Position

To run the examples, you must specify the algorithm and its corresponding input values in a properties file including the equation.
Under examples directory, you'll find a directory per algorithm with a script that runs the corresponding examples. 
If you want to run the algorithms with another polynomial, please create your own properties file with the necessary parameters for the given algorithm and pass it to the jar when running it.

Note.- The only types of equations supported are polynomials of the form C0 + C1x + C2x^2 ... with integer exponents. Also Fixed Point calculates iterator function g(x) automatically after generalizing the expression using the degree of the function. Therefore there's no need to implement g(x) unless extending support to other type of equations that are not polynomials.
