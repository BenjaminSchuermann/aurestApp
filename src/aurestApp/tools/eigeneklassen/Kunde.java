package aurestApp.tools.eigeneklassen;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Kunde {

    private SimpleIntegerProperty kundenid;
    private SimpleStringProperty realername;
    private SimpleStringProperty strasse;
    private SimpleStringProperty stadt;
    private SimpleIntegerProperty plz;
    private SimpleStringProperty land;
    private SimpleStringProperty telefon;
    private SimpleStringProperty fax;
    private ArrayList<String> ordnernamen;


    public Kunde(Integer kundenid, String realername, String strasse, String stadt, Integer plz, String land, String telefon, String fax, ArrayList<String> ordnernamen) {
        this.kundenid = new SimpleIntegerProperty(kundenid);
        this.realername = new SimpleStringProperty(realername);
        this.strasse = new SimpleStringProperty(strasse);
        this.stadt = new SimpleStringProperty(stadt);
        this.plz = new SimpleIntegerProperty(plz);
        this.land = new SimpleStringProperty(land);
        this.telefon = new SimpleStringProperty(telefon);
        this.fax = new SimpleStringProperty(fax);
        this.ordnernamen = ordnernamen;
    }

    public int getKundenID() {
        return kundenid.get();
    }

    public void setKundenID(int id) {
        this.kundenid.set(id);
    }

    public SimpleIntegerProperty kundenIDProperty() {
        return kundenid;
    }

    public String getName() {
        return realername.get();
    }

    public void setName(String name) {
        this.realername.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return realername;
    }

    public String getStrasse() {
        return strasse.get();
    }

    public void setStrasse(String strasse) {
        this.strasse.set(strasse);
    }

    public SimpleStringProperty strasseProperty() {
        return strasse;
    }

    public String getStadt() {
        return stadt.get();
    }

    public void setStadt(String stadt) {
        this.stadt.set(stadt);
    }

    public SimpleStringProperty stadtProperty() {
        return stadt;
    }

    public int getPlz() {
        return plz.get();
    }

    public void setPlz(int plz) {
        this.plz.set(plz);
    }

    public SimpleIntegerProperty plzProperty() {
        return plz;
    }

    public String getLand() {
        return land.get();
    }

    public void setLand(String land) {
        this.land.set(land);
    }

    public SimpleStringProperty landProperty() {
        return land;
    }

    public String getTelefon() {
        return telefon.get();
    }

    public void setTelefon(String telefon) {
        this.telefon.set(telefon);
    }

    public SimpleStringProperty telefonProperty() {
        return telefon;
    }

    public String getFax() {
        return fax.get();
    }

    public void setFax(String fax) {
        this.fax.set(fax);
    }

    public SimpleStringProperty faxProperty() {
        return fax;
    }

    public ArrayList<String> getOrdnernamen() {
        return ordnernamen;
    }

    public void setOrdnernamen(ArrayList<String> ordnernamen) {
        this.ordnernamen = ordnernamen;
    }

    public void addOrdner(String ordner) {
        this.ordnernamen.add(ordner);
    }
}
