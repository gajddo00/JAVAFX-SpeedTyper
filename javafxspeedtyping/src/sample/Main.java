package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("SpeedTyper");
        primaryStage.getIcons().addAll(new Image(Main.class.getResourceAsStream("../images/icon.png")));

        Controller mainController = loader.getController();
        mainController.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root, 800, 600);
        mainController.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
