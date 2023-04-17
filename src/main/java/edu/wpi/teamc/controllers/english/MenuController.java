package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import java.awt.*;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
  @FXML private Pane settingsPopOut;
  @FXML private Pane helpPopOut;
  @FXML private Pane historyPopOut;
  @FXML private Pane exitPopOut;
  @FXML private Pane logoutPopOut;
  @FXML private Pane homePopOut;
  @FXML private AnchorPane basePane;

  @FXML
  void getFlowerDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FLOWER);
  }

  @FXML
  void getFurnitureDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FURNITURE);
  }

  @FXML
  void getHelpPage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  void getMealDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.MEAL);
  }

  @FXML
  void getOfficeSuppliesPage(ActionEvent event) {
    Navigation.navigate(Screen.OFFICE_SUPPLY);
  }

  @FXML
  void getRoomReservationPage(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
  }

  @FXML
  void getSignagePage(ActionEvent event) {
    Navigation.navigate(Screen.SIGNAGE);
  }

  @FXML
  void getPathfindingPage(ActionEvent event) {
    Navigation.navigate(Screen.PATHFINDING_PAGE);
  }

  @FXML
  void getGiftBasketRequestPage(ActionEvent event) {
    Navigation.navigate(Screen.GIFT_BASKET);
  }

  @FXML
  void getExit(ActionEvent event) {
    Navigation.navigate(Screen.EXIT_PAGE);
  }

  /** Method run when controller is initialized */
  @FXML
  void getEditMap(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void getMapHistory(ActionEvent event) {
    Navigation.navigate(Screen.MAP_HISTORY_PAGE);
  }

  //  @FXML
  //  void getMapPage(ActionEvent event) {
  //    Navigation.navigate(Screen.FLOOR_PLAN);
  //  }

  @FXML
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }

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
    settingsPopOut.setVisible(false);
    helpPopOut.setVisible(false);
    historyPopOut.setVisible(false);
    exitPopOut.setVisible(false);
    logoutPopOut.setVisible(false);
    homePopOut.setVisible(false);
    // basePane.setVisible(false);
    menuPane.setVisible(true);
    TranslateTransition navigationPopOutTransition = new TranslateTransition();
    PauseTransition pause = new PauseTransition(Duration.millis(200));
    TranslateTransition serviceRequestPopOutTransition = new TranslateTransition();
    TranslateTransition settingsPopOutTransition = new TranslateTransition();
    TranslateTransition helpPopOutTransition = new TranslateTransition();
    TranslateTransition historyPopOutTransition = new TranslateTransition();
    TranslateTransition exitPopOutTransition = new TranslateTransition();
    TranslateTransition logoutPopOutTransition = new TranslateTransition();
    TranslateTransition homePopOutTransition = new TranslateTransition();

    homeButton.setOnMouseEntered(
        e -> {
          serviceRequestTrigger.setVisible(false);
          navigationTrigger.setVisible(false);
          settingsTrigger.setVisible(false);
          helpTrigger.setVisible(false);
          historyTrigger.setVisible(false);
          exitTrigger.setVisible(false);
          logoutTrigger.setVisible(false);

          serviceRequestButton.setVisible(true);
          navigationButton.setVisible(true);
          settingsButton.setVisible(true);
          helpButton.setVisible(true);
          historyButton.setVisible(true);
          exitButton.setVisible(true);
          logoutButton.setVisible(true);

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

          historyPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          historyPopOutTransition.setNode(historyPopOut);
          historyPopOutTransition.setToX(0);
          historyPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                historyPopOut.setVisible(false);
                historyTrigger.setVisible(false);
                historyButton.setVisible(true);
              });
          pause.play();

          settingsPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          settingsPopOutTransition.setNode(settingsPopOut);
          settingsPopOutTransition.setToX(0);
          settingsPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                settingsPopOut.setVisible(false);
                settingsTrigger.setVisible(false);
                settingsButton.setVisible(true);
              });
          pause.play();

          helpPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          helpPopOutTransition.setNode(helpPopOut);
          helpPopOutTransition.setToX(0);
          helpPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                helpPopOut.setVisible(false);
                helpTrigger.setVisible(false);
                helpButton.setVisible(true);
              });
          pause.play();

          exitPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          exitPopOutTransition.setNode(exitPopOut);
          exitPopOutTransition.setToX(0);
          exitPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                exitPopOut.setVisible(false);
                exitTrigger.setVisible(false);
                exitButton.setVisible(true);
              });
          pause.play();

          logoutPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          logoutPopOutTransition.setNode(logoutPopOut);
          logoutPopOutTransition.setToX(0);
          logoutPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                logoutPopOut.setVisible(false);
                logoutTrigger.setVisible(false);
                logoutButton.setVisible(true);
              });
          pause.play();

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

          homeTrigger.setVisible(true);
          homeButton.setVisible(false);
          homePopOutTransition.setDuration(javafx.util.Duration.millis(200));
          homePopOutTransition.setNode(homePopOut);
          homePopOutTransition.setToX(180);
          homePopOutTransition.play();

          homePopOut.setVisible(true);

          homePopOut.setOnMouseExited(
              g -> {
                homePopOutTransition.setDuration(javafx.util.Duration.millis(200));
                homePopOutTransition.setNode(homePopOut);
                homePopOutTransition.setToX(0);
                homePopOutTransition.play();

                pause.setOnFinished(
                    h -> {
                      homePopOut.setVisible(false);
                      homeTrigger.setVisible(false);
                      homeButton.setVisible(true);
                    });
                pause.play();
              });
        });
    homeTrigger.setOnMouseExited(
        e -> {
          homeTrigger.setVisible(false);
          homeButton.setVisible(true);
        });
    serviceRequestButton.setOnMouseEntered(
        e -> {
          homeTrigger.setVisible(false);

          navigationTrigger.setVisible(false);
          settingsTrigger.setVisible(false);
          helpTrigger.setVisible(false);
          historyTrigger.setVisible(false);
          exitTrigger.setVisible(false);
          logoutTrigger.setVisible(false);

          homeButton.setVisible(true);

          navigationButton.setVisible(true);
          settingsButton.setVisible(true);
          helpButton.setVisible(true);
          historyButton.setVisible(true);
          exitButton.setVisible(true);
          logoutButton.setVisible(true);
          homePopOutTransition.setDuration(javafx.util.Duration.millis(200));
          homePopOutTransition.setNode(homePopOut);
          homePopOutTransition.setToX(0);
          homePopOutTransition.play();

          pause.setOnFinished(
              h -> {
                homePopOut.setVisible(false);
                homeTrigger.setVisible(false);
                homeButton.setVisible(true);
              });
          pause.play();

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

          historyPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          historyPopOutTransition.setNode(historyPopOut);
          historyPopOutTransition.setToX(0);
          historyPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                historyPopOut.setVisible(false);
                historyTrigger.setVisible(false);
                historyButton.setVisible(true);
              });
          pause.play();

          settingsPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          settingsPopOutTransition.setNode(settingsPopOut);
          settingsPopOutTransition.setToX(0);
          settingsPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                settingsPopOut.setVisible(false);
                settingsTrigger.setVisible(false);
                settingsButton.setVisible(true);
              });
          pause.play();

          helpPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          helpPopOutTransition.setNode(helpPopOut);
          helpPopOutTransition.setToX(0);
          helpPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                helpPopOut.setVisible(false);
                helpTrigger.setVisible(false);
                helpButton.setVisible(true);
              });
          pause.play();

          exitPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          exitPopOutTransition.setNode(exitPopOut);
          exitPopOutTransition.setToX(0);
          exitPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                exitPopOut.setVisible(false);
                exitTrigger.setVisible(false);
                exitButton.setVisible(true);
              });
          pause.play();

          logoutPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          logoutPopOutTransition.setNode(logoutPopOut);
          logoutPopOutTransition.setToX(0);
          logoutPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                logoutPopOut.setVisible(false);
                logoutTrigger.setVisible(false);
                logoutButton.setVisible(true);
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
          homeTrigger.setVisible(false);
          serviceRequestTrigger.setVisible(false);

          settingsTrigger.setVisible(false);
          helpTrigger.setVisible(false);
          historyTrigger.setVisible(false);
          exitTrigger.setVisible(false);
          logoutTrigger.setVisible(false);

          homeButton.setVisible(true);
          serviceRequestButton.setVisible(true);

          settingsButton.setVisible(true);
          helpButton.setVisible(true);
          historyButton.setVisible(true);
          exitButton.setVisible(true);
          logoutButton.setVisible(true);
          homePopOutTransition.setDuration(javafx.util.Duration.millis(200));
          homePopOutTransition.setNode(homePopOut);
          homePopOutTransition.setToX(0);
          homePopOutTransition.play();

          pause.setOnFinished(
              h -> {
                homePopOut.setVisible(false);
                homeTrigger.setVisible(false);
                homeButton.setVisible(true);
              });
          pause.play();

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

          historyPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          historyPopOutTransition.setNode(historyPopOut);
          historyPopOutTransition.setToX(0);
          historyPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                historyPopOut.setVisible(false);
                historyTrigger.setVisible(false);
                historyButton.setVisible(true);
              });
          pause.play();

          settingsPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          settingsPopOutTransition.setNode(settingsPopOut);
          settingsPopOutTransition.setToX(0);
          settingsPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                settingsPopOut.setVisible(false);
                settingsTrigger.setVisible(false);
                settingsButton.setVisible(true);
              });
          pause.play();

          helpPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          helpPopOutTransition.setNode(helpPopOut);
          helpPopOutTransition.setToX(0);
          helpPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                helpPopOut.setVisible(false);
                helpTrigger.setVisible(false);
                helpButton.setVisible(true);
              });
          pause.play();

          exitPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          exitPopOutTransition.setNode(exitPopOut);
          exitPopOutTransition.setToX(0);
          exitPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                exitPopOut.setVisible(false);
                exitTrigger.setVisible(false);
                exitButton.setVisible(true);
              });
          pause.play();

          logoutPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          logoutPopOutTransition.setNode(logoutPopOut);
          logoutPopOutTransition.setToX(0);
          logoutPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                logoutPopOut.setVisible(false);
                logoutTrigger.setVisible(false);
                logoutButton.setVisible(true);
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
    navigationPopOut.setOnMouseEntered(
        e -> {
          navigationTrigger.setVisible(true);
          navigationButton.setVisible(false);
        });
    navigationTrigger.setOnMouseExited(
        e -> {
          navigationTrigger.setVisible(false);
          navigationButton.setVisible(true);
        });
    historyButton.setOnMouseEntered(
        e -> {
          homeTrigger.setVisible(false);
          serviceRequestTrigger.setVisible(false);
          navigationTrigger.setVisible(false);
          settingsTrigger.setVisible(false);
          helpTrigger.setVisible(false);

          exitTrigger.setVisible(false);
          logoutTrigger.setVisible(false);

          homeButton.setVisible(true);
          serviceRequestButton.setVisible(true);
          navigationButton.setVisible(true);
          settingsButton.setVisible(true);
          helpButton.setVisible(true);

          exitButton.setVisible(true);
          logoutButton.setVisible(true);

          homePopOutTransition.setDuration(javafx.util.Duration.millis(200));
          homePopOutTransition.setNode(homePopOut);
          homePopOutTransition.setToX(0);
          homePopOutTransition.play();

          pause.setOnFinished(
              h -> {
                homePopOut.setVisible(false);
                homeTrigger.setVisible(false);
                homeButton.setVisible(true);
              });
          pause.play();

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

          settingsPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          settingsPopOutTransition.setNode(settingsPopOut);
          settingsPopOutTransition.setToX(0);
          settingsPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                settingsPopOut.setVisible(false);
                settingsTrigger.setVisible(false);
                settingsButton.setVisible(true);
              });
          pause.play();

          helpPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          helpPopOutTransition.setNode(helpPopOut);
          helpPopOutTransition.setToX(0);
          helpPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                helpPopOut.setVisible(false);
                helpTrigger.setVisible(false);
                helpButton.setVisible(true);
              });
          pause.play();

          exitPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          exitPopOutTransition.setNode(exitPopOut);
          exitPopOutTransition.setToX(0);
          exitPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                exitPopOut.setVisible(false);
                exitTrigger.setVisible(false);
                exitButton.setVisible(true);
              });
          pause.play();

          logoutPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          logoutPopOutTransition.setNode(logoutPopOut);
          logoutPopOutTransition.setToX(0);
          logoutPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                logoutPopOut.setVisible(false);
                logoutTrigger.setVisible(false);
                logoutButton.setVisible(true);
              });
          pause.play();

          historyTrigger.setVisible(true);
          historyButton.setVisible(false);

          historyPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          historyPopOutTransition.setNode(historyPopOut);
          historyPopOutTransition.setToX(180);
          historyPopOutTransition.play();

          historyPopOut.setVisible(true);
          historyPopOut.setOnMouseExited(
              f -> {
                historyPopOutTransition.setDuration(javafx.util.Duration.millis(200));
                historyPopOutTransition.setNode(historyPopOut);
                historyPopOutTransition.setToX(0);
                historyPopOutTransition.play();

                pause.setOnFinished(
                    g -> {
                      historyPopOut.setVisible(false);
                      historyTrigger.setVisible(false);
                      historyButton.setVisible(true);
                    });
                pause.play();
              });
        });
    historyPopOut.setOnMouseEntered(
        e -> {
          historyTrigger.setVisible(true);
          historyButton.setVisible(false);
        });
    historyTrigger.setOnMouseExited(
        e -> {
          historyTrigger.setVisible(false);
          historyButton.setVisible(true);
        });
    settingsButton.setOnMouseEntered(
        e -> {
          homeTrigger.setVisible(false);
          serviceRequestTrigger.setVisible(false);
          navigationTrigger.setVisible(false);

          helpTrigger.setVisible(false);
          historyTrigger.setVisible(false);
          exitTrigger.setVisible(false);
          logoutTrigger.setVisible(false);

          homeButton.setVisible(true);
          serviceRequestButton.setVisible(true);
          navigationButton.setVisible(true);

          helpButton.setVisible(true);
          historyButton.setVisible(true);
          exitButton.setVisible(true);
          logoutButton.setVisible(true);

          homePopOutTransition.setDuration(javafx.util.Duration.millis(200));
          homePopOutTransition.setNode(homePopOut);
          homePopOutTransition.setToX(0);
          homePopOutTransition.play();

          pause.setOnFinished(
              h -> {
                homePopOut.setVisible(false);
                homeTrigger.setVisible(false);
                homeButton.setVisible(true);
              });
          pause.play();

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

          historyPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          historyPopOutTransition.setNode(historyPopOut);
          historyPopOutTransition.setToX(0);
          historyPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                historyPopOut.setVisible(false);
                historyTrigger.setVisible(false);
                historyButton.setVisible(true);
              });
          pause.play();

          helpPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          helpPopOutTransition.setNode(helpPopOut);
          helpPopOutTransition.setToX(0);
          helpPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                helpPopOut.setVisible(false);
                helpTrigger.setVisible(false);
                helpButton.setVisible(true);
              });
          pause.play();

          exitPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          exitPopOutTransition.setNode(exitPopOut);
          exitPopOutTransition.setToX(0);
          exitPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                exitPopOut.setVisible(false);
                exitTrigger.setVisible(false);
                exitButton.setVisible(true);
              });
          pause.play();

          logoutPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          logoutPopOutTransition.setNode(logoutPopOut);
          logoutPopOutTransition.setToX(0);
          logoutPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                logoutPopOut.setVisible(false);
                logoutTrigger.setVisible(false);
                logoutButton.setVisible(true);
              });
          pause.play();

          settingsTrigger.setVisible(true);
          settingsButton.setVisible(false);

          settingsPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          settingsPopOutTransition.setNode(settingsPopOut);
          settingsPopOutTransition.setToX(180);
          settingsPopOutTransition.play();

          settingsPopOut.setVisible(true);
          settingsPopOut.setOnMouseExited(
              f -> {
                settingsPopOutTransition.setDuration(javafx.util.Duration.millis(200));
                settingsPopOutTransition.setNode(settingsPopOut);
                settingsPopOutTransition.setToX(0);
                settingsPopOutTransition.play();

                pause.setOnFinished(
                    g -> {
                      settingsPopOut.setVisible(false);
                      settingsTrigger.setVisible(false);
                      settingsButton.setVisible(true);
                    });
                pause.play();
              });
        });
    settingsPopOut.setOnMouseEntered(
        e -> {
          settingsTrigger.setVisible(true);
          settingsButton.setVisible(false);
        });
    settingsTrigger.setOnMouseExited(
        e -> {
          settingsTrigger.setVisible(false);
          settingsButton.setVisible(true);
        });
    helpButton.setOnMouseEntered(
        e -> {
          homeTrigger.setVisible(false);
          serviceRequestTrigger.setVisible(false);
          navigationTrigger.setVisible(false);
          settingsTrigger.setVisible(false);

          historyTrigger.setVisible(false);
          exitTrigger.setVisible(false);
          logoutTrigger.setVisible(false);

          homeButton.setVisible(true);
          serviceRequestButton.setVisible(true);
          navigationButton.setVisible(true);
          settingsButton.setVisible(true);

          historyButton.setVisible(true);
          exitButton.setVisible(true);
          logoutButton.setVisible(true);

          homePopOutTransition.setDuration(javafx.util.Duration.millis(200));
          homePopOutTransition.setNode(homePopOut);
          homePopOutTransition.setToX(0);
          homePopOutTransition.play();

          pause.setOnFinished(
              h -> {
                homePopOut.setVisible(false);
                homeTrigger.setVisible(false);
                homeButton.setVisible(true);
              });
          pause.play();

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

          historyPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          historyPopOutTransition.setNode(historyPopOut);
          historyPopOutTransition.setToX(0);
          historyPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                historyPopOut.setVisible(false);
                historyTrigger.setVisible(false);
                historyButton.setVisible(true);
              });
          pause.play();

          settingsPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          settingsPopOutTransition.setNode(settingsPopOut);
          settingsPopOutTransition.setToX(0);
          settingsPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                settingsPopOut.setVisible(false);
                settingsTrigger.setVisible(false);
                settingsButton.setVisible(true);
              });
          pause.play();

          exitPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          exitPopOutTransition.setNode(exitPopOut);
          exitPopOutTransition.setToX(0);
          exitPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                exitPopOut.setVisible(false);
                exitTrigger.setVisible(false);
                exitButton.setVisible(true);
              });
          pause.play();

          helpTrigger.setVisible(true);
          helpButton.setVisible(false);

          helpPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          helpPopOutTransition.setNode(helpPopOut);
          helpPopOutTransition.setToX(180);
          helpPopOutTransition.play();

          logoutPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          logoutPopOutTransition.setNode(logoutPopOut);
          logoutPopOutTransition.setToX(0);
          logoutPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                logoutPopOut.setVisible(false);
                logoutTrigger.setVisible(false);
                logoutButton.setVisible(true);
              });
          pause.play();

          helpPopOut.setVisible(true);
          helpPopOut.setOnMouseExited(
              f -> {
                helpPopOutTransition.setDuration(javafx.util.Duration.millis(200));
                helpPopOutTransition.setNode(helpPopOut);
                helpPopOutTransition.setToX(0);
                helpPopOutTransition.play();

                pause.setOnFinished(
                    g -> {
                      helpPopOut.setVisible(false);
                      helpTrigger.setVisible(false);
                      helpButton.setVisible(true);
                    });
                pause.play();
              });
        });
    helpPopOut.setOnMouseEntered(
        e -> {
          helpTrigger.setVisible(true);
          helpButton.setVisible(false);
        });
    helpTrigger.setOnMouseExited(
        e -> {
          helpTrigger.setVisible(false);
          helpButton.setVisible(true);
        });
    exitButton.setOnMouseEntered(
        e -> {
          homeTrigger.setVisible(false);
          serviceRequestTrigger.setVisible(false);
          navigationTrigger.setVisible(false);
          settingsTrigger.setVisible(false);
          helpTrigger.setVisible(false);
          historyTrigger.setVisible(false);

          homeButton.setVisible(true);
          serviceRequestButton.setVisible(true);
          navigationButton.setVisible(true);
          settingsButton.setVisible(true);
          helpButton.setVisible(true);
          historyButton.setVisible(true);

          logoutButton.setVisible(true);

          logoutTrigger.setVisible(false);
          homePopOutTransition.setDuration(javafx.util.Duration.millis(200));
          homePopOutTransition.setNode(homePopOut);
          homePopOutTransition.setToX(0);
          homePopOutTransition.play();

          pause.setOnFinished(
              h -> {
                homePopOut.setVisible(false);
                homeTrigger.setVisible(false);
                homeButton.setVisible(true);
              });
          pause.play();

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

          historyPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          historyPopOutTransition.setNode(historyPopOut);
          historyPopOutTransition.setToX(0);
          historyPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                historyPopOut.setVisible(false);
                historyTrigger.setVisible(false);
                historyButton.setVisible(true);
              });
          pause.play();

          settingsPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          settingsPopOutTransition.setNode(settingsPopOut);
          settingsPopOutTransition.setToX(0);
          settingsPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                settingsPopOut.setVisible(false);
                settingsTrigger.setVisible(false);
                settingsButton.setVisible(true);
              });
          pause.play();

          helpPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          helpPopOutTransition.setNode(helpPopOut);
          helpPopOutTransition.setToX(0);
          helpPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                helpPopOut.setVisible(false);
                helpTrigger.setVisible(false);
                helpButton.setVisible(true);
              });
          pause.play();

          exitTrigger.setVisible(true);
          exitButton.setVisible(false);

          exitPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          exitPopOutTransition.setNode(exitPopOut);
          exitPopOutTransition.setToX(180);
          exitPopOutTransition.play();

          logoutPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          logoutPopOutTransition.setNode(logoutPopOut);
          logoutPopOutTransition.setToX(0);
          logoutPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                logoutPopOut.setVisible(false);
                logoutTrigger.setVisible(false);
                logoutButton.setVisible(true);
              });
          pause.play();

          exitPopOut.setVisible(true);
          exitPopOut.setOnMouseExited(
              f -> {
                exitPopOutTransition.setDuration(javafx.util.Duration.millis(200));
                exitPopOutTransition.setNode(exitPopOut);
                exitPopOutTransition.setToX(0);
                exitPopOutTransition.play();

                pause.setOnFinished(
                    g -> {
                      exitPopOut.setVisible(false);
                      exitTrigger.setVisible(false);
                      exitButton.setVisible(true);
                    });
                pause.play();
              });
        });
    exitPopOut.setOnMouseEntered(
        e -> {
          exitTrigger.setVisible(true);
          exitButton.setVisible(false);
        });

    exitTrigger.setOnMouseExited(
        e -> {
          exitTrigger.setVisible(false);
          exitButton.setVisible(true);
        });
    logoutButton.setOnMouseEntered(
        e -> {
          homeTrigger.setVisible(false);
          serviceRequestTrigger.setVisible(false);
          navigationTrigger.setVisible(false);
          settingsTrigger.setVisible(false);
          helpTrigger.setVisible(false);
          historyTrigger.setVisible(false);
          exitTrigger.setVisible(false);

          homeButton.setVisible(true);
          serviceRequestButton.setVisible(true);
          navigationButton.setVisible(true);
          settingsButton.setVisible(true);
          helpButton.setVisible(true);
          historyButton.setVisible(true);
          exitButton.setVisible(true);

          homePopOutTransition.setDuration(javafx.util.Duration.millis(200));
          homePopOutTransition.setNode(homePopOut);
          homePopOutTransition.setToX(0);
          homePopOutTransition.play();

          pause.setOnFinished(
              h -> {
                homePopOut.setVisible(false);
                homeTrigger.setVisible(false);
                homeButton.setVisible(true);
              });
          pause.play();

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

          settingsPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          settingsPopOutTransition.setNode(settingsPopOut);
          settingsPopOutTransition.setToX(0);
          settingsPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                settingsPopOut.setVisible(false);
                settingsTrigger.setVisible(false);
                settingsButton.setVisible(true);
              });
          pause.play();

          helpPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          helpPopOutTransition.setNode(helpPopOut);
          helpPopOutTransition.setToX(0);
          helpPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                helpPopOut.setVisible(false);
                helpTrigger.setVisible(false);
                helpButton.setVisible(true);
              });
          pause.play();

          exitPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          exitPopOutTransition.setNode(exitPopOut);
          exitPopOutTransition.setToX(0);
          exitPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                exitPopOut.setVisible(false);
                exitTrigger.setVisible(false);
                exitButton.setVisible(true);
              });
          pause.play();

          logoutTrigger.setVisible(true);
          logoutButton.setVisible(false);

          logoutPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          logoutPopOutTransition.setNode(logoutPopOut);
          logoutPopOutTransition.setToX(180);
          logoutPopOutTransition.play();

          historyPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          historyPopOutTransition.setNode(historyPopOut);
          historyPopOutTransition.setToX(0);
          historyPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                historyPopOut.setVisible(false);
                historyTrigger.setVisible(false);
                historyButton.setVisible(true);
              });
          pause.play();

          logoutPopOut.setVisible(true);
          logoutPopOut.setOnMouseExited(
              f -> {
                logoutPopOutTransition.setDuration(javafx.util.Duration.millis(200));
                logoutPopOutTransition.setNode(logoutPopOut);
                logoutPopOutTransition.setToX(0);
                logoutPopOutTransition.play();

                pause.setOnFinished(
                    g -> {
                      logoutPopOut.setVisible(false);
                      logoutTrigger.setVisible(false);
                      logoutButton.setVisible(true);
                    });
                pause.play();
              });
        });
    logoutPopOut.setOnMouseEntered(
        e -> {
          logoutTrigger.setVisible(true);
          logoutButton.setVisible(false);
        });
    logoutTrigger.setOnMouseExited(
        e -> {
          logoutTrigger.setVisible(false);
          logoutButton.setVisible(true);
        });

    basePane.setOnMouseEntered(
        e -> {
          homeTrigger.setVisible(false);
          serviceRequestTrigger.setVisible(false);
          navigationTrigger.setVisible(false);
          settingsTrigger.setVisible(false);
          helpTrigger.setVisible(false);
          historyTrigger.setVisible(false);
          exitTrigger.setVisible(false);
          logoutTrigger.setVisible(false);

          homeButton.setVisible(true);
          serviceRequestButton.setVisible(true);
          navigationButton.setVisible(true);
          settingsButton.setVisible(true);
          helpButton.setVisible(true);
          historyButton.setVisible(true);
          exitButton.setVisible(true);
          logoutButton.setVisible(true);

          homePopOutTransition.setDuration(javafx.util.Duration.millis(200));
          homePopOutTransition.setNode(homePopOut);
          homePopOutTransition.setToX(0);
          homePopOutTransition.play();

          pause.setOnFinished(
              h -> {
                homePopOut.setVisible(false);
                homeTrigger.setVisible(false);
                homeButton.setVisible(true);
              });
          pause.play();

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

          settingsPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          settingsPopOutTransition.setNode(settingsPopOut);
          settingsPopOutTransition.setToX(0);
          settingsPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                settingsPopOut.setVisible(false);
                settingsTrigger.setVisible(false);
                settingsButton.setVisible(true);
              });
          pause.play();

          helpPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          helpPopOutTransition.setNode(helpPopOut);
          helpPopOutTransition.setToX(0);
          helpPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                helpPopOut.setVisible(false);
                helpTrigger.setVisible(false);
                helpButton.setVisible(true);
              });
          pause.play();

          exitPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          exitPopOutTransition.setNode(exitPopOut);
          exitPopOutTransition.setToX(0);
          exitPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                exitPopOut.setVisible(false);
                exitTrigger.setVisible(false);
                exitButton.setVisible(true);
              });
          pause.play();

          logoutPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          logoutPopOutTransition.setNode(logoutPopOut);
          logoutPopOutTransition.setToX(0);
          logoutPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                logoutPopOut.setVisible(false);
                logoutTrigger.setVisible(false);
                logoutButton.setVisible(true);
              });
          pause.play();

          historyPopOutTransition.setDuration(javafx.util.Duration.millis(200));
          historyPopOutTransition.setNode(historyPopOut);
          historyPopOutTransition.setToX(0);
          historyPopOutTransition.play();

          pause.setOnFinished(
              g -> {
                historyPopOut.setVisible(false);
                historyTrigger.setVisible(false);
                historyButton.setVisible(true);
              });
          pause.play();
        });
  }
}
