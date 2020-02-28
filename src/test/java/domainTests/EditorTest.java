package domainTests;

import domain.Editor;
import org.junit.jupiter.api.Test;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class EditorTest {

    @Test
    void getUserMethodTest() throws ParseException {
        Editor editor = new Editor("Joe", "2019-11-28 06:58:48", 1);
        assertEquals("Joe", editor.getUser());
    }

    @Test
    void getTimestampMethodTest() throws ParseException {
        Editor editor = new Editor("Joe", "2019-11-28 06:58:48", 1);
        assertEquals("2019-11-28 06:58:48", editor.getTimestamp());
    }

    @Test
    void getNumEditsMethodTest() throws ParseException {
        Editor editor = new Editor("Joe", "2019-11-28 06:58:48", 1);
        assertEquals(1, editor.getNumEdits());
    }
}