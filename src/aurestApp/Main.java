package aurestApp;

import aurestApp.Tools.Settings;
import aurestApp.interfaces.Seiten;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application implements Seiten {
    private final Model m = new Model();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        initialisierung();
        stage.setTitle("aurestApp v" + m.getVersion());
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/aurestApp/img/a128x128.png")));

        stage.setScene(erstelleScene(m));

        stage.show();
    }

    private Scene erstelleScene(Model m) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(HAUPTFENSTER));
        MainController mainController = new MainController(m);
        loader.setController(mainController);

        Pane mainPane = loader.load();

        mainController.startBild();

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().setAll(getClass().getResource("styles/stylesheet.css").toExternalForm());

        return scene;
    }

    private void initialisierung(){
        Settings.checkSettings();
        Settings.ladeKunden(m);
        m.setMitarbeiterListe(Settings.getNamesFrom());
        m.setServicejahr(Settings.getServiceJahr());
        m.setVorlagen(Settings.getVorlagen());
    }
}