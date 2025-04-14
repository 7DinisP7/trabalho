import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandInterpreterTest {
    @Test
    public void testAddBounds() {
        CommandInterpreter ci = new CommandInterpreter();
        ci.bounds(38848911991L, -9497899738L, 38461247511L, -8885274394L, "Lisbon Area");
        // Validate output manually or mock System.out to capture the output
    }

    @Test
    public void testAddEatingService() {
        CommandInterpreter ci = new CommandInterpreter();
        ci.bounds(38848911991L, -9497899738L, 38461247511L, -8885274394L, "Lisbon Area");
        ci.addEatingService("Akatsuki Caparica", 38639378483L, -9234400975L, 8);
        assertEquals(1, ci.services.size());
        assertTrue(ci.services.containsKey("Akatsuki Caparica"));
    }

    @Test
    public void testAddStudent() {
        CommandInterpreter ci = new CommandInterpreter();
        ci.bounds(38848911991L, -9497899738L, 38461247511L, -8885274394L, "Lisbon Area");
        ci.addLodgingService("Frausto da Silva Residency", 38639378483L, -9234400975L, 251);
        ci.addStudent("bookish", "Neil Perry", "Frausto da Silva Residency");
        assertEquals(1, ci.students.size());
        assertTrue(ci.students.containsKey("Neil Perry"));
    }
}