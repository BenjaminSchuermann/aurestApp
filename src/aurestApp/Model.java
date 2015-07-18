package aurestApp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Model {
    private final String aktuellerBenutzer;
    private final String aktuellesDatum;
    final private String SERVERADRESSE = "192.168.0.56";
    final private String DATENBANK = "~/aurestApp";
    final private String DBLOGIN = "h2";
    final private String DBPASSWORT = "aurestApp";
    private String servicejahr;
    private ArrayList<String> mitarbeiterListe;
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

    public Model() {
        this.aktuellesDatum = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        this.aktuellerBenutzer = System.getProperty("user.name");
    }

    public String getVersion() {
        return "1.1.1";
    }

    public String getServicejahr() {
        return servicejahr;
    }

    public void setServicejahr(String servicejahr) {
        this.servicejahr = servicejahr;
    }

    public ArrayList<String> getMitarbeiterListe() {
        return mitarbeiterListe;
    }

    public void setMitarbeiterListe(ArrayList<String> mitarbeiter) {
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
}
