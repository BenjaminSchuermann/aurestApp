package aurestApp.Tools;

import aurestApp.Model;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.sql.*;
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

    public static String getServiceJahr(Connection conn) throws SQLException {

        //Ein "Statement" erzeugen
        Statement stmt = conn.createStatement();
        //Statement mit der Abfrage füllen und ein Result erstellen
        ResultSet rs = stmt.executeQuery("select WERT from EINSTELLUNGEN WHERE CONFIG = 'servicejahr' LIMIT 1;");
        //Alle Results ausgeben
        String servicejahr = "5015";
        while (rs.next()) {
            servicejahr = rs.getString("WERT");
        }
        //fertig
        stmt.close();

        return servicejahr;
    }

    public static ArrayList<String> getVorlagen(Connection conn) throws SQLException {
        ArrayList<String> vorlagen = new ArrayList<>();
        //Ein "Statement" erzeugen
        Statement stmt = conn.createStatement();
        //Statement mit der Abfrage füllen und ein Result erstellen
        ResultSet rs = stmt.executeQuery("select WERT from EINSTELLUNGEN WHERE CONFIG = 'projektvorlage' LIMIT 1;");
        //Lade die Projektvorlage
        while (rs.next()) {
            vorlagen.add(rs.getString("WERT"));
        }
        rs = stmt.executeQuery("select WERT from EINSTELLUNGEN WHERE CONFIG = 'servicevorlage' LIMIT 1;");
        //Lade die Servicevorlage
        while (rs.next()) {
            vorlagen.add(rs.getString("WERT"));
        }
        rs = stmt.executeQuery("select WERT from EINSTELLUNGEN WHERE CONFIG = 'offertenvorlage' LIMIT 1;");
        //Lade die Offertenvorlage
        while (rs.next()) {
            vorlagen.add(rs.getString("WERT"));
        }
        //fertig
        stmt.close();

        return vorlagen;
    }

    public static ArrayList<String> getNamesFrom(Connection conn) throws SQLException {
        ArrayList<String> mitarbeiter = new ArrayList<>();
        //Ein "Statement" erzeugen
        Statement stmt = conn.createStatement();
        //Statement mit der Abfrage füllen und ein Result erstellen
        ResultSet rs = stmt.executeQuery("SELECT altname FROM ((SELECT m.NAME, a.NAME AS altname, 1 AS isalt FROM MITARBEITER  M JOIN ALTNAME  A ON M.ID = A.MITARBEITERID ) UNION ALL (SELECT m.NAME, m.NAME, 0 FROM MITARBEITER  m WHERE m.AKTIV )) n ORDER BY altname ASC;");
        //Lade die Mitarbeiternamen und alternativen Namen
        while (rs.next()) {
            mitarbeiter.add(rs.getString("ALTNAME"));
            }
        //Das Servicejahr und die Vorlagenpfade entfernen aus der Liste

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

    /*public static void ladeKunden(Model m) {

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
    }*/

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

    public static Connection connectDB(Model m) {
        //Und ab zur Datenbank, den Datenbanktreiber laden
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            Dialoge.exceptionDialog(e, "Der Datenbanktreiber kann nicht geladen werden");
            return null;
        }
        //Verbindungsparameter
        Connection conn = null;
        try {
            conn = DriverManager.
                    getConnection("jdbc:h2:tcp://" + m.getSERVERADRESSE() + "/" + m.getDATENBANK() + "", m.getDBLOGIN(), m.getDBPASSWORT());
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Die Datenbank kann nicht erreicht werden");
            return null;
        }
        return conn;
    }

    public static void closeDB(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Datenbankverbindung konnte nicht geschlossen werden");
        }
    }

    public static ArrayList<String> ladeKunden(Connection conn) throws SQLException {
        //Ein "Statement" erzeugen
        Statement stmt = conn.createStatement();
        //Statement mit der Abfrage füllen und ein Result erstellen
        ResultSet rs = stmt.executeQuery("select KUNDE from KUNDEN ORDER BY KUNDE ASC;");
        //Alle Results ausgeben
        ArrayList<String> kunden = new ArrayList<>();
        while (rs.next()) {
            kunden.add(rs.getString("KUNDE"));
        }
        //fertig
        stmt.close();
        return kunden;
    }

    public static boolean speicherKunden(Model m, ArrayList<String> kundeliste) throws SQLException {
        Connection conn = Settings.connectDB(m);
        if (conn == null)
            return false;
        Statement stmt = conn.createStatement();
        //Statement mit Insert erstellen
        for (String kunde : kundeliste) {
            stmt.execute("INSERT INTO KUNDEN ( KUNDE ) SELECT * FROM (SELECT '" + kunde + "') AS tmp WHERE NOT EXISTS ( SELECT KUNDE FROM KUNDEN  WHERE KUNDE  = '" + kunde + "') LIMIT 1;");
        }
        //fertig
        stmt.close();
        closeDB(conn);

        return true;
    }

}

