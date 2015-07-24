package aurestApp.controller;

import aurestApp.Model;
import aurestApp.Tools.Dialoge;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import static org.apache.commons.io.FileUtils.moveDirectory;

public class MenuProjektArchivierenController implements Initializable {
    private final Model m;
    Set<String> possibleSuggestions;
    private AutoCompletionBinding<String> autoCompletionBinding;

    @FXML
    private TextField projektnummer;

    public MenuProjektArchivierenController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        possibleSuggestions = new HashSet<>(Arrays.asList(new File("P:\\").list()));

        autoCompletionBinding = TextFields.bindAutoCompletion(projektnummer, possibleSuggestions);
    }

    @FXML
    private void handelArchivierenProjekt() {
        String spn = projektnummer.getText().split(" ")[0];

        File[] projekte = new File("P:\\").listFiles();
        if (projekte == null) {
            Dialoge.InfoAnzeigen("Es konnten keine Projekte gefunden werden. Vielleicht ist das Netzlaufwerk nicht als P:\\ eingebunden ?");
            return;
        }

        File projektpfad = new File("");

        for (File f : projekte)
            if (f.getName().startsWith(spn)) {
                //System.out.println(f.getName());
                projektpfad = f;
                break;
            }

        if (!projektpfad.isDirectory()) {
            Dialoge.DialogAnzeigeBox("error", "Fehler bei der Projektnummer :" + projektpfad.toString());
            return;
        }

        File[] projektdateien = projektpfad.listFiles();
        if (projektdateien == null) {
            Dialoge.DialogAnzeigeBox("error", "Es konnten keine Dateien oder Ordner im Projekt gefunden werden. Möglicherweise enthält das Projekt keine Dateien und Ordner ?");
            return;
        }

        String offerte = "";
        String urprojekt = "";
        for (File f : projektdateien) {
            if (f.getName().endsWith("lnk")) {
                if (f.getName().startsWith("Offerte"))
                    offerte = f.getName().split(" ")[1];
                if (f.getName().startsWith("UrProjekt"))
                    urprojekt = f.getName().split(" ")[2];
                //System.out.println(f.getName());
                //System.out.println(offerte);
                //System.out.println(urprojekt);
            }
        }

        File ziel = new File("Q:\\" + spn.substring(0, 2) + "_");
        if (!ziel.exists())
            if (!ziel.mkdir()) {
                Dialoge.DialogAnzeigeBox("error", "Verzeichnis " + "Q:\\" + spn.substring(0, 2) + "_" + " konnte nicht erstellt werden");
                return;
            }

        File target = new File(ziel.toString() + "\\" + projektpfad.getName());
        System.out.println(target.toString());

        //File target = new File(zielodner);

        try {
            moveDirectory(projektpfad, target);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
