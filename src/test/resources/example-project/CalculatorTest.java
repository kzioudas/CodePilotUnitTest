import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    @Test
    void testdivide_rainy_day() {
        // Instantiate the class
        Calculator instance = new Calculator();

        // Prepare method parameters
        double a = "";
        double b = "";

        // Call the method
        double result = instance.divide(a, b);

        // Assertions
        assertThrows(ArithmeticException.class, () -> instance.a(a), "Expected a to throw ArithmeticException");
        assertThrows(ArithmeticException.class, () -> instance.b(b), "Expected b to throw ArithmeticException");
    }

}
