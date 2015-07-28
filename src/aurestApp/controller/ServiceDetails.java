package aurestApp.controller;

import aurestApp.Model;
import aurestApp.interfaces.Seiten;
import aurestApp.tools.Dialoge;
import aurestApp.tools.eigeneklassen.Logbucheintrag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServiceDetails implements Initializable {
    private Model m;
    @FXML
    private TextField bezeichnung;
    @FXML
    private ChoiceBox<String> status;
    @FXML
    private SplitPane tblservices_details;
    @FXML
    private TextField servicejahr;
    @FXML
    private TextField servicenummer;
    @FXML
    private Button openservice;
    @FXML
    private TextField kundenid;
    @FXML
    private TextField kundenname;
    @FXML
    private TextField projektjahr;
    @FXML
    private TextField projektnummer;
    @FXML
    private Button openprojekt;
    @FXML
    private TextField projektbezeichnung;
    @FXML
    private TableView<Logbucheintrag> tbllogbuch;
    @FXML
    private TableColumn<Logbucheintrag, String> tbllog_datum;
    @FXML
    private TableColumn<Logbucheintrag, String> tbllog_titel;
    @FXML
    private TableColumn<Logbucheintrag, String> tbllog_kurz;
    @FXML
    private TableColumn<Logbucheintrag, String> tbllog_problem;
    @FXML
    private Button tbllog_add;
    @FXML
    private Button tbllog_details;
    @FXML
    private Button abbruch;
    @FXML
    private Button speichern;

    public ServiceDetails(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        openservice.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.FOLDER_OPEN_ALT).size(15.0).color(Color.BLACK));
        openservice.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        openprojekt.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.FOLDER_OPEN_ALT).size(15.0).color(Color.BLACK));
        openprojekt.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        tbllog_add.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.PLUS).size(25.0).color(Color.GREEN));
        tbllog_add.setContentDisplay(ContentDisplay.LEFT);
        tbllog_details.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.SEARCH).size(25.0).color(Color.BLACK));
        tbllog_details.setContentDisplay(ContentDisplay.LEFT);
        abbruch.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.REMOVE).size(25.0).color(Color.BLACK));
        abbruch.setContentDisplay(ContentDisplay.LEFT);
        speichern.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.SAVE).size(25.0).color(Color.BLUE));
        speichern.setContentDisplay(ContentDisplay.LEFT);

        status.getItems().addAll("offen", "geschlossen", "pending");
        status.getSelectionModel().selectFirst();

        //Zum vorführen
        bezeichnung.setText("Störung SPS 1");
        bezeichnung.setEditable(false);
        servicejahr.setText("5013");
        servicenummer.setText("1612");
        kundenid.setText("1");
        kundenname.setText("Schmid GmbH Schaltanlagen");
        projektjahr.setText("2014");
        projektnummer.setText("7187_1");
        projektbezeichnung.setText("Renschler Laupheim - LE2");

        ObservableList<Logbucheintrag> datalog =
                FXCollections.observableArrayList(new Logbucheintrag("10.10.2010", "Fehler", "bs", true, false, "1612", "Störung SPS 1")
                );
        tbllog_datum.setCellValueFactory(cellData -> cellData.getValue().datumProperty());
        tbllog_titel.setCellValueFactory(cellData -> cellData.getValue().titelProperty());
        tbllog_kurz.setCellValueFactory(cellData -> cellData.getValue().kuerzelProperty());
        tbllog_problem.setCellValueFactory(cellData -> cellData.getValue().bezeichnungProperty());
        tbllogbuch.setItems(datalog);
    }

    public void handelopenservice(ActionEvent actionEvent) {

    }

    public void handelopenprojekt(ActionEvent actionEvent) {

    }

    public void handeltbllog_add(ActionEvent actionEvent) {

    }

    public void handeltbllog_details(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setTitle("aurestApp v" + m.getVersion());
        stage.getIcons().add(new Image(ServiceDetails.class.getResourceAsStream("/aurestApp/img/a128x128.png")));

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Seiten.SERVICELOGBUCHDETAILS));
        loader.setController(new ServiceLogbuchDetails(m));

        try {
            VBox mainVbox = loader.load();
            Scene scene = new Scene(mainVbox);
            scene.getStylesheets().setAll(getClass().getResource("/aurestApp/styles/stylesheet.css").toExternalForm());
            stage.setScene(scene);

        } catch (IOException e) {
            Dialoge.exceptionDialog(e, "Fehler beim erstellen der Detailseite");
            return;
        }

        stage.show();
    }

    public void handelabbruch(ActionEvent actionEvent) {
        Stage stage = (Stage) abbruch.getScene().getWindow();
        stage.close();
    }

    public void handelspeichern(ActionEvent actionEvent) {

    }
}
