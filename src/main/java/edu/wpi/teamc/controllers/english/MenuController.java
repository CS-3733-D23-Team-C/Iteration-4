package edu.wpi.teamc.controllers.english;

import java.awt.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;

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

    homeButton.setOnMouseEntered(
        e -> {
          homeTrigger.setVisible(true);
        });
    homeTrigger.setOnMouseExited(
        e -> {
          homeTrigger.setVisible(false);
        });
  }
}
