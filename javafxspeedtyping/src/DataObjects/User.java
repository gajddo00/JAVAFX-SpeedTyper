package DataObjects;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String userName;
    private List<Score> scoreList;

    User(String userName) {
        this.userName = userName;
        this.scoreList = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public List<Score> getScoreList() {
        return scoreList;
    }
}
