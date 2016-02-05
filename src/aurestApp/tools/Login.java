package aurestApp.tools;

import aurestApp.Model;
import aurestApp.img.icons.Images;
import aurestApp.tools.eigeneklassen.GetImageView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class Login {
    private static Boolean loginok = false;

    public static int login(Model m) throws SQLException {
        // Logindialog erstellen
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Bitte Benutzername und Passwort eingeben");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Login.class.getResourceAsStream("/aurestApp/img/a128x128.png")));

        File configpfad = new File(System.getenv("LOCALAPPDATA") + "\\aurestApp");
        if (!configpfad.exists())
            configpfad.mkdir();

        // das Icon setzten neben dem Text
        dialog.setGraphic(new ImageView(new Image(Login.class.getResourceAsStream("/aurestApp/img/login.png"))));

        // Die Buttons anlegen
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Die Eingaben und Labels anlegen.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        CustomTextField username = new CustomTextField();
        username.setLeft(GetImageView.load(Images.USER_SILHOUETTE, 16));
        username.setPromptText("Benutzername");

        CustomPasswordField password = new CustomPasswordField();
        password.setLeft(GetImageView.load(Images.LOCK, 16));
        password.setPromptText("Passwort");


        CheckBox saveLogin = new CheckBox("Logindaten Speichern");
        //und der Fläche hinzufügen
        grid.add(new Label("Benutzername:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Passwort:"), 0, 1);
        grid.add(password, 1, 1);
        grid.add(saveLogin, 0, 2);

        // Den Loginbutton anlegen und deaktivieren.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        //wenn etwas im Benutzernamefeld eingeben wurde, dann den Loginbutton aktivieren
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        //Prüfen ob eine config Datei existiert, in der bereits Logindaten gespeichert sind
        if (new File(configpfad + "/login.ini").exists()) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        new FileInputStream(configpfad + "/login.ini"), "UTF-8"));
                username.setText(in.readLine());
                password.setText(in.readLine());
                in.close();
                saveLogin.setSelected(true);
                loginButton.setDisable(false);
            } catch (IOException e) {
                Dialoge.exceptionDialog(e, "Fehler bei laden der gespeicherten Logindaten");
            }
        }

        //und alles dem Dialogfeld hinzufügen
        dialog.getDialogPane().setContent(grid);

        // Anschließend den Fokus auf das Eingabefeld Benutzername anfordern
        Platform.runLater(username::requestFocus);

        // Das Ergebnis in ein username-password-pair konvertieren, wenn der Loginbutton gedrückt wurde.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        //Ein "Statement" erzeugen
        Statement stmt = m.getConn().createStatement();
        //Solange kein gültiger Login eingegeben wurde, wiederhole die Eingabemöglichkeit
        do {
            //Loginfeld anzeugen und Ergebnis zurückliefern
            Optional<Pair<String, String>> result = dialog.showAndWait();

            //Wenn ein Login eingegben wurde...
            result.ifPresent(usernamePassword -> {

                String user = usernamePassword.getKey();
                String pw = usernamePassword.getValue();

                if (!saveLogin.isSelected()) {
                    File logindatei = new File(configpfad + "/login.ini");
                    if (logindatei.exists())
                        logindatei.delete();
                } else {
                    try {
                        PrintWriter pWriter = new PrintWriter(configpfad + "/login.ini", "UTF-8");
                        pWriter.println(user);
                        pWriter.println(pw);
                        pWriter.close();
                    } catch (IOException e) {
                        return;
                    }
                }
                String pwmd5 = MD5.md5(pw);
                try {
                    //... dann in der Datenbank prüfen ob es die Kombination aus Login und PW gibt....
                    ResultSet rs = stmt.executeQuery("select * from Logins where Login='" + user + "' and Passwort='" + pwmd5 + "' and Aktiv");
                    while (rs.next()) {
                        //...wenn ja, dann setze den Login auf ok und speicher die UserID
                        loginok = true;
                        m.setUserid(rs.getInt("ID"));
                        m.setLogin(user);
                        m.setPasswort(pw);
                    }
                    if (loginok) {
                        //Benutzerdaten laden
                        rs = stmt.executeQuery("select * from Mitarbeiter where id='" + m.getUserid() + "';");
                        while (rs.next()) {
                            m.setNutzername(rs.getString("NAME"));
                            m.setEmail(rs.getString("EMAIL"));
                            m.setKuerzel(rs.getString("KURZ"));
                            m.setIsadmin(rs.getBoolean("ISADMIN"));
                        }
                    }

                } catch (SQLException e) {
                    Dialoge.exceptionDialog(e, "Fehler bei der Datenbankabfrage");
                }
                if (!loginok)
                    //...wenn nein, dann zeige eine Warnung an und es geht von vorne los
                    Dialoge.DialogAnzeigeBox("warnung", "Login nicht korrekt");
            });
            //falls ja, raus aus der Schleige
            if (!result.isPresent()) break;

        } while (!loginok);
        //fertig

        //Die Datenbankverbindungen schließen
        try {
            stmt.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Statement kann nicht geschlossen werden");
            return 0;
        }
        //Die userid des eingeloggten Benutzers zurückgeben
        return m.getUserid();
    }
}
