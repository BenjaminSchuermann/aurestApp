package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.Settings;
import aurestApp.tools.eigeneklassen.GetImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EinstellungenLoginDetailsController implements Initializable {
    private Model m;
    @FXML
    private CustomTextField login;
    @FXML
    private CustomPasswordField passwort;
    @FXML
    private Button speichern;

    public EinstellungenLoginDetailsController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        login.setLeft(GetImageView.load("user_silhouette.png", 16));
        passwort.setLeft(GetImageView.load("lock.png", 16));

        login.setText(m.getLogin());
        login.setEditable(false);
        passwort.setText(m.getPasswort());

        passwort.textProperty().addListener((observable, oldValue, newValue) -> {
            speichern.setDisable(newValue.trim().isEmpty());
        });
    }

    public void speicherLogin(ActionEvent actionEvent) {
        Settings.speicherLogin(m, passwort.getText());
    }


}
