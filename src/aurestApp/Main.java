package aurestApp;

import aurestApp.interfaces.Seiten;
import aurestApp.tools.CheckVersion;
import aurestApp.tools.Dialoge;
import aurestApp.tools.Login;
import aurestApp.tools.Settings;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application implements Seiten {
    private final Model m = new Model();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setOnCloseRequest(e -> {
            m.closeDB();
            Platform.exit();
            System.exit(0);
        });

        //Wenn keine Datenbankverbindung hergestellt werden kann, direkt beenden.
        if (m.getConn() == null)
            return;

        if (CheckVersion.checkVersion(m))
            return;

        //als erstes das Loginfeld anzeigen und die userid holen
        int userid = Login.login(m);

        //hat ein Login stattgefunden dann starte das eigentliche Programm
        if (userid != 0) {
            try {
                initialisierung();
            } catch (SQLException e) {
                Dialoge.exceptionDialog(e, "Fehler beim Datenbankzugriff");
                return;
            }

            stage.setTitle("aurestApp v" + m.getVersion());
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/aurestApp/img/a128x128.png")));

            stage.setScene(erstelleScene(m));

            stage.show();
        } else
            m.closeDB();
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

    private void initialisierung() throws SQLException {
        Settings.ladeKunden(m);
        Settings.ladeServiceJahr(m);
        Settings.ladeVorlagen(m);
        Settings.ladeMitarbeiter(m);
        Settings.ladeProjekte(m);
    }
}