package aurestApp.Tools;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Dialoge {
    private static final Stage stage = new Stage(StageStyle.UNDECORATED);
    private static Alert infoAnzeige;

    public static void DialogAnzeigeBox(String typ, String statusText) {
        if (infoAnzeige != null)
            infoAnzeige.close();

        Alert alert;
        switch (typ) {
            case "warnung":
                alert = new Alert(Alert.AlertType.WARNING);
                break;
            case "info":
                alert = new Alert(Alert.AlertType.INFORMATION);
                break;
            case "fehler":
            default:
                alert = new Alert(Alert.AlertType.ERROR);
                break;
        }

        alert.setHeaderText(null);
        //Text hinzufügen
        alert.setContentText(statusText);
        //Icon hinzufügen
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:src/aurestApp/img/a128x128.png"));
        //Anzeigen
        alert.showAndWait();
    }

    public static boolean isOpen() {
        return infoAnzeige != null;
    }

    public static void AnzeigeClose() {
        infoAnzeige.close();
    }
    public static void showWartebalken(String text){

        stage.getIcons().add(new Image("file:src/aurestApp/img/a128x128.png"));

        //create root node of scene, i.e. group
        Group rootGroup = new Group();
        //create scene with set width, height and color
        Scene scene = new Scene(rootGroup, 220, 80, Color.WHITE);
        //set scene to stage
        stage.setScene(scene);
        //center stage on screen
        stage.centerOnScreen();
        //show the stage
        stage.show();


        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);

        Label infotext = new Label(text);

        // USE A LAYOUT VBOX FOR EASIER POSITIONING OF THE VISUAL NODES ON SCENE
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 0, 0, 10));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(infotext,progressBar);

        //add all nodes to main root group
        rootGroup.getChildren().addAll(vBox);
    }
    public static void closeWartebalken(){
        stage.close();
    }

    public static void InfoAnzeigen(String statusText) {
        infoAnzeige = new Alert(Alert.AlertType.INFORMATION);

        infoAnzeige.setHeaderText(null);
        //Text hinzufügen
        infoAnzeige.setContentText(statusText);
        //Icon hinzufügen
        Stage stage = (Stage) infoAnzeige.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:src/aurestApp/img/a128x128.png"));
        //Anzeigen
        infoAnzeige.show();
    }

    public static void exceptionDialog(Exception ex, String Meldung) {
        if (infoAnzeige != null)
            infoAnzeige.close();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText(null);
        alert.setContentText(Meldung);

        // Create expandable Exception.
        String exceptionText = ex.getLocalizedMessage();

        Label label = new Label("Fehlermeldung:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:src/aurestApp/img/a128x128.png"));

        alert.showAndWait();
    }
}
