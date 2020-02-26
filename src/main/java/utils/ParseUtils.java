package utils;

import com.google.gson.*;
import domain.Editor;
import domain.Redirect;
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
        int length = pagesObject.keySet().toString().length();
        String pageIdObjectName = pagesObject.keySet().toString().substring(1, length - 1);
        JsonObject pageidObject = pagesObject.getAsJsonObject(pageIdObjectName);
        var pageID = pageidObject.getAsJsonPrimitive("pageid").getAsInt();
        var pageTitle = pageidObject.getAsJsonPrimitive("title").getAsString();
        JsonArray revisionsObject = pageidObject.getAsJsonArray("revisions");

        List<Editor> pageEditors = new ArrayList<>();
        for (int i = 0; i<revisionsObject.size(); i++){
            JsonObject object = revisionsObject.get(i).getAsJsonObject();
            Gson tempGson = new Gson();
            Editor editor = tempGson.fromJson(object, Editor.class);
            pageEditors.add(editor);
        }
        if(queryObject.keySet().contains("redirects")){
            JsonArray redirectsArray = queryObject.getAsJsonArray("redirects");
            JsonObject redirectsObject = redirectsArray.get(0).getAsJsonObject();
            Gson tempGson = new Gson();
            Redirect pageRedirect = tempGson.fromJson(redirectsObject, Redirect.class);
            return new WikipediaPage(pageTitle, pageID, pageEditors, pageRedirect);
        }
        return new WikipediaPage(pageTitle, pageID, pageEditors);
    }
}
