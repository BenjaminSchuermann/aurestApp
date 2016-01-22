package aurestApp;

import aurestApp.interfaces.Seiten;
import aurestApp.tools.CheckVersion;
import aurestApp.tools.Dialoge;
import aurestApp.tools.Login;
import aurestApp.tools.Settings;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class Main extends Application implements Seiten {

    private final Model m = new Model();
    private boolean firstTime;
    private TrayIcon trayIcon;

    public static void main(String[] args) {
        launch(args);
    }

    //Um ein awt Image auszugeben
    static java.awt.Image createImage(String path, String description) {
        URL imageURL = Main.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setOnCloseRequest(e -> {
            m.closeDB();
            //Platform.exit();  wurde durch das TrayMenu Thema erledigt
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

            //TrayIcon anlegen, brauchen wir nur, wenn auch wirklich wer eigenloggt hat
            createTrayIcon(stage);
            firstTime = true;
            //nötig damit nicht direkt beim klick auf x geschlossen wird
            Platform.setImplicitExit(false);

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

    public void createTrayIcon(final Stage stage) {
        if (SystemTray.isSupported()) {
            // Systemtray holen
            SystemTray tray = SystemTray.getSystemTray();

            //Anstelle zu schließen, soll nur versteckt werden
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    hide(stage);
                }
            });

            //Action Listener für den Klick auf beenden
            final ActionListener closeListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            };

            //Action Listener für den Klick auf öffnen
            ActionListener showListener = e -> Platform.runLater(() -> stage.show());

            // Das PopUp Menü anlegen, welches beim rechtsklick auf das "a" Logo im Tray geöffnet werden soll
            PopupMenu popup = new PopupMenu();

            //Und die Items dazu anlegen, zuerst den Anzeigen Button
            MenuItem showItem = new MenuItem("Anzeigen");
            showItem.addActionListener(showListener);
            popup.add(showItem);

            //Dann einen Seperator
            popup.addSeparator();

            //und beenden darf nicht fehlen
            MenuItem closeItem = new MenuItem("Beenden");
            closeItem.addActionListener(closeListener);
            popup.add(closeItem);

            //TrayIcon erzeugen und einstellen das die Bildgröße automatisch ermittelt werden soll, damit auch große Bilder ins 16x16 passen
            trayIcon = new TrayIcon(createImage("/aurestApp/img/a128x128.png", "tray icon"), "aurestApp Tray", popup);
            trayIcon.setImageAutoSize(true);

            // wenn man doppelt auf das "a" mit links klickt, dann soll sich das Programm ebenfalls wieder öffnen
            trayIcon.addActionListener(showListener);

            //und hinzufügen
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
        }
    }

    public void showProgramIsMinimizedMsg() {
        if (firstTime) {
            trayIcon.displayMessage("aurestApp",
                    "Wird weiter im Tray weiter ausgeführt.",
                    TrayIcon.MessageType.INFO);
            firstTime = false;
        }
    }

    //Von AWT aus die FX Stage verstecken, aber nur wenn Supported, sonst komplett schließen
    private void hide(final Stage stage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (SystemTray.isSupported()) {
                    stage.hide();
                    showProgramIsMinimizedMsg();
                } else {
                    System.exit(0);
                }
            }
        });
    }

}