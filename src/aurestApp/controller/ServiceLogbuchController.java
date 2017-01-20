package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.Logbuch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Benjamin on 16.07.2015.
 */
public class ServiceLogbuchController implements Initializable {
    private final Model m;
    //@FXML
    //private RadioButton rbprojektoffen;
    //@FXML
    //private RadioButton rbprojektarchiviert;
    @FXML
    private TextField servicejahr;
    @FXML
    private TextField slurprojekt;
    @FXML
    private TextField servicenummer;
    @FXML
    private CheckBox checkbox;
    @FXML
    private DatePicker sldatum;
    @FXML
    private TextField slmitarbeiter;
    @FXML
    private TextField slkontakt;
    @FXML
    private TextField slanlagenteil;
    @FXML
    private TextField slproblem;
    @FXML
    private TextField slursache;
    @FXML
    private TextArea slloesung;
    @FXML
    private TextField slbemerkung;
    @FXML
    private Button erstellesl;

    public ServiceLogbuchController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        servicejahr.setPromptText(m.getServicejahr());
        sldatum.setPromptText(m.getAktuellesDatum());
        slmitarbeiter.setPromptText(m.getKuerzel());

        if (m.isServiceToLog()) {
            slurprojekt.setText(m.getServiceUrprojekt());
            servicenummer.setText(m.getServiceNummer());
            m.setServiceToLog(false);
            Platform.runLater(slkontakt::requestFocus);
        }
    }

    @FXML
    public void handelServiceLogbuch(ActionEvent actionEvent) {
        if (slurprojekt.getText().isEmpty()) {
            Notifications.create().darkStyle()
                    .title("Fehler")
                    .text("UrProjektnummer fehlt")
                    .showError();
            return;
        }
        Notifications.create().darkStyle()
                .title("Neuer Lobbucheintrag")
                .text("Bitte warten während der Logbucheintrag erstellt wird")
                .showInformation();
        //Dialoge.InfoAnzeigen("Bitte warten während der Logbucheintrag erstellt wird");

        m.setServiceLogUrprojekt(slurprojekt.getText());
        m.setServiceLogNummer(servicenummer.getText());
        m.setServiceLogAnlagenteil(slanlagenteil.getText());
        m.setServiceLogProblem(slproblem.getText());
        m.setServiceLogLösung(slloesung.getText());
        m.setServiceLogUrsache(slursache.getText());
        m.setServiceLogBemerkung(slbemerkung.getText());
        m.setServiceLogKontakt(slkontakt.getText());
        m.setServiceLogCheckBox(checkbox.isSelected());

        if (servicejahr.getText().isEmpty()) {
            m.setServiceLogJahr(servicejahr.getPromptText());
        } else {
            m.setServiceLogJahr(servicejahr.getText());
        }
        LocalDate date = sldatum.getValue();
        if (date == null) {
            date = LocalDate.now();
        }
        m.setServiceLogDatum(date);

        if (slmitarbeiter.getText().isEmpty())
            m.setServiceLogMitarbeiter(slmitarbeiter.getPromptText());
        else
            m.setServiceLogMitarbeiter(slmitarbeiter.getText());

        Logbuch.prüfeAufProjektLogbuch(m, false);

    }

    @FXML
    public void handelCheckbox(ActionEvent actionEvent) {
        servicejahr.setDisable(checkbox.isSelected());
        servicenummer.setDisable(checkbox.isSelected());
    }

}

