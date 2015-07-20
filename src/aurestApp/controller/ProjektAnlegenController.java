package aurestApp.controller;

import aurestApp.Model;
import aurestApp.Tools.Generator;
import aurestApp.Tools.Settings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

public class ProjektAnlegenController implements Initializable {
    private final Model m;
    Set<String> possibleSuggestions;
    private TabPane tabPane;
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

    private AutoCompletionBinding<String> autoCompletionBinding;

    public ProjektAnlegenController(Model m, TabPane tabPane) {
        this.m = m;
        this.tabPane = tabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        possibleSuggestions = new HashSet<>(m.getKundennamen());

        autoCompletionBinding = TextFields.bindAutoCompletion(projektkunde, possibleSuggestions);
        projektkunde.setOnKeyPressed(ke -> {
            switch (ke.getCode()) {
                case ENTER:
                    Settings.speicherKunde(m, projektkunde.getText());
                    autoCompletionLearnWord(projektkunde.getText());
                    break;
                default:
                    break;
            }
        });

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
        Settings.speicherKunde(m, projektkunde.getText());

    }


    private void autoCompletionLearnWord(String newWord) {
        possibleSuggestions.add(newWord);

        // we dispose the old binding and recreate a new binding
        if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(projektkunde, possibleSuggestions);
        m.addKunde(newWord.trim());
    }


}