package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.Dialoge;
import aurestApp.tools.eigeneklassen.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;
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
    private TextField kundenid;
    @FXML
    private TextField kundenname;
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
    private TableView<Service> tblservices;
    @FXML
    private TableColumn<Service, Integer> tblservice_columnjahr;
    @FXML
    private TableColumn<Service, Integer> tblservice_columnnummer;
    @FXML
    private TableColumn<Service, String> tblservice_columbezeichnung;
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

        ObservableList<Service> data =
                FXCollections.observableArrayList(new Service(0, 1745, 1, "Störung BHKW 1", 5015, "7187"),
                        new Service(0, 1798, 1, "Störung BHKW 2", 5014, "7187"),
                        new Service(0, 1612, 1, "Störung SPS 1", 5013, "7187")
                );
        tblservice_columnjahr.setCellValueFactory(cellData -> cellData.getValue().servicejahrProperty().asObject());
        tblservice_columnnummer.setCellValueFactory(cellData -> cellData.getValue().serviceProperty().asObject());
        tblservice_columbezeichnung.setCellValueFactory(cellData -> cellData.getValue().bezeichnungProperty());
        tblservices.setItems(data);


        //Zum vorführen
        bezeichnung.setText("Renschler Laupheim - LE2");
        bezeichnung.setEditable(false);
        projektjahr.setText("2014");
        projektnummer.setText("7187_1");
        kundenid.setText("1");
        kundenname.setText("Schmid GmbH Schaltanlagen");
        stammjahr.setText("2013");
        stammnummer.setText("7187");
        offerte.setDisable(true);
        openofferte.setDisable(true);


    }

    @FXML
    private void handelopenprojekt(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new File("P:\\7187_1 Schmid-Schaltanlagen Renschler Laupheim - LE2").toURI());
        } catch (IOException e) {
            Dialoge.exceptionDialog(e, "Kann Ordner nicht öffnen");
        }
    }
    @FXML
    private void handelopenofferte(ActionEvent actionEvent) {

    }

    @FXML
    private void handelopenstammprojekt(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new File("P:\\7187 Schmid-Schaltanlagen Renschler Laupheim").toURI());
        } catch (IOException e) {
            Dialoge.exceptionDialog(e, "Kann Ordner nicht öffnen");
        }
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
        Stage stage = (Stage) abbruch.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handelspeichern(ActionEvent actionEvent) {

    }
}
