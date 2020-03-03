package DataObjects;

public class DisplayUser {
    private String userName;
    private int time;
    private int charsPerSecond;
    private int avgCharsCount;
    private int avgTime;

    public DisplayUser(String name, int time, int charsPerSecond, int avgCharsCount, int avgTime) {
        this.userName = name;
        this.time = time;
        this.charsPerSecond = charsPerSecond;
        this.avgCharsCount = avgCharsCount;
        this.avgTime = avgTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCharsPerSecond() {
        return charsPerSecond;
    }

    public void setCharsPerSecond(int charsPerSecond) {
        this.charsPerSecond = charsPerSecond;
    }

    public int getAvgCharsCount() {
        return avgCharsCount;
    }

    public void setAvgCharsCountt(int avgWordCount) {
        this.avgCharsCount = avgWordCount;
    }

    public int getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(int avgTime) {
        this.avgTime = avgTime;
    }
}
