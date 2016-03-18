package aurestApp.controller;

import aurestApp.Model;
import aurestApp.interfaces.Seiten;
import aurestApp.tools.Generator;
import aurestApp.tools.eigeneklassen.Kunde;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProjektAnlegenController implements Initializable {
    private final Model m;
    ArrayList<String> kundenAlsStrings = new ArrayList<>();
    private VBox programmInhalt;
    private ToggleButton pbt2;
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

    public ProjektAnlegenController(Model m, VBox pi, ToggleButton pbt2) {
        this.m = m;
        this.programmInhalt = pi;
        this.pbt2 = pbt2;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kundenAlsStrings.addAll(m.getKunden().stream().map(Kunde::getName).collect(Collectors.toList()));

        TextFields.bindAutoCompletion(projektkunde, new HashSet<>(kundenAlsStrings));
    }

    @FXML
    private void handelErstelleProjekt() throws IOException {
        Notifications.create().darkStyle()
                .title("Projekt erstellen")
                .text("Projekt wird angelegt")
                .showInformation();

        String status = Generator.erstelleProjekt(projektnummer.getText(), projektkunde.getText(), projektbeschreibung.getText(), rbeigenfertigung.isSelected(), projektofferte.getText(), projektursprung.getText(), m);

        //Wenn das Projekt angelegt wurde, dann frage ob ein Logbucheintrag vorgenommen werden soll
        if (status.equals("Projekt angelegt")) {
            Notifications.create().darkStyle()
                    .title("Projekt angelegt")
                    .text("Das neue Projekt wurde erfolgreich angelegt")
                    .showInformation();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Projekt " + m.getProjektNummer() + " angelegt");
            alert.setHeaderText("Projekt " + m.getProjektNummer() + " wurde erfolgreich angelegt");
            alert.setContentText("Willst du direkt einen Logbucheintrag vornehmen?");

            ButtonType buttonTypeJa = new ButtonType("Ja");
            ButtonType buttonTypeNein = new ButtonType("Nein, Schließen", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeJa, buttonTypeNein);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeJa) {

                m.setProjektToLog(true);

                checkSize();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Seiten.PROJEKTLOGBUCH));
                loader.setController(new ProjektLogbuchController(m));
                Node mainNode = loader.load();
                setInhalt(mainNode);

                pbt2.setSelected(true);
            }
        }
        //Sollte der Kunde noch nicht vorhanden sein, dann speichern
        //if (kundenAlsStrings.contains(projektkunde.getText().trim()))
        //    return;
        //Settings.speicherKunde(m, new Kunde(0, projektkunde.getText().trim()));
    }

    private void setInhalt(Node node) {
        programmInhalt.getChildren().setAll(node);
    }

    private void checkSize() {
        if (programmInhalt.getScene().getWindow().getHeight() < 850.0)
            programmInhalt.getScene().getWindow().setHeight(850.0);
        if (programmInhalt.getScene().getWindow().getWidth() < 850.0)
            programmInhalt.getScene().getWindow().setWidth(850.0);
    }

}