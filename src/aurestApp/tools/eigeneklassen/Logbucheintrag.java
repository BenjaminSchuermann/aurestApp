package aurestApp.tools.eigeneklassen;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Logbucheintrag {

    private SimpleStringProperty datum;
    private SimpleStringProperty titel;
    private SimpleStringProperty kuerzel;
    private SimpleBooleanProperty isservice;
    private SimpleBooleanProperty isprojekt;
    private SimpleStringProperty nummer;
    private SimpleStringProperty bezeichnung;


    public Logbucheintrag(String datum, String titel, String kuerzel, boolean isservice, boolean isprojekt, String nummer, String bezeichnung) {
        this.datum = new SimpleStringProperty(datum);
        this.titel = new SimpleStringProperty(titel);
        this.kuerzel = new SimpleStringProperty(kuerzel);
        this.isservice = new SimpleBooleanProperty(isservice);
        this.isprojekt = new SimpleBooleanProperty(isprojekt);
        this.nummer = new SimpleStringProperty(nummer);
        this.bezeichnung = new SimpleStringProperty(bezeichnung);
    }

    public String getDatum() {
        return datum.get();
    }

    public void setDatum(String datum) {
        this.datum.set(datum);
    }

    public SimpleStringProperty datumProperty() {
        return datum;
    }

    public String getTitel() {
        return titel.get();
    }

    public void setTitel(String titel) {
        this.titel.set(titel);
    }

    public SimpleStringProperty titelProperty() {
        return titel;
    }

    public String getKuerzel() {
        return kuerzel.get();
    }

    public void setKuerzel(String kuerzel) {
        this.kuerzel.set(kuerzel);
    }

    public SimpleStringProperty kuerzelProperty() {
        return kuerzel;
    }

    public boolean getIsservice() {
        return isservice.get();
    }

    public void setIsservice(boolean isservice) {
        this.isservice.set(isservice);
    }

    public SimpleBooleanProperty isserviceProperty() {
        return isservice;
    }

    public boolean getIsprojekt() {
        return isprojekt.get();
    }

    public void setIsprojekt(boolean isprojekt) {
        this.isprojekt.set(isprojekt);
    }

    public SimpleBooleanProperty isprojektProperty() {
        return isprojekt;
    }

    public String getNummer() {
        return nummer.get();
    }

    public void setNummer(String nummer) {
        this.nummer.set(nummer);
    }

    public SimpleStringProperty nummerProperty() {
        return nummer;
    }

    public String getBezeichnung() {
        return bezeichnung.get();
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung.set(bezeichnung);
    }

    public SimpleStringProperty bezeichnungProperty() {
        return bezeichnung;
    }
}
