package utils;

import com.google.gson.Gson;
import domain.WikipediaPage;
import exceptions.ParameterIsNotJsonStringException;

public class ParseUtils {

    public static WikipediaPage parseJsonToWikipediaPageDirect(String jsonString) throws ParameterIsNotJsonStringException {
        if (!jsonString.startsWith("{")){
            throw new ParameterIsNotJsonStringException();
        }
        Gson tempGson = new Gson();
        WikipediaPage wikiPage = tempGson.fromJson(jsonString, WikipediaPage.class);
        return wikiPage;
    }

}
