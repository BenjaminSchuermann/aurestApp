package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.Logbuch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProjektLogbuchController implements Initializable {
    private final Model m;
    //@FXML
    //private RadioButton rbprojektoffen;
    //@FXML
    //private RadioButton rbprojektarchiviert;
    @FXML
    private TextField plurprojekt;
    @FXML
    private TextField plprojekt;
    @FXML
    private DatePicker pldatum;
    @FXML
    private TextField plmitarbeiter;
    @FXML
    private TextField planlagenteil;
    @FXML
    private TextArea plbeschreibung;
    @FXML
    private Button erstellepl;

    public ProjektLogbuchController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pldatum.setPromptText(m.getAktuellesDatum());
        plmitarbeiter.setPromptText(m.getKuerzel());

        if (m.isProjektToLog()) {
            plurprojekt.setText(m.getProjektUrprojekt());
            plprojekt.setText(m.getProjektNummer());
            m.setProjektToLog(false);
            Platform.runLater(planlagenteil::requestFocus);
        }
    }

    @FXML
    private void handelProjektLogbuch(ActionEvent actionEvent) {
        if (plurprojekt.getText().isEmpty()) {
            Notifications.create().darkStyle()
                    .title("Fehler")
                    .text("UrProjektnummer fehlt")
                    .showError();
            return;
        }

        if (plprojekt.getText().isEmpty()) {
            Notifications.create().darkStyle()
                    .title("Fehler")
                    .text("Projektnummer fehlt")
                    .showError();
            return;
        }

        Notifications.create().darkStyle()
                .title("Neuer Lobbucheintrag")
                .text("Bitte warten während der Logbucheintrag erstellt wird")
                .showInformation();
        //Dialoge.InfoAnzeigen("Bitte warten während der Logbucheintrag erstellt wird");
        m.setProjektLogUrprojekt(plurprojekt.getText());
        m.setProjektLogProjekt(plprojekt.getText());
        m.setProjektLogAnlagenteil(planlagenteil.getText());

        LocalDate date = pldatum.getValue();
        if (date == null) {
            date = LocalDate.now();
        }
        m.setProjektLogDatum(date);
        if (plmitarbeiter.getText().isEmpty())
            m.setProjektLogMitarbeiter(plmitarbeiter.getPromptText());
        else
            m.setProjektLogMitarbeiter(plmitarbeiter.getText());
        //m.setProjektLogArchiviert(rbprojektarchiviert.isSelected());
        m.setProjektLogBeschreibung(plbeschreibung.getText());

        Logbuch.prüfeAufProjektLogbuch(m, true);
    }
}
