package utilsTest;

import exceptions.PageNotFoundException;
import exceptions.ParameterIsNotJsonStringException;
import utils.ParseUtils;

import static org.junit.jupiter.api.Assertions.*;

class ParseUtilsTest {

    @org.junit.jupiter.api.Test
    void parseJsonToWikipediaPageDirectButNotJsonString() throws ParameterIsNotJsonStringException, PageNotFoundException {
        assertEquals(null, ParseUtils.parseJsonToWikipediaPageDirect("not a json string!"));
    }

    @org.junit.jupiter.api.Test
    void parseJsonToWikipediaPageManual() {
    }
}