package edu.wpi.teamc.controllers.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.HTMLEditor;
import javafx.util.Duration;

public class ScreenSaverController {

  @FXML private Label dateDisplay;

  @FXML private HTMLEditor screenWeather;

  @FXML private Label timeDisplay;

  @FXML
  public void initialize() {
    Timeline clock =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                e ->
                    timeDisplay.setText(
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss a")))),
            new KeyFrame(Duration.seconds(1)));
    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();

    Timeline date =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                e ->
                    dateDisplay.setText(
                        LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy")))),
            new KeyFrame(Duration.seconds(1)));
    date.setCycleCount(Animation.INDEFINITE);
    date.play();
  }
}
