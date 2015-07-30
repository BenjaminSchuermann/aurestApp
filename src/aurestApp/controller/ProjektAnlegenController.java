package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.Generator;
import aurestApp.tools.eigeneklassen.Kunde;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProjektAnlegenController implements Initializable {
    private final Model m;
    ArrayList<String> kundenAlsStrings = new ArrayList<>();
    private TabPane tabPane;
    @FXML
    private TextField projektjahr;
    @FXML
    private TextField projektnummer;
    @FXML
    private TextField projektkunde;
    @FXML
    private TextField projektbeschreibung;
    @FXML
    private TextField projektofferte;
    @FXML
    private TextField projektursprung;
    @FXML
    private ToggleGroup myToggleGroup;
    @FXML
    private RadioButton rbeigenfertigung;
    @FXML
    private Button erstelleProjekt;

    public ProjektAnlegenController(Model m, TabPane tabPane) {
        this.m = m;
        this.tabPane = tabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kundenAlsStrings.addAll(m.getKunden().stream().map(Kunde::getName).collect(Collectors.toList()));

        TextFields.bindAutoCompletion(projektkunde, new HashSet<>(kundenAlsStrings));

        erstelleProjekt.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.FILE_POWERPOINT_ALT).size(25.0).color(Color.BLACK));
        erstelleProjekt.setContentDisplay(ContentDisplay.LEFT);
    }

    @FXML
    private void handelErstelleProjekt() {
        Notifications.create().darkStyle()
                .title("Projekt erstellen")
                .text("Projekt wird angelegt")
                .showInformation();

        Generator.erstelleProjekt(projektnummer.getText(), projektkunde.getText(), projektbeschreibung.getText(), rbeigenfertigung.isSelected(), projektofferte.getText(), projektursprung.getText(), m, tabPane);
        //Sollte der Kunde noch nicht vorhanden sein, dann speichern
        //if (kundenAlsStrings.contains(projektkunde.getText().trim()))
        //    return;
        //Settings.speicherKunde(m, new Kunde(0, projektkunde.getText().trim()));
    }
}