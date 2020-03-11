package projectUIs;

import domain.Editor;
import domain.WikipediaPage;
import exceptions.NetworkConnectionFailedException;
import exceptions.PageNotFoundException;
import utils.RequestUtils;
import utils.ParseUtils;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class WikipediaPageUI extends Application implements EventHandler {

    String articleName;
    WikipediaPage wikiPage;
    Label loggingArea = new Label("This is a log");
    TextField articleNameField = new TextField("Enter Article Name Here!");
    GridPane byEditsGridPane = new GridPane();
    GridPane byTimestampGridPane = new GridPane();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Wikipedia Page Viewer");

        GridPane ConsoleGridPane = new GridPane();

        articleNameField.setPrefSize(200, 50);
        ConsoleGridPane.add(articleNameField, 1, 0);

        Button searchArticleButton = new Button("Search Article");
        searchArticleButton.setOnAction(this);
        searchArticleButton.setPrefSize(200, 50);
        ConsoleGridPane.add(searchArticleButton, 1, 1);

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(value -> {
            System.exit(0);
        });
        quitButton.setPrefSize(200, 50);
        ConsoleGridPane.add(quitButton, 1, 2);

        loggingArea.setWrapText(true);
        loggingArea.setPrefSize(200, 150);
        ConsoleGridPane.add(loggingArea, 1, 3);

        byEditsGridPane.setPrefSize(860, 1080);
        byTimestampGridPane.setPrefSize(860, 1080);

        ConsoleGridPane.add(byEditsGridPane, 0, 0, 1, 3);
        ConsoleGridPane.add(byTimestampGridPane, 2, 0, 1, 3);


        Scene Console = new Scene(ConsoleGridPane, 1920, 1080);
        stage.setScene(Console);
        stage.show();
    }

    @Override
    public void handle(Event event) {
        String result;
        articleName = articleNameField.getText();
        try {
            result = RequestUtils.establishConnection(articleName);
            wikiPage = ParseUtils.parseJsonToWikipediaPageManual(result);
            setByEditsGridPane(byEditsGridPane, wikiPage);
            setByTimestampGridPane(byTimestampGridPane, wikiPage);
            loggingArea.setText("");
            if(wikiPage.getRedirect() != null) {
                loggingArea.setText("Your search term " + "'" + articleName + "'" +  " was redirected to " + "'" + wikiPage.getPageTitle() + "'");
            }
        } catch (IOException e) {
            loggingArea.setText("Error: IO Exception");
        } catch (NetworkConnectionFailedException e) {
            loggingArea.setText("Error: Network Connection Failed");
        } catch (ParseException e) {
            loggingArea.setText("Error: Parsing Json to String Failed");
        } catch (PageNotFoundException e) {
            loggingArea.setText("Error: Page Doesn't Exist");
        }
    }

    public static void setByEditsGridPane(GridPane gridPane, WikipediaPage page) {
        List<Editor> list = page.getPageEditorsByEdits();
        int counter = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 9; j++) {
                String user = list.get(counter).getUser();
                int editNum = list.get(counter).getNumEdits();
                String editNumString = Integer.toString(editNum);
                String gridPaneStringObject = user + "\n" + editNumString;
                Label newLabel = new Label(gridPaneStringObject);
                gridPane.add(newLabel, i, j);
                counter++;
            }
        }
    }

    public static void setByTimestampGridPane(GridPane gridPane, WikipediaPage page) {
        List<Editor> list = page.getPageEditorsByTimestamp();
        int counter = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 9; j++) {
                String user = list.get(counter).getUser();
                String timestamp = list.get(counter).getTimestamp().toString();
                String gridPaneStringObject = user + "\n" + timestamp;
                Label newLabel = new Label(gridPaneStringObject);
                gridPane.add(newLabel, i, j);
                counter++;
            }
        }
    }
}