package aurestApp;

import aurestApp.controller.*;
import aurestApp.interfaces.Seiten;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.control.SegmentedButton;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

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
        if (!m.isadmin()) {
            ebt1.setVisible(false);
            ebt2.setVisible(false);
            ebt3.setVisible(false);
            ebt4.setVisible(false);
        }

//Hauptleiste anlegen
        bt1.setUserData("Projekt");
        bt1.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.TASKS).size(25.0).color(Color.BLACK));
        bt1.setContentDisplay(ContentDisplay.LEFT);

        bt2.setUserData("Service");
        bt2.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.WRENCH).size(25.0).color(Color.SANDYBROWN));
        bt2.setContentDisplay(ContentDisplay.LEFT);

        bt3.setUserData("eMails");
        bt3.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.ENVELOPE_ALT).size(25.0).color(Color.BLUE));
        bt3.setContentDisplay(ContentDisplay.LEFT);

        bt4.setUserData("Einstellungen");
        bt4.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.GEAR).size(25.0).color(Color.DARKGRAY));
        bt4.setContentDisplay(ContentDisplay.LEFT);

        bt5.setUserData("Info");
        bt5.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.INFO_CIRCLE).size(25.0).color(Color.BLUE));
        bt5.setContentDisplay(ContentDisplay.LEFT);

        bt6.setUserData("Beenden");
        bt6.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.CLOSE).size(25.0).color(Color.BLACK));
        bt6.setContentDisplay(ContentDisplay.LEFT);

        sb1.getButtons().addAll(bt1, bt2, bt3, bt4, bt5, bt6);
        sb1.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);

        sb1.getToggleGroup().selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
            if (new_toggle != null) {
                if (new_toggle.getUserData().equals("Einstellungen")) {
                    egp.setVisible(true);
                } else {
                    egp.setVisible(false);
                }
                if (new_toggle.getUserData().equals("Projekt")) {
                    pgp.setVisible(true);
                } else {
                    pgp.setVisible(false);
                }
                if (new_toggle.getUserData().equals("Service")) {
                    sgp.setVisible(true);
                } else {
                    sgp.setVisible(false);
                }
            }
        });

        //Leiste Projekt anlegen
        pbt1.setUserData("Projekt anlegen");
        pbt1.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.PLUS).size(15.0).color(Color.BLACK));
        pbt1.setContentDisplay(ContentDisplay.LEFT);

        pbt2.setUserData("Projekt Logbuch");
        pbt2.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.BOOK).size(15.0).color(Color.BLACK));
        pbt2.setContentDisplay(ContentDisplay.LEFT);

        pbt3.setUserData("Offene Projekte");
        pbt3.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.ARCHIVE).size(15.0).color(Color.BLACK));
        pbt3.setContentDisplay(ContentDisplay.LEFT);

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
        sbt1.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.PLUS).size(15.0).color(Color.BLACK));
        sbt1.setContentDisplay(ContentDisplay.LEFT);

        sbt2.setUserData("Service Logbuch");
        sbt2.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.BOOK).size(15.0).color(Color.BLACK));
        sbt2.setContentDisplay(ContentDisplay.LEFT);

        ssb.getButtons().addAll(sbt1, sbt2);
        ssb.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);

        //Leiste Einstellungen anlegen
        ebt1.setUserData("Mitarbeiter");
        ebt1.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.USER).size(15.0).color(Color.BLACK));
        ebt1.setContentDisplay(ContentDisplay.LEFT);

        ebt2.setUserData("Servicejahr");
        ebt2.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.CALENDAR).size(15.0).color(Color.BLACK));
        ebt2.setContentDisplay(ContentDisplay.LEFT);

        ebt3.setUserData("Ordnervorlagen");
        ebt3.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.FOLDER).size(15.0).color(Color.BLACK));
        ebt3.setContentDisplay(ContentDisplay.LEFT);

        ebt4.setUserData("Kunden");
        ebt4.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.USERS).size(15.0).color(Color.BLACK));
        ebt4.setContentDisplay(ContentDisplay.LEFT);

        ebt5.setUserData("Logindaten");
        ebt5.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.LOCK).size(15.0).color(Color.BLACK));
        ebt5.setContentDisplay(ContentDisplay.LEFT);

        sb2.getButtons().addAll(ebt1, ebt2, ebt3, ebt4, ebt5);
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