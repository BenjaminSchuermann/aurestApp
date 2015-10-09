package aurestApp.tools;

import aurestApp.Model;
import aurestApp.tools.eigeneklassen.Suchprojekt;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.io.*;

public class Logbuch {

    private static String urProjektOrdner;
    private static String projektOrdner;
    private static String serviceOrdner;

    public static void erstelleLogbuch(Model m, boolean projekt) {
        PrintWriter pWriter;
        try {
            pWriter = new PrintWriter(urProjektOrdner + "\\Logbuch.txt", "UTF-8");
            pWriter.println("Projekt-/Servicenr.\tDatum\t\tMA\t\tAnlagenteil\t\tTätigkeit");
            pWriter.println("-----------------------------------------------------------------------------------------------------------------");
            pWriter.close();
        } catch (FileNotFoundException e) {
            Dialoge.exceptionDialog(e, "Logbuchdatei kann nicht erstellt werden");
            e.printStackTrace();
            return;
        } catch (UnsupportedEncodingException e) {
            Dialoge.exceptionDialog(e, "Probleme beim Encoding der Datei");
            e.printStackTrace();
            return;
        }
        erweiterLogbuch(m, projekt);

    }

    public static void prüfeAufProjektLogbuch(Model m, boolean projekt) {
        String urProjektPfad = getUrProjektordner(m, projekt);
        if (urProjektPfad.isEmpty()) {
            Dialoge.DialogAnzeigeBox("fehler", "Das Ursprungsprojekt konnte nicht gefunden werden");
            return;
        }
        urProjektOrdner = urProjektPfad;
        if (new File(urProjektOrdner + "\\Logbuch.txt").exists())
            erweiterLogbuch(m, projekt);
        else
            erstelleLogbuch(m, projekt);

    }

    public static void erweiterLogbuch(Model m, boolean projekt) {
        if (projekt) {
            String projektPfad = getProjektordner(m);
            if (projektPfad.isEmpty()) {
                Dialoge.DialogAnzeigeBox("fehler", "Das Projekt konnte nicht gefunden werden");
                return;
            }
            projektOrdner = projektPfad;
            erweiterProjekteintrag(m);
        } else {
            String servicePfad = getServiceordner(m);
            if (servicePfad.isEmpty()) {
                Dialoge.DialogAnzeigeBox("fehler", "Der Service konnte nicht gefunden werden");
                return;
            }
            serviceOrdner = servicePfad;
            erweiterServiceeintrag(m);
        }

    }

