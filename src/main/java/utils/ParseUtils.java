package utils;

import com.google.gson.*;
import domain.Editor;
import domain.Redirect;
import domain.WikipediaPage;
import exceptions.PageNotFoundException;
import exceptions.ParameterIsNotJsonStringException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParseUtils {

    public static WikipediaPage parseJsonToWikipediaPageDirect(String jsonString) throws ParameterIsNotJsonStringException {
        if (!jsonString.startsWith("{")){
            throw new ParameterIsNotJsonStringException();
        }
        Gson tempGson = new Gson();
        return tempGson.fromJson(jsonString, WikipediaPage.class);
    }

    public static WikipediaPage parseJsonToWikipediaPageManual(String jsonString) throws ParseException, PageNotFoundException {
        if(jsonString.charAt(2) == 'b'){
            throw new PageNotFoundException();
        }
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

        List<Editor> pageEditorsTimestamp = new ArrayList<>();
        List<Editor> pageEditorsEdits;
        for (int i = 0; i < revisionsObject.size(); i++) {
            JsonObject object = revisionsObject.get(i).getAsJsonObject();
            var aUser = object.getAsJsonPrimitive("user").getAsString();
            var aTimestamp = object.getAsJsonPrimitive("timestamp").getAsString();
            int numEdits = 1;
            Editor editor = new Editor(aUser, aTimestamp, numEdits);
            pageEditorsTimestamp.add(editor);

        }
        pageEditorsEdits = pageEditorsTimestamp;
        List<Editor> pageEditorsEdits2 = new ArrayList<>();
        pageEditorsEdits2.add(pageEditorsEdits.get(0));

        for (int i = 1; i < pageEditorsEdits.size(); i++) {
            Editor e = pageEditorsEdits.get(i);
            pageEditorsEdits2.add(e);
            innerloop:
            for (int j = 0; j < pageEditorsEdits2.size()-1; j++) {
                Editor e2 = pageEditorsEdits2.get(j);
                if (e.getUser().contentEquals(e2.getUser())){
                    pageEditorsEdits2.get(j).addNumEdits();
                    pageEditorsEdits2.remove(e);
                    break innerloop;
                }
            }
        }

        if(queryObject.keySet().contains("redirects")){
            JsonArray redirectsArray = queryObject.getAsJsonArray("redirects");
            JsonObject redirectsObject = redirectsArray.get(0).getAsJsonObject();
            Gson tempGson = new Gson();
            Redirect pageRedirect = tempGson.fromJson(redirectsObject, Redirect.class);
            return new WikipediaPage(pageTitle, pageID, pageEditorsTimestamp, pageEditorsEdits2, pageRedirect);
        }
        return new WikipediaPage(pageTitle, pageID, pageEditorsTimestamp, pageEditorsEdits2);
    }
}
