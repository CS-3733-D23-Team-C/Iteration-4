package edu.wpi.teamc.controllers.pages;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ScreenSaverController {

  @FXML private Label dateDisplay;

  @FXML private Label timeDisplay;

  @FXML
  void getHome(ActionEvent event) {
    CApp.currScreen = Screen.HOME;
    Navigation.navigate(Screen.HOME);
    CApp.timeOut();
  }

  @FXML
  public void initialize() {

    Timeline clock =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                e ->
                    timeDisplay.setText(
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")))),
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
                            .format(DateTimeFormatter.ofPattern("EEEE MMMM dd, yyyy")))),
            new KeyFrame(Duration.seconds(1)));
    date.setCycleCount(Animation.INDEFINITE);
    date.play();
  }
}
