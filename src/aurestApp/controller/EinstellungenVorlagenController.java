package aurestApp.controller;

import aurestApp.Model;
import aurestApp.interfaces.Images;
import aurestApp.tools.Dialoge;
import aurestApp.tools.Settings;
import aurestApp.tools.eigeneklassen.GetImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EinstellungenVorlagenController implements Initializable {
    private final Model m;
    @FXML
    private TextField projektvorlagen;
    @FXML
    private TextField servicevorlage;
    @FXML
    private TextField offertevorlage;
    @FXML
    private Button projektvorlagenordnerwaehlen;
    @FXML
    private Button servicevorlageordnerwaehlen;
    @FXML
    private Button offertevorlageordnerwaehlen;
    @FXML
    private Button speichern;

    public EinstellungenVorlagenController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projektvorlagen.setText(m.getVorlagenProjekt());
        servicevorlage.setText(m.getVorlagenService());
        offertevorlage.setText(m.getVorlagenOfferte());

        projektvorlagenordnerwaehlen.setGraphic(GetImageView.load(Images.FOLDER_EXPLORE, 16));
        projektvorlagenordnerwaehlen.setContentDisplay(ContentDisplay.LEFT);

        servicevorlageordnerwaehlen.setGraphic(GetImageView.load(Images.FOLDER_EXPLORE, 16));
        servicevorlageordnerwaehlen.setContentDisplay(ContentDisplay.LEFT);

        offertevorlageordnerwaehlen.setGraphic(GetImageView.load(Images.FOLDER_EXPLORE, 16));
        offertevorlageordnerwaehlen.setContentDisplay(ContentDisplay.LEFT);

        speichern.setGraphic(GetImageView.load(Images.DISK, 32));
        speichern.setContentDisplay(ContentDisplay.LEFT);
    }

    @FXML
    private void waehlenProjektvorlagenOrdner(ActionEvent actionEvent) {
        //Ordner auswählen
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Projektvorlageordner auswählen");

        Stage stage = (Stage) projektvorlagenordnerwaehlen.getScene().getWindow();
        //Ordnerauswahl anzeigen
        File projektVorlagen = dirChooser.showDialog(stage);
        //Prüfen ob ein Ordner gewählt wurde
        if (projektVorlagen != null) {
            //Prüfen ob der Ordner die beiden Unterordner besitzt
            if (!(new File(projektVorlagen + "\\Eigenfertigung").exists() && new File(projektVorlagen + "\\Fremdfertigung").exists()))
                //wenn nein dann eine Meldung erzeugen
                Dialoge.DialogAnzeigeBox("fehler", "Der gewählte Ordner enthält keine Projekteinteilung. Es wurde kein Ordner \"Eigenfertigung\" oder \"Fremdfertigung\" gefunden ");
            else
                //wenn ja dann als in das Textfeld geben
                projektvorlagen.setText(projektVorlagen.toString());
        } else
            //Meldung rausgeben
            Notifications.create().darkStyle()
                    .title("Abbruch")
                    .text("Es wurde kein Ordner gewählt")
                    .showError();

    }

    @FXML
    private void waehlenServicevorlageOrdner(ActionEvent actionEvent) {
        //Ordner auswählen
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Servicevorlageordner auswählen");

        Stage stage = (Stage) servicevorlageordnerwaehlen.getScene().getWindow();
        //Ordnerauswahl anzeigen
        File serviceVorlage = dirChooser.showDialog(stage);
        //Prüfen ob ein Ordner gewählt wurde
        if (serviceVorlage != null) {
            servicevorlage.setText(serviceVorlage.toString());
        } else
            //Meldung rausgeben
            Notifications.create().darkStyle()
                    .title("Abbruch")
                    .text("Es wurde kein Ordner gewählt")
                    .showError();
    }

    @FXML
    private void waehlenOffertevorlageOrdner(ActionEvent actionEvent) {
        //Ordner auswählen
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Offertenvorlageordner auswählen");

        Stage stage = (Stage) offertevorlageordnerwaehlen.getScene().getWindow();
        //Ordnerauswahl anzeigen
        File offerteVorlage = dirChooser.showDialog(stage);
        //Prüfen ob ein Ordner gewählt wurde
        if (offerteVorlage != null) {
            offertevorlage.setText(offerteVorlage.toString());
        } else
            //Meldung rausgeben
            Notifications.create().darkStyle()
                    .title("Abbruch")
                    .text("Es wurde kein Ordner gewählt")
                    .showError();
    }

    @FXML
    private void speicherEinstellungen(ActionEvent actionEvent) {
        ArrayList<String> list = new ArrayList<>();
        list.add(projektvorlagen.getText());
        list.add(servicevorlage.getText());
        list.add(offertevorlage.getText());

        Settings.speicherVorlagen(m, list);
    }
}
