package aurestApp.tools.eigeneklassen;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GetImageView {
    public static ImageView load(String name, Integer size) {
        return new ImageView(new Image(GetImageView.class.getResourceAsStream("/aurestApp/img/icons/" + size.toString() + "/" + name)));
    }

}
