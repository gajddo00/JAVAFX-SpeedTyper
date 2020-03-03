package sample;

import DataObjects.Score;
import DataObjects.XMLReaderWriter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

public class StartSceneController {

    private Stage primaryStage;
    @FXML private Label textLbl;
    @FXML private TextField textFld;
    @FXML private Pane drawPane;
    @FXML private AnchorPane gameOverPane;
    @FXML private AnchorPane pausePane;
    @FXML private AnchorPane difficultyPane;
    @FXML private Label timerLabel;
    @FXML private Label timeResultLabel;
    @FXML private Label gameOverLabel;
    private String currentUserName;
    private AnimationLogic animationLogic;
    private Timeline timeline;
    private Timeline timer;
    private int finalTime;
    private XMLReaderWriter XMLreaderWriter;
    private boolean clicked = false;
    private boolean pressed = false;
    private TextComparator tc;
    private GetBack gb = new GetBack();
    private ChangeListener<String> listener;

    public StartSceneController() {
        timeline = new Timeline();
        timer = new Timeline();
        XMLreaderWriter = new XMLReaderWriter();
    }

    void setCurrentUserName(String userName) {
        this.currentUserName = userName;
    }

    void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() throws IOException {
        tc = new TextComparator(textFld, textLbl);
        textFld.setFocusTraversable(false);
        animationLogic = new AnimationLogic(drawPane);
        tc.setLogic(animationLogic);

       listener = (observable, oldValue, newValue) -> Platform.runLater(()
               -> tc.doCompare(oldValue, newValue));

        showDifficultyWindow();
        textFld.setOnMouseClicked(e -> textFieldMouseClicked());
        addTxtFieldListener();
    }

    private void addTxtFieldListener() {
        textFld.textProperty().addListener(listener);
    }

    private void textFieldMouseClicked() {
        textFld.setEditable(true);
        if (!clicked) {
            textFld.setText("");
            clicked = true;
            textFld.setOnKeyPressed(this::dispatchKeyEvents);
            startAnimation();
            startTimer();
        }
    }

    public void reinitialize() throws IOException {
        textFld.setText("Click here to start the game.");
        textFld.textProperty().removeListener(listener);
        textFld.setStyle("-fx-text-fill: black");
        clicked = false;
        tc = new TextComparator(textFld, textLbl);
        timeline = new Timeline();
        timer = new Timeline();
        timerLabel.setText("SECONDS ELAPSED: 0");
        drawPane.getChildren().clear();
        animationLogic = new AnimationLogic(drawPane);
        tc.setLogic(animationLogic);
        gameOverPane.setVisible(false);
        showDifficultyWindow();
        textFld.setOnMouseClicked(e -> textFieldMouseClicked());
        addTxtFieldListener();
    }

    public void pauseGame() {
        pausePane.setVisible(true);
        pausePane.setOpacity(0.9);
        textFld.setEditable(false);
        timeline.pause();
        timer.pause();
    }

    public void continueGame() {
        pausePane.setVisible(false);
        timeline.play();
        timer.play();
    }

    private void startAnimation() {
        KeyFrame updates = new KeyFrame(
                Duration.millis(150),
                e -> {
                    if (!animationLogic.checkCollision()) {
                        animationLogic.moveBall();
                    } else {
                        if (animationLogic.getOvertype().equals(AnimationLogic.OVERTYPE.FINISH)) {
                            try {
                                gameOverLabel.setText("SUCCESS!");
                                writeUserScore();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            gameOverLabel.setText("GAME OVER!");
                        }
                        showGameOver();
                    }
                });
        timeline.getKeyFrames().add(updates);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void showGameOver() {
        gameOverPane.setVisible(true);
        gameOverPane.setOpacity(0.9);
        timeline.stop();
        timer.stop();
        if (animationLogic.getOvertype().equals(AnimationLogic.OVERTYPE.FINISH)) {
            timeResultLabel.setText("Time result: " + String.valueOf(finalTime) + " seconds");
        }
    }

    private void startTimer() {
        final int[] i = {0};
        KeyFrame updates = new KeyFrame(
                Duration.seconds(1),
                e -> {
                    i[0]++;
                    finalTime = +i[0];
                    timerLabel.setText("Seconds elapsed: " + i[0]);
                });
        timer.getKeyFrames().add(updates);
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    private void showDifficultyWindow() {
        difficultyPane.setVisible(true);
        difficultyPane.setOpacity(0.9);
    }

    public void easyAction() {
        animationLogic.setDifficulty(AnimationLogic.DIFFICULTY.EASY);
        difficultyPane.setVisible(false);
    }

    public void middleAction() {
        animationLogic.setDifficulty(AnimationLogic.DIFFICULTY.MIDDLE);
        difficultyPane.setVisible(false);
    }

    public void fastAction() {
        animationLogic.setDifficulty(AnimationLogic.DIFFICULTY.FAST);
        difficultyPane.setVisible(false);
    }

    private void writeUserScore() throws Exception {
        if (!XMLreaderWriter.findUser(currentUserName)) {
            XMLreaderWriter.addUser(currentUserName);
        }
        Score score = new Score(finalTime, tc.getCharCount(), animationLogic.getDifficulty().toString());
        XMLreaderWriter.addScore(currentUserName, score);
    }

    private void dispatchKeyEvents(KeyEvent e) {
        switch (e.getCode()) {
            case SPACE:
                if (!pressed) {
                    pressed = true;
                    pauseGame();
                } else {
                    pressed = false;
                    continueGame();
                }
                break;
            default:
        }
    }

    public void getBack() throws IOException {
        gb.getBack(primaryStage, currentUserName);
    }
}
