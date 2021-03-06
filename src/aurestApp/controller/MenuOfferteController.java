package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.Generator;
import aurestApp.tools.eigeneklassen.Kunde;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MenuOfferteController implements Initializable {
    private final Model m;
    ArrayList<String> kundenAlsStrings = new ArrayList<>();
    @FXML
    private TextField offertennummer;
    @FXML
    private TextField offertenkunde;
    @FXML
    private TextField offertenbeschreibung;
    @FXML
    private Button erstelleOfferte;

    public MenuOfferteController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kundenAlsStrings.addAll(m.getKunden().stream().map(Kunde::getName).collect(Collectors.toList()));

        TextFields.bindAutoCompletion(offertenkunde, new HashSet<>(kundenAlsStrings));
    }

    @FXML
    private void handelErstelleOfferte() throws IOException {
        Notifications.create().darkStyle()
                .title("Offerte erstellen")
                .text("Offerte wird angelegt")
                .showInformation();

        String status = Generator.erstelleOfferte(offertennummer.getText(), offertenkunde.getText(), offertenbeschreibung.getText(), m);

        //Wenn das Projekt angelegt wurde, dann frage ob ein Logbucheintrag vorgenommen werden soll
        if (status.equals("Offerte angelegt")) {
            Notifications.create().darkStyle()
                    .title("Offerte angelegt")
                    .text("Die neue Offerte wurde erfolgreich angelegt")
                    .showInformation();
        }
    }

}