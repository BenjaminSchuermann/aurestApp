package aurestApp.controller;

import aurestApp.Model;
import aurestApp.interfaces.Seiten;
import aurestApp.tools.Dialoge;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;

public class MenuEmailsUmbennenController {
    private final Model m;
    private File selectedDirectory;
    private boolean emailsVorhanden;
    @FXML
    private ListView<String> listView;
    @FXML
    private Button emailordnerwaehlen;
    @FXML
    private Label pfad;
    @FXML
    private TextField pfadtext;
    @FXML
    private Button emailsumbennen;

    public MenuEmailsUmbennenController(Model m) {
        this.m = m;
    }

    @FXML
    private void waehlenOrdner(ActionEvent actionEvent) {
        //Ordner auswählen
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Ordner mit eMails auswählen");
        //Abfrage ob im Netzwerk aurest und Projektfpad verbunden ist noch hinzufügen
        //dirChooser.setInitialDirectory(new File("P:\\"));

        Stage stage = (Stage) emailordnerwaehlen.getScene().getWindow();
        //Ordnerauswahl anzeigen
        selectedDirectory = dirChooser.showDialog(stage);
        //Prüfen ob ein Ordner gewählt wurde
        if (selectedDirectory != null) {
            //Prüfen ob sich im Ordner .msg Dateien befinden
            File[] files = selectedDirectory.listFiles();
            emailsVorhanden = false;
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".msg")) {
                        pfad.setVisible(true);
                        pfadtext.setVisible(true);
                        listView.setVisible(true);
                        pfadtext.setText(selectedDirectory.toString());
                        emailsVorhanden = true;
                        if (emailsumbennen.getScene().getWindow().getHeight() < 400.0)
                            emailsumbennen.getScene().getWindow().setHeight(400.0);
                        break;
                    }
                }
            }
            if (emailsVorhanden) {
                //Wenn eMails vorhanden sind, dann erzeuge den Inhalt für die ListView
                int idn = 0;
                for (File file : files) {
                    if (file.getName().endsWith(".msg")) {
                        idn++;
                    }
                }
                File[] nureMails = new File[idn];
                int inm = 0;
                for (File file : files) {
                    if (file.getName().endsWith(".msg"))
                        nureMails[inm++] = file;
                }

                String[] dateinamen = new String[idn];
                for (int i = 0; i < nureMails.length; i++) {
                    dateinamen[i] = nureMails[i].getName();
                }

                listView.setItems(FXCollections.observableArrayList(dateinamen));
                listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            } else
                Dialoge.DialogAnzeigeBox("fehler", "Es wurde keine eMail in diesem Ordner gefunden");
        } else {
            pfad.setVisible(false);
            pfadtext.setVisible(false);
            listView.setVisible(false);
            //Dialoge.DialogAnzeigeBox("fehler", "Es wurde kein Ordner gewählt");
            Notifications.create().darkStyle()
                    .title("eMail auswählen")
                    .text("Es wurde kein Ordner ausgewählt")
                    .showWarning();
        }
    }

    @FXML
    private void umbennenEmails(ActionEvent actionEvent) {
        if (emailsVorhanden) {

            Stage stage = new Stage();
            stage.setTitle("aurestApp v" + m.getVersion());
            stage.getIcons().add(new Image(MenuEmailsUmbennenController.class.getResourceAsStream("/aurestApp/img/a128x128.png")));

            try {
                stage.setScene(erstelleScene(selectedDirectory, listView));
            } catch (IOException e) {
                e.printStackTrace();
            }

            stage.show();
        } else
            Dialoge.DialogAnzeigeBox("fehler", "Es wurden kein Ordner mit einer eMail ausgewählt");
    }

    private Scene erstelleScene(File selectedDirectory, ListView listView) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Seiten.EMAILPROGRESS));
        loader.setController(new eMailProgress(selectedDirectory, listView, m));

        VBox mainVbox = loader.load();

        return new Scene(mainVbox);

    }
}
