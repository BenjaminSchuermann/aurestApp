package aurestApp.controller;

import aurestApp.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class UeberInfoController implements Initializable {
    private final Model m;
    @FXML
    private Label version;

    @FXML
    private ImageView logo;

    public UeberInfoController(Model m) {
        this.m = m;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        version.setText(m.getVersion());

        logo.setImage(new Image(UeberInfoController.class.getResourceAsStream("/aurestApp/img/logo700x187.png")));

    }
}
