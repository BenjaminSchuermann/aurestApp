package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.Kunde;
import aurestApp.tools.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
    TextField txtAddKunde;
    private ObservableList<Kunde> kunden;
    private ArrayList<Kunde> zuLoeschend = new ArrayList<>();
    @FXML
    private CustomTextField filterField;
    @FXML
    private Button btnAddKunde;
    @FXML
    private Button btnDelKunde;
    @FXML
    private Button btnSpeichern;

    public EinstellungenKundenController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //wenn etwas im Benutzernamefeld eingeben wurde, dann den Loginbutton aktivieren
        txtAddKunde.textProperty().addListener((observable, oldValue, newValue) -> {
            btnAddKunde.setDisable(newValue.trim().isEmpty());
        });

        kunden = FXCollections.observableArrayList(m.getKunden());

        //Das asObject() muss hier leider dran, da aus Kompatibilitätsgründen der SimpleIntegerProperty ein Number und kein Integer liefert
        //Es könnte auch die Tabelle auf Number geändert werden. könnte.. auch...
        columnID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        columnKunde.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        btnAddKunde.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.USER_PLUS).size(25.0).color(Color.GREEN));
        btnAddKunde.setContentDisplay(ContentDisplay.LEFT);

        btnDelKunde.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.USER_TIMES).size(25.0).color(Color.RED));
        btnDelKunde.setContentDisplay(ContentDisplay.LEFT);

        btnSpeichern.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.SAVE).size(25.0).color(Color.BLUE));
        btnSpeichern.setContentDisplay(ContentDisplay.LEFT);

        filterField.setLeft(new Glyph("FontAwesome", FontAwesome.Glyph.SEARCH).color(Color.BLACK));
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

                if (kunde.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (kunde.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Kunde> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(kundenTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        kundenTable.setItems(sortedData);
    }

    @FXML
    private void handelAddKunde(ActionEvent actionEvent) {
        //Zur Sicherheit, auch wenn der Button disabled sein sollte
        if (txtAddKunde.getText().trim().isEmpty())
            return;

        //Checken ob der Kunde, bzw. der Name, bereits angelegt wurde
        for (Kunde kunde : kundenTable.getItems()) {
            if (kunde.getName().equals(txtAddKunde.getText().trim())) {
                Notifications.create().darkStyle()
                        .title("Kunde vorhanden")
                        .text("Der Kunde ist bereits mit der ID: " + kunde.getId() + " vorhanden")
                        .showWarning();
                return;
            }
        }
        //wenn alles OK, dann füge ihn der Tabelle!! hinzu, erst mit dem Speichern wird es übernommen
        kunden.add(new Kunde(0, txtAddKunde.getText()));
    }

    @FXML
    private void handelDelKunde(ActionEvent actionEvent) {
        //Abfrage ib wirklich gelöscht werden soll
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Kunde entfernen");
        alert.setHeaderText("Kunde entfernen");
        alert.setContentText("Soll " + kundenTable.getSelectionModel().getSelectedItem().getName() + " wirklich entfernt werden?\n Erst nach dem Speichern wird die Änderung übernommen");

        ButtonType buttonTypeJa = new ButtonType("Ja");
        ButtonType buttonTypeNein = new ButtonType("Nein, Schließen", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeJa, buttonTypeNein);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeJa) {
            Kunde currentPerson = kundenTable.getSelectionModel().getSelectedItem();
            //remove selected item from the table list
            zuLoeschend.add(currentPerson);
            kunden.remove(currentPerson);
        }
    }

    @FXML
    private void handelSpeicherKunden(ActionEvent actionEvent) {
        m.setZuLoeschendenKunden(zuLoeschend);
        Settings.speicherKunden(m, kunden);
    }
}
