import domain.Editor;
import domain.WikipediaPage;
import exceptions.NetworkConnectionFailedException;
import exceptions.ParameterIsNotJsonStringException;
import utils.ParseUtils;
import utils.RequestUtils;

import java.io.*;
import java.net.*;

import java.util.*;

public class Main {

    public static void main(String[] args) throws NetworkConnectionFailedException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Welcome to Wikipedia Revision Viewer!");
            while (true){
                System.out.println("Please enter the name of the article you want to view or type 'q' to quit: ");
                String articleName = br.readLine();
                if (articleName.contentEquals("q")){
                    break;
                }
                URL url = new URL("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles="
                        + articleName
                        +"&rvprop=timestamp|user&rvlimit=30&redirects"); //This needs to throw a PageNotFoundException
                RequestUtils urlRequest = new RequestUtils();
                URLConnection connection = url.openConnection(); //This needs to throw a NetworkConnectionFailedException
                connection.setRequestProperty("User-Agent",
                        "Revision Tracker/0.1 (http://www.cs.bsu.edu/; jbishop@bsu.edu)");

                InputStream in = connection.getInputStream();
                Scanner scanner = new Scanner(in);
                String result = scanner.nextLine(); //Originally scanner.next()
                WikipediaPage page = ParseUtils.parseJsonToWikipediaPageManual(result);
                System.out.println("What format do you want your page info to come back in?\n");
                System.out.println("1) Changelog Viewer");
                System.out.println("2) Editor List Viewer\n");
                String viewerChoice = br.readLine().toLowerCase();
                if(page.getRedirect() != null){
                    System.out.println("Your search term " + "'" + articleName + "'" +  " was redirected to " + "'" + page.getPageTitle() + "'");
                }
                if(viewerChoice.contentEquals("q")){
                    break;
                }else if (viewerChoice.contentEquals("1") || viewerChoice.contentEquals("changelog viewer") || viewerChoice.contentEquals("changelog")){
                    System.out.println("Most Recent");
                    for (int i = 0; i < page.getPageEditors().size(); i++) {
                        System.out.println("**************");
                        System.out.println("User: " + page.getPageEditors().get(i).getUser());
                        System.out.println("Date edited: " + page.getPageEditors().get(i).getTimestamp());
                    }
                }else if (viewerChoice.contentEquals("2") || viewerChoice.contentEquals("editor list viewer") || viewerChoice.contentEquals("editor list")){
                    List<Editor> editors = page.getPageEditors();
                    while (editors.size() > 0){
                        Editor maxEditor = editors.get(0);
                        int maxEditorIndex = editors.indexOf(maxEditor);
                        for (int i = 1; i < editors.size(); i++){
                            if (editors.get(i).getNumEdits() > maxEditor.getNumEdits()){
                                maxEditor = editors.get(i);
                                maxEditorIndex = editors.indexOf(maxEditor);
                            }
                            System.out.println("********************");
                            System.out.println("User: " + editors.get(i).getUser());
                            System.out.println("Number of Edits: " + editors.get(i).getNumEdits());
                            editors.remove(maxEditorIndex);
                        }
                    }
                }else{
                    System.out.println("Error: not a valid choice.");
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (ParameterIsNotJsonStringException e){
            System.out.println("Parameter Is Not Json String Exception");
        }
    }
}
