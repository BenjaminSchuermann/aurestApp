package aurestApp.controller;

import aurestApp.Model;
import aurestApp.Tools.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EinstellungenEmailController implements Initializable {
    private final Model m;
    @FXML
    private TextArea mitarbeiter;

    public EinstellungenEmailController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String mitarbeiterListe = "";
        for (String mitarbeiter : m.getMitarbeiterListe())
            mitarbeiterListe += mitarbeiter + System.getProperty("line.separator");
        mitarbeiter.setText(mitarbeiterListe);
    }

    @FXML
    private void speicherEinstellungen(ActionEvent actionEvent) {
        ArrayList<String> myList = new ArrayList<>(Arrays.asList(mitarbeiter.getText().split("\\n")));
        m.setMitarbeiterListe(myList);
        Settings.saveSettings(m);
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
