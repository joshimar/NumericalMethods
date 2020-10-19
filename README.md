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

Note.- The only types of equations supported are polynomials of the form **C0 + C1x + C2x^2 ... Cnx^n** where **n is the degree** of the equation and **C0 is the constant**, with integer exponents. Also Fixed Point calculates iterator function g(x) automatically after generalizing the expression using the degree of the function. Therefore there's no need to implement g(x) unless extending support to other type of equations that are not polynomials.

Supported properties

property | description | example(s)
------------ | ------------- | -------------
method | algorithm used to compute the root | False Position, Bisection, Newton Raphson, Fixed Point 
polynomial | expression representing the input equation | x^3 + x^2 - 10
epsilon | error tolerance | 0.0001, 1e-4
iterations_limit | desired limit of iterations | 20
initial_value | starting value of x | -0.7
lower_limit | lower limit of x | -3.5
upper_limit | uppper limit of x | 10
