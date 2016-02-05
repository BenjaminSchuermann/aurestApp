package aurestApp.controller;

import aurestApp.Model;
import aurestApp.img.icons.Images;
import aurestApp.tools.Dialoge;
import aurestApp.tools.MD5;
import aurestApp.tools.Settings;
import aurestApp.tools.eigeneklassen.GetImageView;
import aurestApp.tools.eigeneklassen.Mitarbeiter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MitarbeiterDetailsController implements Initializable {
    private Model m;
    private int userid;
    private Mitarbeiter mitarbeiter;
    private ObservableList<Mitarbeiter> obMitarbeiter;
    private Mitarbeiter startMitarbeiter;

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
    private String oldPasswort;

    public MitarbeiterDetailsController(Model m, Mitarbeiter mitarbeiter, ObservableList<Mitarbeiter> obMitarbeiter) {
        this.m = m;
        this.obMitarbeiter = obMitarbeiter;
        this.mitarbeiter = mitarbeiter;
        startMitarbeiter = mitarbeiter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(mitarbeiter.getName());
        email.setText(mitarbeiter.getEmail());
        kuerzel.setText(mitarbeiter.getKurz());
        emailaktiv.setSelected(mitarbeiter.geteMailAktiv());
        telenotizaktiv.setSelected(mitarbeiter.getTeleAktiv());
        loginaktiv.setSelected(mitarbeiter.getLoginAktiv());
        admin.setSelected(mitarbeiter.getIsAdmin());
        listaltnamen.setItems(FXCollections.observableArrayList(mitarbeiter.getAltnamen()));
        login.setText(mitarbeiter.getLogin());
        oldPasswort = mitarbeiter.getPasswort();
        passwort.setText(oldPasswort);

        btn_addaltname.setDisable(true);
        //wenn etwas im Benutzernamefeld eingeben wurde, dann den Loginbutton aktivieren
        txt_addaltname.textProperty().addListener((observable, oldValue, newValue) -> {
            btn_addaltname.setDisable(newValue.trim().isEmpty());
        });

        emailaktiv.setDisable(email.getText().trim().isEmpty());
        //wenn etwas im Benutzernamefeld eingeben wurde, dann den Loginbutton aktivieren
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            emailaktiv.setDisable(newValue.trim().isEmpty());
            if (newValue.trim().isEmpty())
                emailaktiv.setSelected(false);
        });

        telenotizaktiv.setDisable(kuerzel.getText().trim().isEmpty());
        //wenn etwas im Benutzernamefeld eingeben wurde, dann den Loginbutton aktivieren
        kuerzel.textProperty().addListener((observable, oldValue, newValue) -> {
            telenotizaktiv.setDisable(newValue.trim().isEmpty());
            if (newValue.trim().isEmpty())
                telenotizaktiv.setSelected(false);
        });

        loginaktiv.setDisable(login.getText().trim().isEmpty());
        //wenn etwas im Benutzernamefeld eingeben wurde, dann den Loginbutton aktivieren
        login.textProperty().addListener((observable, oldValue, newValue) -> {
            loginaktiv.setDisable(newValue.trim().isEmpty());
            if(newValue.trim().isEmpty())
                loginaktiv.setSelected(false);
        });

        loeschealtnamen.setGraphic(GetImageView.load(Images.TABLE_ROW_DELETE, 16));
        loeschealtnamen.setContentDisplay(ContentDisplay.LEFT);

        btn_addaltname.setGraphic(GetImageView.load(Images.ADD, 16));
        btn_addaltname.setContentDisplay(ContentDisplay.LEFT);

        login.setLeft(GetImageView.load(Images.USER_SILHOUETTE, 16));
        passwort.setLeft(GetImageView.load(Images.LOCK, 16));

        loeschemitarbeiter.setGraphic(GetImageView.load(Images.USER_DELETE, 32));
        loeschemitarbeiter.setContentDisplay(ContentDisplay.LEFT);

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
                    .title("Ungültig")
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mitarbeiter entfernen");
        alert.setHeaderText("Mitarbeiter entfernen");
        alert.setContentText("Soll " + mitarbeiter.getName() + " wirklich entfernt werden?");

        ButtonType buttonTypeJa = new ButtonType("Ja");
        ButtonType buttonTypeNein = new ButtonType("Nein, Schließen", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeJa, buttonTypeNein);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeJa) {
            Settings.deleteMitarbeiter(m, mitarbeiter, obMitarbeiter);
            Stage stage = (Stage) loeschemitarbeiter.getScene().getWindow();
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
        for (Mitarbeiter einzelnerMitarbeiter : m.getMitarbeiterListe()) {
            if (einzelnerMitarbeiter.getName().equals(name.getText()) & !einzelnerMitarbeiter.getName().equals(startMitarbeiter.getName())) {
                Dialoge.InfoAnzeigen("Der Mitarbeiter exisitert bereits!");
                return;
            }
            if (!einzelnerMitarbeiter.getLogin().isEmpty() &&
                    (einzelnerMitarbeiter.getLogin().equals(login.getText()) & !einzelnerMitarbeiter.getLogin().equals(startMitarbeiter.getLogin()))) {
                Dialoge.InfoAnzeigen("Diese Logindaten werden schon für "+einzelnerMitarbeiter.getName()+" benutzt!");
                return;
            }
        }
        mitarbeiter.setName(name.getText());
        mitarbeiter.setEmail(email.getText());
        mitarbeiter.setKurz(kuerzel.getText());
        mitarbeiter.seteMailAktiv(emailaktiv.isSelected());
        mitarbeiter.setTeleAktiv(telenotizaktiv.isSelected());
        mitarbeiter.setLoginAktiv(loginaktiv.isSelected());
        mitarbeiter.setIsAdmin(admin.isSelected());
        mitarbeiter.setAltnamen(new ArrayList<>(listaltnamen.getItems()));
        mitarbeiter.setLogin(login.getText());
        if (oldPasswort.equals(passwort.getText())) {
            mitarbeiter.setPasswort(oldPasswort);
        } else {
            mitarbeiter.setPasswort(MD5.md5(passwort.getText()));
        }
        Settings.updateMitarbeiter(m, mitarbeiter, obMitarbeiter);
    }
}
