package aurestApp.Tools;

import aurestApp.Model;
import org.controlsfx.control.Notifications;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Settings {

    public static void ladeMitarbeiter(Model m) {
        ArrayList<String> mitarbeiter = new ArrayList<>();
        //Ein "Statement" erzeugen
        Statement stmt;
        try {
            stmt = m.getConn().createStatement();
            //Statement mit der Abfrage füllen und ein Result erstellen
            ResultSet rs = stmt.executeQuery("SELECT altname FROM ((SELECT m.NAME, a.NAME AS altname, 1 AS isalt FROM MITARBEITER  M JOIN ALTNAME  A ON M.ID = A.MITARBEITERID ) UNION ALL (SELECT m.NAME, m.NAME, 0 FROM MITARBEITER  m WHERE m.TELEAKTIV )) n ORDER BY altname ASC;");
            //Lade die Mitarbeiternamen und alternativen Namen
            while (rs.next()) {
                mitarbeiter.add(rs.getString("ALTNAME"));
            }
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim laden der Mitarbeiterliste");
            return;
        }
        m.setMitarbeiterListe(mitarbeiter);
    }

    public static void ladeVorlagen(Model m) {
        ArrayList<String> vorlagen = new ArrayList<>();
        //Ein "Statement" erzeugen
        Statement stmt;
        try {
            stmt = m.getConn().createStatement();

            //Statement mit der Abfrage füllen und ein Result erstellen
            ResultSet rs = stmt.executeQuery("SELECT WERT FROM EINSTELLUNGEN WHERE CONFIG = 'projektvorlage' LIMIT 1;");
            //Lade die Projektvorlage
            while (rs.next()) {
                vorlagen.add(rs.getString("WERT"));
            }
            rs = stmt.executeQuery("SELECT WERT FROM EINSTELLUNGEN WHERE CONFIG = 'servicevorlage' LIMIT 1;");
            //Lade die Servicevorlage
            while (rs.next()) {
                vorlagen.add(rs.getString("WERT"));
            }
            rs = stmt.executeQuery("SELECT WERT FROM EINSTELLUNGEN WHERE CONFIG = 'offertenvorlage' LIMIT 1;");
            //Lade die Offertenvorlage
            while (rs.next()) {
                vorlagen.add(rs.getString("WERT"));
            }
            //fertig
            stmt.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim laden der Vorlagenpfade");
            return;
        }
        m.setVorlagen(vorlagen);
    }

    public static void speicherVorlagen(Model m, ArrayList<String> vorlagen) {

        Statement stmt;
        try {
            stmt = m.getConn().createStatement();
            //Speicher die Projektvorlage
            stmt.execute("UPDATE EINSTELLUNGEN SET WERT ='" + vorlagen.get(0) + "' WHERE CONFIG ='projektvorlage';");
            //Speicher die Servicevorlage
            stmt.execute("UPDATE EINSTELLUNGEN SET WERT ='" + vorlagen.get(1) + "' WHERE CONFIG ='servicevorlage';");
            //Speicher die Offertenvorlage
            stmt.execute("UPDATE EINSTELLUNGEN SET WERT ='" + vorlagen.get(2) + "' WHERE CONFIG ='offertenvorlage';");
            //fertig
            stmt.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim speichern der Einstellungen");
            return;
        }
        //und neu laden
        ladeVorlagen(m);
        //Meldung rausgeben
        Notifications.create().darkStyle()
                .title("Speichern")
                .text("Die Einstellungen wurden gespeichert")
                .showInformation();

    }

    public static void ladeServiceJahr(Model m) {

        //Ein "Statement" erzeugen
        Statement stmt;
        String servicejahr = "";
        try {
            stmt = m.getConn().createStatement();
            //Statement mit der Abfrage füllen und ein Result erstellen
            ResultSet rs = stmt.executeQuery("SELECT WERT FROM EINSTELLUNGEN WHERE CONFIG = 'servicejahr' LIMIT 1;");
            //Alle Results ausgeben

            while (rs.next()) {
                servicejahr = rs.getString("WERT");
            }
            //fertig
            stmt.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim laden des Servicejahres");
            return;
        }
        m.setServicejahr(servicejahr);
    }

    public static void speicherServicejahr(Model m, String jahr) {

        Statement stmt;
        try {
            stmt = m.getConn().createStatement();
            //Speicher die Projektvorlage
            stmt.execute("UPDATE EINSTELLUNGEN SET WERT ='" + jahr.trim() + "' WHERE CONFIG ='servicejahr';");
            //fertig
            stmt.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim speichern der Einstellung");
            return;
        }
        //und neu laden
        ladeServiceJahr(m);
        //Meldung rausgeben
        Notifications.create().darkStyle()
                .title("Speichern")
                .text("Die Einstellung wurden gespeichert")
                .showInformation();

    }

    public static void ladeKunden(Model m) {

        //Ein "Statement" erzeugen
        Statement stmt;
        ArrayList<String> kunden = new ArrayList<>();
        try {
            stmt = m.getConn().createStatement();
            //Statement mit der Abfrage füllen und ein Result erstellen
            ResultSet rs = stmt.executeQuery("SELECT KUNDE FROM KUNDEN ORDER BY KUNDE ASC;");
            //Alle Results ausgeben
            while (rs.next()) {
                kunden.add(rs.getString("KUNDE"));
            }
            //fertig
            stmt.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim laden der Kundendaten");
            return;
        }
        m.setKundennamen(kunden);
    }

    public static void speicherKunden(Model m, ArrayList<String> kundeliste) {

        Statement stmt;
        try {
            stmt = m.getConn().createStatement();
            //Statement mit Insert erstellen
            for (String kunde : kundeliste) {
                stmt.execute("INSERT INTO KUNDEN ( KUNDE ) SELECT * FROM (SELECT '" + kunde + "') AS tmp WHERE NOT EXISTS ( SELECT KUNDE FROM KUNDEN  WHERE KUNDE  = '" + kunde + "') LIMIT 1;");
            }
            //fertig
            stmt.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim Speichern");
        }
        //und neu laden
        ladeKunden(m);
        //Meldung rausgeben
        Notifications.create().darkStyle()
                .title("Speichern")
                .text("Die Einstellungen wurden gespeichert")
                .showInformation();
    }

    public static void speicherKunde(Model m, String kunde) {

        if (kunde.isEmpty())
            return;

        if (!m.getKundennamen().contains(kunde.trim())) {
            Statement stmt;
            try {
                stmt = m.getConn().createStatement();
                //Statement mit Insert erstellen
                stmt.execute("INSERT INTO KUNDEN ( KUNDE ) SELECT * FROM (SELECT '" + kunde.trim() + "') AS tmp WHERE NOT EXISTS ( SELECT KUNDE FROM KUNDEN  WHERE KUNDE  = '" + kunde.trim() + "') LIMIT 1;");
                //fertig
                stmt.close();
            } catch (SQLException e) {
                Dialoge.exceptionDialog(e, "Fehler beim speichern des Kunden");
                return;
            }
            //und neu laden
            ladeKunden(m);
            //Meldung rausgeben
            Notifications.create().darkStyle()
                    .title("Neuer Kunde gefunden")
                    .text("Der neue Kunde wurde gespeichert")
                    .showInformation();
        }
    }
}

