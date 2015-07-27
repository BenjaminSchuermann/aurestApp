package aurestApp.tools.eigeneklassen;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Calendar;

public class Service {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty service;
    private SimpleIntegerProperty kundeid;
    private SimpleStringProperty bezeichnung;
    private Calendar datumangelegt;
    private Calendar datumservice;
    private SimpleIntegerProperty servicejahr;
    private SimpleStringProperty projekt;

    //für tests entfernt Calendar datumangelegt, Calendar datumservice,
    public Service(int id, int service, int kundeid, String bezeichnung, int servicejahr, String projekt) {
        this.id = new SimpleIntegerProperty(id);
        this.service = new SimpleIntegerProperty(service);
        this.kundeid = new SimpleIntegerProperty(kundeid);
        this.bezeichnung = new SimpleStringProperty(bezeichnung);
        //this.datumangelegt = datumangelegt;
        //this.datumservice = datumservice;
        this.servicejahr = new SimpleIntegerProperty(servicejahr);
        this.projekt = new SimpleStringProperty(projekt);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public int getService() {
        return service.get();
    }

    public void setService(int service) {
        this.service.set(service);
    }

    public SimpleIntegerProperty serviceProperty() {
        return service;
    }

    public int getKundeid() {
        return kundeid.get();
    }

    public void setKundeid(int kundeid) {
        this.kundeid.set(kundeid);
    }

    public SimpleIntegerProperty kundeidProperty() {
        return kundeid;
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

    public Calendar getDatumangelegt() {
        return datumangelegt;
    }

    public void setDatumangelegt(Calendar datumangelegt) {
        this.datumangelegt = datumangelegt;
    }

    public Calendar getDatumservice() {
        return datumservice;
    }

    public void setDatumservice(Calendar datumservice) {
        this.datumservice = datumservice;
    }

    public int getServicejahr() {
        return servicejahr.get();
    }

    public void setServicejahr(int servicejahr) {
        this.servicejahr.set(servicejahr);
    }

    public SimpleIntegerProperty servicejahrProperty() {
        return servicejahr;
    }

    public String getProjekt() {
        return projekt.get();
    }

    public void setProjekt(String projekt) {
        this.projekt.set(projekt);
    }

    public SimpleStringProperty projektProperty() {
        return projekt;
    }
}
