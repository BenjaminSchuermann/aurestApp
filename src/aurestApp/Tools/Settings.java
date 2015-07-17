package aurestApp.Tools;

import aurestApp.Model;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Settings {

    public static void checkSettings() {

        File file = new File("../cfg/");
        if (!file.exists()) {
            file.mkdirs();

        }
        //Allgemeine Settings
        file = new File("../cfg/settings.ini");
        if (!file.exists()) {
            Calendar date = new GregorianCalendar();
            try {
                PrintWriter pWriter = new PrintWriter("../cfg/settings.ini", "UTF-8");
                pWriter.println(date.get(Calendar.YEAR) + 3000);
                pWriter.println("P:\\Projekteinteilung");
                pWriter.println("P:\\Servicevorlage");
                pWriter.close();

            } catch (IOException e) {
                Dialoge.exceptionDialog(e, "Fehler beim anlegen der Konfigurationsdatei");
                e.printStackTrace();
            }
        }
        //Kundennamen
        file = new File("../cfg/kundenvorschlaege.ini");
        if (!file.exists()) {
            try {
                PrintWriter pWriter = new PrintWriter("../cfg/kundenvorschlaege.ini", "UTF-8");
                pWriter.close();

            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getServiceJahr(){

        String zeile1 = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream("../cfg/settings.ini"), "UTF-8"));
            try {
                zeile1 = br.readLine();
                br.close();
            } catch (IOException e) {
                Dialoge.exceptionDialog(e, "Fehler beim auslesen der Konfigurationsdatei");
            }
        } catch (FileNotFoundException e) {
            Dialoge.exceptionDialog(e, "Konfigurationsdatei nicht gefunden");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        //System.out.println(zeile1);


        //try (PrintWriter pWriter = new PrintWriter(new FileWriter(settingsfile, true),true)) {
        //    pWriter.println("set WshShell = WScript.CreateObject(\"WScript.Shell\")");
        //    pWriter.close();
        //}
        return zeile1;

    }

    public static ArrayList<String> getVorlagen() {
        ArrayList<String> vorlagen = new ArrayList<>();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream("../cfg/settings.ini"), "UTF-8"));            //Servicejahr überspringen
            in.readLine();
            //Vorlageordner lesen
            vorlagen.add(in.readLine());
            vorlagen.add(in.readLine());
            in.close();
        } catch (IOException e) {
            Dialoge.DialogAnzeigeBox("fehler", "Fehler beim auslesen der Mitarbeiter");
            e.printStackTrace();
        }

        return vorlagen;
    }

    public static ArrayList<String> getNamesFrom() {
        ArrayList<String> mitarbeiter = new ArrayList<>();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream("../cfg/settings.ini"), "UTF-8"));
            String inhalt;
            while ((inhalt = in.readLine()) != null) {
                mitarbeiter.add(inhalt);
            }
            in.close();
        } catch (IOException e) {
            Dialoge.DialogAnzeigeBox("fehler", "Fehler beim auslesen der Mitarbeiter");
            e.printStackTrace();
        }
        //Das Servicejahr und die Vorlagenpfade entfernen aus der Liste
        mitarbeiter.remove(0);
        mitarbeiter.remove(0);
        mitarbeiter.remove(0);
        return mitarbeiter;
    }

    public static void saveSettings(Model m) {

        ArrayList<String> konfig = new ArrayList<>();
        konfig.add(m.getServicejahr());
        konfig.add(m.getVorlagenProjekt());
        konfig.add(m.getVorlagenService());
        konfig.addAll(m.getMitarbeiterListe());
        setSettings(konfig);
    }

    public static void ladeKunden(Model m) {

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

        m.setKundennamen(kunden);
    }

    public static void saveKunde(String text) {

        if (text.isEmpty())
            return;

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

        if (!kunden.contains(text)) {
            try (FileWriter fw = new FileWriter("../cfg/kundenvorschlaege.ini", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(text.trim());
                out.close();
                Notifications.create().darkStyle().darkStyle()
                        .title("Neuer Kunde")
                        .text("Neuer Kundeneintrag wurde gespeichert")
                        .showInformation();
            } catch (IOException ignored) {
            }
        }
    }

    private static void setSettings(ArrayList<String> konfig) {

        try {
            PrintWriter pWriter = new PrintWriter("../cfg/settings.ini", "UTF-8");

            //try (BufferedWriter bw = new BufferedWriter(new FileWriter("settings.ini", false))) {
            for (String aKonfig : konfig) {
                pWriter.println(aKonfig.trim());
                //bw.write(aKonfig.trim());
                //bw.write(System.lineSeparator());
            }
            pWriter.close();
            //bw.close();
            //Dialoge.DialogAnzeigeBox("info", "Die Einstellungen wurden gespeichert");
            Notifications.create().darkStyle().darkStyle()
                    .title("Speichern")
                    .text("Die Einstellungen wurden gespeichert")
                    .showInformation()
            ;
        } catch (IOException e) {
            Dialoge.exceptionDialog(e, "Fehler beim speichern der Einstellungen");
        }

    }


}

