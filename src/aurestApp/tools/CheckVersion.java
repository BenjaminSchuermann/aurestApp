package aurestApp.tools;

import aurestApp.Model;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckVersion {
    private static boolean newVersion;
    private static String versionDB;

    public static boolean checkVersion(Model m) throws SQLException {
        if (new File("../cfg/engi").exists())
            return false;
        Statement stmt = m.getConn().createStatement();
        //Checke auf neue Version
        try {
            ResultSet rs = stmt.executeQuery("SELECT wert FROM Einstellungen WHERE config = 'version';");
            while (rs.next()) {
                versionDB = rs.getString("wert");
                if (!m.getVersion().equals(versionDB))
                    newVersion = true;
                else
                    return false;
            }

        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Fehler bei der Datenbankabfrage");
        }

        if (newVersion) {
            Desktop desktop = Desktop.getDesktop();
            Dialog dialog = new Dialog();
            dialog.setTitle("Update");
            dialog.setHeaderText("Bitte die neue Version " + versionDB + " installieren");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(CheckVersion.class.getResourceAsStream("/aurestApp/img/a128x128.png")));

            // Close Button anlegen
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);

            dialog.show();

            try {
                File setup = new File("T:\\EDV\\Software\\aurestApp\\" + versionDB + "\\setup.exe");
                desktop.open(setup);
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
                Dialoge.exceptionDialog(e, "Fehler beim öffnen des Setups");
            }
            dialog.close();
        }
        return newVersion;
    }
}
