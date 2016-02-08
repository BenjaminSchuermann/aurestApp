package aurestApp.controller;

import aurestApp.Model;
import aurestApp.interfaces.Images;
import aurestApp.interfaces.Seiten;
import aurestApp.tools.Dialoge;
import aurestApp.tools.eigeneklassen.GetImageView;
import aurestApp.tools.eigeneklassen.Kunde;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.CustomTextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EinstellungenKundenController implements Initializable {
    private final Model m;
    @FXML
    TableView<Kunde> kundenTable;
    @FXML
    TableColumn<Kunde, Integer> columnID;
    @FXML
    TableColumn<Kunde, String> columnKunde;
    @FXML
    TableColumn<Kunde, String> columnStrasse;
    @FXML
    TableColumn<Kunde, String> columnStadt;
    @FXML
    private CustomTextField filterField;
    @FXML
    private Button refreshbutton;
    @FXML
    private Button btnNeuerKunde;
    @FXML
    private Button btnBearbeiten;
    private ObservableList<Kunde> kunden;
    private SortedList<Kunde> sortedData;

    public EinstellungenKundenController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        kunden =
                FXCollections.observableArrayList(m.getKunden()
                );

        //Das asObject() muss hier leider dran, da aus Kompatibilitätsgründen der SimpleIntegerProperty ein Number und kein Integer liefert
        //Es könnte auch die Tabelle auf Number geändert werden. könnte.. auch...
        columnID.setCellValueFactory(cellData -> cellData.getValue().kundenIDProperty().asObject());

        columnKunde.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        columnStrasse.setCellValueFactory(cellData -> cellData.getValue().strasseProperty());
        columnStadt.setCellValueFactory(cellData -> cellData.getValue().stadtProperty());

        refreshbutton.setGraphic(GetImageView.load(Images.ARROW_REFRESH, 16));
        refreshbutton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        btnNeuerKunde.setGraphic(GetImageView.load(Images.VCARD_ADD, 32));
        btnNeuerKunde.setContentDisplay(ContentDisplay.LEFT);

        btnBearbeiten.setGraphic(GetImageView.load(Images.VCARD_EDIT, 32));
        btnBearbeiten.setContentDisplay(ContentDisplay.LEFT);

        filterField.setLeft(GetImageView.load(Images.MAGNIFIER, 16));
        filterField.setPromptText("Suchen");

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Kunde> filteredData = new FilteredList<>(kunden, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(kunde -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (kunde.getName() != null && kunde.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name.
                } else if (kunde.getStrasse() != null && kunde.getStrasse().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches strasse.
                } else if (kunde.getStadt() != null && kunde.getStadt().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter stadt.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(kundenTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        kundenTable.setItems(sortedData);

        //Beim Doppelklick die Detailsseite öffnen
        kundenTable.setRowFactory(tv -> {
            TableRow<Kunde> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    bearbeiteKunde(row.getItem());
                }
            });
            return row;
        });
    }

    @FXML
    private void handelNeuerKunde(ActionEvent actionEvent) {
        anlegenKunde();
    }

    @FXML
    private void handelBearbeitenKunden(ActionEvent actionEvent) {
        Kunde einzelnerKunde = kundenTable.getSelectionModel().getSelectedItem();
        bearbeiteKunde(einzelnerKunde);
    }

    @FXML
    private void handelrefresh(ActionEvent actionEvent) {
        //todo
        kunden = FXCollections.observableArrayList(m.getKunden());
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Kunde> filteredData = new FilteredList<>(kunden, p -> true);

        // 3. Wrap the FilteredList in a SortedList.
        sortedData = new SortedList<>(filteredData);
        kundenTable.setItems(sortedData);
    }

    private void anlegenKunde() {
        Stage stage = new Stage();
        stage.setTitle("aurestApp v" + m.getVersion());
        stage.getIcons().add(new Image(EinstellungenKundenController.class.getResourceAsStream("/aurestApp/img/a128x128.png")));

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Seiten.KUNDENDETAILS));
        loader.setController(new KundenAnlegenController(m));

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

    private void bearbeiteKunde(Kunde einzelnerKunde) {
        if (einzelnerKunde == null) {
            Notifications.create().darkStyle()
                    .title("Mitarbeiter bearbeiten")
                    .text("Es wurde kein Mitarbeiter zum bearbeiten gewählt")
                    .showInformation();
            return;

        }

        Stage stage = new Stage();
        stage.setTitle("aurestApp v" + m.getVersion());
        stage.getIcons().add(new Image(EinstellungenMitarbeiterController.class.getResourceAsStream("/aurestApp/img/a128x128.png")));

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Seiten.KUNDENDETAILS));
        loader.setController(new KundenDetailsController(m, einzelnerKunde));

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
}
