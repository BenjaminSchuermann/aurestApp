package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.Dialoge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServiceLogbuchDetails implements Initializable {
    private Model m;
    @FXML
    private TextField servicejahr;
    @FXML
    private TextField servicenummer;
    @FXML
    private Button openservice;
    @FXML
    private TextField servicebezeichnung;
    @FXML
    private TextField projektjahr;
    @FXML
    private TextField projektnummer;
    @FXML
    private Button openprojekt;
    @FXML
    private TextField projektbezeichnung;
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
    private Button abbruch;

    public ServiceLogbuchDetails(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        openservice.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.FOLDER_OPEN_ALT).size(15.0).color(Color.BLACK));
        openservice.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        openprojekt.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.FOLDER_OPEN_ALT).size(15.0).color(Color.BLACK));
        openprojekt.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        abbruch.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.REMOVE).size(25.0).color(Color.BLACK));
        abbruch.setContentDisplay(ContentDisplay.LEFT);
        //Zum vorführen
        servicejahr.setText("5013");
        servicenummer.setText("1612");
        servicebezeichnung.setText("Störung SPS1");
        projektjahr.setText("2014");
        projektnummer.setText("7187_1");
        projektbezeichnung.setText("Renschler Laupheim - LE2");
        sldatum.setPromptText("10.10.2010");
        slmitarbeiter.setText("bs");
        slkontakt.setText("Herr Kohn");
        slanlagenteil.setText("SPS LE1");
        slproblem.setText("SPS in Stop");
        slursache.setText("Überlauf im Arrayzugriff");
        slloesung.setText("Grenzenabfrage korrigiert");
        slbemerkung.setText("Array von 0-1000 sind 1001 Elemente");
    }

    public void handelopenservice(ActionEvent actionEvent) {

    }

    public void handelopenprojekt(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new File("P:\\7187_1 Schmid-Schaltanlagen Renschler Laupheim - LE2").toURI());
        } catch (IOException e) {
            Dialoge.exceptionDialog(e, "Kann Ordner nicht öffnen");
        }
    }

    public void handelAbbruch(ActionEvent actionEvent) {
        Stage stage = (Stage) abbruch.getScene().getWindow();
        stage.close();
    }
}
