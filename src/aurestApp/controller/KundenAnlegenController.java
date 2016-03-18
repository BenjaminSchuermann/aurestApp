package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.Dialoge;
import aurestApp.tools.Settings;
import aurestApp.tools.eigeneklassen.Kunde;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class KundenAnlegenController implements Initializable {
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

    public KundenAnlegenController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Neuen Mitarbeiter mit nichts anlegen
        kunde = new Kunde(0, "", "", "", 0, "", "", "", new ArrayList<>(listordner.getItems()));

        btn_addordner.setDisable(true);
        //wenn etwas im Benutzernamefeld eingeben wurde, dann den Loginbutton aktivieren
        txt_addordner.textProperty().addListener((observable, oldValue, newValue) -> {
            btn_addordner.setDisable(newValue.trim().isEmpty());
        });

        //Hier nicht genutzt
        loeschekunde.setVisible(false);
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
        //hier nicht genutzt
    }

    @FXML
    public void abbruch(ActionEvent actionEvent) {
        Stage stage = (Stage) abbruch.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void speichern(ActionEvent actionEvent) {
        for (Kunde einzelnerKunde : m.getKunden()) {
            if (einzelnerKunde.getName().equals(name.getText())) {
                Dialoge.InfoAnzeigen("Der Kunde exisitert bereits!");
                return;
            }
        }
        kunde = new Kunde(0, name.getText(), strasse.getText(), stadt.getText(), Integer.parseInt(((plz.getText().equals("")) ? "0" : plz.getText())), land.getText(),
                "", "", new ArrayList<>(listordner.getItems()));

        Settings.addKunde(m, kunde);
    }
}
