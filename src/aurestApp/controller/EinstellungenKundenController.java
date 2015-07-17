package aurestApp.controller;

import aurestApp.Model;
import aurestApp.Tools.Dialoge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

public class EinstellungenKundenController implements Initializable {
    private final Model m;
    @FXML
    private TextArea kundenFeld;

    public EinstellungenKundenController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<String> kunden = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream("../cfg/kundenvorschlaege.ini"), "UTF-8"));
            String inhalt;
            while ((inhalt = in.readLine()) != null) {
                kunden.add(inhalt);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(kunden);

        String kundenListe = "";
        for (String kunde : kunden)
            kundenListe += kunde + System.getProperty("line.separator");
        kundenFeld.setText(kundenListe);
    }

    @FXML
    private void speicherEinstellungen(ActionEvent actionEvent) {
        ArrayList<String> myList = new ArrayList<>(Arrays.asList(kundenFeld.getText().split("\\n")));
        try (FileWriter fw = new FileWriter("../cfg/kundenvorschlaege.ini", false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (String s : myList) {
                out.println(s.trim());
            }
            Notifications.create().darkStyle()
                    .title("Speichern")
                    .text("Die Einstellungen wurden gespeichert")
                    .showInformation();
        } catch (IOException e) {
            Dialoge.exceptionDialog(e, "Fehler beim speichern der Einstellungen");

        }
        m.setKundennamen(myList);
    }
}
