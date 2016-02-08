package aurestApp.tools;

import aurestApp.Model;
import aurestApp.tools.eigeneklassen.Kunde;
import aurestApp.tools.eigeneklassen.Mitarbeiter;
import aurestApp.tools.eigeneklassen.Projekt;
import javafx.collections.ObservableList;
import org.controlsfx.control.Notifications;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Settings {

    public static boolean ladeMitarbeiter(Model m) {
        ArrayList<Mitarbeiter> mitarbeiter = new ArrayList<>();
        //Ein "Statement" erzeugen
        Statement stmt;
        Statement stmt2;
        try {
            stmt = m.getConn().createStatement();
            stmt2 = m.getConn().createStatement();
            //Statement mit der Abfrage füllen und ein Result erstellen
            //ResultSet rs = stmt.executeQuery("SELECT mitarbeitername FROM ((SELECT m.Name, a.Name AS mitarbeitername, 1 AS isalt FROM Mitarbeiter m JOIN Altname a ON m.id = a.MitarbeiterID WHERE m.eMailAktiv) UNION ALL (SELECT m.Name, m.Name, 0 FROM Mitarbeiter m WHERE m.eMailAktiv ))n ORDER BY mitarbeitername, isalt;");
            ResultSet rs = stmt.executeQuery("SELECT m.*, l.* FROM Mitarbeiter m LEFT JOIN Logins l ON m.id = l.MitarbeiterID ORDER BY m.Name ASC");
            //Lade die Mitarbeiternamen und alternativen Namen
            while (rs.next()) {
                int userid = rs.getInt("ID");
                ResultSet rsalts = stmt2.executeQuery("SELECT Name FROM Altname a WHERE a.MitarbeiterID = " + userid + " ORDER BY a.Name ASC");
                ArrayList<String> altnamen = new ArrayList<>();
                while (rsalts.next()) {
                    altnamen.add(rsalts.getString("Name"));
                }
                mitarbeiter.add(new Mitarbeiter(userid, rs.getString("Name"), rs.getString("eMail"), rs.getString("Kurz"), rs.getBoolean("TeleAktiv"), rs.getBoolean("eMailAktiv"), rs.getBoolean("isAdmin"), rs.getBoolean("Aktiv"), rs.getString("Login"), rs.getString("Passwort"), altnamen));
            }
            //fertig
            stmt.close();
            stmt2.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim laden der Mitarbeiterliste");
            return false;
        }
        m.setMitarbeiterListe(mitarbeiter);
        return true;
    }

    public static void updateMitarbeiter(Model m, Mitarbeiter mitarbeiter, ObservableList<Mitarbeiter> obMitarbeiter) {
        Statement stmt;
        try {
            stmt = m.getConn().createStatement();
            //Speichern der Personendaten
            stmt.execute("UPDATE Mitarbeiter SET " +
                    "Name ='" + mitarbeiter.getName() + "', " +
                    "Kurz ='" + mitarbeiter.getKurz() + "'," +
                    "eMail ='" + mitarbeiter.getEmail() + "'," +
                    "TeleAktiv ='" + (mitarbeiter.getTeleAktiv() ? 1 : 0) + "'," +
                    "eMailAktiv ='" + (mitarbeiter.geteMailAktiv() ? 1 : 0) + "'," +
                    "isAdmin ='" + (mitarbeiter.getIsAdmin() ? 1 : 0) + "'" +
                    " WHERE id ='" + mitarbeiter.getUserID() + "';");
            //Speichern der Logindaten
            stmt.execute("UPDATE Logins SET " +
                    "Login ='" + mitarbeiter.getLogin() + "', " +
                    "Passwort ='" + mitarbeiter.getPasswort() + "'," +
                    "Aktiv ='" + (mitarbeiter.getLoginAktiv() ? 1 : 0) + "'" +
                    " WHERE MitarbeiterID ='" + mitarbeiter.getUserID() + "';");

            //Die alternativen Namen für den Mitarbeiter löschen....
            stmt.execute("DELETE FROM Altname WHERE MitarbeiterID = '" + mitarbeiter.getUserID() + "';");

            //...und dann wieder hinzufügen
            for (String s : mitarbeiter.getAltnamen()) {
                stmt.execute("REPLACE INTO Altname (MitarbeiterID, Name) VALUES ('" + mitarbeiter.getUserID() + "','" + s + "');");
            }
            stmt.close();

        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim speichern der Einstellungen");
            return;
        }
        //und neu laden
        if (!ladeMitarbeiter(m))
            return;
        obMitarbeiter.setAll(m.getMitarbeiterListe());
        //Meldung rausgeben
        Notifications.create().darkStyle()
                .title("Speichern")
                .text("Die Einstellungen wurden gespeichert")
                .showInformation();

    }

    public static void addMitarbeiter(Model m, Mitarbeiter mitarbeiter, ObservableList<Mitarbeiter> obMitarbeiter) {
        Statement stmt;
        try {
            stmt = m.getConn().createStatement();

            //Speichern der Personendaten
            stmt.execute("INSERT INTO `aurestApp`.`Mitarbeiter`(`Name`, `Kurz`, `eMail`, `TeleAktiv`, `eMailAktiv`, `isAdmin`) VALUES (" +
                    "'" + mitarbeiter.getName() + "'," +
                    "'" + mitarbeiter.getKurz() + "'," +
                    "'" + mitarbeiter.getEmail() + "'," +
                    "'" + (mitarbeiter.getTeleAktiv() ? 1 : 0) + "'," +
                    "'" + (mitarbeiter.geteMailAktiv() ? 1 : 0) + "'," +
                    "'" + (mitarbeiter.getIsAdmin() ? 1 : 0) + "');");

            //Statement mit der Abfrage füllen und ein Result erstellen
            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `aurestApp`.`Mitarbeiter` WHERE `Name` ='"+mitarbeiter.getName()+"';");
            //Holen der userID des Mitarbeiters
            while (rs.next()) {
                mitarbeiter.setUserID(rs.getInt("id"));
            }

            //Speichern der Logindaten
            stmt.execute("INSERT INTO `aurestApp`.`Logins`(`Login`, `Passwort`, `MitarbeiterID`, `Aktiv`) VALUES ("+
                    "'" + mitarbeiter.getLogin() + "'," +
                    "'" + mitarbeiter.getPasswort() + "'," +
                    "'" + mitarbeiter.getUserID() + "'," +
                    "'" + (mitarbeiter.getLoginAktiv() ? 1 : 0) + "');");

            //Mitarbeiter alternative Namen hinzufügen
            for (String s : mitarbeiter.getAltnamen()) {
                stmt.execute("INSERT INTO `aurestApp`.`Altname` (`MitarbeiterID`, `Name`) VALUES (" +
                        "'" + mitarbeiter.getUserID() + "'," +
                        "'" + s + "');");
            }
            stmt.close();

        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim speichern der Daten");
            return;
        }
        //und neu laden
        if (!ladeMitarbeiter(m))
            return;
        obMitarbeiter.setAll(m.getMitarbeiterListe());
        //Meldung rausgeben
        Notifications.create().darkStyle()
                .title("Speichern")
                .text("Der neue Mitarbeiter wurde angelegt")
                .showInformation();
    }

    public static void deleteMitarbeiter(Model m, Mitarbeiter mitarbeiter, ObservableList<Mitarbeiter> obMitarbeiter) {
        Statement stmt;
        try {
            stmt = m.getConn().createStatement();
            //Löschen der Personendaten
            stmt.execute("DELETE FROM Mitarbeiter WHERE id ='" + mitarbeiter.getUserID() + "';");
            //Löschen der Logindaten
            stmt.execute("DELETE FROM Logins WHERE MitarbeiterID ='" + mitarbeiter.getUserID() + "';");

            //Die alternativen Namen für den Mitarbeiter löschen....
            stmt.execute("DELETE FROM Altname WHERE MitarbeiterID = '" + mitarbeiter.getUserID() + "';");

            stmt.close();

        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim löschen des Mitarbeiters");
            return;
        }
        //und neu laden
        if (!ladeMitarbeiter(m))
            return;
        obMitarbeiter.setAll(m.getMitarbeiterListe());
        //Meldung rausgeben
        Notifications.create().darkStyle()
                .title("Mitarbeiter löschen")
                .text("Der Mitarbeiter wurde erfolgreich gelöscht")
                .showInformation();
    }

    public static boolean ladeVorlagen(Model m) {
        ArrayList<String> vorlagen = new ArrayList<>();
        //Ein "Statement" erzeugen
        Statement stmt;
        try {
            stmt = m.getConn().createStatement();

            //Statement mit der Abfrage füllen und ein Result erstellen
            ResultSet rs = stmt.executeQuery("SELECT wert FROM Einstellungen WHERE config = 'projektvorlage' LIMIT 1;");
            //Lade die Projektvorlage
            while (rs.next()) {
                vorlagen.add(rs.getString("wert"));
            }
            rs = stmt.executeQuery("SELECT wert FROM Einstellungen WHERE config = 'servicevorlage' LIMIT 1;");
            //Lade die Servicevorlage
            while (rs.next()) {
                vorlagen.add(rs.getString("wert"));
            }
            rs = stmt.executeQuery("SELECT wert FROM Einstellungen WHERE config = 'offertenvorlage' LIMIT 1;");
            //Lade die Offertenvorlage
            while (rs.next()) {
                vorlagen.add(rs.getString("wert"));
            }
            //fertig
            stmt.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim laden der Vorlagenpfade");
            return false;
        }
        m.setVorlagen(vorlagen);
        return true;
    }

    public static void speicherVorlagen(Model m, ArrayList<String> vorlagen) {
        Statement stmt;
        try {
            stmt = m.getConn().createStatement();
            //Speicher die Projektvorlage
            stmt.execute("UPDATE Einstellungen SET wert ='" + vorlagen.get(0).replace("\\", "\\\\") + "' WHERE config ='projektvorlage';");
            //Speicher die Servicevorlage
            stmt.execute("UPDATE Einstellungen SET wert ='" + vorlagen.get(1).replace("\\", "\\\\") + "' WHERE config ='servicevorlage';");
            //Speicher die Offertenvorlage
            stmt.execute("UPDATE Einstellungen SET wert ='" + vorlagen.get(2).replace("\\", "\\\\") + "' WHERE config ='offertenvorlage';");
            //fertig
            stmt.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim speichern der Einstellungen");
            return;
        }
        //und neu laden
        if (!ladeVorlagen(m))
            return;
        //Meldung rausgeben
        Notifications.create().darkStyle()
                .title("Speichern")
                .text("Die Einstellungen wurden gespeichert")
                .showInformation();

    }

    public static boolean ladeServiceJahr(Model m) {
        //Ein "Statement" erzeugen
        Statement stmt;
        String servicejahr = "";
        try {
            stmt = m.getConn().createStatement();
            //Statement mit der Abfrage füllen und ein Result erstellen
            ResultSet rs = stmt.executeQuery("SELECT wert FROM Einstellungen WHERE config = 'servicejahr' LIMIT 1;");
            //Alle Results ausgeben

            while (rs.next()) {
                servicejahr = rs.getString("wert");
            }
            //fertig
            stmt.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim laden des Servicejahres");
            return false;
        }
        m.setServicejahr(servicejahr);
        return true;
    }

    public static void speicherServicejahr(Model m, String jahr) {
        //Ein "Statement" erzeugen
        Statement stmt;
        try {
            stmt = m.getConn().createStatement();
            //Speicher die Projektvorlage
            stmt.execute("UPDATE Einstellungen SET wert ='" + jahr.trim() + "' WHERE config ='servicejahr';");
            //fertig
            stmt.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim speichern der Einstellung");
            return;
        }
        //und neu laden
        if (!ladeServiceJahr(m))
            return;
        //Meldung rausgeben
        Notifications.create().darkStyle()
                .title("Speichern")
                .text("Die Einstellung wurden gespeichert")
                .showInformation();

    }

    public static boolean ladeKunden(Model m) {
        //Ein "Statement" erzeugen
        Statement stmt;
        Statement stmt2;
        ArrayList<Kunde> kunden = new ArrayList<>();
        try {
            stmt = m.getConn().createStatement();
            stmt2 = m.getConn().createStatement();

            //Statement mit der Abfrage füllen und ein Result erstellen
            ResultSet rs = stmt.executeQuery("SELECT * FROM Kunden ORDER BY RealName ASC;");
            //Alle Results ausgeben
            while (rs.next()) {
                int kundenid = rs.getInt("id");
                ResultSet rsordner = stmt2.executeQuery("SELECT Name FROM KundenOrdner WHERE KundenID = " + kundenid + " ORDER BY Name ASC");
                ArrayList<String> ordner = new ArrayList<>();
                while (rsordner.next()) {
                    ordner.add(rsordner.getString("Name"));
                }
                kunden.add(new Kunde(kundenid, rs.getString("RealName"), rs.getString("Strasse"), rs.getString("Stadt"), rs.getInt("PLZ"), rs.getString("Land"), rs.getString("Telefon"), rs.getString("Fax"), ordner));
            }
            //fertig
            stmt.close();
            stmt2.close();

        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim laden der Kundendaten");
            return false;
        }
        m.setKunden(kunden);
        return true;
    }

    public static void addKunde(Model m, Kunde kunde) {
        Statement stmt;
        try {
            stmt = m.getConn().createStatement();

            //Speichern der Kundendaten
            stmt.execute("INSERT INTO `aurestApp`.`Kunden`(`RealName`, `Strasse`, `Stadt`, `PLZ`, `Land`) VALUES (" +
                    "'" + kunde.getName() + "'," +
                    "'" + kunde.getStrasse() + "'," +
                    "'" + kunde.getStadt() + "'," +
                    "'" + (kunde.getPlz()) + "'," +
                    "'" + (kunde.getLand()) + "');");

            //Statement mit der Abfrage füllen und ein Result erstellen
            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `aurestApp`.`Kunden` WHERE `RealName` ='" + kunde.getName() + "';");
            //Holen der userID des Mitarbeiters
            while (rs.next()) {
                kunde.setKundenID(rs.getInt("id"));
            }

            //Mitarbeiter alternative Namen hinzufügen
            for (String s : kunde.getOrdnernamen()) {
                stmt.execute("INSERT INTO `aurestApp`.`KundenOrdner` (`KundenID`, `Name`) VALUES (" +
                        "'" + kunde.getKundenID() + "'," +
                        "'" + s + "');");
            }
            stmt.close();

        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim speichern der Daten");
            return;
        }
        //und neu laden
        if (!ladeKunden(m))
            return;
        //Meldung rausgeben
        Notifications.create().darkStyle()
                .title("Speichern")
                .text("Der neue Kunde wurde angelegt")
                .showInformation();
    }

    public static void updateKunde(Model m, Kunde kunde) {
        Statement stmt;
        try {
            stmt = m.getConn().createStatement();
            //Speichern der Kundendaten
            stmt.execute("UPDATE Kunden SET " +
                    "RealName ='" + kunde.getName() + "', " +
                    "Strasse ='" + kunde.getStrasse() + "'," +
                    "PLZ ='" + kunde.getPlz() + "'," +
                    "Stadt ='" + kunde.getStadt() + "'," +
                    "Land ='" + kunde.getLand() + "'" +
                    " WHERE id =" + kunde.getKundenID() + ";");

            //...und dann wieder hinzufügen
            for (String s : kunde.getOrdnernamen()) {
                stmt.execute("INSERT INTO KundenOrdner (Name, KundenID) " +
                        "SELECT '" + s + "', " + kunde.getKundenID() + " FROM KundenOrdner " +
                        "WHERE NOT EXISTS (" +
                        "    SELECT Name FROM KundenOrdner WHERE Name = '" + s + "' " +
                        ") LIMIT 1;");
            }
            stmt.close();

        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim speichern der Einstellungen");
            return;
        }
        //und neu laden
        if (!ladeKunden(m))
            return;
        //Meldung rausgeben
        Notifications.create().darkStyle()
                .title("Speichern")
                .text("Die Einstellungen wurden gespeichert")
                .showInformation();

    }

    public static void deleteKunde(Model m, Kunde kunde) {
        Statement stmt;
        try {
            stmt = m.getConn().createStatement();
            //Löschen der Kundendaten
            stmt.execute("DELETE FROM Kunden WHERE id ='" + kunde.getKundenID() + "';");

            //Die alternativen Ordner für den Kunden löschen....
            stmt.execute("DELETE FROM KundenOrdner WHERE KundenID = '" + kunde.getKundenID() + "';");

            stmt.close();

        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim löschen des Kunden");
            return;
        }
        //und neu laden
        if (!ladeKunden(m))
            return;
        //Meldung rausgeben
        Notifications.create().darkStyle()
                .title("Kunde löschen")
                .text("Der Kunde wurde erfolgreich gelöscht")
                .showInformation();
    }

    public static void speicherLogin(Model m, String passwort) {
        if (passwort.isEmpty())
            return;

        Statement stmt;
        try {
            stmt = m.getConn().createStatement();
            //Statement mit Insert erstellen
            String pwmd5 = MD5.md5(passwort);
            stmt.execute("UPDATE Logins SET Passwort='" + pwmd5 + "' WHERE MitarbeiterID ='" + m.getUserid() + "';");
            //fertig
            stmt.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim speichern des Kunden");
            return;
        }
        //und neu laden
        if (!ladeMitarbeiter(m))
            return;
        //Meldung rausgeben
        Notifications.create().darkStyle()
                .title("Logindaten")
                .text("Die Daten wurden gespeichert")
                .showInformation();
    }

    public static boolean ladeProjekte(Model m) {
        ArrayList<Projekt> projekte = new ArrayList<>();
        //Ein "Statement" erzeugen
        Statement stmt;
        try {
            stmt = m.getConn().createStatement();
            //Statement mit der Abfrage füllen und ein Result erstellen
            ResultSet rs = stmt.executeQuery("SELECT p.*, k.RealName FROM Projekte p LEFT JOIN Kunden k ON p.KundeID = k.id WHERE p.StatusOffen ORDER BY p.Projekt ASC");
            //Lade die Mitarbeiternamen und alternativen Namen
            while (rs.next()) {
                projekte.add(new Projekt(rs.getString("Projekt"), rs.getString("RealName"), rs.getString("Bezeichnung"), rs.getInt("Offerte"), rs.getString("UrProjekt"), false));
            }
            //fertig
            stmt.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler beim laden der Projektliste");
            return false;
        }
        m.setProjekte(projekte);
        return true;
    }
}

