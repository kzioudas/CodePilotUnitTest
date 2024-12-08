package example;

public class Calculator {
    private int precision;

    public Calculator() {
        this.precision = 2;
    }

    public Calculator(int precision) {
        this.precision = precision;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }
}
