package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.Dialoge;
import aurestApp.tools.eigeneklassen.Mitarbeiter;
import aurestApp.interfaces.Seiten;
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
import org.controlsfx.control.Notifications;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EinstellungenMitarbeiterController implements Initializable {
    private final Model m;
    @FXML
    private VBox vbox;
    @FXML
    private TableView<Mitarbeiter> mitarbeiter;
    @FXML
    private TableColumn<Mitarbeiter, Integer> columnID;
    @FXML
    private TableColumn<Mitarbeiter, String> columnMitarbeiter;
    @FXML
    private TableColumn<Mitarbeiter, String> columnEmailAdresse;
    @FXML
    private TableColumn<Mitarbeiter, String> columnKurz;
    @FXML
    private TableColumn<Mitarbeiter, String> columnLogin;
    @FXML
    private TableColumn<Mitarbeiter, Boolean> columnTele;
    @FXML
    private TableColumn<Mitarbeiter, Boolean> columnEmail;

    @FXML
    private Button bearbeite;
    @FXML
    private Button addMitarbeiter;

    public EinstellungenMitarbeiterController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Mitarbeiter> data =
                FXCollections.observableArrayList(m.getMitarbeiterListe()
                );

        columnID.setCellValueFactory(cellData -> cellData.getValue().userIDProperty().asObject());

        columnMitarbeiter.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        columnEmailAdresse.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        columnKurz.setCellValueFactory(cellData -> cellData.getValue().kurzProperty());

        columnLogin.setCellValueFactory(cellData -> cellData.getValue().loginProperty());

        columnTele.setCellValueFactory(cellData -> cellData.getValue().teleAktivProperty());

        columnEmail.setCellValueFactory(cellData -> cellData.getValue().eMailAktivProperty());

        mitarbeiter.setItems(data);

        //Beim Doppelklick die Detailsseite öffnen
        mitarbeiter.setRowFactory(tv -> {
            TableRow<Mitarbeiter> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    bearbeiteMitarbeiter(row.getItem());
                }
            });
            return row;
        });

        addMitarbeiter.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.USER_PLUS).size(25.0).color(Color.GREEN));
        addMitarbeiter.setContentDisplay(ContentDisplay.LEFT);

        bearbeite.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.EDIT).size(25.0).color(Color.ORANGE));
        bearbeite.setContentDisplay(ContentDisplay.LEFT);
    }

    @FXML
    private void handelbearbeiteMitarbeiter(ActionEvent actionEvent) {
        Mitarbeiter einzelnerMitarbeiter = mitarbeiter.getSelectionModel().getSelectedItem();
        bearbeiteMitarbeiter(einzelnerMitarbeiter);
    }

    @FXML
    private void handelAddMitarbeiter(ActionEvent actionEvent){
        anlegenMitarbeiter();
    }

    private void bearbeiteMitarbeiter(Mitarbeiter einzelnerMitarbeiter) {
        if (einzelnerMitarbeiter == null) {
            Notifications.create().darkStyle()
                    .title("Mitarbeiter bearbeiten")
                    .text("Es wurde kein Mitarbeiter zum bearbeiten gewählt")
                    .showInformation();
            return;

        }
        System.out.println(einzelnerMitarbeiter.userIDProperty().getValue());

        Stage stage = new Stage();
        stage.setTitle("aurestApp v" + m.getVersion());
        stage.getIcons().add(new Image(EinstellungenMitarbeiterController.class.getResourceAsStream("/aurestApp/img/a128x128.png")));

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Seiten.MITARBEITERDETAILS));
        loader.setController(new MitarbeiterDetailsController(m, einzelnerMitarbeiter, mitarbeiter.getItems()));

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

    private void anlegenMitarbeiter() {
        Stage stage = new Stage();
        stage.setTitle("aurestApp v" + m.getVersion());
        stage.getIcons().add(new Image(EinstellungenMitarbeiterController.class.getResourceAsStream("/aurestApp/img/a128x128.png")));

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Seiten.MITARBEITERDETAILS));
        loader.setController(new MitarbeiterAnlegenController(m, mitarbeiter.getItems()));

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

    /* Test und Vorbereitung zum eMail versenden mit Outlook
    @FXML
    private void mailEinstellungen(ActionEvent actionEvent) {
        CreateOutlookMail newMail = new CreateOutlookMail();
        newMail.setEmpfaenger("a@b.c");
        newMail.setBetreff("Test");
        newMail.setHTMLbody("This is an <b>HTML</b> test body.");
        newMail.sendEMail();
    }
    */
}
