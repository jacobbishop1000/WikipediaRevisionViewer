import domain.WikipediaPage;
import exceptions.ParameterIsNotJsonStringException;
import utils.ParseUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Welcome to Wikipedia Revision Viewer! Type 'q' at any time to quit.");
            while (true){
                System.out.println("Please enter the name of the article you want to view or type 'q' to quit: ");
                String articleName = br.readLine();
                if (articleName == "q"){
                    break; //Doesn't work
                }
                URL url = new URL("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles="
                        + articleName
                        +"&rvprop=timestamp|user&rvlimit=30&redirects");
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
                if(viewerChoice == "q"){
                    break;
                }else if (viewerChoice == "1" || viewerChoice == "changelog viewer" || viewerChoice == "changelog"){
                    //stuff (need to finish ChangeLogViewer class!)
                }else if (viewerChoice == "2" || viewerChoice == "editor list viewer" || viewerChoice == "editor list"){
                    //stuff (need to finish EditorListViewer class!)
                }else{
                    System.out.println("Error: not a valid choice.");
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception");
        }catch (ParameterIsNotJsonStringException e){
            System.out.println("Parameter Is Not Json String Exception");
        }
    }
}
