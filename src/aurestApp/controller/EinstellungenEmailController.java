package aurestApp.controller;

import aurestApp.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class EinstellungenEmailController implements Initializable {
    private final Model m;
    @FXML
    private ListView<String> mitarbeiter;

    public EinstellungenEmailController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String mitarbeiterListe = "";
        for (String mitarbeiter : m.getMitarbeiterListe())
            mitarbeiterListe += mitarbeiter + System.getProperty("line.separator");

        ObservableList<String> names = FXCollections.observableArrayList(m.getMitarbeiterListe());
        mitarbeiter.setItems(names);

        //mitarbeiter.setText(mitarbeiterListe);
    }

    @FXML
    private void speicherEinstellungen(ActionEvent actionEvent) {
        //ArrayList<String> myList = new ArrayList<>(Arrays.asList(mitarbeiter.getText().split("\\n")));
        //m.setMitarbeiterListe(myList);
        //Settings.saveSettings(m);
    }

    /* Test und Vorbereitung zum eMail versenden mit Outlook
    @FXML
    private void mailEinstellungen(ActionEvent actionEvent) {
        CreateOutlookMail newMail = new CreateOutlookMail();
        newMail.setEmpfaenger("a@b.c");
        newMail.setBetreff("Test");
        newMail.setHTMLbody("This is an <b>HTML</b> test body.");
        newMail.sendEMail();
    }
    */
}
