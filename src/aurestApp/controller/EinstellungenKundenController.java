package aurestApp.controller;

import aurestApp.Model;
import aurestApp.Tools.Kunde;
import aurestApp.Tools.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import org.controlsfx.control.Notifications;
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

        setTableData();

        btnAddKunde.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.USER_PLUS).size(25.0).color(Color.GREEN));
        btnAddKunde.setContentDisplay(ContentDisplay.LEFT);

        btnDelKunde.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.USER_TIMES).size(25.0).color(Color.RED));
        btnDelKunde.setContentDisplay(ContentDisplay.LEFT);

        btnSpeichern.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.SAVE).size(25.0).color(Color.BLUE));
        btnSpeichern.setContentDisplay(ContentDisplay.LEFT);
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
        kundenTable.getItems().add(new Kunde(0, txtAddKunde.getText()));
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
            kundenTable.getItems().remove(currentPerson);
        }
    }

    @FXML
    private void handelSpeicherKunden(ActionEvent actionEvent) {
        m.setZuLoeschendenKunden(zuLoeschend);
        Settings.speicherKunden(m, kunden);
    }

    private void setTableData() {
        kunden = FXCollections.observableArrayList(m.getKunden());

        //Das asObject() muss hier leider dran, da aus Kompatibilitätsgründen der SimpleIntegerProperty ein Number und kein Integer liefert
        //Es könnte auch die Tabelle auf Number geändert werden. könnte.. auch...
        columnID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        columnKunde.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        kundenTable.setItems(kunden);
    }
}
