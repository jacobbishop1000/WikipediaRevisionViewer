package projectUIs;

import domain.Editor;
import domain.WikipediaPage;
import exceptions.NetworkConnectionFailedException;
import exceptions.PageNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import utils.RequestUtils;
import utils.ParseUtils;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class WikipediaPageUI extends Application implements EventHandler {

    String articleName;
    WikipediaPage wikiPage;
    Label loggingArea = new Label();
    TextField articleNameField = new TextField("Obama");
    GridPane byEditsGridPane = new GridPane();
    GridPane byTimestampGridPane = new GridPane();
    GridPane ConsoleGridPane = new GridPane();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Wikipedia Page Editors Viewer");
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double width = bounds.getWidth();
        double height = bounds.getHeight();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(width);
        stage.setHeight(height);

        articleNameField.setPrefSize(width/2.5, height/15);
        ConsoleGridPane.add(articleNameField, 1, 0);

        Button searchArticleButton = new Button("Search Article");
        searchArticleButton.setOnAction(this);
        searchArticleButton.setPrefSize(width/2.5, height/15);
        ConsoleGridPane.add(searchArticleButton, 1, 1);

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(value -> {
            System.exit(0);
        });
        quitButton.setPrefSize(width/2.5, height/15);
        ConsoleGridPane.add(quitButton, 1, 2);

        loggingArea.setWrapText(true);
        loggingArea.setPrefSize(width/2.5, height/0.7);
        loggingArea.setPadding(new Insets(3));
        loggingArea.setAlignment(Pos.TOP_LEFT);
        ConsoleGridPane.add(loggingArea, 1, 3);

        byEditsGridPane.setPrefSize(width/1.5, height);
        byTimestampGridPane.setPrefSize(width/1.5, height);

        byTimestampGridPane.setAlignment(Pos.CENTER);
        byEditsGridPane.setAlignment(Pos.CENTER);

        ConsoleGridPane.add(byEditsGridPane, 0, 0, 1, ConsoleGridPane.getRowCount());
        ConsoleGridPane.add(byTimestampGridPane, 2, 0, 1, ConsoleGridPane.getRowCount());

        Scene Console = new Scene(ConsoleGridPane, bounds.getMinX(), bounds.getMinY());
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
            clearEditorGridPane(byEditsGridPane);
            clearEditorGridPane(byTimestampGridPane);
            loggingArea.setText("Error: IO Exception");
        } catch (NetworkConnectionFailedException e) {
            clearEditorGridPane(byEditsGridPane);
            clearEditorGridPane(byTimestampGridPane);
            loggingArea.setText("Error: Network Connection Failed");
        } catch (ParseException e) {
            clearEditorGridPane(byEditsGridPane);
            clearEditorGridPane(byTimestampGridPane);
            loggingArea.setText("Error: Parsing Json to String Failed");
        } catch (PageNotFoundException e) {
            clearEditorGridPane(byEditsGridPane);
            clearEditorGridPane(byTimestampGridPane);
            loggingArea.setText("Error: Page Doesn't Exist");
        }
        byEditsGridPane.setGridLinesVisible(true);
        byTimestampGridPane.setGridLinesVisible(true);

    }

    private static void setByEditsGridPane(GridPane gridPane, WikipediaPage page) {
        int x = 0;
        int y = 0;
        List<Editor> list = page.getPageEditorsByEdits();
        clearEditorGridPane(gridPane);
        while (list.size() > 0) {
            //First, find the biggest amount of Edits.
            Editor maxEditor = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getNumEdits() > maxEditor.getNumEdits()) {
                    maxEditor = list.get(i);
                }
            }
            //Next, make label.
            String user = maxEditor.getUser();
            int editNum = maxEditor.getNumEdits();
            String editDate = maxEditor.getTimestamp().toString();
            String editNumString = Integer.toString(editNum);
            String gridPaneStringObject = "User: " + user + "\nNumber of Edits: " + editNumString + "\n" + editDate;
            Label editorsLabel = new Label(gridPaneStringObject);
            editorsLabel.setPadding(new Insets(3));
            gridPane.add(editorsLabel, x, y);
            y++;
            if (y == 10){
                y = 0;
                x++;
            }
            //Finally, remove it.
            list.remove(maxEditor);
        }
        gridPane.setGridLinesVisible(true);
    }

    private static void setByTimestampGridPane(GridPane gridPane, WikipediaPage page) {
        List<Editor> list = page.getPageEditorsByTimestamp();
        int counter = 0;
        clearEditorGridPane(gridPane);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                String user = list.get(counter).getUser();
                String timestamp = list.get(counter).getTimestamp().toString();
                String gridPaneStringObject = user + "\n" + timestamp + "\n ";
                Label newLabel = new Label(gridPaneStringObject);
                newLabel.setPadding(new Insets(3));
                gridPane.add(newLabel, i, j);
                counter++;
            }
        }
        gridPane.setGridLinesVisible(true);
    }

    public static void clearEditorGridPane(GridPane gridPane){
        //Roundabout way because .clear() has awkward properties for this purpose.
        gridPane.setGridLinesVisible(false);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
    }
}