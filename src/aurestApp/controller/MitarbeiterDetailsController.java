package aurestApp.controller;

import aurestApp.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MitarbeiterDetailsController implements Initializable {
    private Model m;
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
    private ListView listaltnamen;
    @FXML
    private Button loeschealtnamen;
    @FXML
    private TextField login;
    @FXML
    private PasswordField passwort;
    @FXML
    private Button loeschemitarbeiter;
    @FXML
    private Button abbruch;
    @FXML
    private Button speichern;

    public MitarbeiterDetailsController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addaltname(ActionEvent actionEvent) {

    }

    @FXML
    public void loeschealtnamen(ActionEvent actionEvent) {

    }

    @FXML
    public void loeschemitarbeiter(ActionEvent actionEvent) {

    }

    @FXML
    public void abbruch(ActionEvent actionEvent) {

    }

    @FXML
    public void speichern(ActionEvent actionEvent) {

    }
}
