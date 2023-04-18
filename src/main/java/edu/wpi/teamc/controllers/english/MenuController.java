package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
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

    AtomicBoolean isHovering = new AtomicBoolean(false);

    // Define a PauseTransition with a delay of 200 milliseconds
    PauseTransition delay = new PauseTransition(Duration.millis(200));

    homeButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      homePopOutTransition.setDuration(Duration.millis(200));
                      homePopOutTransition.setNode(homePopOut);
                      homePopOutTransition.setToX(180);
                      homePopOutTransition.play();
                      homePopOut.setVisible(true);
                      homeTrigger.setVisible(true);
                      homeButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                homePopOutTransition.setDuration(Duration.millis(200));
                homePopOutTransition.setNode(homePopOut);
                homePopOutTransition.setToX(0);
                homePopOutTransition.setOnFinished(
                    e -> {
                      if (!homePopOut.isHover() && !homeTrigger.isHover()) {
                        homePopOut.setVisible(false);
                        homeTrigger.setVisible(false);
                        homeButton.setVisible(true);
                      }
                    });
                homePopOutTransition.play();
              }
            });

    homePopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !homeButton.isHover() && !homeTrigger.isHover()) {
                homePopOutTransition.setDuration(Duration.millis(200));
                homePopOutTransition.setNode(homePopOut);
                homePopOutTransition.setToX(0);
                homePopOutTransition.setOnFinished(
                    e -> {
                      if (!homePopOut.isHover() && !homeTrigger.isHover()) {
                        homePopOut.setVisible(false);
                        homeTrigger.setVisible(false);
                        homeButton.setVisible(true);
                      }
                    });
                homePopOutTransition.play();
              } else if (newValue) {
                homePopOutTransition.stop();
                homePopOut.setTranslateX(180);
              }
            });

    homeTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !homeButton.isHover() && !homePopOut.isHover()) {
                homePopOutTransition.setDuration(Duration.millis(200));
                homePopOutTransition.setNode(homePopOut);
                homePopOutTransition.setToX(0);
                homePopOutTransition.setOnFinished(
                    e -> {
                      if (!homePopOut.isHover() && !homeTrigger.isHover()) {
                        homePopOut.setVisible(false);
                        homeTrigger.setVisible(false);
                        homeButton.setVisible(true);
                      }
                    });
                homePopOutTransition.play();
              } else if (newValue) {
                homePopOutTransition.setDuration(Duration.millis(200));
                homePopOutTransition.setNode(homePopOut);
                homePopOut.setTranslateX(180);
                homePopOutTransition.play();
              }
            });

    serviceRequestButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      serviceRequestPopOutTransition.setDuration(Duration.millis(200));
                      serviceRequestPopOutTransition.setNode(serviceRequestPopOut);
                      serviceRequestPopOutTransition.setToX(180);
                      serviceRequestPopOutTransition.play();
                      serviceRequestPopOut.setVisible(true);
                      serviceRequestTrigger.setVisible(true);
                      serviceRequestButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                serviceRequestPopOutTransition.setDuration(Duration.millis(200));
                serviceRequestPopOutTransition.setNode(serviceRequestPopOut);
                serviceRequestPopOutTransition.setToX(0);
                serviceRequestPopOutTransition.setOnFinished(
                    e -> {
                      if (!serviceRequestPopOut.isHover() && !serviceRequestTrigger.isHover()) {
                        serviceRequestPopOut.setVisible(false);
                        serviceRequestTrigger.setVisible(false);
                        serviceRequestButton.setVisible(true);
                      }
                    });
                serviceRequestPopOutTransition.play();
              }
            });
    serviceRequestPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue
                  && !serviceRequestButton.isHover()
                  && !serviceRequestTrigger.isHover()) {
                serviceRequestPopOutTransition.setDuration(Duration.millis(200));
                serviceRequestPopOutTransition.setNode(serviceRequestPopOut);
                serviceRequestPopOutTransition.setToX(0);
                serviceRequestPopOutTransition.setOnFinished(
                    e -> {
                      if (!serviceRequestPopOut.isHover() && !serviceRequestTrigger.isHover()) {
                        serviceRequestPopOut.setVisible(false);
                        serviceRequestTrigger.setVisible(false);
                        serviceRequestButton.setVisible(true);
                      }
                    });
                serviceRequestPopOutTransition.play();
              } else if (newValue) {
                serviceRequestPopOutTransition.stop();
                serviceRequestPopOut.setTranslateX(180);
              }
            });
    serviceRequestTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !serviceRequestButton.isHover() && !serviceRequestPopOut.isHover()) {
                serviceRequestPopOutTransition.setDuration(Duration.millis(200));
                serviceRequestPopOutTransition.setNode(serviceRequestPopOut);
                serviceRequestPopOutTransition.setToX(0);
                serviceRequestPopOutTransition.setOnFinished(
                    e -> {
                      if (!serviceRequestPopOut.isHover() && !serviceRequestTrigger.isHover()) {
                        serviceRequestPopOut.setVisible(false);
                        serviceRequestTrigger.setVisible(false);
                        serviceRequestButton.setVisible(true);
                      }
                    });
                serviceRequestPopOutTransition.play();
              } else if (newValue) {
                serviceRequestPopOutTransition.setDuration(Duration.millis(200));
                serviceRequestPopOutTransition.setNode(serviceRequestPopOut);
                serviceRequestPopOut.setTranslateX(180);
                serviceRequestPopOutTransition.play();
              }
            });
    navigationButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      navigationPopOutTransition.setDuration(Duration.millis(200));
                      navigationPopOutTransition.setNode(navigationPopOut);
                      navigationPopOutTransition.setToX(180);
                      navigationPopOutTransition.play();
                      navigationPopOut.setVisible(true);
                      navigationTrigger.setVisible(true);
                      navigationButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                navigationPopOutTransition.setDuration(Duration.millis(200));
                navigationPopOutTransition.setNode(navigationPopOut);
                navigationPopOutTransition.setToX(0);
                navigationPopOutTransition.setOnFinished(
                    e -> {
                      if (!navigationPopOut.isHover() && !navigationTrigger.isHover()) {
                        navigationPopOut.setVisible(false);
                        navigationTrigger.setVisible(false);
                        navigationButton.setVisible(true);
                      }
                    });
                navigationPopOutTransition.play();
              }
            });
    navigationPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !navigationButton.isHover() && !navigationTrigger.isHover()) {
                navigationPopOutTransition.setDuration(Duration.millis(200));
                navigationPopOutTransition.setNode(navigationPopOut);
                navigationPopOutTransition.setToX(0);
                navigationPopOutTransition.setOnFinished(
                    e -> {
                      if (!navigationPopOut.isHover() && !navigationTrigger.isHover()) {
                        navigationPopOut.setVisible(false);
                        navigationTrigger.setVisible(false);
                        navigationButton.setVisible(true);
                      }
                    });
                navigationPopOutTransition.play();
              } else if (newValue) {
                navigationPopOutTransition.stop();
                navigationPopOut.setTranslateX(180);
              }
            });
    navigationTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !navigationButton.isHover() && !navigationPopOut.isHover()) {
                navigationPopOutTransition.setDuration(Duration.millis(200));
                navigationPopOutTransition.setNode(navigationPopOut);
                navigationPopOutTransition.setToX(0);
                navigationPopOutTransition.setOnFinished(
                    e -> {
                      if (!navigationPopOut.isHover() && !navigationTrigger.isHover()) {
                        navigationPopOut.setVisible(false);
                        navigationTrigger.setVisible(false);
                        navigationButton.setVisible(true);
                      }
                    });
                navigationPopOutTransition.play();
              } else if (newValue) {
                navigationPopOutTransition.setDuration(Duration.millis(200));
                navigationPopOutTransition.setNode(navigationPopOut);
                navigationPopOut.setTranslateX(180);
                navigationPopOutTransition.play();
              }
            });
    settingsButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      settingsPopOutTransition.setDuration(Duration.millis(200));
                      settingsPopOutTransition.setNode(settingsPopOut);
                      settingsPopOutTransition.setToX(180);
                      settingsPopOutTransition.play();
                      settingsPopOut.setVisible(true);
                      settingsTrigger.setVisible(true);
                      settingsButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                settingsPopOutTransition.setDuration(Duration.millis(200));
                settingsPopOutTransition.setNode(settingsPopOut);
                settingsPopOutTransition.setToX(0);
                settingsPopOutTransition.setOnFinished(
                    e -> {
                      if (!settingsPopOut.isHover() && !settingsTrigger.isHover()) {
                        settingsPopOut.setVisible(false);
                        settingsTrigger.setVisible(false);
                        settingsButton.setVisible(true);
                      }
                    });
                settingsPopOutTransition.play();
              }
            });
    settingsPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !settingsButton.isHover() && !settingsTrigger.isHover()) {
                settingsPopOutTransition.setDuration(Duration.millis(200));
                settingsPopOutTransition.setNode(settingsPopOut);
                settingsPopOutTransition.setToX(0);
                settingsPopOutTransition.setOnFinished(
                    e -> {
                      if (!settingsPopOut.isHover() && !settingsTrigger.isHover()) {
                        settingsPopOut.setVisible(false);
                        settingsTrigger.setVisible(false);
                        settingsButton.setVisible(true);
                      }
                    });
                settingsPopOutTransition.play();
              } else if (newValue) {
                settingsPopOutTransition.stop();
                settingsPopOut.setTranslateX(180);
              }
            });
    settingsTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !settingsButton.isHover() && !settingsPopOut.isHover()) {
                settingsPopOutTransition.setDuration(Duration.millis(200));
                settingsPopOutTransition.setNode(settingsPopOut);
                settingsPopOutTransition.setToX(0);
                settingsPopOutTransition.setOnFinished(
                    e -> {
                      if (!settingsPopOut.isHover() && !settingsTrigger.isHover()) {
                        settingsPopOut.setVisible(false);
                        settingsTrigger.setVisible(false);
                        settingsButton.setVisible(true);
                      }
                    });
                settingsPopOutTransition.play();
              } else if (newValue) {
                settingsPopOutTransition.setDuration(Duration.millis(200));
                settingsPopOutTransition.setNode(settingsPopOut);
                settingsPopOut.setTranslateX(180);
                settingsPopOutTransition.play();
              }
            });
    historyButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      historyPopOutTransition.setDuration(Duration.millis(200));
                      historyPopOutTransition.setNode(historyPopOut);
                      historyPopOutTransition.setToX(180);
                      historyPopOutTransition.play();
                      historyPopOut.setVisible(true);
                      historyTrigger.setVisible(true);
                      historyButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                historyPopOutTransition.setDuration(Duration.millis(200));
                historyPopOutTransition.setNode(historyPopOut);
                historyPopOutTransition.setToX(0);
                historyPopOutTransition.setOnFinished(
                    e -> {
                      if (!historyPopOut.isHover() && !historyTrigger.isHover()) {
                        historyPopOut.setVisible(false);
                        historyTrigger.setVisible(false);
                        historyButton.setVisible(true);
                      }
                    });
                historyPopOutTransition.play();
              }
            });
    historyPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !historyButton.isHover() && !historyTrigger.isHover()) {
                historyPopOutTransition.setDuration(Duration.millis(200));
                historyPopOutTransition.setNode(historyPopOut);
                historyPopOutTransition.setToX(0);
                historyPopOutTransition.setOnFinished(
                    e -> {
                      if (!historyPopOut.isHover() && !historyTrigger.isHover()) {
                        historyPopOut.setVisible(false);
                        historyTrigger.setVisible(false);
                        historyButton.setVisible(true);
                      }
                    });
                historyPopOutTransition.play();
              } else if (newValue) {
                historyPopOutTransition.stop();
                historyPopOut.setTranslateX(180);
              }
            });
    historyTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !historyButton.isHover() && !historyPopOut.isHover()) {
                historyPopOutTransition.setDuration(Duration.millis(200));
                historyPopOutTransition.setNode(historyPopOut);
                historyPopOutTransition.setToX(0);
                historyPopOutTransition.setOnFinished(
                    e -> {
                      if (!historyPopOut.isHover() && !historyTrigger.isHover()) {
                        historyPopOut.setVisible(false);
                        historyTrigger.setVisible(false);
                        historyButton.setVisible(true);
                      }
                    });
                historyPopOutTransition.play();
              } else if (newValue) {
                historyPopOutTransition.setDuration(Duration.millis(200));
                historyPopOutTransition.setNode(historyPopOut);
                historyPopOut.setTranslateX(180);
                historyPopOutTransition.play();
              }
            });
    helpButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      helpPopOutTransition.setDuration(Duration.millis(200));
                      helpPopOutTransition.setNode(helpPopOut);
                      helpPopOutTransition.setToX(180);
                      helpPopOutTransition.play();
                      helpPopOut.setVisible(true);
                      helpTrigger.setVisible(true);
                      helpButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                helpPopOutTransition.setDuration(Duration.millis(200));
                helpPopOutTransition.setNode(helpPopOut);
                helpPopOutTransition.setToX(0);
                helpPopOutTransition.setOnFinished(
                    e -> {
                      if (!helpPopOut.isHover() && !helpTrigger.isHover()) {
                        helpPopOut.setVisible(false);
                        helpTrigger.setVisible(false);
                        helpButton.setVisible(true);
                      }
                    });
                helpPopOutTransition.play();
              }
            });
    helpPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !helpButton.isHover() && !helpTrigger.isHover()) {
                helpPopOutTransition.setDuration(Duration.millis(200));
                helpPopOutTransition.setNode(helpPopOut);
                helpPopOutTransition.setToX(0);
                helpPopOutTransition.setOnFinished(
                    e -> {
                      if (!helpPopOut.isHover() && !helpTrigger.isHover()) {
                        helpPopOut.setVisible(false);
                        helpTrigger.setVisible(false);
                        helpButton.setVisible(true);
                      }
                    });
                helpPopOutTransition.play();
              } else if (newValue) {
                helpPopOutTransition.stop();
                helpPopOut.setTranslateX(180);
              }
            });
    helpTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !helpButton.isHover() && !helpPopOut.isHover()) {
                helpPopOutTransition.setDuration(Duration.millis(200));
                helpPopOutTransition.setNode(helpPopOut);
                helpPopOutTransition.setToX(0);
                helpPopOutTransition.setOnFinished(
                    e -> {
                      if (!helpPopOut.isHover() && !helpTrigger.isHover()) {
                        helpPopOut.setVisible(false);
                        helpTrigger.setVisible(false);
                        helpButton.setVisible(true);
                      }
                    });
                helpPopOutTransition.play();
              } else if (newValue) {
                helpPopOutTransition.setDuration(Duration.millis(200));
                helpPopOutTransition.setNode(helpPopOut);
                helpPopOut.setTranslateX(180);
                helpPopOutTransition.play();
              }
            });
    logoutButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      logoutPopOutTransition.setDuration(Duration.millis(200));
                      logoutPopOutTransition.setNode(logoutPopOut);
                      logoutPopOutTransition.setToX(180);
                      logoutPopOutTransition.play();
                      logoutPopOut.setVisible(true);
                      logoutTrigger.setVisible(true);
                      logoutButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                logoutPopOutTransition.setDuration(Duration.millis(200));
                logoutPopOutTransition.setNode(logoutPopOut);
                logoutPopOutTransition.setToX(0);
                logoutPopOutTransition.setOnFinished(
                    e -> {
                      if (!logoutPopOut.isHover() && !logoutTrigger.isHover()) {
                        logoutPopOut.setVisible(false);
                        logoutTrigger.setVisible(false);
                        logoutButton.setVisible(true);
                      }
                    });
                logoutPopOutTransition.play();
              }
            });
    logoutPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !logoutButton.isHover() && !logoutTrigger.isHover()) {
                logoutPopOutTransition.setDuration(Duration.millis(200));
                logoutPopOutTransition.setNode(logoutPopOut);
                logoutPopOutTransition.setToX(0);
                logoutPopOutTransition.setOnFinished(
                    e -> {
                      if (!logoutPopOut.isHover() && !logoutTrigger.isHover()) {
                        logoutPopOut.setVisible(false);
                        logoutTrigger.setVisible(false);
                        logoutButton.setVisible(true);
                      }
                    });
                logoutPopOutTransition.play();
              } else if (newValue) {
                logoutPopOutTransition.stop();
                logoutPopOut.setTranslateX(180);
              }
            });
    logoutTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !logoutButton.isHover() && !logoutPopOut.isHover()) {
                logoutPopOutTransition.setDuration(Duration.millis(200));
                logoutPopOutTransition.setNode(logoutPopOut);
                logoutPopOutTransition.setToX(0);
                logoutPopOutTransition.setOnFinished(
                    e -> {
                      if (!logoutPopOut.isHover() && !logoutTrigger.isHover()) {
                        logoutPopOut.setVisible(false);
                        logoutTrigger.setVisible(false);
                        logoutButton.setVisible(true);
                      }
                    });
                logoutPopOutTransition.play();
              } else if (newValue) {
                logoutPopOutTransition.setDuration(Duration.millis(200));
                logoutPopOutTransition.setNode(logoutPopOut);
                logoutPopOut.setTranslateX(180);
                logoutPopOutTransition.play();
              }
            });
    exitButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      exitPopOutTransition.setDuration(Duration.millis(200));
                      exitPopOutTransition.setNode(exitPopOut);
                      exitPopOutTransition.setToX(180);
                      exitPopOutTransition.play();
                      exitPopOut.setVisible(true);
                      exitTrigger.setVisible(true);
                      exitButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                exitPopOutTransition.setDuration(Duration.millis(200));
                exitPopOutTransition.setNode(exitPopOut);
                exitPopOutTransition.setToX(0);
                exitPopOutTransition.setOnFinished(
                    e -> {
                      if (!exitPopOut.isHover() && !exitTrigger.isHover()) {
                        exitPopOut.setVisible(false);
                        exitTrigger.setVisible(false);
                        exitButton.setVisible(true);
                      }
                    });
                exitPopOutTransition.play();
              }
            });
    exitPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !exitButton.isHover() && !exitTrigger.isHover()) {
                exitPopOutTransition.setDuration(Duration.millis(200));
                exitPopOutTransition.setNode(exitPopOut);
                exitPopOutTransition.setToX(0);
                exitPopOutTransition.setOnFinished(
                    e -> {
                      if (!exitPopOut.isHover() && !exitTrigger.isHover()) {
                        exitPopOut.setVisible(false);
                        exitTrigger.setVisible(false);
                        exitButton.setVisible(true);
                      }
                    });
                exitPopOutTransition.play();
              } else if (newValue) {
                exitPopOutTransition.stop();
                exitPopOut.setTranslateX(180);
              }
            });
    exitTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !exitButton.isHover() && !exitPopOut.isHover()) {
                exitPopOutTransition.setDuration(Duration.millis(200));
                exitPopOutTransition.setNode(exitPopOut);
                exitPopOutTransition.setToX(0);
                exitPopOutTransition.setOnFinished(
                    e -> {
                      if (!exitPopOut.isHover() && !exitTrigger.isHover()) {
                        exitPopOut.setVisible(false);
                        exitTrigger.setVisible(false);
                        exitButton.setVisible(true);
                      }
                    });
                exitPopOutTransition.play();
              } else if (newValue) {
                exitPopOutTransition.setDuration(Duration.millis(200));
                exitPopOutTransition.setNode(exitPopOut);
                exitPopOut.setTranslateX(180);
                exitPopOutTransition.play();
              }
            });
  }
}
