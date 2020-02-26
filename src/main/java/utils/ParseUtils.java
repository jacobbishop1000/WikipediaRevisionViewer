package utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Edit;
import domain.WikipediaPage;
import exceptions.ParameterIsNotJsonStringException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ParseUtils {

    public static WikipediaPage parseJsonToWikipediaPageDirect(String jsonString) throws ParameterIsNotJsonStringException{
        if (!jsonString.startsWith("{")){
            throw new ParameterIsNotJsonStringException();
        }
        Gson tempGson = new Gson();
        return tempGson.fromJson(jsonString, WikipediaPage.class);
    }

    public static WikipediaPage parseJsonToWikipediaPageManual(String jsonString) throws ParameterIsNotJsonStringException{ //What exception does this throw?
        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(jsonString);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonObject queryObject = rootObject.getAsJsonObject("query");
        JsonObject pagesObject = queryObject.getAsJsonObject("pages");
        JsonObject pageidObject = pagesObject.getAsJsonObject(getPageId(pagesObject));
        var pageTitle = pageidObject.getAsJsonPrimitive("title").getAsString();
        var pageID = pageidObject.getAsJsonPrimitive("pageid").getAsInt();
        JsonObject revisionsObject = pageidObject.getAsJsonObject("revisions");
        var revisions = revisionsObject.getAsJsonPrimitive("revisions").getAsString();

        List<Edit> pageEdits = new ArrayList<>();
        Gson tempGson = new Gson();
        while (revisions != null){
            var pageEdit = tempGson.fromJson(revisionsObject, Edit.class);
            pageEdits.add(pageEdit);
        }
        return new WikipediaPage(pageTitle, pageID, pageEdits);
    }

    private static String getPageId(JsonObject jObject) {
        String jObjectString = jObject.getAsString();
        String pageId = "";
        for (int i = 3; i < jObjectString.length(); i++) {
            if (jObjectString.charAt(i) != '"') {
                pageId += jObjectString.charAt(i);
            }
        }
        return pageId;
    }


}
