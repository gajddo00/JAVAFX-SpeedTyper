package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class AnimationLogic {

    private ImageView runner;
    private Circle cr;
    private boolean currentOrientation = false;
    private DIFFICULTY difficulty = DIFFICULTY.EASY;
    private int WIDTH = 800;
    private int SIZE = 15;
    private OVERTYPE overtype = null;

    AnimationLogic(Pane drawPane) throws FileNotFoundException {
        cr = new Circle(40);
        cr.setLayoutX(50);
        cr.setLayoutY(230);
        cr.setFill(Color.RED);

        Image image = new Image(new FileInputStream("src/images/guy.png"));
        runner = new ImageView(image);
        runner.setLayoutX(120);
        runner.setLayoutY(180);
        runner.prefHeight(50);
        runner.prefWidth(50);

        Image image2 = new Image(new FileInputStream("src/images/ladder.png"));
        ImageView ladder = new ImageView(image2);
        ladder.setLayoutX(WIDTH - 120);
        ladder.setLayoutY(120);
        ladder.prefHeight(50);
        ladder.prefWidth(50);

        drawPane.getChildren().addAll(ladder, runner, cr);
    }

    boolean checkCollision() {
        //checks for collision with the chasing object
        if (cr.getLayoutX() == runner.getLayoutX() - 10) {
            runner.setRotate(75);
            overtype = OVERTYPE.CRASH;
            return true;
        } else if (runner.getLayoutX() == WIDTH - 170 || runner.getLayoutX() == WIDTH - 180) {
            //checks whether it should start climbing the ladder
            currentOrientation = true;
        } else if (runner.getLayoutX() == WIDTH - 95 || runner.getLayoutX() == WIDTH - 100) {
            //checks for ladder climbed
            overtype = OVERTYPE.FINISH;
            return true;
        }
        return false;
    }

    void moveBall() {
        cr.setLayoutX(cr.getLayoutX() + 1);
    }

    private void moveSwitch() {
        switch (difficulty) {
            case EASY:
                SIZE =  20;
                break;
            case MIDDLE:
                SIZE =  15;
                break;
            case FAST:
                SIZE = 10;
                break;
            default:
                break;
        }
    }

    void makeAnimation() {
        if (currentOrientation) {
            runner.setLayoutX(runner.getLayoutX() + SIZE);
            runner.setLayoutY(runner.getLayoutY() - SIZE);
        } else {
            runner.setLayoutX(runner.getLayoutX() + SIZE);
        }
    }

    DIFFICULTY getDifficulty() {
        return difficulty;
    }

    void setDifficulty(DIFFICULTY difficulty) {
        this.difficulty = difficulty;
        moveSwitch();
    }

    OVERTYPE getOvertype() {
        return overtype;
    }

    public enum DIFFICULTY {
        EASY,
        MIDDLE,
        FAST
    }

    public enum OVERTYPE {
        CRASH,
        FINISH
    }
}
