package aurestApp.tools.eigeneklassen;

import java.io.File;

/**
 * Created by Benjamin on 09.10.2015.
 */
public class Suchprojekt {
    private boolean gefunden = false;
    private String projektname;
    private String projektlaufwerk;
    private File projektpfad;

    public boolean isGefunden() {
        return gefunden;
    }

    public void setGefunden(boolean gefunden) {
        this.gefunden = gefunden;
    }

    public String getProjektname() {
        return projektname;
    }

    public void setProjektname(String projektname) {
        this.projektname = projektname;
    }

    public String getProjektlaufwerk() {
        return projektlaufwerk;
    }

    public void setProjektlaufwerk(String projektlaufwerk) {
        this.projektlaufwerk = projektlaufwerk;
    }

    public File getProjektpfad() {
        return projektpfad;
    }

    public void setProjektpfad(File projektpfad) {
        this.projektpfad = projektpfad;
    }
}
