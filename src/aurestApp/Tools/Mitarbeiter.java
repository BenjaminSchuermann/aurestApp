package aurestApp.Tools;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Mitarbeiter {

    private SimpleIntegerProperty userID;
    private SimpleStringProperty name;
    private SimpleStringProperty email;
    private SimpleStringProperty kurz;
    private SimpleBooleanProperty teleAktiv;
    private SimpleBooleanProperty eMailAktiv;
    private SimpleBooleanProperty isAdmin;
    private SimpleBooleanProperty loginAktiv;
    private SimpleStringProperty login;
    private SimpleStringProperty passwort;
    private ArrayList<String> altnamen;

    public Mitarbeiter(int userID, String name, String email, String kurz, boolean teleAktiv, boolean eMailAktiv, boolean isAdmin, boolean loginAktiv,
                       String login, String passwort, ArrayList<String> altnamen) {
        this.userID = new SimpleIntegerProperty(userID);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.kurz = new SimpleStringProperty(kurz);
        this.teleAktiv = new SimpleBooleanProperty(teleAktiv);
        this.eMailAktiv = new SimpleBooleanProperty(eMailAktiv);
        this.isAdmin = new SimpleBooleanProperty(isAdmin);
        this.loginAktiv = new SimpleBooleanProperty(loginAktiv);
        this.login = new SimpleStringProperty(login);
        this.passwort = new SimpleStringProperty(passwort);
        this.altnamen = altnamen;
    }

    public int getUserID() {
        return userID.get();
    }

    public void setUserID(int userID) {
        this.userID.set(userID);
    }

    public SimpleIntegerProperty userIDProperty() {
        return userID;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public String getKurz() {
        return kurz.get();
    }

    public void setKurz(String kurz) {
        this.kurz.set(kurz);
    }

    public SimpleStringProperty kurzProperty() {
        return kurz;
    }

    public boolean getTeleAktiv() {
        return teleAktiv.get();
    }

    public void setTeleAktiv(boolean teleAktiv) {
        this.teleAktiv.set(teleAktiv);
    }

    public SimpleBooleanProperty teleAktivProperty() {
        return teleAktiv;
    }

    public boolean geteMailAktiv() {
        return eMailAktiv.get();
    }

    public void seteMailAktiv(boolean eMailAktiv) {
        this.eMailAktiv.set(eMailAktiv);
    }

    public SimpleBooleanProperty eMailAktivProperty() {
        return eMailAktiv;
    }

    public boolean getIsAdmin() {
        return isAdmin.get();
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin.set(isAdmin);
    }

    public SimpleBooleanProperty isAdminProperty() {
        return isAdmin;
    }

    public boolean getLoginAktiv() {
        return loginAktiv.get();
    }

    public void setLoginAktiv(boolean loginAktiv) {
        this.loginAktiv.set(loginAktiv);
    }

    public SimpleBooleanProperty loginAktivProperty() {
        return loginAktiv;
    }

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public String getPasswort() {
        return passwort.get();
    }

    public void setPasswort(String passwort) {
        this.passwort.set(passwort);
    }

    public SimpleStringProperty passwortProperty() {
        return passwort;
    }

    public ArrayList<String> getAltnamen() {
        return altnamen;
    }

    public void setAltnamen(ArrayList<String> altnamen) {
        this.altnamen = altnamen;
    }

    public void addAltname(String altname) {
        altnamen.add(altname);
    }

}
