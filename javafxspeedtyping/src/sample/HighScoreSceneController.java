package sample;

import DataObjects.DisplayUser;
import DataObjects.Score;
import DataObjects.User;
import DataObjects.XMLReaderWriter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class HighScoreSceneController implements GetBackI{

    private Stage primaryStage;
    @FXML private TableView<DisplayUser> easyTable;
    @FXML private TableView<DisplayUser> middleTable;
    @FXML private TableView<DisplayUser> fastTable;
    private XMLReaderWriter xmlReaderWriter;
    private GetBack gb = new GetBack();
    private String currentUserName;

    void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() throws Exception {
        xmlReaderWriter = new XMLReaderWriter();
        easyTable.setSelectionModel(null);
        middleTable.setSelectionModel(null);
        fastTable.setSelectionModel(null);
        loadEasyTable();
        loadMiddleTable();
        loadFastTable();
    }

    private void addToUserList(List<User> userList, ObservableList<DisplayUser> data) {
        List<DisplayUser> duserList = new ArrayList<>();
        for (User user : userList) {
            Score resScore = filterBestScore(user.getScoreList());
            duserList.add(new DisplayUser(user.getUserName(), resScore.getTime(),
                    calcAvgCharsPerSecond(user.getScoreList()), calcAvgCharsCount(user.getScoreList()),
                    calcAvgTime(user.getScoreList())));
        }
        duserList.sort(Comparator.comparingInt(DisplayUser::getTime));
        data.addAll(duserList);
    }

    private void loadEasyTable() throws Exception {
        ObservableList<DisplayUser> data = easyTable.getItems();
        List<User> userList = xmlReaderWriter.read("EASY");
        addToUserList(userList, data);
    }

    private void loadMiddleTable() throws Exception {
        ObservableList<DisplayUser> data = middleTable.getItems();
        List<User> userList = xmlReaderWriter.read("MIDDLE");
        addToUserList(userList, data);
    }

    private void loadFastTable() throws Exception {
        ObservableList<DisplayUser> data = fastTable.getItems();
        List<User> userList = xmlReaderWriter.read("FAST");
        addToUserList(userList, data);
    }

    private Score filterBestScore (List<Score> scoreList) {
        Score bestYet = scoreList.get(0);

        for (Score score : scoreList) {
            if (score.getTime() < bestYet.getTime()){
                bestYet = score;
            }
        }
        return bestYet;
    }

    private int calcAvgTime(List<Score> scoreList){
        int res = 0;
        for (Score score : scoreList) {
            res += score.getTime();
        }
        return Math.round(res / scoreList.size());
    }

    private int calcAvgCharsPerSecond(List<Score> scoreList) {
        int res = 0;
        for (Score score : scoreList) {
            res += Math.round(score.getCharCount() / score.getTime());
        }
        return Math.round(res / scoreList.size());
    }

    private int calcAvgCharsCount(List<Score> scoreList) {
        int res = 0;
        for (Score score : scoreList) {
            res += score.getCharCount();
        }
        return Math.round(res / scoreList.size());
    }

    public void getBack() throws IOException {
        gb.getBack(primaryStage, currentUserName);
    }


    public String getCurrentUserName() {
        return currentUserName;
    }

    void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

}
