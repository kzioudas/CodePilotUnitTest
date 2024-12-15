import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentHandlerTest {

    @Test
    void testaddDocument_happy_path() {
        // Instantiate the class
        DocumentHandler instance = new DocumentHandler();

        // Prepare method parameters
        String document = ""Sample Document"";

        // Call the method
        void result = instance.addDocument(document);

        // Assertions
        assertEquals(""Sample Document"", document, "Expected document to equal ""Sample Document""");
    }

    @Test
    void testgetDocument_rainy_day() {
        // Instantiate the class
        DocumentHandler instance = new DocumentHandler();

        // Prepare method parameters
        int index = "";

        // Call the method
        String result = instance.getDocument(index);

        // Assertions
        assertThrows(IndexOutOfBoundsException.class, () -> instance.index(index), "Expected index to throw IndexOutOfBoundsException");
    }

    @Test
    void testaddDocument_rainy_day() {
        // Instantiate the class
        DocumentHandler instance = new DocumentHandler();

        // Prepare method parameters
        String document = "";

        // Call the method
        void result = instance.addDocument(document);

        // Assertions
        assertThrows(NullPointerException.class, () -> instance.document(document), "Expected document to throw NullPointerException");
    }

}
