package aurestApp.tools.eigeneklassen;

import aurestApp.Model;
import aurestApp.tools.Dialoge;
import javafx.application.Platform;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class Email {
    private final Model m;
    private final boolean debug = false;
    private int doppelcount = 0;
    private int anzahlGesamt = 0;
    private int anzahlOk = 0;
    private int anzahlDoppelt = 0;
    private int error = 0;

    public Email(Model m) {
        this.m = m;
    }

    public void renameMail(File file) {

        if (debug) System.out.println("Das ist File:" + file);
        boolean res = false;
        anzahlGesamt++;
        try {
            res = file.renameTo(new File(file.getParent() + "//" + getFileName(file) + ".msg"));
            if (!res) {
                checkNameDoppelt(new File(file.getParent() + "//" + getFileName(file) + ".msg"), file);
            } else
                anzahlOk++;

        } catch (IOException e) {
            error++;
            Platform.runLater(() -> Dialoge.exceptionDialog(e, "Fehler bei der Verarbeitung"));
            e.printStackTrace();

        }
        if (debug) System.out.println("Datei umbennant:" + res);
    }

    public String getStatus() {
        String text;
        String textDoppelt = "";
        if (anzahlDoppelt > 1)
            textDoppelt = anzahlDoppelt + " sind doppelt";
        else if (anzahlDoppelt == 1)
            textDoppelt = "1 ist doppelt";

        if (anzahlGesamt > 1)
            text = "Es wurden " + anzahlGesamt + " eMails gefunden. " + anzahlOk + " wurden umbennant. " + textDoppelt;
        else
            text = "Es wurde 1 eMail gefunden. " + anzahlOk + " wurde umbennant. " + textDoppelt;
        if (error == 1)
            text += "\nEs wurde ein Fehler bei der Bearbeitung festgestellt, ist eine Datei noch geöffnet ?";
        if (error > 1)
            text += "\nEs wurden " + error + " Fehler bei der Bearbeitung festgestellt, sind Dateien noch geöffnet ?";
        return text;
    }

    private void checkNameDoppelt(File filepruefen, File altername) {

        if (filepruefen.exists()) {
            try {
                boolean res = altername.renameTo(new File(altername.getParent() + "//Doppelt" + getFileName(filepruefen) + "(" + doppelcount++ + ").msg"));
                if (res)
                    anzahlDoppelt++;
                else {
                    error++;
                    Platform.runLater(() -> Dialoge.DialogAnzeigeBox("error", "Datei kann nicht umbenannt werden " + altername));
                    if (debug) System.out.println("Datei kann nicht umbenannt werden" + altername);
                }
            } catch (IOException e) {
                error++;
                Platform.runLater(() -> Dialoge.exceptionDialog(e, "Fehler bei der Verarbeitung"));
                e.printStackTrace();
            }
        }

    }

    private String getFileName(File dirl) throws IOException {
        //eMail Objekt anlegen
        OutlookMessage eMail = new OutlookMessage(dirl.getAbsolutePath());

        //erste Daten parsen und initialisieren
        String betreff = "";
        if (eMail.getSubject() != null)
            betreff = eMail.getSubject().replaceAll("[:*?\"<>|\\\\/]", "");
        //Tabs aus dem Betreff entfernen.. wer auch immer sowas macht...
        betreff = betreff.replaceAll("\t", " ");
        //System.out.println(betreff);
        String from = "";

        if (eMail.getDisplayFrom() == null)
            from = "Entwurf";
        else if (!eMail.getDisplayFrom().isEmpty())
            from = eMail.getDisplayFrom();

        String einaus = "E";

        //Kalender anlegen und mit Datum aus eMail bestücken
        Calendar cal = Calendar.getInstance();
        if (debug) System.out.println("getMessageDeliveryTime :" + eMail.getMessageDeliveryTime());
        cal.setTime(eMail.getMessageDeliveryTime());

        //Ersetz durch Liste !!! wird nun direkt in der for each Schleife gemacht
        //Die Hauseigenen Namen aus der Konfiguration lesen
        //System.out.println("mailfrom:"+from);
        //System.out.println(fromMails.length);
        //Ist der Absender in der Konfiguration aufgeführt, dann als Ausgang deklarieren
        if (from.equals("Entwurf"))
            einaus = "Entwurf";

        //Ein Label setzten um ganz aus den Schleifen raus zu kommen, wenn ein Treffer gefunden wurde
        //Mitarbeiter Namen vergleichen
        schleife:
        for (Mitarbeiter mitarbeiter : m.getMitarbeiterListe()) {
            if (mitarbeiter.getName().equals(from)) {
                einaus = "A";
                break;
            }
            //Für jeden Mitarbeiter die Altnamen vergleichen
            for (String altname : mitarbeiter.getAltnamen()) {
                if (altname.equals(from)) {
                    einaus = "A";
                    break schleife;
                }
            }
        }

        //Die Kalenderdaten auslesen
        Integer year = cal.get(Calendar.YEAR) - 2000;
        Integer month = cal.get(Calendar.MONTH) + 1; // Jan = 0, dec = 11
        Integer dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        Integer hourOfDay = cal.get(Calendar.HOUR_OF_DAY); // 24 hour clock
        Integer minute = cal.get(Calendar.MINUTE);
        Integer second = cal.get(Calendar.SECOND);

        //Und entsprechend zusammensetzte, hier die führenden 0 bei Zahlen <10 anfügen
        String Omonth = month.toString();
        if (month < 10) Omonth = "0" + Omonth;

        String OdayOfMonth = dayOfMonth.toString();
        if (dayOfMonth < 10) OdayOfMonth = "0" + OdayOfMonth;

        String OhourOfDay = hourOfDay.toString();
        if (hourOfDay < 10) OhourOfDay = "0" + OhourOfDay;

        String Ominute = minute.toString();
        if (minute < 10) Ominute = "0" + Ominute;

        String Osecond = second.toString();
        if (second < 10) Osecond = "0" + Osecond;

        //Den laaangen Dateinamen erzeugen
        //System.out.println(dateiname);

        //Und ausgeben
        return year.toString() + Omonth + OdayOfMonth + "_" + einaus + "_" + betreff + " " + OhourOfDay + "_" + Ominute + "_" + Osecond;
    }

    private String getProjektPfad(String Projektnummer) {
        String pfadsp;
        String spshort;
        String spkomplett;

        spshort = Projektnummer.substring(0, 2);
        //System.out.print(spshort);
        File test;
        if (Integer.parseInt(spshort) <= 60) {
            test = new File("Q:/" + spshort + "__/");
        } else {
            test = new File("Q:/" + spshort + "_/");
        }
        spkomplett = searchFile(test, Projektnummer);

        //System.out.print(test.toString());
        //System.out.println(spkomplett);

        if (Integer.parseInt(spshort) <= 60) {
            pfadsp = "Q:/" + spshort + "__/" + spkomplett + "/Dok/E-Mail/";
        } else {
            pfadsp = "Q:/" + spshort + "_/" + spkomplett + "/Dok/E-Mail/";
        }
        pfadsp = pfadsp.replace("/", "\\");
        if (debug) System.out.println(pfadsp);
        return pfadsp;
    }

    private String searchFile(File dir, String find) {
        File[] files = dir.listFiles();
        String matches = "";
        //System.out.println(dir);
        //System.out.println(find);
        if (files != null) {
            for (File file : files) {
                if (file.getName().startsWith(find)) { // überprüft ob der Dateiname mit dem Suchstring
                    // übereinstimmt. Groß-/Kleinschreibung wird
                    // ignoriert.
                    matches = file.getName();
                    if (debug) System.out.println(file.getName());
                    break;
                }
            }
        }
        return matches;
    }
}
