package carrental.car_rental;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class SceneController {
    /**
     * Public method used to change scenes
     *
     * @param fxmlName name of scene to change to
     */
    public static void switchTo(String fxmlName) {
        try {
            // Load fxml
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(fxmlName + ".fxml")));
            // Change to new scene
            Main.stage.setScene(new Scene(root));
            // Request focus so stuff works
            Main.stage.setTitle(fxmlName);
            root.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
