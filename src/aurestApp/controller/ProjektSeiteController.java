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
public class ProjektSeiteController implements Initializable {
    @FXML
    private VBox projektSeite;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabProjektAnlegen;
    @FXML
    private Tab tabProjektLogbuch;
    @FXML
    private Tab tabProjektOffen;
    @FXML
    private Label lbl_ProjektAnlegen;
    @FXML
    private Label lbl_ProjektLogbuch;
    @FXML
    private Label lbl_ProjektOffen;


    private Model m;

    public ProjektSeiteController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        * Projekt erstellen
         */
        //Für Projekt erstellen den Tab Klickbar machen um die Seitengröße anzupassen
        lbl_ProjektAnlegen.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (projektSeite.getScene().getWindow().getHeight() < 560.0)
                    projektSeite.getScene().getWindow().setHeight(560.0);
            }
        });
        //Nun den Inhalt des Tabs laden und den Controller setzten
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Seiten.PROJEKTERSTELLEN));
        loader.setController(new ProjektAnlegenController(m));
        Node mainNode = null;
        try {
            mainNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Inhalt in den Tab setzen
        tabProjektAnlegen.setContent(mainNode);

        /*
        * Projekt Logbuch
         */
        //Für Projekt Logbuch den Tab Klickbar machen um die Seitengröße anzupassen
        lbl_ProjektLogbuch.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (projektSeite.getScene().getWindow().getHeight() < 850.0)
                    projektSeite.getScene().getWindow().setHeight(850.0);
            }
        });
        //Nun den Inhalt des Tabs laden und den Controller setzten
        loader = new FXMLLoader(getClass().getResource(Seiten.PROJEKTLOGBUCH));
        loader.setController(new ProjektLogbuchController(m));
        mainNode = null;
        try {
            mainNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Inhalt in den Tab setzen
        tabProjektLogbuch.setContent(mainNode);

        /*
        * Offene Projekte
         */
        //Für offene Projekte den Tab Klickbar machen um die Seitengröße anzupassen
        lbl_ProjektOffen.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (projektSeite.getScene().getWindow().getHeight() < 850.0)
                    projektSeite.getScene().getWindow().setHeight(850.0);
            }
        });
        //Nun den Inhalt des Tabs laden und den Controller setzten
        loader = new FXMLLoader(getClass().getResource(Seiten.PROJEKTOFFEN));
        loader.setController(new ProjektOffeneController(m));
        mainNode = null;
        try {
            mainNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Inhalt in den Tab setzen
        tabProjektOffen.setContent(mainNode);

        if (m.getUserid() != 1)
            tabPane.getTabs().remove(2);
    }
}
