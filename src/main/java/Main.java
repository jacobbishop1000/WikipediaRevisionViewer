import domain.Editor;
import domain.WikipediaPage;
import exceptions.NetworkConnectionFailedException;
import exceptions.PageNotFoundException;
import exceptions.ParameterIsNotJsonStringException;
import utils.ParseUtils;
import utils.RequestUtils;

import java.io.*;
import java.net.*;

import java.text.ParseException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("\nWelcome to Wikipedia Revision Viewer!");
            while (true){
                System.out.println("\n\nPlease enter the name of the article you want to view or type 'q' to quit: ");
                String articleName = br.readLine();
                if (articleName.contentEquals("q")){
                    break;
                }
                String result = RequestUtils.establishConnection(articleName);
                WikipediaPage page = ParseUtils.parseJsonToWikipediaPageManual(result);
                if(page.getRedirect() != null){
                    System.out.println("\n-->Your search term " + "'" + articleName + "'" +  " was redirected to " + "'" + page.getPageTitle() + "'<--");
                }
                System.out.println("\nWhat format do you want your page info to come back in?\n");
                System.out.println("1) Changelog Viewer");
                System.out.println("2) Editor List Viewer\n");
                String viewerChoice = br.readLine().toLowerCase();
                if(viewerChoice.contentEquals("q")){
                    break;
                }else if (viewerChoice.contentEquals("1") || viewerChoice.contentEquals("changelog viewer") || viewerChoice.contentEquals("changelog")){
                    System.out.println("Most Recent");
                    for (int i = 0; i < page.getPageEditorsByTimestamp().size(); i++) {
                        System.out.println("**************");
                        System.out.println("User: " + page.getPageEditorsByTimestamp().get(i).getUser());
                        System.out.println("Date edited: " + page.getPageEditorsByTimestamp().get(i).getTimestamp());
                    }
                }else if (viewerChoice.contentEquals("2") || viewerChoice.contentEquals("editor list viewer") || viewerChoice.contentEquals("editor list")){
                    List<Editor> editors = page.getPageEditorsByEdits();
                    while (editors.size() > 0){
                        //First, find the biggest amount of Edits.
                        Editor maxEditor = editors.get(0);
                        for (int i = 1; i < editors.size(); i++) {
                            if(editors.get(i).getNumEdits() > maxEditor.getNumEdits()){
                                maxEditor = editors.get(i);
                            }
                        }
                        //Next, print that Editor
                        System.out.println("**************");
                        System.out.println("User: " + maxEditor.getUser());
                        System.out.println("Number of Edits: " + maxEditor.getNumEdits());
                        System.out.println("Timestamp: " + maxEditor.getTimestamp());
                        //Finally, remove that Editor from editors
                        editors.remove(maxEditor);
                    }

                }else{
                    System.out.println("Error: not a valid choice.");
                }
            }
        } catch (ParseException e){
            System.out.println("\nError: Parse Exception");
        } catch (PageNotFoundException e) {
            System.out.println("\nError: PageNotFound Exception");
        } catch (IOException e) {
            System.out.println("\nIO Exception");
        } catch (NetworkConnectionFailedException e) {
            System.out.println("\nError: NetworkConnectionFailed Exception");
        }
    }
}
