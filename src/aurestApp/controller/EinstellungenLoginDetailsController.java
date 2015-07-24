package aurestApp.controller;

import aurestApp.Model;
import aurestApp.Tools.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Color;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Benjamin on 20.07.2015.
 */
public class EinstellungenLoginDetailsController implements Initializable {
    private Model m;
    @FXML
    private CustomTextField login;
    @FXML
    private CustomTextField passwort;
    @FXML
    private Button speichern;

    public EinstellungenLoginDetailsController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

        login.setLeft(fontAwesome.create("USER"));
        passwort.setLeft(fontAwesome.create("LOCK"));

        login.setText(m.getLogin());
        login.setEditable(false);
        passwort.setText(m.getPasswort());

        passwort.textProperty().addListener((observable, oldValue, newValue) -> {
            speichern.setDisable(newValue.trim().isEmpty());
        });

        speichern.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.SAVE).size(25.0).color(Color.BLUE));
        speichern.setContentDisplay(ContentDisplay.LEFT);
    }

    public void speicherLogin(ActionEvent actionEvent) {
        Settings.speicherLogin(m, passwort.getText());
    }


}
