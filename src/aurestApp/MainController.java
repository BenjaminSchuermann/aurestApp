package aurestApp;

import aurestApp.controller.*;
import aurestApp.interfaces.Seiten;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SegmentedButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

class MainController implements Seiten, Initializable {
    private final Model m;
    @FXML
    private VBox programmInhalt;

    @FXML
    private SegmentedButton sb1;
    @FXML
    private ToggleButton bt1;
    @FXML
    private ToggleButton bt2;
    @FXML
    private ToggleButton bt3;
    @FXML
    private ToggleButton bt4;
    @FXML
    private ToggleButton bt5;
    @FXML
    private ToggleButton bt6;
    @FXML
    private ToggleButton bt7;

    @FXML
    private Pane pgp;
    @FXML
    private SegmentedButton psb;
    @FXML
    private ToggleButton pbt1;
    @FXML
    private ToggleButton pbt2;
    @FXML
    private ToggleButton pbt3;

    @FXML
    private Pane sgp;
    @FXML
    private SegmentedButton ssb;
    @FXML
    private ToggleButton sbt1;
    @FXML
    private ToggleButton sbt2;

    @FXML
    private Pane egp;
    @FXML
    private SegmentedButton sb2;
    @FXML
    private ToggleButton ebt1;
    @FXML
    private ToggleButton ebt2;
    @FXML
    private ToggleButton ebt3;
    @FXML
    private ToggleButton ebt4;
    @FXML
    private ToggleButton ebt5;

    public MainController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//Hauptleiste anlegen
        bt1.setUserData("Projekt");

        bt2.setUserData("Service");

        bt3.setUserData("eMails");

        bt4.setUserData("Einstellungen");

        bt5.setUserData("Info");

        bt6.setUserData("Beenden");

        bt7.setUserData("Offerte");

        sb1.getButtons().addAll(bt1, bt2, bt7, bt3, bt4, bt5, bt6);
        sb1.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);

        sb1.getToggleGroup().selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
            if (new_toggle != null) {
                if (new_toggle.getUserData().equals("Einstellungen")) {
                    egp.setVisible(true);
                } else {
                    egp.setVisible(false);
                    //Werden die Untersmenüs nicht angezeigt, dann die Anwahl deaktivieren
                    ebt1.setSelected(false);
                    ebt2.setSelected(false);
                    ebt3.setSelected(false);
                    ebt4.setSelected(false);
                    ebt5.setSelected(false);
                }
                if (new_toggle.getUserData().equals("Projekt")) {
                    pgp.setVisible(true);
                } else {
                    pgp.setVisible(false);
                    //Werden die Untersmenüs nicht angezeigt, dann die Anwahl deaktivieren
                    pbt1.setSelected(false);
                    pbt2.setSelected(false);
                    pbt3.setSelected(false);
                }
                if (new_toggle.getUserData().equals("Service")) {
                    sgp.setVisible(true);
                } else {
                    sgp.setVisible(false);
                    //Werden die Untersmenüs nicht angezeigt, dann die Anwahl deaktivieren
                    sbt1.setSelected(false);
                    sbt2.setSelected(false);
                }
            }
        });

        //Leiste Projekt anlegen
        pbt1.setUserData("Projekt anlegen");

        pbt2.setUserData("Projekt Logbuch");

        pbt3.setUserData("Offene Projekte");

        //Zum Entwicklen nur meinem Login den dritten Button anzeigen
        if (m.getUserid() != 1) {
            psb.getButtons().addAll(pbt1, pbt2);
            pbt3.setVisible(false);
        } else {
            psb.getButtons().addAll(pbt1, pbt2, pbt3);
        }

        psb.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);

        //Leiste Service anlegen
        sbt1.setUserData("Service anlegen");

        sbt2.setUserData("Service Logbuch");

        ssb.getButtons().addAll(sbt1, sbt2);
        ssb.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);

        //Leiste Einstellungen anlegen
        ebt1.setUserData("Mitarbeiter");

        ebt2.setUserData("Servicejahr");

        ebt3.setUserData("Ordnervorlagen");

        ebt4.setUserData("Kunden");

        ebt5.setUserData("Logindaten");
        if (!m.isadmin()) {
            ebt1.setVisible(false);
            ebt2.setVisible(false);
            ebt3.setVisible(false);
            ebt4.setVisible(false);
            sb2.getButtons().addAll(ebt5);
        } else {
            sb2.getButtons().addAll(ebt1, ebt2, ebt3, ebt4, ebt5);
        }
        sb2.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);

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
    private void handelProjektAnlegen(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PROJEKTERSTELLEN));
        loader.setController(new ProjektAnlegenController(m, programmInhalt, pbt2));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelProjektLogbuch(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PROJEKTLOGBUCH));
        loader.setController(new ProjektLogbuchController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelOffeneProjekte(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PROJEKTOFFEN));
        loader.setController(new ProjektOffeneController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelProjektArchivieren(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PROJEKTARCHIVIEREN));
        loader.setController(new MenuProjektArchivierenController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelServiceAnlegen(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SERVICEERSTELLEN));
        loader.setController(new ServiceAnlegenController(m, programmInhalt, sbt2));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelServiceLogbuch(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SERVICELOGBUCH));
        loader.setController(new ServiceLogbuchController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelOfferte(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(OFFERTEERSTELLEN));
        loader.setController(new MenuOfferteController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelEmail(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EMAILSBEARBEITEN));
        loader.setController(new MenuEmailsUmbennenController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelMitarbeiterEinstellungen(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EINSTELLUNGENMITARBEITER));
        loader.setController(new EinstellungenMitarbeiterController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
        if (programmInhalt.getScene().getWindow().getWidth() < 970.0)
            programmInhalt.getScene().getWindow().setWidth(970.0);
    }

    @FXML
    private void handelServiceEinstellungen(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EINSTELLUNGENSERVICE));
        loader.setController(new EinstellungenServiceController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelVorlagenEinstellungen(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EINSTELLUNGENVORLAGEN));
        loader.setController(new EinstellungenVorlagenController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelKundenEinstellungen(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EINSTELLUNGENKUNDEN));
        loader.setController(new EinstellungenKundenController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelLoginEinstellungen(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EINSTELLUNGENLOGIN));
        loader.setController(new EinstellungenLoginDetailsController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }


    @FXML
    private void handelInfo(ActionEvent actionEvent) throws IOException {
        checkSize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UEBERINFO));
        loader.setController(new UeberInfoController(m));
        Node mainNode = loader.load();
        setInhalt(mainNode);
    }

    @FXML
    private void handelExit(ActionEvent actionEvent) {
        m.closeDB();
        System.exit(0);
    }

    private void checkSize() {
        if (programmInhalt.getScene().getWindow().getHeight() < 850.0)
            programmInhalt.getScene().getWindow().setHeight(850.0);
        if (programmInhalt.getScene().getWindow().getWidth() < 850.0)
            programmInhalt.getScene().getWindow().setWidth(850.0);
    }
}