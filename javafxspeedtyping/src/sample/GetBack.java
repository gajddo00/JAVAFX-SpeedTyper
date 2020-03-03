package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

class GetBack {

    void getBack(Stage primaryStage, String userName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/sample.fxml"));
        Parent root = loader.load();

        Controller mainController = loader.getController();
        mainController.setPrimaryStage(primaryStage);
        mainController.setUsernameField(userName);

        Scene scene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        mainController.setScene(scene);
        primaryStage.setScene(scene);
    }
}
