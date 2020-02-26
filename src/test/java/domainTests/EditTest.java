package domainTests;

import domain.Edit;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.util.Date;

class EditTest {

    @Test
    void parseDateTest() throws ParseException {
        Edit anEdit = new Edit("joe", "2019-11-28T06:58:48Z");
        //assertEquals(, ); //Confused on how to test.
    }
}