    private static void erweiterProjekteintrag(Model m) {
        String text;
        String nummer = m.getProjektLogProjekt();
        //Service/Projektnummer erstellen und Tabs einfügen
        if (nummer.length() < 8)
            text = nummer + "\t\t\t";
        else if (nummer.length() < 16)
            text = nummer + "\t\t";
        else
            text = nummer + "\t";
        //Das gewählte Datum einfügen
        text += m.getProjektLogDatum() + "\t";
        //Den Mitarbeiter einfügen
        String mitarbeiter = m.getProjektLogMitarbeiter();
        if (mitarbeiter.length() < 8)
            text += mitarbeiter + "\t\t";
        else if (mitarbeiter.length() < 16)
            text += mitarbeiter + "\t";
        else
            text += mitarbeiter;
        //Den Anlagenteil/Ort einfügen
        String anlagenteil = m.getProjektLogAnlagenteil();
        if (anlagenteil.length() < 8)
            text += anlagenteil + "\t\t\t";
        else if (anlagenteil.length() < 16)
            text += anlagenteil + "\t\t";
        else if (anlagenteil.length() < 24)
            text += anlagenteil + "\t";
        else
            text += anlagenteil + "\t";

        //Projektbeschreibung einfügen
        text += m.getProjektLogBeschreibung().replace("\n", System.lineSeparator() + "\t\t\t\t\t\t\t\t\t\t");
        text += System.lineSeparator();

        //Und das ganze versuchen ans Logbuch anzuhängen
        try (FileWriter fw = new FileWriter(urProjektOrdner + "\\Logbuch.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(text);
        } catch (IOException e) {
            Dialoge.exceptionDialog(e, "Das Logbuch konnte nicht erweitert werden");
            return;
        }
        String verknüpfungZiel = projektOrdner + "\\Logbuch vom " + m.getProjektLogDatum().toString();
        String verknüpfungQuelle = urProjektOrdner + "\\Logbuch.txt";
        erstelleVerknüpfung(verknüpfungZiel, verknüpfungQuelle);

        Notifications.create().darkStyle()
                .title("Neuer Lobbucheintrag")
                .text("Der Eintrag wurde ins Logbuch übernommen")
                .showInformation();
        //Dialoge.DialogAnzeigeBox("info", "Der Eintrag wurde ins Logbuch übernommen");
    }

    private static void erweiterServiceeintrag(Model m) {
        String text;
        String nummer;
        if (m.isServiceLogCheckBox()) {
            nummer = "Ohne Servicenummer";
        } else {
            if (m.getServiceLogNummer().isEmpty()) {
                Dialoge.DialogAnzeigeBox("fehler", "Es wurde keine Servicenummer angegeben");
                return;
            }
            nummer = m.getServiceLogJahr() + "." + m.getServiceLogNummer();

        }

        //Service/Projektnummer erstellen und Tabs einfügen
        if (nummer.length() < 8)
            text = nummer + "\t\t\t";
        else if (nummer.length() < 16)
            text = nummer + "\t\t";
        else
            text = nummer + "\t";

        //Das gewählte Datum einfügen
        text += m.getServiceLogDatum() + "\t";
        //Den Mitarbeiter einfügen
        String mitarbeiter = m.getServiceLogMitarbeiter();
        if (mitarbeiter.length() < 8)
            text += mitarbeiter + "\t\t";
        else if (mitarbeiter.length() < 16)
            text += mitarbeiter + "\t";
        else
            text += mitarbeiter;

        //Den Anlagenteil/Ort einfügen
        String anlagenteil = m.getServiceLogAnlagenteil();
        if (anlagenteil.length() < 8)
            text += anlagenteil + "\t\t\t";
        else if (anlagenteil.length() < 16)
            text += anlagenteil + "\t\t";
        else if (anlagenteil.length() < 24)
            text += anlagenteil + "\t";
        else
            text += anlagenteil + "\t";

        //Service Kontakt einfügen
        if (!m.getServiceLogKontakt().isEmpty()) {
            text += "Kontakt: " + m.getServiceLogKontakt().replace("\n", System.lineSeparator() + "\t\t\t\t\t\t\t\t\t\t");
            text += System.lineSeparator() + "\t\t\t\t\t\t\t\t\t\t";
        }
        //Service Problem einfügen
        text += "Problem: " + m.getServiceLogProblem().replace("\n", System.lineSeparator() + "\t\t\t\t\t\t\t\t\t\t");
        text += System.lineSeparator() + "\t\t\t\t\t\t\t\t\t\t";
        //Service Ursache einfügen
        text += "Ursache: " + m.getServiceLogUrsache().replace("\n", System.lineSeparator() + "\t\t\t\t\t\t\t\t\t\t");
        text += System.lineSeparator() + "\t\t\t\t\t\t\t\t\t\t";
        //Service Lösung einfügen
        text += "Lösung: " + m.getServiceLogLösung().replace("\n", System.lineSeparator() + "\t\t\t\t\t\t\t\t\t\t");
        text += System.lineSeparator() + "\t\t\t\t\t\t\t\t\t\t";
        //Service Bermerkung einfügen
        if (!m.getServiceLogBemerkung().isEmpty()) {
            text += "Bemerkung / Hardware Austausch: " + m.getServiceLogBemerkung().replace("\n", System.lineSeparator() + "\t\t\t\t\t\t\t\t\t\t");
            text += System.lineSeparator();
        }

        //Und das ganze versuchen ans Logbuch anzuhängen
        try (FileWriter fw = new FileWriter(urProjektOrdner + "\\Logbuch.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(text);
        } catch (IOException e) {
            Dialoge.exceptionDialog(e, "Das Logbuch konnte nicht erweitert werden");
            return;
        }
        String verknüpfungZiel = serviceOrdner + "\\Logbuch vom " + m.getServiceLogDatum().toString();
        String verknüpfungQuelle = urProjektOrdner + "\\Logbuch.txt";
        erstelleVerknüpfung(verknüpfungZiel, verknüpfungQuelle);

        Notifications.create().darkStyle()
                .title("Neuer Lobbucheintrag")
                .text("Der Eintrag wurde ins Logbuch übernommen")
                .showInformation();

        //Dialoge.DialogAnzeigeBox("info", "Der Eintrag wurde ins Logbuch übernommen");
    }

    private static String getUrProjektordner(Model m, boolean projekt) {

        //Die ersten beiden Zahlen rausschneiden
        String pfadsp;
        String spkomplett;
        String urProjekt;
            if (projekt) {
                urProjekt = m.getProjektLogUrprojekt();
            } else {
                urProjekt = m.getServiceLogUrprojekt();
            }

        Suchprojekt urprojekt = Generator.findeProjekt(urProjekt);

        pfadsp = urprojekt.getProjektpfad().getAbsolutePath();
        spkomplett = urprojekt.getProjektname();

        if (spkomplett.isEmpty()) {
                return "";
        }
        //noinspection UnnecessaryLocalVariable
        System.out.println("der pfadsp:" + pfadsp);
        System.out.println("der spkomplett:" + spkomplett);

        return pfadsp;
    }

    private static String getProjektordner(Model m) {

        //Das Projekt suchen
        String spkomplett = Generator.searchFile(new File("P:/"), m.getProjektLogProjekt());
        if (spkomplett.isEmpty()) {
            return "";
        }
        //noinspection UnnecessaryLocalVariable
        String pfadsp = ("P:\\" + spkomplett).replace("/", "\\");
        //System.out.println("der pfadsp:" + pfadsp);
        //System.out.println("der spkomplett:" + spkomplett);

        return pfadsp;
    }

    private static String getServiceordner(Model m) {

        //Den Service suchen
        String serviceJahrOrdner = "Q:/" + "Service_" + m.getServicejahr().substring(2, 4) + "/";
        File serviceordner = new File(serviceJahrOrdner);
        System.out.println(serviceordner);
        if (!serviceordner.exists()) {
            Dialoge.DialogAnzeigeBox("fehler", "Serviceordner für das Jahr " + m.getServicejahr().substring(2, 4) + " nicht gefunden");
            return "";
        }

        String spkomplett = Generator.searchFile(serviceordner, m.getServiceLogNummer());
        System.out.println(spkomplett);
        if (spkomplett.isEmpty()) {
            return "";
        }

        //System.out.println("der pfadsp:" + pfadsp);
        //System.out.println("der spkomplett:" + spkomplett);

        return serviceJahrOrdner + spkomplett;
    }

    private static void erstelleVerknüpfung(String ziel, String quelle) {
        //ziel, der Link, quelle, der Ursprung
        String ausgabe = System.getProperty("java.io.tmpdir") + "\\makeLink.vbs";

        try {
            PrintWriter pWriter = new PrintWriter(ausgabe, "ISO-8859-1");
            pWriter.println("set WshShell = WScript.CreateObject(\"WScript.Shell\")");
            pWriter.println("set oShellLink = WshShell.CreateShortcut(\"" + ziel + ".lnk\")");
            pWriter.println("oShellLink.TargetPath = \"" + quelle + "\"");
            pWriter.println("oShellLink.WindowStyle = 1");
            pWriter.println("oShellLink.Hotkey = \"CTRL+SHIFT+F\"");
            pWriter.println("oShellLink.Description = \"Ben\"");
            pWriter.println("oShellLink.Save");
            pWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            Dialoge.exceptionDialog(e, "Fehler beim erstellen der Linkdatei für die Offerte");
            return;
        }

        try {
            Desktop.getDesktop().open(new File(System.getProperty("java.io.tmpdir") + "makeLink.vbs"));
        } catch (IOException e) {
            e.printStackTrace();
            Dialoge.exceptionDialog(e, "Fehler beim öffnen der Linkdatei für das Logbuch");
        }

    }
}
