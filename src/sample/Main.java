package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // LOAD RES
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        String css = this.getClass().getResource("style.css").toExternalForm();

        // GET THE CONTROLLER
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        Controller controller = loader.getController();

        // SET THE SCENE
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        Scene scene = new Scene(root);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);

        // SET THE STAGE
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        primaryStage.setTitle("DRIVERSLOG");
        primaryStage.setScene(scene);
        primaryStage.show();

        // TRIGGER AFTER .show FUNCTIONS
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        controller.colorIt();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
