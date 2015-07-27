package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.Dialoge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by garog on 27.07.15.
 */
public class ProjektDetails implements Initializable {
    private Model m;

    @FXML
    private TextField bezeichnung;
    @FXML
    private ChoiceBox status;
    @FXML
    private SplitPane tblservices_details;
    @FXML
    private TextField projektjahr;
    @FXML
    private TextField projektnummer;
    @FXML
    private Button openprojekt;
    @FXML
    private TextField offerte;
    @FXML
    private Button openofferte;
    @FXML
    private TextField stammjahr;
    @FXML
    private TextField stammnummer;
    @FXML
    private Button openstammprojekt;
    @FXML
    private TableView tblservices;
    @FXML
    private TableColumn tblservice_columnjahr;
    @FXML
    private TableColumn tblservice_columnnummer;
    @FXML
    private TableColumn tblservice_columbezeichnung;
    @FXML
    private Button tblservices_anzeigen;
    @FXML
    private TableView tbllogbuch;
    @FXML
    private TableColumn tbllog_datum;
    @FXML
    private TableColumn tbllog_titel;
    @FXML
    private TableColumn tbllog_kurz;
    @FXML
    private TableColumn tbllog_typservice;
    @FXML
    private TableColumn tbllog_typprojekt;
    @FXML
    private TableColumn tbllog_nummer;
    @FXML
    private TableColumn tbllog_bezeichnung;
    @FXML
    private Button tbllog_add;
    @FXML
    private Button tbllog_details;
    @FXML
    private Button abbruch;
    @FXML
    private Button speichern;

    public ProjektDetails(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        openprojekt.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.FOLDER_OPEN_ALT).size(15.0).color(Color.BLACK));
        openprojekt.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        openofferte.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.FOLDER_OPEN_ALT).size(15.0).color(Color.BLACK));
        openofferte.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        openstammprojekt.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.FOLDER_OPEN_ALT).size(15.0).color(Color.BLACK));
        openstammprojekt.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        abbruch.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.REMOVE).size(25.0).color(Color.BLACK));
        abbruch.setContentDisplay(ContentDisplay.LEFT);
        speichern.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.SAVE).size(25.0).color(Color.BLUE));
        speichern.setContentDisplay(ContentDisplay.LEFT);
    }

    @FXML
    private void handelopenprojekt(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new File("P:\\7187 Schmid-Schaltanlagen Renschler Laupheim").toURI());
        } catch (IOException e) {
            Dialoge.exceptionDialog(e, "Kann Ordner nicht öffnen");
        }
    }

    @FXML
    private void handelopenofferte(ActionEvent actionEvent) {

    }

    @FXML
    private void handelopenstammprojekt(ActionEvent actionEvent) {

    }

    @FXML
    private void handeltblservices_anzeigen(ActionEvent actionEvent) {

    }

    @FXML
    private void handeltbllog_add(ActionEvent actionEvent) {

    }

    @FXML
    private void handeltbllog_details(ActionEvent actionEvent) {

    }

    @FXML
    private void handelabbruch(ActionEvent actionEvent) {

    }

    @FXML
    private void handelspeichern(ActionEvent actionEvent) {

    }
}
