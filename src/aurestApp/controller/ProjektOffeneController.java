package aurestApp.controller;

import aurestApp.Model;
import aurestApp.interfaces.Seiten;
import aurestApp.tools.Dialoge;
import aurestApp.tools.eigeneklassen.Projekt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
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
    private TableColumn<Projekt, Boolean> checkBoxTableColumn;
    @FXML
    private TableColumn<Projekt, String> columnProjekt;
    @FXML
    private TableColumn<Projekt, String> columnKunde;
    @FXML
    private TableColumn<Projekt, String> columnBezeichnung;
    @FXML
    private TableColumn<Projekt, Integer> columnOfferte;
    @FXML
    private TableColumn<Projekt, String> columnUrProjekt;
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

        checkBoxTableColumn.setCellValueFactory(cellData -> cellData.getValue().checkedProperty());

        columnProjekt.setCellValueFactory(cellData -> cellData.getValue().projektnummerProperty());

        columnKunde.setCellValueFactory(cellData -> cellData.getValue().kundeProperty());

        columnBezeichnung.setCellValueFactory(cellData -> cellData.getValue().bezeichnungProperty());

        columnOfferte.setCellValueFactory(cellData -> cellData.getValue().offerteProperty().asObject());

        columnUrProjekt.setCellValueFactory(cellData -> cellData.getValue().urProjektProperty());

        offeneProjekte.setItems(data);
    }

    public void handelProjektLoeschen(ActionEvent actionEvent) {

    }

    public void handelProjektArchivieren(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setTitle("aurestApp v" + m.getVersion());
        stage.getIcons().add(new Image(ProjektOffeneController.class.getResourceAsStream("/aurestApp/img/a128x128.png")));

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Seiten.PROJEKTDETAILS));
        loader.setController(new ProjektDetails(m));

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
