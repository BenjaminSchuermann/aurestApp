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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        bt1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/32/application_from_storage.png"))));//.getGraphic().set.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.TASKS).size(25.0).color(Color.BLACK));
        bt1.setContentDisplay(ContentDisplay.LEFT);

        bt2.setUserData("Service");
        bt2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/32/wrench.png"))));
        bt2.setContentDisplay(ContentDisplay.LEFT);

        bt3.setUserData("eMails");
        bt3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/32/emails_stack.png"))));
        bt3.setContentDisplay(ContentDisplay.LEFT);

        bt4.setUserData("Einstellungen");
        bt4.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/32/setting_tools.png"))));
        bt4.setContentDisplay(ContentDisplay.LEFT);

        bt5.setUserData("Info");
        bt5.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/32/info_rhombus.png"))));
        bt5.setContentDisplay(ContentDisplay.LEFT);

        bt6.setUserData("Beenden");
        bt6.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/32/cancel.png"))));
        bt6.setContentDisplay(ContentDisplay.LEFT);

        sb1.getButtons().addAll(bt1, bt2, bt3, bt4, bt5, bt6);
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
        pbt1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/16/application_form_add.png"))));

        pbt1.setContentDisplay(ContentDisplay.LEFT);

        pbt2.setUserData("Projekt Logbuch");
        pbt2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/16/report_add.png"))));
        pbt2.setContentDisplay(ContentDisplay.LEFT);

        pbt3.setUserData("Offene Projekte");
        pbt3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/16/application_view_list.png"))));
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
        sbt1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/16/wrenchadd.png"))));
        sbt1.setContentDisplay(ContentDisplay.LEFT);

        sbt2.setUserData("Service Logbuch");
        sbt2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/16/report_add.png"))));
        sbt2.setContentDisplay(ContentDisplay.LEFT);

        ssb.getButtons().addAll(sbt1, sbt2);
        ssb.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);

        //Leiste Einstellungen anlegen
        ebt1.setUserData("Mitarbeiter");
        ebt1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/16/user_edit.png"))));
        ebt1.setContentDisplay(ContentDisplay.LEFT);

        ebt2.setUserData("Servicejahr");
        ebt2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/16/calendar_edit.png"))));
        ebt2.setContentDisplay(ContentDisplay.LEFT);

        ebt3.setUserData("Ordnervorlagen");
        ebt3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/16/folder_edit.png"))));
        ebt3.setContentDisplay(ContentDisplay.LEFT);

        ebt4.setUserData("Kunden");
        ebt4.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/16/vcard_edit.png"))));
        ebt4.setContentDisplay(ContentDisplay.LEFT);

        ebt5.setUserData("Logindaten");
        ebt5.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/icons/16/lock_edit.png"))));
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