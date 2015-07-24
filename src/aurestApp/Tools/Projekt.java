package aurestApp.Tools;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Projekt {

    private SimpleStringProperty projektnummer;
    private SimpleStringProperty kunde;
    private SimpleStringProperty bezeichnung;
    private SimpleIntegerProperty offerte;
    private SimpleStringProperty urProjekt;
    private SimpleBooleanProperty checked;

    public Projekt(String projektnummer, String kunde, String bezeichnung, Integer offerte, String urProjekt, boolean checked) {
        this.projektnummer = new SimpleStringProperty(projektnummer);
        this.kunde = new SimpleStringProperty(kunde);
        this.bezeichnung = new SimpleStringProperty(bezeichnung);
        this.offerte = new SimpleIntegerProperty(offerte);
        this.urProjekt = new SimpleStringProperty(urProjekt);
        this.checked = new SimpleBooleanProperty(checked);
    }

    public String getProjektnummer() {
        return projektnummer.get();
    }

    public void setProjektnummer(String projektnummer) {
        this.projektnummer.set(projektnummer);
    }

    public SimpleStringProperty projektnummerProperty() {
        return projektnummer;
    }

    public String getKunde() {
        return kunde.get();
    }

    public void setKunde(String kunde) {
        this.kunde.set(kunde);
    }

    public SimpleStringProperty kundeProperty() {
        return kunde;
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

    public int getOfferte() {
        return offerte.get();
    }

    public void setOfferte(Integer offerte) {
        this.offerte.set(offerte);
    }

    public SimpleIntegerProperty offerteProperty() {
        return offerte;
    }

    public String getUrProjekt() {
        return urProjekt.get();
    }

    public void setUrProjekt(String urProjekt) {
        this.urProjekt.set(urProjekt);
    }

    public SimpleStringProperty urProjektProperty() {
        return urProjekt;
    }

    public boolean getChecked() {
        return checked.get();
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public SimpleBooleanProperty checkedProperty() {
        return checked;
    }
}
