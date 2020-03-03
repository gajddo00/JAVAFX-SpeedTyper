package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private Stage primaryStage;
    @FXML private TextField usernameField;

    void setScene(Scene sc) {
        sc.setOnKeyPressed(e -> {
            try {
                dispatchKeyEvents(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        usernameField.setFocusTraversable(false);
        usernameField.setOnMouseClicked(e -> {
            if (usernameField.getText().equals("Username")) {
                usernameField.setText("");
            }
        });

    }

    @FXML
    public void startScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("../FXML/startScene.fxml"));
        Parent root = loader.load();

        StartSceneController ssc = loader.getController();
        ssc.setPrimaryStage(primaryStage);
        ssc.setCurrentUserName(usernameField.getText());
        Scene scene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        primaryStage.setScene(scene);
    }

    @FXML
    public void startTrainingMode () throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("../FXML/trainModeScene.fxml"));
        Parent root = loader.load();

        TrainModeController ssc = loader.getController();
        ssc.setPrimaryStage(primaryStage);
        ssc.setCurrentUserName(usernameField.getText());
        Scene scene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        primaryStage.setScene(scene);
    }

    @FXML
    public void highscoreScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("../FXML/highscoreScene.fxml"));
        Parent root = loader.load();

        HighScoreSceneController hscc = loader.getController();
        hscc.setPrimaryStage(primaryStage);
        hscc.setCurrentUserName(usernameField.getText());
        Scene scene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        primaryStage.setScene(scene);

    }

    private void dispatchKeyEvents(KeyEvent e) throws IOException {
        switch (e.getCode()) {
            case ENTER:
                startScene();
                break;
            default:
        }
    }

    void setUsernameField(String userName) {
        this.usernameField.setText(userName);
    }

    @FXML
    public void exitAction() {
        primaryStage.close();
    }

}
