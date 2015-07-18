package aurestApp;

import aurestApp.Tools.Dialoge;
import aurestApp.Tools.Login;
import aurestApp.Tools.Settings;
import aurestApp.interfaces.Seiten;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application implements Seiten {
    private final Model m = new Model();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
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
        }
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
        Settings.checkSettings();

        Connection conn = Settings.connectDB(m);
        m.setKundennamen(Settings.ladeKunden(conn));
        m.setServicejahr(Settings.getServiceJahr(conn));
        m.setVorlagen(Settings.getVorlagen(conn));
        m.setMitarbeiterListe(Settings.getNamesFrom(conn));
        Settings.closeDB(conn);
    }
}