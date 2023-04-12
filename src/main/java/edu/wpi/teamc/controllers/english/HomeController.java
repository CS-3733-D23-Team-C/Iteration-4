package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class HomeController {

  @FXML private MFXButton GuestPage;

  @FXML private MFXButton AdminPage;

  @FXML private MFXButton clear;

  @FXML private Rectangle HOME_sqr;

  @FXML
  void getAdmin(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getGuest(ActionEvent event) {
    Navigation.navigate(Screen.GUEST_HOME);
  }

  @FXML
  public void getExit(ActionEvent actionEvent) {
    Navigation.navigate(Screen.EXIT_PAGE);
  }

  @FXML
  void fancy_exit(ActionEvent event) {

    TranslateTransition tran = new TranslateTransition();
    FadeTransition fade = new FadeTransition();
    ScaleTransition scale = new ScaleTransition();
    scale.setNode(HOME_sqr);
    fade.setNode(HOME_sqr);
    tran.setNode(HOME_sqr);

    Timeline t1 =
        new Timeline(
            new KeyFrame(
                Duration.millis(80),
                ae -> {
                  HOME_sqr.setVisible(true);
                  HOME_sqr.setOpacity(1);
                  scale.setToX(600);
                  scale.setToY(400);
                  scale.setDuration(Duration.millis(1500));
                  scale.play();
                  tran.setByX(-277);
                  tran.setByY(-204);
                  tran.setDuration(Duration.millis(1500));
                  tran.play();
                }),
            new KeyFrame(
                Duration.millis(1500),
                ae -> {
                  Navigation.navigate((Screen.EXIT_PAGE));
                }));
    t1.setCycleCount(1);
    t1.play();
  }
}
