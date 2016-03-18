package aurestApp.controller;

import aurestApp.Model;
import aurestApp.interfaces.Seiten;
import aurestApp.tools.Generator;
import aurestApp.tools.eigeneklassen.Kunde;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
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

public class ServiceAnlegenController implements Initializable {
    private final Model m;
    ArrayList<String> kundenAlsStrings = new ArrayList<>();
    private VBox programmInhalt;
    private ToggleButton sbt2;
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

    public ServiceAnlegenController(Model m, VBox pi, ToggleButton sbt2) {
        this.m = m;
        this.programmInhalt = pi;
        this.sbt2 = sbt2;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        servicejahr.setPromptText(m.getServicejahr());

        kundenAlsStrings.addAll(m.getKunden().stream().map(Kunde::getName).collect(Collectors.toList()));
        TextFields.bindAutoCompletion(servicekunde, new HashSet<>(kundenAlsStrings));
    }

    @FXML
    public void erstelleService(ActionEvent actionEvent) throws IOException {
        Notifications.create().darkStyle()
                .title("Neuer Service")
                .text("Neuer Service wird angelegt")
                .showInformation();

        String serviceJahr = servicejahr.getText();
        if (serviceJahr.isEmpty())
            serviceJahr = servicejahr.getPromptText();

        String status = Generator.erstelleService(serviceJahr + "." + servicenummer.getText(), servicekunde.getText(), servicebeschreibung.getText(), false, serviceprojekt.getText(), m);
        //Sollte der Kunde noch nicht vorhanden sein, dann speichern
        //if (kundenAlsStrings.contains(servicekunde.getText().trim()))
        //    return;
        //Settings.speicherKunde(m, new Kunde(0, servicekunde.getText().trim()));
        if (status.equals("Service angelegt")) {

            Notifications.create().darkStyle()
                    .title("Neuer Service")
                    .text("Der neue Service wurde erfolgreich angelegt")
                    .showInformation();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Service " + m.getServiceNummer() + " angelegt");
            alert.setHeaderText("Service " + m.getServiceNummer() + " wurde erfolgreich angelegt");
            alert.setContentText("Willst du direkt einen Logbucheintrag vornehmen?");

            ButtonType buttonTypeJa = new ButtonType("Ja");
            ButtonType buttonTypeNein = new ButtonType("Nein, Schließen", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeJa, buttonTypeNein);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeJa) {

                m.setServiceToLog(true);

                checkSize();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Seiten.SERVICELOGBUCH));
                loader.setController(new ServiceLogbuchController(m));
                Node mainNode = loader.load();
                setInhalt(mainNode);

                sbt2.setSelected(true);
            }
        }
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
