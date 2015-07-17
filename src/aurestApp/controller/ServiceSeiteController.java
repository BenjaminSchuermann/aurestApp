package aurestApp.controller;

import aurestApp.Model;
import aurestApp.interfaces.Seiten;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Benjamin on 16.07.2015.
 */
public class ServiceSeiteController implements Initializable {
    @FXML
    private VBox serviceSeite;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabServiceAnlegen;
    @FXML
    private Tab tabServiceLogbuch;
    @FXML
    private Label lbl_ServiceAnlegen;
    @FXML
    private Label lbl_ServiceLogbuch;


    private Model m;

    public ServiceSeiteController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        * Projekt erstellen
         */
        //Für Projekt erstellen den Tab Klickbar machen um die Seitengröße anzupassen
        lbl_ServiceAnlegen.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (serviceSeite.getScene().getWindow().getHeight() < 560.0)
                    serviceSeite.getScene().getWindow().setHeight(560.0);
            }
        });
        //Nun den Inhalt des Tabs laden und den Controller setzten
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Seiten.SERVICEERSTELLEN));
        loader.setController(new ServiceAnlegenController(m, tabPane));
        Node mainNode = null;
        try {
            mainNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Inhalt in den Tab setzen
        tabServiceAnlegen.setContent(mainNode);

        /*
        * Projekt Logbuch
         */
        //Für Projekt Logbuch den Tab Klickbar machen um die Seitengröße anzupassen
        lbl_ServiceLogbuch.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (serviceSeite.getScene().getWindow().getHeight() < 850.0)
                    serviceSeite.getScene().getWindow().setHeight(850.0);
            }
        });
        //Nun den Inhalt des Tabs laden und den Controller setzten
        loader = new FXMLLoader(getClass().getResource(Seiten.SERVICELOGBUCH));
        loader.setController(new ServiceLogbuchController(m, tabPane));
        mainNode = null;
        try {
            mainNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Inhalt in den Tab setzen
        tabServiceLogbuch.setContent(mainNode);
    }
}
