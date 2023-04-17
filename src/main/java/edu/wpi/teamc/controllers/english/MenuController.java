package edu.wpi.teamc.controllers.english;

import java.awt.*;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.util.Duration;

public class MenuController {

  @FXML private Pane menuPane;
  @FXML private ImageView homeButton;
  @FXML private ImageView serviceRequestButton;

  @FXML private ImageView navigationButton;

  @FXML private ImageView settingsButton;

  @FXML private ImageView helpButton;
  @FXML private ImageView exitButton;
  @FXML private ImageView logoutButton;
  @FXML private ImageView historyButton;

  @FXML private Pane homeTrigger;
  @FXML private Pane serviceRequestTrigger;
  @FXML private Pane navigationTrigger;
  @FXML private Pane settingsTrigger;
  @FXML private Pane helpTrigger;
  @FXML private Pane historyTrigger;

  @FXML private Pane exitTrigger;
  @FXML private Pane logoutTrigger;
  @FXML private Pane serviceRequestPopOut;
  @FXML private Pane navigationPopOut;

  @FXML
  public void initialize() {
    homeTrigger.setVisible(false);
    serviceRequestTrigger.setVisible(false);
    navigationTrigger.setVisible(false);
    settingsTrigger.setVisible(false);
    helpTrigger.setVisible(false);
    historyTrigger.setVisible(false);
    exitTrigger.setVisible(false);
    logoutTrigger.setVisible(false);
    serviceRequestPopOut.setVisible(false);
    navigationPopOut.setVisible(false);
    TranslateTransition navigationPopOutTransition = new TranslateTransition();
    PauseTransition pause = new PauseTransition(Duration.millis(200));
    TranslateTransition serviceRequestPopOutTransition = new TranslateTransition();

    homeButton.setOnMouseEntered(
        e -> {
          homeTrigger.setVisible(true);
          homeButton.setVisible(false);
        });
    homeTrigger.setOnMouseExited(
        e -> {
          homeTrigger.setVisible(false);
          homeButton.setVisible(true);
        });
    serviceRequestButton.setOnMouseEntered(
        e -> {
          navigationPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          navigationPopOutTransition.setNode(navigationPopOut);
          navigationPopOutTransition.setToX(0);
          navigationPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                navigationPopOut.setVisible(false);
                navigationTrigger.setVisible(false);
                navigationButton.setVisible(true);
              });
          pause.play();

          serviceRequestTrigger.setVisible(true);
          serviceRequestButton.setVisible(false);

          serviceRequestPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          serviceRequestPopOutTransition.setNode(serviceRequestPopOut);
          serviceRequestPopOutTransition.setToX(180);
          serviceRequestPopOutTransition.play();

          serviceRequestPopOut.setVisible(true);

          serviceRequestPopOut.setOnMouseExited(
              f -> {
                serviceRequestPopOutTransition.setDuration(javafx.util.Duration.millis(200));
                serviceRequestPopOutTransition.setNode(serviceRequestPopOut);
                serviceRequestPopOutTransition.setToX(0);
                serviceRequestPopOutTransition.play();

                pause.setOnFinished(
                    g -> {
                      serviceRequestPopOut.setVisible(false);
                      serviceRequestTrigger.setVisible(false);
                      serviceRequestButton.setVisible(true);
                    });
                pause.play();
              });
        });
    serviceRequestTrigger.setOnMouseExited(
        e -> {
          serviceRequestTrigger.setVisible(false);
          serviceRequestButton.setVisible(true);
        });
    serviceRequestPopOut.setOnMouseEntered(
        e -> {
          serviceRequestTrigger.setVisible(true);
          serviceRequestButton.setVisible(false);
        });

    navigationButton.setOnMouseEntered(
        e -> {
          serviceRequestPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          serviceRequestPopOutTransition.setNode(serviceRequestPopOut);
          serviceRequestPopOutTransition.setToX(0);
          serviceRequestPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                serviceRequestPopOut.setVisible(false);
                serviceRequestTrigger.setVisible(false);
                serviceRequestButton.setVisible(true);
              });
          pause.play();

          navigationTrigger.setVisible(true);
          navigationButton.setVisible(false);

          navigationPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          navigationPopOutTransition.setNode(navigationPopOut);
          navigationPopOutTransition.setToX(180);
          navigationPopOutTransition.play();

          navigationPopOut.setVisible(true);

          navigationPopOut.setOnMouseExited(
              f -> {
                navigationPopOutTransition.setDuration(javafx.util.Duration.millis(200));
                navigationPopOutTransition.setNode(navigationPopOut);
                navigationPopOutTransition.setToX(0);
                navigationPopOutTransition.play();

                pause.setOnFinished(
                    g -> {
                      navigationPopOut.setVisible(false);
                      navigationTrigger.setVisible(false);
                      navigationButton.setVisible(true);
                    });
                pause.play();
              });
        });
    navigationTrigger.setOnMouseExited(
        e -> {
          navigationTrigger.setVisible(false);
          navigationButton.setVisible(true);
        });

  }
}
