package edu.wpi.teamc.controllers.english;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ExitPageController {
  @FXML private Rectangle upperhalf;
  @FXML private Rectangle lowerhalf;

  @FXML
  public void initialize() {
    /*
            TranslateTransition uppertran = new TranslateTransition();
            TranslateTransition lowertran = new TranslateTransition();
            uppertran.setNode(upperhalf);
            lowertran.setNode(lowerhalf);

            uppertran.setDuration(Duration.millis(1000));
            uppertran.setByY(-100.0);
            lowertran.setByY(100.0);

            uppertran.play();
            lowertran.play();
    */

    /// *
    Timeline t1 =
        new Timeline(
            new KeyFrame(
                Duration.millis(10),
                ae -> {
                  TranslateTransition uppertran = new TranslateTransition();
                  TranslateTransition lowertran = new TranslateTransition();
                  uppertran.setNode(upperhalf);
                  lowertran.setNode(lowerhalf);

                  uppertran.setDuration(Duration.millis(3000));
                  uppertran.setByY(-1000.0);
                  lowertran.setDuration(Duration.millis(3000));
                  lowertran.setByY(1000.0);

                  uppertran.play();
                  lowertran.play();
                }),
            new KeyFrame(
                Duration.millis(2000),
                ae -> {
                  System.exit(0);
                }));

    t1.play();
    // */
  }
}
