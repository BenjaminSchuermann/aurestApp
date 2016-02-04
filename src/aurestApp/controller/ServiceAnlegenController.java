package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.Generator;
import aurestApp.tools.eigeneklassen.Kunde;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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

public class ServiceAnlegenController implements Initializable {
    private final Model m;
    ArrayList<String> kundenAlsStrings = new ArrayList<>();

    @FXML
    private Pane menuServiceAnlegen;
    @FXML
    private TextField servicejahr;
    @FXML
    private TextField servicenummer;
    @FXML
    private TextField servicekunde;
    @FXML
    private TextField servicebeschreibung;
    @FXML
    private Button erstelleService;
    @FXML
    private TextField serviceprojekt;

    public ServiceAnlegenController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        servicejahr.setPromptText(m.getServicejahr());

        kundenAlsStrings.addAll(m.getKunden().stream().map(Kunde::getName).collect(Collectors.toList()));
        TextFields.bindAutoCompletion(servicekunde, new HashSet<>(kundenAlsStrings));

        erstelleService.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.FILE_TEXT_ALT).size(25.0).color(Color.BLACK));
        erstelleService.setContentDisplay(ContentDisplay.LEFT);
    }

    @FXML
    public void erstelleService(ActionEvent actionEvent) {
        Notifications.create().darkStyle()
                .title("Neuer Service")
                .text("Neuer Service wird angelegt")
                .showInformation();

        String serviceJahr = servicejahr.getText();
        if (serviceJahr.isEmpty())
            serviceJahr = servicejahr.getPromptText();

        Generator.erstelleService(serviceJahr + "." + servicenummer.getText(), servicekunde.getText(), servicebeschreibung.getText(), false, serviceprojekt.getText(), m);
        //Sollte der Kunde noch nicht vorhanden sein, dann speichern
        //if (kundenAlsStrings.contains(servicekunde.getText().trim()))
        //    return;
        //Settings.speicherKunde(m, new Kunde(0, servicekunde.getText().trim()));
    }
}
