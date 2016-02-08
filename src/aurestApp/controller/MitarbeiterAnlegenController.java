package aurestApp.controller;

import aurestApp.Model;
import aurestApp.interfaces.Images;
import aurestApp.tools.Dialoge;
import aurestApp.tools.Settings;
import aurestApp.tools.eigeneklassen.GetImageView;
import aurestApp.tools.eigeneklassen.Mitarbeiter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MitarbeiterAnlegenController implements Initializable {
    private Model m;
    private int userid;
    private Mitarbeiter mitarbeiter;
    private ObservableList<Mitarbeiter> obMitarbeiter;

    @FXML
    private VBox vbox;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField kuerzel;
    @FXML
    private TextField txt_addaltname;
    @FXML
    private Button btn_addaltname;
    @FXML
    private CheckBox emailaktiv;
    @FXML
    private CheckBox telenotizaktiv;
    @FXML
    private CheckBox loginaktiv;
    @FXML
    private CheckBox admin;
    @FXML
    private CheckListView<String> listaltnamen;
    @FXML
    private Button loeschealtnamen;
    @FXML
    private CustomTextField login;
    @FXML
    private CustomPasswordField passwort;
    @FXML
    private Button loeschemitarbeiter;
    @FXML
    private Button abbruch;
    @FXML
    private Button speichern;

    public MitarbeiterAnlegenController(Model m, ObservableList<Mitarbeiter> obMitarbeiter) {
        this.m = m;
        this.obMitarbeiter = obMitarbeiter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Neuen Mitarbeiter mit nichts anlegen
        mitarbeiter = new Mitarbeiter(0, "", "", "", false, false, false, false, "", "", new ArrayList<>(listaltnamen.getItems()));

        btn_addaltname.setDisable(true);
        //wenn etwas im Benutzernamefeld eingeben wurde, dann den Loginbutton aktivieren
        txt_addaltname.textProperty().addListener((observable, oldValue, newValue) -> {
            btn_addaltname.setDisable(newValue.trim().isEmpty());
        });

        emailaktiv.setDisable(true);
        //wenn etwas im Benutzernamefeld eingeben wurde, dann den Loginbutton aktivieren
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            emailaktiv.setDisable(newValue.trim().isEmpty());
            if (newValue.trim().isEmpty())
                emailaktiv.setSelected(false);
        });

        telenotizaktiv.setDisable(true);
        //wenn etwas im Benutzernamefeld eingeben wurde, dann den Loginbutton aktivieren
        kuerzel.textProperty().addListener((observable, oldValue, newValue) -> {
            telenotizaktiv.setDisable(newValue.trim().isEmpty());
            if (newValue.trim().isEmpty())
                telenotizaktiv.setSelected(false);
        });

        loginaktiv.setDisable(true);
        //wenn etwas im Benutzernamefeld eingeben wurde, dann den Loginbutton aktivieren
        login.textProperty().addListener((observable, oldValue, newValue) -> {
            loginaktiv.setDisable(newValue.trim().isEmpty());
            if (newValue.trim().isEmpty())
                loginaktiv.setSelected(false);
        });

        loeschealtnamen.setGraphic(GetImageView.load(Images.TABLE_ROW_DELETE, 16));
        loeschealtnamen.setContentDisplay(ContentDisplay.LEFT);

        btn_addaltname.setGraphic(GetImageView.load(Images.ADD, 16));
        btn_addaltname.setContentDisplay(ContentDisplay.LEFT);

        login.setLeft(GetImageView.load(Images.USER_SILHOUETTE, 16));
        passwort.setLeft(GetImageView.load(Images.LOCK, 16));

        //Hier nicht genutzt
        loeschemitarbeiter.setGraphic(GetImageView.load(Images.USER_DELETE, 32));
        loeschemitarbeiter.setContentDisplay(ContentDisplay.LEFT);
        loeschemitarbeiter.setVisible(false);

        abbruch.setGraphic(GetImageView.load(Images.CANCEL, 32));
        abbruch.setContentDisplay(ContentDisplay.LEFT);

        speichern.setGraphic(GetImageView.load(Images.DISK, 32));
        speichern.setContentDisplay(ContentDisplay.LEFT);
    }

    @FXML
    public void addaltname(ActionEvent actionEvent) {
        String altname = txt_addaltname.getText().trim();
        if (altname.isEmpty()) {
            //Meldung rausgeben
            Notifications.create().darkStyle()
                    .title("Ungueltig")
                    .text("Es wurde kein alternativer Name eingetragen")
                    .showError();
            return;
        }

        if (mitarbeiter.getAltnamen().contains(altname)) {
            //Meldung rausgeben
            Notifications.create().darkStyle()
                    .title("Alternativer Name vorhanden")
                    .text("Der alternative Name ist bereits vorhanden")
                    .showWarning();
            return;
        }

        mitarbeiter.addAltname(txt_addaltname.getText().trim());
        listaltnamen.setItems(FXCollections.observableArrayList(mitarbeiter.getAltnamen()));
    }

    @FXML
    public void loeschealtnamen(ActionEvent actionEvent) {
        ArrayList<String> test = new ArrayList<>(listaltnamen.getCheckModel().getCheckedItems());
        for (String s : test) {
            mitarbeiter.getAltnamen().remove(s);
        }
        listaltnamen.setItems(FXCollections.observableArrayList(mitarbeiter.getAltnamen()));
    }

    @FXML
    public void loeschemitarbeiter(ActionEvent actionEvent) {
        //hier nicht genutzt
    }

    @FXML
    public void abbruch(ActionEvent actionEvent) {
        Stage stage = (Stage) abbruch.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void speichern(ActionEvent actionEvent) {
        for (Mitarbeiter einzelnerMitarbeiter : m.getMitarbeiterListe()) {
            if(einzelnerMitarbeiter.getName().equals(name.getText())){
                Dialoge.InfoAnzeigen("Der Mitarbeiter exisitert bereits!");
                return;
            }
            if(!einzelnerMitarbeiter.getLogin().isEmpty() && einzelnerMitarbeiter.getLogin().equals(login.getText())){
                Dialoge.InfoAnzeigen("Diese Logindaten werden schon für "+einzelnerMitarbeiter.getName()+" benutzt!");
                return;
            }
        }
        mitarbeiter = new Mitarbeiter(0, name.getText(), email.getText(),kuerzel.getText(),telenotizaktiv.isSelected(),emailaktiv.isSelected(),
                admin.isSelected(),loginaktiv.isSelected(),login.getText(),passwort.getText(),new ArrayList<>(listaltnamen.getItems()));

        Settings.addMitarbeiter(m, mitarbeiter, obMitarbeiter);
    }
}
