package aurestApp.controller;

import aurestApp.Model;
import aurestApp.Tools.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EinstellungenServiceController implements Initializable {
    private final Model m;
    @FXML
    private TextField servicejahr;

    public EinstellungenServiceController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        servicejahr.setText(m.getServicejahr());
    }

    @FXML
    private void speicherEinstellungen(ActionEvent actionEvent) {
        Settings.speicherServicejahr(m, servicejahr.getText());
    }
}
