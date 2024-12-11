import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testisAdult_happy_path() {
        // Instantiate the class
        User instance = new User(25);

        // Prepare method parameters

        // Call the method
        boolean result = instance.isAdult();

        // Assertions
        assertEquals(25, age, "Expected age to equal 25");
    }

    @Test
    void testisAdult_rainy_day() {
        // Instantiate the class
        User instance = new User(15);

        // Prepare method parameters

        // Call the method
        boolean result = instance.isAdult();

        // Assertions
        assertEquals(15, age, "Expected age to equal 15");
    }

    @Test
    void testupdateName_rainy_day() {
        // Instantiate the class
        User instance = new User("");

        // Prepare method parameters
        String name = "";

        // Call the method
        void result = instance.updateName(name);

        // Assertions
        assertThrows(IllegalArgumentException.class, () -> instance.name(name), "Expected name to throw IllegalArgumentException");
    }

}
