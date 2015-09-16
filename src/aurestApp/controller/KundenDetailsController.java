package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.Dialoge;
import aurestApp.tools.Settings;
import aurestApp.tools.eigeneklassen.Kunde;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.Notifications;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class KundenDetailsController implements Initializable {
    private Model m;
    private Kunde kunde;
    private Kunde startKunde;

    @FXML
    private VBox vbox;
    @FXML
    private TextField name;
    @FXML
    private TextField strasse;
    @FXML
    private TextField plz;
    @FXML
    private TextField stadt;
    @FXML
    private TextField land;
    @FXML
    private TextField txt_addordner;
    @FXML
    private Button btn_addordner;
    @FXML
    private CheckBox kundeaktiv;
    @FXML
    private CheckListView<String> listordner;
    @FXML
    private Button loescheordner;
    @FXML
    private Button loeschekunde;
    @FXML
    private Button abbruch;
    @FXML
    private Button speichern;

    public KundenDetailsController(Model m, Kunde kunde) {
        this.m = m;
        this.kunde = kunde;
        startKunde = kunde;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(kunde.getName());
        strasse.setText(kunde.getStrasse());
        plz.setText(Integer.toString(kunde.getPlz()));
        stadt.setText(kunde.getStadt());
        land.setText(kunde.getLand());

        kundeaktiv.setSelected(true);
        kundeaktiv.setDisable(true);

        listordner.setItems(FXCollections.observableArrayList(kunde.getOrdnernamen()));

        btn_addordner.setDisable(true);
        //wenn etwas im Benutzernamefeld eingeben wurde, dann den Loginbutton aktivieren
        txt_addordner.textProperty().addListener((observable, oldValue, newValue) -> {
            btn_addordner.setDisable(newValue.trim().isEmpty());
        });

        loeschekunde.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.USER_TIMES).size(25.0).color(Color.RED));
        loeschekunde.setContentDisplay(ContentDisplay.LEFT);

        abbruch.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.REMOVE).size(25.0).color(Color.BLACK));
        abbruch.setContentDisplay(ContentDisplay.LEFT);

        speichern.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.SAVE).size(25.0).color(Color.BLUE));
        speichern.setContentDisplay(ContentDisplay.LEFT);
    }

    @FXML
    public void addordner(ActionEvent actionEvent) {
        String ordner = txt_addordner.getText().trim();
        if (ordner.isEmpty()) {
            //Meldung rausgeben
            Notifications.create().darkStyle()
                    .title("Ungültig")
                    .text("Es wurde kein Ordnername eingetragen")
                    .showError();
            return;
        }

        if (kunde.getOrdnernamen().contains(ordner)) {
            //Meldung rausgeben
            Notifications.create().darkStyle()
                    .title("Ordnername vorhanden")
                    .text("Der Ordner ist bereits vorhanden")
                    .showWarning();
            return;
        }

        kunde.addOrdner(txt_addordner.getText().trim());
        listordner.setItems(FXCollections.observableArrayList(kunde.getOrdnernamen()));
    }

    @FXML
    public void loescheordner(ActionEvent actionEvent) {
        ArrayList<String> test = new ArrayList<>(listordner.getCheckModel().getCheckedItems());
        for (String s : test) {
            kunde.getOrdnernamen().remove(s);
        }
        listordner.setItems(FXCollections.observableArrayList(kunde.getOrdnernamen()));
    }

    @FXML
    public void loeschekunde(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Kunde entfernen");
        alert.setHeaderText("Kunde entfernen");
        alert.setContentText("Soll " + kunde.getName() + " wirklich entfernt werden?");

        ButtonType buttonTypeJa = new ButtonType("Ja");
        ButtonType buttonTypeNein = new ButtonType("Nein, Schließen", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeJa, buttonTypeNein);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeJa) {
            Settings.deleteKunde(m, kunde);
            Stage stage = (Stage) loeschekunde.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    public void abbruch(ActionEvent actionEvent) {
        Stage stage = (Stage) abbruch.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void speichern(ActionEvent actionEvent) {
        for (Kunde einzelnerKunde : m.getKunden()) {
            if (einzelnerKunde.getName().equals(name.getText()) & !einzelnerKunde.getName().equals(startKunde.getName())) {
                Dialoge.InfoAnzeigen("Der Kunde exisitert bereits!");
                return;
            }
        }

        kunde.setName(name.getText());
        kunde.setStrasse(strasse.getText());
        kunde.setPlz(Integer.parseInt(((plz.getText().equals("")) ? "0" : plz.getText())));
        kunde.setStadt(stadt.getText());
        kunde.setLand(land.getText());
        kunde.setOrdnernamen(new ArrayList<>(listordner.getItems()));


        //kunde = new Kunde(0, name.getText(), strasse.getText(),stadt.getText(),Integer.parseInt(((plz.getText().equals("")) ? "0" : plz.getText())),land.getText(),
        //        "","",new ArrayList<>(listordner.getItems()));

        Settings.updateKunde(m, kunde);
    }
}
