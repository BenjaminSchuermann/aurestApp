package aurestApp.Tools;

import aurestApp.Model;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import java.sql.*;
import java.util.Optional;

public class Login {
    private static Boolean loginok = false;
    private static int userid = 0;

    public static int login(Model m) throws SQLException {
        // Logindialog erstellen
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Bitte Benutzername und Passwort eingeben");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:src/aurestApp/img/a128x128.png"));

        // das Icon setzten neben dem Text
        dialog.setGraphic(new ImageView(Login.class.getResource("../img/login.png").toString()));

        // Die Buttons anlegen
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Die Eingaben und Labels anlegen.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

        CustomTextField username = new CustomTextField();
        username.setLeft(fontAwesome.create("USER"));
        username.setPromptText("Benutzername");

        CustomPasswordField password = new CustomPasswordField();
        password.setLeft(fontAwesome.create("LOCK"));
        password.setPromptText("Passwort");


        //und der Fläche hinzufügen
        grid.add(new Label("Benutzername:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Passwort:"), 0, 1);
        grid.add(password, 1, 1);

        // Den Loginbutton anlegen und deaktivieren.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        //wenn etwas im Benutzernamefeld eingeben wurde, dann den Loginbutton aktivieren
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

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

        //Und ab zur Datenbank, den Datenbanktreiber laden
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            Dialoge.exceptionDialog(e, "Der Datenbanktreiber kann nicht geladen werden");
            return 0;
        }
        //Verbindungsparameter
        Connection conn = null;
        try {
            conn = DriverManager.
                    getConnection("jdbc:h2:tcp://" + m.getSERVERADRESSE() + "/" + m.getDATENBANK() + "", m.getDBLOGIN(), m.getDBPASSWORT());
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Die Datenbank kann nicht erreicht werden");
            return 0;
        }
        //Ein "Statement" erzeugen
        Statement stmt = conn.createStatement();

        //Solange kein gültiger Login eingegeben wurde, wiederhole die Eingabemöglichkeit
        do {
            //Loginfeld anzeugen und Ergebnis zurückliefern
            Optional<Pair<String, String>> result = dialog.showAndWait();

            //Wenn ein Login eingegben wurde...
            result.ifPresent(usernamePassword -> {
                try {
                    String user = usernamePassword.getKey();
                    String pw = usernamePassword.getValue();

                    //todo entfernen - Hintertür zum testen
                    if (user.equals("backdoor") && pw.equals("backdoor")) {
                        loginok = true;
                        userid = 1000;
                        return;
                    }

                    //... dann in der Datenbank prüfen ob es die Kombination aus Login und PW gibt....
                    ResultSet rs = stmt.executeQuery("select * from LOGINS where LOGINNAME='" + user + "' and PASSWORT='" + pw + "'");
                    while (rs.next()) {
                        //...wenn ja, dann setze den Login auf ok und speicher die UserID
                        loginok = true;
                        userid = rs.getInt("ID");
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
        try {
            conn.close();
        } catch (SQLException e) {
            Dialoge.exceptionDialog(e, "Die Datenbankverbindung kann nicht abgebaut werden");
            return 0;
        }
        //Die userid des eingeloggten Benutzers zurückgeben
        return userid;
    }
}
