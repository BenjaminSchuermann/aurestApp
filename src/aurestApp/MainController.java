package aurestApp;

import aurestApp.controller.*;
import aurestApp.interfaces.Seiten;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

class MainController implements Seiten, Initializable {
    private final Model m;
    @FXML
    private VBox programmInhalt;
    @FXML
    private MenuItem setMitarbeiterEinstellungen;
    @FXML
    private MenuItem setServiceEinstellungen;
    @FXML
    private MenuItem setVorlagenEinstellungen;
    @FXML
    private MenuItem setKundenEinstellungen;
    @FXML
    private MenuItem setLoginEinstellungen;

    public MainController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!m.isadmin()) {
            setMitarbeiterEinstellungen.setVisible(false);
            setServiceEinstellungen.setVisible(false);
            setVorlagenEinstellungen.setVisible(false);
            setKundenEinstellungen.setVisible(false);
        }
    }

    public void startBild() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UEBERINFO));
        loader.setController(new UeberInfoController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    private void setInhalt(Node node) {
        programmInhalt.getChildren().setAll(node);

    }

    @FXML
    private void handelProjekt(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PROJEKT));
        loader.setController(new ProjektSeiteController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelProjektArchivieren(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PROJEKTARCHIVIEREN));
        loader.setController(new MenuProjektArchivierenController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelService(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SERVICE));
        loader.setController(new ServiceSeiteController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelEmail(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EMAILSBEARBEITEN));
        loader.setController(new MenuEmailsUmbennenController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelMitarbeiterEinstellungen(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EINSTELLUNGENMITARBEITER));
        loader.setController(new EinstellungenMitarbeiterController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelServiceEinstellungen(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EINSTELLUNGENSERVICE));
        loader.setController(new EinstellungenServiceController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelVorlagenEinstellungen(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EINSTELLUNGENVORLAGEN));
        loader.setController(new EinstellungenVorlagenController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelKundenEinstellungen(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EINSTELLUNGENKUNDEN));
        loader.setController(new EinstellungenKundenController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelLoginEinstellungen(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EINSTELLUNGENLOGIN));
        loader.setController(new EinstellungenLoginDetailsController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }


    @FXML
    private void handelInfo(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UEBERINFO));
        loader.setController(new UeberInfoController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelExit(ActionEvent actionEvent) {
        m.closeDB();
        Platform.exit();
    }
}