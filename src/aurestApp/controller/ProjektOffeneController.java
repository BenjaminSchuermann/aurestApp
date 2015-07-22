package aurestApp.controller;

import aurestApp.Model;
import aurestApp.Tools.Projekt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Benjamin on 22.07.2015.
 */
public class ProjektOffeneController implements Initializable {
    private final Model m;
    private TabPane tabPane;

    @FXML
    private VBox vbox;
    @FXML
    private TableView<Projekt> offeneProjekte;
    @FXML
    private TableColumn<Object, Object> checkBoxTableColumn;
    @FXML
    private TableColumn<Object, Object> columnProjekt;
    @FXML
    private TableColumn<Object, Object> columnKunde;
    @FXML
    private TableColumn<Object, Object> columnBezeichnung;
    @FXML
    private TableColumn<Object, Object> columnOfferte;
    @FXML
    private TableColumn<Object, Object> columnUrProjekt;
    @FXML
    private Button projektLoeschen;
    @FXML
    private Button projektArchivieren;

    public ProjektOffeneController(Model m, TabPane tabPane) {
        this.m = m;
        this.tabPane = tabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Projekt> data =
                FXCollections.observableArrayList(m.getProjekte());

        checkBoxTableColumn.setCellValueFactory(
                new PropertyValueFactory<>("checked"));

        columnProjekt.setCellValueFactory(
                new PropertyValueFactory<>("projektnummer"));

        columnKunde.setCellValueFactory(
                new PropertyValueFactory<>("kunde"));

        columnBezeichnung.setCellValueFactory(
                new PropertyValueFactory<>("bezeichnung"));

        columnOfferte.setCellValueFactory(
                new PropertyValueFactory<>("offerte"));

        columnUrProjekt.setCellValueFactory(
                new PropertyValueFactory<>("urProjekt"));

        offeneProjekte.setItems(data);
        //erstellepl.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.BOOK).size(25.0).color(Color.BROWN).useGradientEffect());
        //erstellepl.setContentDisplay(ContentDisplay.LEFT);
    }

    public void handelProjektLoeschen(ActionEvent actionEvent) {

    }

    public void handelProjektArchivieren(ActionEvent actionEvent) {

    }
}
