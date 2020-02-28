package utilsTest;

import exceptions.PageNotFoundException;
import exceptions.ParameterIsNotJsonStringException;
import org.junit.jupiter.api.Test;
import utils.ParseUtils;

import static org.junit.jupiter.api.Assertions.*;

class ParseUtilsTest {

    @Test
    void parseJsonToWikipediaPageDirectButNotJsonString() throws ParameterIsNotJsonStringException{
        String sampleString = "__{something that would normally be a json string}";
        assertThrows(ParameterIsNotJsonStringException.class, () -> {
            var resultingWikipediaPage = ParseUtils.parseJsonToWikipediaPageManual(sampleString);
        });
    }

    @Test
    void parseJsonToWikipediaPageManualButNotJsonString() throws  ParameterIsNotJsonStringException{
        String sampleString = "__{something that would normally be a json string}";
        assertThrows(ParameterIsNotJsonStringException.class, () -> {
            var resultingWikipediaPage = ParseUtils.parseJsonToWikipediaPageManual(sampleString);
        });
    }

}