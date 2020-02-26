package utils;

import com.google.gson.*;
import domain.Edit;
import domain.WikipediaPage;
import exceptions.ParameterIsNotJsonStringException;
import java.util.ArrayList;
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
        for (int i = 0; i<revisionsObject.size(); i++){
            JsonObject object = revisionsObject.get(i).getAsJsonObject();
            String user = object.get("user").getAsString();
            String timeStamp = object.get("timestamp").getAsString();
            Gson tempGson = new Gson();
            Edit edit = tempGson.fromJson(object, Edit.class);
            pageEdits.add(edit);
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
