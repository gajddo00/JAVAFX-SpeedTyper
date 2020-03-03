package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;

public class TrainModeController implements GetBackI{

    private Stage primaryStage;
    @FXML private Label textLbl;
    @FXML private Label wordsCorrect;
    @FXML private Label wordsWrong;
    @FXML private Label accuracyLbl;
    @FXML private TextField textFld;
    private TextComparator tc;
    private String currentUserName;
    private GetBack gb = new GetBack();

    void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        tc = new TextComparator(textFld, textLbl);
        textFld.setFocusTraversable(false);

        textFld.textProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(() -> {
                    tc.doCompare(oldValue, newValue);
                    wordsCorrect.setText(String.valueOf(tc.getWordsCorrect()));
                    wordsWrong.setText(String.valueOf(tc.getWordCount() - tc.getWordsCorrect()));
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(1);
                    df.setMinimumFractionDigits(1);
                    accuracyLbl.setText(df.format(calcAccuracy()) + "%");
                }));
        textFld.setOnMouseClicked(e -> textFld.setText(""));
    }

    private float calcAccuracy() {
        if (tc.getWordCount() != 0) return (float) (tc.getWordsCorrect() * 100) / tc.getWordCount();
        return (float) 100.00;
    }

    public void getBack() throws IOException {
        gb.getBack(primaryStage, currentUserName);
    }

    void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }


}
