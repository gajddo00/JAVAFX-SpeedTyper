package DataObjects;

public class Score {

    private int time;
    private int charCount;
    private String difficulty;

    public Score(int time, int charCount, String difficulty) {
        this.time = time;
        this.charCount = charCount;
        this.difficulty = difficulty;
    }

    String getDifficulty() {
        return difficulty;
    }

    public int getTime() {
        return time;
    }

    public int getCharCount() {
        return charCount;
    }
}
