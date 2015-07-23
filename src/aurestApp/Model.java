package aurestApp;

import aurestApp.Tools.Dialoge;
import aurestApp.Tools.Kunde;
import aurestApp.Tools.Mitarbeiter;
import aurestApp.Tools.Projekt;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Model {
    private final String aktuellerBenutzer;
    private final String aktuellesDatum;
    private String SERVERADRESSE;
    private String DATENBANK;
    private String DBLOGIN;
    private String DBPASSWORT;
    private String servicejahr;
    private ArrayList<Mitarbeiter> mitarbeiterListe;
    private File[] emailListe;
    private String vorlagenProjekt;
    private String vorlagenService;
    private String projektLogUrprojekt;
    private String projektLogProjekt;
    private LocalDate projektLogDatum;
    private String projektLogMitarbeiter;
    private boolean projektLogArchiviert;
    private String projektLogAnlagenteil;
    private String projektLogBeschreibung;
    private String serviceLogUrprojekt;
    private String serviceLogJahr;
    private String serviceLogNummer;
    private boolean serviceLogCheckBox;
    private LocalDate serviceLogDatum;
    private String serviceLogMitarbeiter;
    private String serviceLogKontakt;
    private boolean serviceLogArchiviert;
    private String serviceLogAnlagenteil;
    private String serviceLogProblem;
    private String serviceLogLösung;
    private String serviceLogUrsache;
    private String serviceLogBemerkung;
    private ArrayList<String> kundennamen;
    private String serviceNummer;
    private String serviceKunde;
    private String serviceUrprojekt;
    private boolean serviceToLog;
    private String projektNummer;
    private String projektKunde;
    private String projektUrprojekt;
    private boolean projektToLog;
    private String vorlagenOfferte;
    private Connection conn;
    private String nutzername;
    private String kuerzel;
    private String email;
    private int userid;
    private boolean isadmin;
    private String login;
    private String passwort;
    private ArrayList<Projekt> projekte;
    private ArrayList<Kunde> kunden;
    private ArrayList<Kunde> zuLoeschendeKunden;

    public Model() {
        ladeDatenbankEinstellungen();
        connectDB();
        this.aktuellesDatum = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        this.aktuellerBenutzer = System.getProperty("user.name");
    }

    public String getVersion() {
        return "1.1.9";
    }

    public String getServicejahr() {
        return servicejahr;
    }

    public void setServicejahr(String servicejahr) {
        this.servicejahr = servicejahr;
    }

    public ArrayList<Mitarbeiter> getMitarbeiterListe() {
        return mitarbeiterListe;
    }

    public void setMitarbeiterListe(ArrayList<Mitarbeiter> mitarbeiter) {
        this.mitarbeiterListe = mitarbeiter;
    }

    public File[] getEmailListe() {
        return emailListe;
    }

    public void setEmailListe(File[] emailListe) {
        this.emailListe = emailListe;
    }

    public String getAktuellerBenutzer() {
        return aktuellerBenutzer;
    }

    public String getAktuellesDatum() {
        return aktuellesDatum;
    }

    public void setVorlagen(ArrayList<String> vorlagen) {
        this.vorlagenProjekt = vorlagen.get(0);
        this.vorlagenService = vorlagen.get(1);
        this.vorlagenOfferte = vorlagen.get(2);
    }

    public String getVorlagenProjekt() {
        return vorlagenProjekt;
    }

    public String getVorlagenService() {
        return vorlagenService;
    }

    public String getVorlagenOfferte() {
        return vorlagenOfferte;
    }

    public LocalDate getProjektLogDatum() {
        return projektLogDatum;
    }

    public void setProjektLogDatum(LocalDate projektLogDatum) {
        this.projektLogDatum = projektLogDatum;
    }

    public String getProjektLogUrprojekt() {
        return projektLogUrprojekt;
    }

    public void setProjektLogUrprojekt(String projektLogUrprojekt) {
        this.projektLogUrprojekt = projektLogUrprojekt;
    }

    public String getProjektLogProjekt() {
        return projektLogProjekt;
    }

    public void setProjektLogProjekt(String projektLogProjekt) {
        this.projektLogProjekt = projektLogProjekt;
    }

    public String getProjektLogMitarbeiter() {
        return projektLogMitarbeiter;
    }

    public void setProjektLogMitarbeiter(String projektLogMitarbeiter) {
        this.projektLogMitarbeiter = projektLogMitarbeiter;
    }

    public boolean isProjektLogArchiviert() {
        return projektLogArchiviert;
    }

    public void setProjektLogArchiviert(boolean projektLogArchiviert) {
        this.projektLogArchiviert = projektLogArchiviert;
    }

    public String getProjektLogAnlagenteil() {
        return projektLogAnlagenteil;
    }

    public void setProjektLogAnlagenteil(String projektLogAnlagenteil) {
        this.projektLogAnlagenteil = projektLogAnlagenteil;
    }

    public String getProjektLogBeschreibung() {
        return projektLogBeschreibung;
    }

    public void setProjektLogBeschreibung(String projektLogBeschreibung) {
        this.projektLogBeschreibung = projektLogBeschreibung;
    }

    public String getServiceLogUrprojekt() {
        return serviceLogUrprojekt;
    }

    public void setServiceLogUrprojekt(String serviceLogUrprojekt) {
        this.serviceLogUrprojekt = serviceLogUrprojekt;
    }

    public String getServiceLogJahr() {
        return serviceLogJahr;
    }

    public void setServiceLogJahr(String serviceLogJahr) {
        this.serviceLogJahr = serviceLogJahr;
    }

    public String getServiceLogNummer() {
        return serviceLogNummer;
    }

    public void setServiceLogNummer(String serviceLogNummer) {
        this.serviceLogNummer = serviceLogNummer;
    }

    public boolean isServiceLogCheckBox() {
        return serviceLogCheckBox;
    }

    public void setServiceLogCheckBox(boolean serviceLogCheckBox) {
        this.serviceLogCheckBox = serviceLogCheckBox;
    }

    public LocalDate getServiceLogDatum() {
        return serviceLogDatum;
    }

    public void setServiceLogDatum(LocalDate serviceLogDatum) {
        this.serviceLogDatum = serviceLogDatum;
    }

    public String getServiceLogMitarbeiter() {
        return serviceLogMitarbeiter;
    }

    public void setServiceLogMitarbeiter(String serviceLogMitarbeiter) {
        this.serviceLogMitarbeiter = serviceLogMitarbeiter;
    }

    public String getServiceLogKontakt() {
        return serviceLogKontakt;
    }

    public void setServiceLogKontakt(String serviceLogKontakt) {
        this.serviceLogKontakt = serviceLogKontakt;
    }

    public boolean isServiceLogArchiviert() {
        return serviceLogArchiviert;
    }

    public void setServiceLogArchiviert(boolean serviceLogArchiviert) {
        this.serviceLogArchiviert = serviceLogArchiviert;
    }

    public String getServiceLogAnlagenteil() {
        return serviceLogAnlagenteil;
    }

    public void setServiceLogAnlagenteil(String serviceLogAnlagenteil) {
        this.serviceLogAnlagenteil = serviceLogAnlagenteil;
    }

    public String getServiceLogProblem() {
        return serviceLogProblem;
    }

    public void setServiceLogProblem(String serviceLogProblem) {
        this.serviceLogProblem = serviceLogProblem;
    }

    public String getServiceLogLösung() {
        return serviceLogLösung;
    }

    public void setServiceLogLösung(String serviceLogLösung) {
        this.serviceLogLösung = serviceLogLösung;
    }

    public String getServiceLogUrsache() {
        return serviceLogUrsache;
    }

    public void setServiceLogUrsache(String serviceLogUrsache) {
        this.serviceLogUrsache = serviceLogUrsache;
    }

    public String getServiceLogBemerkung() {
        return serviceLogBemerkung;
    }

    public void setServiceLogBemerkung(String serviceLogBemerkung) {
        this.serviceLogBemerkung = serviceLogBemerkung;
    }

    public ArrayList<String> getKundennamen() {
        return kundennamen;
    }

    public void setKundennamen(ArrayList<String> kundennamen) {
        this.kundennamen = kundennamen;
    }

    public void addKunde(String kunde) {
        this.kundennamen.add(kunde);
    }

    public String getServiceNummer() {
        return serviceNummer;
    }

    public void setServiceNummer(String serviceNummer) {
        this.serviceNummer = serviceNummer;
    }

    public String getServiceKunde() {
        return serviceKunde;
    }

    public void setServiceKunde(String serviceKunde) {
        this.serviceKunde = serviceKunde;
    }

    public String getServiceUrprojekt() {
        return serviceUrprojekt;
    }

    public void setServiceUrprojekt(String serviceUrprojekt) {
        this.serviceUrprojekt = serviceUrprojekt;
    }

    public boolean isServiceToLog() {
        return serviceToLog;
    }

    public void setServiceToLog(boolean serviceToLog) {
        this.serviceToLog = serviceToLog;
    }

    public String getProjektNummer() {
        return projektNummer;
    }

    public void setProjektNummer(String projektNummer) {
        this.projektNummer = projektNummer;
    }

    public String getProjektKunde() {
        return projektKunde;
    }

    public void setProjektKunde(String projektKunde) {
        this.projektKunde = projektKunde;
    }

    public String getProjektUrprojekt() {
        return projektUrprojekt;
    }

    public void setProjektUrprojekt(String projektUrprojekt) {
        this.projektUrprojekt = projektUrprojekt;
    }

    public boolean isProjektToLog() {
        return projektToLog;
    }

    public void setProjektToLog(boolean projektToLog) {
        this.projektToLog = projektToLog;
    }

    public String getSERVERADRESSE() {
        return SERVERADRESSE;
    }

    public String getDBPASSWORT() {
        return DBPASSWORT;
    }

    public String getDBLOGIN() {
        return DBLOGIN;
    }

    public String getDATENBANK() {
        return DATENBANK;
    }

    private void ladeDatenbankEinstellungen() {
        File file = new File("../cfg/dbconnect.ini");
        if (!file.exists()) {
            try {
                PrintWriter pWriter = new PrintWriter("../cfg/dbconnect.ini", "UTF-8");
                pWriter.println("Adresse");
                pWriter.println("Datenbank");
                pWriter.println("Login");
                pWriter.println("Passwort");
                pWriter.close();
                Dialoge.InfoAnzeigen("Bitte tragen sie die Datenbankeinstellungen in der Datei cfg/dbconnect.ini ein");
                return;
            } catch (IOException e) {
                Dialoge.exceptionDialog(e, "Fehler beim anlegen der Konfigurationsdatei");
                return;
            }
        }
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream("../cfg/dbconnect.ini"), "UTF-8"));
            SERVERADRESSE = in.readLine();
            DATENBANK = in.readLine();
            DBLOGIN = in.readLine();
            DBPASSWORT = in.readLine();
            in.close();
        } catch (IOException e) {
            Dialoge.exceptionDialog(e, "Die Datenbankeinstellungen können nicht gefunden werden \n Bitte tragen sie die Datenbankeinstellungen in der Datei cfg/dbconnect.ini ein");
        }
    }

    private void connectDB() {
        //Und ab zur Datenbank, den Datenbanktreiber laden
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            Dialoge.exceptionDialog(e, "Der Datenbanktreiber kann nicht geladen werden");
        }
        //Verbindungsparameter
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + getSERVERADRESSE() + "/" + getDATENBANK() + "?user=" + getDBLOGIN() + "&password=" + getDBPASSWORT());
            //conn = DriverManager.getConnection("jdbc:h2:tcp://" + getSERVERADRESSE() + "/" + getDATENBANK() + "", getDBLOGIN(), getDBPASSWORT());
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Die Datenbank kann nicht erreicht werden");
        }
    }

    public void closeDB() {
        //Falls die Verbindung erst garnicht aufgebaut werden konnte, direkt verlassen
        if (conn == null)
            return;
        //Ansonsten versuchen sie zu schließen
        try {
            conn.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Datenbankverbindung konnte nicht geschlossen werden");
        }
    }

    public Connection getConn() {
        return conn;
    }

    public String getNutzername() {
        return nutzername;
    }

    public void setNutzername(String nutzername) {
        this.nutzername = nutzername;
    }

    public String getKuerzel() {
        return kuerzel;
    }

    public void setKuerzel(String kuerzel) {
        this.kuerzel = kuerzel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public boolean isadmin() {
        return isadmin;
    }

    public void setIsadmin(boolean isadmin) {
        this.isadmin = isadmin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public ArrayList<Projekt> getProjekte() {
        return projekte;
    }

    public void setProjekte(ArrayList<Projekt> projekte) {
        this.projekte = projekte;
    }

    public ArrayList<Kunde> getKunden() {
        return kunden;
    }

    public void setKunden(ArrayList<Kunde> kunden) {
        this.kunden = kunden;
    }

    public ArrayList<Kunde> getZuLoeschendeKunden() {
        return zuLoeschendeKunden;
    }

    public void setZuLoeschendenKunden(ArrayList<Kunde> zuLoeschendeKunden) {
        this.zuLoeschendeKunden = zuLoeschendeKunden;
    }
}
