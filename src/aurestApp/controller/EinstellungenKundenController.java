package aurestApp.controller;

import aurestApp.Model;
import aurestApp.Tools.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.Glyph;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EinstellungenKundenController implements Initializable {
    private final Model m;
    @FXML
    private TextArea kundenFeld;
    @FXML
    private Button speichern;

    public EinstellungenKundenController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String kundenListe = "";
        for (String kunde : m.getKundennamen())
            kundenListe += kunde + System.getProperty("line.separator");
        kundenFeld.setText(kundenListe);

        speichern.setGraphic(new Glyph("FontAwesome", "SAVE").size(25.0).color(Color.BLUE).useGradientEffect());
        speichern.setContentDisplay(ContentDisplay.LEFT);
    }

    @FXML
    private void speicherEinstellungen(ActionEvent actionEvent) {
        ArrayList<String> myList = new ArrayList<>(Arrays.asList(kundenFeld.getText().split("\\n")));

        Settings.speicherKunden(m, myList);
        m.setKundennamen(myList);
    }
}
