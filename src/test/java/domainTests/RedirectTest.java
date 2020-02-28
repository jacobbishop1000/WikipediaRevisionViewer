package domainTests;

import domain.Redirect;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedirectTest {

    @Test
    void getFrom() {
        Redirect redirect = new Redirect("Obama", "Barack Obama");
        assertEquals("Obama", redirect.getFrom());
    }

    @Test
    void getTo() {
        Redirect redirect = new Redirect("Obama", "Barack Obama");
        assertEquals("Barack Obama", redirect.getTo());
    }
}