package aurestApp.controller;

import aurestApp.Model;
import aurestApp.Tools.Generator;
import aurestApp.Tools.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by Benjamin on 16.07.2015.
 */
public class ServiceAnlegenController implements Initializable {
    private final Model m;
    Set<String> possibleSuggestions;
    private TabPane tabPane;
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
    private AutoCompletionBinding<String> autoCompletionBinding;

    public ServiceAnlegenController(Model m, TabPane tabPane) {
        this.m = m;
        this.tabPane = tabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        servicejahr.setPromptText(m.getServicejahr());

        possibleSuggestions = new HashSet<>(m.getKundennamen());
        autoCompletionBinding = TextFields.bindAutoCompletion(servicekunde, possibleSuggestions);

        servicekunde.setOnKeyPressed(ke -> {
            switch (ke.getCode()) {
                case ENTER:
                    Settings.speicherKunde(m, servicekunde.getText());
                    autoCompletionLearnWord(servicekunde.getText());
                    break;
                default:
                    break;
            }
        });

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
        Settings.speicherKunde(m, servicekunde.getText());
        Generator.erstelleService(serviceJahr + "." + servicenummer.getText(), servicekunde.getText(), servicebeschreibung.getText(), false, serviceprojekt.getText(), m, tabPane);

    }

    private void autoCompletionLearnWord(String newWord) {
        possibleSuggestions.add(newWord);

        // we dispose the old binding and recreate a new binding
        if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(servicekunde, possibleSuggestions);
        m.addKunde(newWord.trim());
    }
}
