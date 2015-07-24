package aurestApp.controller;

import aurestApp.Model;
import aurestApp.Tools.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.net.URL;
import java.util.ResourceBundle;

public class EinstellungenServiceController implements Initializable {
    private final Model m;
    @FXML
    private TextField servicejahr;
    @FXML
    private Button speichern;

    public EinstellungenServiceController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        servicejahr.setText(m.getServicejahr());

        speichern.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.SAVE).size(25.0).color(Color.BLUE));
        speichern.setContentDisplay(ContentDisplay.LEFT);
    }

    @FXML
    private void speicherEinstellungen(ActionEvent actionEvent) {
        Settings.speicherServicejahr(m, servicejahr.getText());
    }
}
