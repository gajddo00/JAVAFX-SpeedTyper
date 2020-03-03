package sample;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

class TextComparator{

    private int i = 0;
    private String currentWord;
    private int charCount = 0;
    private int wordCount = 0;
    private TextField textFld;
    private Label textLbl;
    private RandomWordsGenerator rwg;
    private AnimationLogic logic;
    private int wordsCorrect = 0;
    private boolean correct = true;

    TextComparator(TextField textFld, Label textLbl) {
        rwg = new RandomWordsGenerator();
        this.textFld = textFld;
        this.textLbl = textLbl;
        currentWord = rwg.readWord();
        textLbl.setText(currentWord);
    }

    void doCompare(String oldValue, String newValue) {
        if (oldValue.length() < newValue.length()) {
            String last = textFld.getText();
            if (!(last.length() > currentWord.length())) {
                compareText(last, CALLER.ADD);
            }
        } else {
            if (i > 0 && !(textFld.getText().length() > currentWord.length())) {
                i--;
                String last = textFld.getText();
                compareText(last, CALLER.REMOVE);
            }
        }
    }

    private void compareText(String letter, CALLER type) {
        if (i == currentWord.length() - 1 && textFld.getText().equals(currentWord)) {
            //final word comparison
            if (correct) wordsCorrect++;
            currentWord = rwg.readWord();
            correct = true;
            textLbl.setText(currentWord);
            i = 0;
            textFld.setText("");
            charCount += currentWord.length();
            wordCount++;
            if (logic != null) {
                logic.makeAnimation();
            }
        } else if (letter.equals(currentWord.substring(0, i + 1))) {
            //comparison successful
            textFld.setStyle("-fx-text-fill: black");
            i++;
        } else {
            //comparison unsuccessful
            textFld.setStyle("-fx-text-fill: red");
            correct = false;
            if (type.equals(CALLER.ADD)) i++;
        }
    }

    int getCharCount() {
        return charCount;
    }

    TextField getTextFld() {
        return textFld;
    }

    int getWordCount() {
        return wordCount;
    }

    int getWordsCorrect() {
        return wordsCorrect;
    }

    void setLogic(AnimationLogic logic) {
        this.logic = logic;
    }

    public enum CALLER {
        ADD,
        REMOVE
    }

}
