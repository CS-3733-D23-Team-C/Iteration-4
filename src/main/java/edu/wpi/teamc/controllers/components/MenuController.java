package edu.wpi.teamc.controllers.components;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.util.Duration;

public class MenuController {

  @FXML public MFXButton Admin_menu_movetable;

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
  @FXML private Pane settingsPane;

  // _______________________________________________________________________________________//

  @FXML private AnchorPane menuPane1;
  @FXML private AnchorPane homeButton1;
  @FXML private AnchorPane serviceRequestButton1;

  @FXML private AnchorPane navigationButton1;

  @FXML private AnchorPane settingsButton1;

  @FXML private AnchorPane helpButton1;
  @FXML private AnchorPane exitButton1;
  @FXML private AnchorPane logoutButton1;
  @FXML private AnchorPane historyButton1;

  @FXML private AnchorPane homeTrigger1;
  @FXML private AnchorPane serviceRequestTrigger1;
  @FXML private AnchorPane navigationTrigger1;
  @FXML private AnchorPane settingsTrigger1;
  @FXML private AnchorPane helpTrigger1;
  @FXML private AnchorPane historyTrigger1;

  @FXML private AnchorPane exitTrigger1;
  @FXML private AnchorPane logoutTrigger1;
  @FXML private AnchorPane serviceRequestPopOut1;
  @FXML private AnchorPane navigationPopOut1;
  @FXML private AnchorPane settingsPopOut1;
  @FXML private AnchorPane helpPopOut1;
  @FXML private AnchorPane historyPopOut1;
  @FXML private AnchorPane exitPopOut1;
  @FXML private AnchorPane logoutPopOut1;
  @FXML private AnchorPane homePopOut1;

  @FXML private AnchorPane basePane1;

  @FXML private AnchorPane settingsPane1;
  // _______________________________________________________________________________________//
  @FXML private MFXButton settingsClose;

  // ALL TEXT//
  @FXML private Text Admin_menu_home;
  @FXML private Text Admin_menu_logout;
  @FXML private Text Admin_menu_exit;
  @FXML private Text Admin_menu_helpmenu;
  @FXML private Text Admin_menu_settings;
  // HISTORY TEXT//
  @FXML private Text Admin_menu_history;
  @FXML private MFXButton Admin_menu_flower_history;
  @FXML private MFXButton Admin_menu_furniture_history;
  @FXML private MFXButton Admin_menu_meal_history;
  @FXML private MFXButton Admin_menu_stationary_history;
  @FXML private MFXButton Admin_menu_mapchange_history;
  @FXML private MFXButton Admin_menu_reservation_history;
  // @FXML private MFXButton Admin_menu_giftbasket_history;
  // NAVIGATION TEXT//
  @FXML private Text Admin_menu_navigation;
  @FXML private MFXButton Admin_menu_directions;
  @FXML private MFXButton Admin_menu_editmap;
  //  @FXML private MFXButton Admin_menu_movetable;
  @FXML private MFXButton Admin_menu_signage;

  // SERVICE REQUEST TEXT//
  @FXML private Text Admin_menu_servicerequests;
  @FXML private MFXButton Admin_menu_flower_delivery;
  @FXML private MFXButton Admin_menu_furniture_delivery;
  @FXML private MFXButton Admin_menu_meal_delivery;
  @FXML private MFXButton Admin_menu_stationary_delivery;
  @FXML private MFXButton Admin_menu_room_reservation;
  // @FXML private MFXButton Admin_menu_giftbasket_delivery;
  @FXML private MFXButton Admin_menu_employee_table;

  @FXML private MFXButton Admin_menu_home_button;
  @FXML private MFXButton Admin_menu_about_button;
  @FXML private MFXButton Admin_menu_credits;
  @FXML private MFXButton meetTheTeam;
  @FXML private Pane aboutPopOut;
  @FXML private Pane creditsPopOut;
  @FXML private Pane aboutPopOut1;

  // LANGUAGE TEXT//

  @FXML
  void getFlowerDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FLOWER);
  }

  @FXML
  void getAdminHome(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getAdminAbout(ActionEvent event) {
    Navigation.navigate(Screen.ABOUT);
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

  @FXML
  void getMoveTable(ActionEvent event) {
    Navigation.navigate(Screen.MOVE_TABLE);
  }

  @FXML
  void getMealHistory(ActionEvent event) {
    Navigation.navigate(Screen.MEAL_HISTORY);
  }

  @FXML
  void getFlowerHistory(ActionEvent event) {
    Navigation.navigate(Screen.FLOWER_HISTORY);
  }

  @FXML
  void getFurnitureHistory(ActionEvent event) {
    Navigation.navigate(Screen.FURNITURE_HISTORY);
  }

  @FXML
  void getConferenceHistory(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE_HISTORY);
  }

  @FXML
  void getOfficeSupplyHistory(ActionEvent event) {
    Navigation.navigate(Screen.OFFICE_SUPPLY_HISTORY);
  }

  public void getEmployeeTablePage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.EMPLOYEETABLE_PAGE);
  }

  //  Login login = new Login();
  //  @FXML MFXButton moveButton;

  //    @FXML
  //    void getGiftBasketHistory(ActionEvent event) {
  //        Navigation.navigate(Screen.GIFTBASKET_HISTORY);
  //    }

  //  @FXML
  //  void getMapPage(ActionEvent event) {
  //    Navigation.navigate(Screen.FLOOR_PLAN);
  //  }

  @FXML
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  public void setlanguage(int language_choice) {
    if (language_choice == 0) {
      Admin_menu_home.setText("Home");
      Admin_menu_about_button.setText("About");
      Admin_menu_credits.setText("Credits");
      Admin_menu_home_button.setText("Home");

      Admin_menu_logout.setText("Logout");
      Admin_menu_exit.setText("Exit");
      Admin_menu_helpmenu.setText("Help Menu");
      Admin_menu_settings.setText("Settings");
      // HISTORY TEXT//
      Admin_menu_history.setText("History");
      Admin_menu_flower_history.setText("Flower Delivery");
      Admin_menu_furniture_history.setText("Furniture Delivery");
      Admin_menu_meal_history.setText("Meal Delivery");
      Admin_menu_stationary_history.setText("Office Supplies");
      Admin_menu_mapchange_history.setText("Map Changes");
      Admin_menu_reservation_history.setText("Room Reservations");
      // Admin_menu_giftbasket_history.setText("Gift Basket Delivery");
      // NAVIGATION TEXT//
      Admin_menu_navigation.setText("Navigation");
      Admin_menu_directions.setText("Directions");
      Admin_menu_editmap.setText("Edit Map");
      Admin_menu_movetable.setText("Move Table");
      Admin_menu_signage.setText("Signage");
      // SERVICE REQUEST TEXT//
      Admin_menu_servicerequests.setText("Service Requests");
      Admin_menu_flower_delivery.setText("Flower Delivery");
      Admin_menu_furniture_delivery.setText("Furniture Delivery");
      Admin_menu_meal_delivery.setText("Meal Delivery");
      Admin_menu_stationary_delivery.setText("Office Supplies");
      Admin_menu_room_reservation.setText("Room Reservations");
      // Admin_menu_giftbasket_delivery.setText("Gift Basket Delivery");
      Admin_menu_employee_table.setText("Employee Table");
    } else if (language_choice == 1) {
      Admin_menu_home.setText("Accueil");
      Admin_menu_about_button.setText("Acerca");
      Admin_menu_credits.setText("Creditos");
      Admin_menu_home_button.setText("Accueil");

      Admin_menu_logout.setText("Se d" + "\u00e9" + "connecter");
      Admin_menu_exit.setText("Sortie");
      Admin_menu_helpmenu.setText("Menu d'aide");
      Admin_menu_settings.setText("Param" + "\u00e9" + "tres");
      // HISTORY TEXT//
      Admin_menu_history.setText("Histoire");
      Admin_menu_flower_history.setText("Livraison de fleurs");
      Admin_menu_furniture_history.setText("Livraison de meubles");
      Admin_menu_meal_history.setText("Livraison de repas");
      Admin_menu_stationary_history.setText("Fournitures de bureau");
      Admin_menu_mapchange_history.setText("Changements de carte");
      Admin_menu_reservation_history.setText("R" + "\u00e9" + "servations de salles");
      // Admin_menu_giftbasket_history.setText("Livraison de paniers-cadeaux");
      // NAVIGATION TEXT//
      Admin_menu_navigation.setText("Navigation");
      Admin_menu_directions.setText("Directions");
      Admin_menu_editmap.setText("Modifier la carte");
      Admin_menu_movetable.setText("D" + "\u00e9" + "placer la table");
      Admin_menu_signage.setText("Signalisation");
      // SERVICE REQUEST TEXT//
      Admin_menu_servicerequests.setText("Demandes de service");
      Admin_menu_flower_delivery.setText("Livraison de fleurs");
      Admin_menu_furniture_delivery.setText("Livraison de meubles");
      Admin_menu_meal_delivery.setText("Livraison de repas");
      Admin_menu_stationary_delivery.setText("Fournitures de bureau");
      Admin_menu_room_reservation.setText("R" + "\u00e9" + "servations de salles");
      // Admin_menu_giftbasket_delivery.setText("Livraison de paniers-cadeaux");
      Admin_menu_employee_table.setText("Table d'employ" + "\u00e9" + "");
    }
  }

  public EventHandler<ActionEvent> nothing() {

    return null;
  }

  @FXML
  public void initialize() {
    setlanguage(language_choice);
    homeTrigger1.setVisible(false);
    serviceRequestTrigger1.setVisible(false);
    navigationTrigger1.setVisible(false);
    settingsTrigger1.setVisible(false);
    helpTrigger1.setVisible(false);
    historyTrigger1.setVisible(false);
    exitTrigger1.setVisible(false);
    logoutTrigger1.setVisible(false);
    serviceRequestPopOut1.setVisible(false);
    navigationPopOut1.setVisible(false);
    settingsPopOut1.setVisible(false);
    helpPopOut1.setVisible(false);
    exitPopOut1.setVisible(false);
    logoutPopOut1.setVisible(false);
    homePopOut1.setVisible(false);
    aboutPopOut1.setVisible(false);
    aboutPopOut.setVisible(false);

    if (!CApp.getAdminLoginCheck()) {
      Admin_menu_movetable.setMouseTransparent(true);
      Tooltip tooltip = new Tooltip();
      tooltip.setText("Can only access this page as an admin");
      tooltip.setShowDuration(Duration.hours(2));
      // tooltip.setShowDelay(Duration.hours(0));
      Admin_menu_movetable.setTooltip(tooltip);
      Admin_menu_movetable.setStyle("-fx-background-color: grey");
      Admin_menu_movetable.setOpacity(40);
      Admin_menu_movetable.setOnAction(null);
      //      moveButton.setMouseTransparent(true);
    }

    // basePane.setVisible(false);
    menuPane.setVisible(true);
    settingsPane.setVisible(false);
    settingsPane.toFront();
    menuPane.toFront();
    basePane.toFront();
    TranslateTransition navigationPopOutTransition = new TranslateTransition();
    PauseTransition pause = new PauseTransition(Duration.millis(200));
    TranslateTransition serviceRequestPopOutTransition = new TranslateTransition();
    TranslateTransition settingsPopOutTransition = new TranslateTransition();
    TranslateTransition helpPopOutTransition = new TranslateTransition();
    TranslateTransition historyPopOutTransition = new TranslateTransition();
    TranslateTransition exitPopOutTransition = new TranslateTransition();
    TranslateTransition logoutPopOutTransition = new TranslateTransition();
    TranslateTransition homePopOutTransition = new TranslateTransition();
    TranslateTransition settingsPaneTransition = new TranslateTransition();
    TranslateTransition aboutPopOutTransition = new TranslateTransition();
    TranslateTransition aboutPopOutTransition1 = new TranslateTransition();

    AtomicBoolean isHovering = new AtomicBoolean(false);

    // Define a PauseTransition with a delay of 200 milliseconds
    PauseTransition delay = new PauseTransition(Duration.millis(200));

    helpTrigger1.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          Navigation.navigate(Screen.HELP);
        });
    homeTrigger1.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          Navigation.navigate(Screen.ADMIN_HOME);
        });

    /*    settingsTrigger1.addEventFilter(
    MouseEvent.MOUSE_CLICKED,
    event -> {
      settingsPaneTransition.setDuration(Duration.millis(350));
      settingsPaneTransition.setNode(settingsPane);
      settingsPaneTransition.setToY(768);
      settingsPaneTransition.play();
      settingsPane.setVisible(true);
      settingsTrigger.setVisible(true);
      settingsButton.setVisible(false);
    });*/

    settingsClose.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          settingsPaneTransition.setDuration(Duration.millis(350));
          settingsPaneTransition.setNode(settingsPane);
          settingsPaneTransition.setToY(0);
          settingsPaneTransition.play();
          settingsPane.setVisible(false);
          settingsTrigger.setVisible(false);
          settingsButton.setVisible(true);
        });

    logoutTrigger1.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          Navigation.navigate(Screen.HOME);
        });
    exitTrigger1.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          Navigation.navigate(Screen.EXIT_PAGE);
        });

    homeButton1
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
                      homePopOut1.setVisible(true);
                      homeTrigger1.setVisible(true);
                      homeButton1.setVisible(false);
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
                        homePopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        homeButton1.setVisible(true);
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
                        homePopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });
                homePopOutTransition.play();
              } else if (newValue) {
                homePopOutTransition.stop();
                homePopOut.setTranslateX(180);
              }
            });

    homeTrigger1
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
                        homePopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        homeButton1.setVisible(true);
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
    Admin_menu_about_button.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          aboutPopOutTransition.setDuration(Duration.millis(200));
          aboutPopOutTransition.setNode(aboutPopOut);
          aboutPopOutTransition.setToX(290);
          aboutPopOutTransition.play();
          aboutPopOut.setVisible(true);
          homePopOutTransition.setDuration(Duration.millis(200));
          homePopOutTransition.setNode(homePopOut);
          homePopOutTransition.setToX(0);
          homePopOutTransition.play();
        });
    //    aboutPopOut
    //        .hoverProperty()
    //        .addListener(
    //            (observable, oldValue, newValue) -> {
    //              isHovering.set(newValue);
    //              if (!newValue && !aboutPopOut1.isHover() && !aboutPopOut.isHover()) {
    //                aboutPopOutTransition.setDuration(Duration.millis(200));
    //                aboutPopOutTransition.setNode(aboutPopOut);
    //                aboutPopOutTransition.setToX(0);
    //                delay.setOnFinished(
    //                    event -> {
    //                      if (!aboutPopOut.isHover() && !aboutPopOut1.isHover()) {
    //                        aboutPopOut.setVisible(false);
    //                        homeTrigger1.setVisible(false);
    //                        homeButton1.setVisible(true);
    //                      }
    //                    });
    //                delay.play();
    //              } else if (newValue) {
    //                aboutPopOutTransition.setDuration(Duration.millis(200));
    //                aboutPopOutTransition.setNode(aboutPopOut);
    //                aboutPopOut.setTranslateX(290);
    //                aboutPopOutTransition.play();
    //              }
    //            });
    aboutPopOut1
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !meetTheTeam.isHover() && !aboutPopOut1.isHover()) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut1);
                aboutPopOutTransition1.setToX(0);
                delay.setOnFinished(
                    event -> {
                      if (!aboutPopOut1.isHover()) {
                        aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });
                delay.play();
              } else if (newValue) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut1);
                aboutPopOut1.setTranslateX(600);
                aboutPopOutTransition1.play();
              }
            });

    serviceRequestButton1
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
                      serviceRequestPopOut1.setVisible(true);
                      serviceRequestTrigger1.setVisible(true);
                      serviceRequestButton1.setVisible(false);
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
                        serviceRequestPopOut1.setVisible(false);
                        serviceRequestTrigger1.setVisible(false);
                        serviceRequestButton1.setVisible(true);
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
                        serviceRequestPopOut1.setVisible(false);
                        serviceRequestTrigger1.setVisible(false);
                        serviceRequestButton1.setVisible(true);
                      }
                    });
                serviceRequestPopOutTransition.play();
              } else if (newValue) {
                serviceRequestPopOutTransition.stop();
                serviceRequestPopOut.setTranslateX(180);
              }
            });

    serviceRequestTrigger1
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
                        serviceRequestPopOut1.setVisible(false);
                        serviceRequestTrigger1.setVisible(false);
                        serviceRequestButton1.setVisible(true);
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

    navigationButton1
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
                      navigationPopOut1.setVisible(true);
                      navigationTrigger1.setVisible(true);
                      navigationButton1.setVisible(false);
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
                        navigationPopOut1.setVisible(false);
                        navigationTrigger1.setVisible(false);
                        navigationButton1.setVisible(true);
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
                        navigationPopOut1.setVisible(false);
                        navigationTrigger1.setVisible(false);
                        navigationButton1.setVisible(true);
                      }
                    });
                navigationPopOutTransition.play();
              } else if (newValue) {
                navigationPopOutTransition.stop();
                navigationPopOut.setTranslateX(180);
              }
            });

    navigationTrigger1
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
                        navigationPopOut1.setVisible(false);
                        navigationTrigger1.setVisible(false);
                        navigationButton1.setVisible(true);
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

    settingsButton1
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
                      settingsPopOut1.setVisible(true);
                      settingsTrigger1.setVisible(true);
                      settingsButton1.setVisible(false);
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
                        settingsPopOut1.setVisible(false);
                        settingsTrigger1.setVisible(false);
                        settingsButton1.setVisible(true);
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
                        settingsPopOut1.setVisible(false);
                        settingsTrigger1.setVisible(false);
                        settingsButton1.setVisible(true);
                      }
                    });
                settingsPopOutTransition.play();
              } else if (newValue) {
                settingsPopOutTransition.stop();
                settingsPopOut.setTranslateX(180);
              }
            });

    settingsTrigger1
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
                        settingsPopOut1.setVisible(false);
                        settingsTrigger1.setVisible(false);
                        settingsButton1.setVisible(true);
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

    historyButton1
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
                      historyPopOut1.setVisible(true);
                      historyTrigger1.setVisible(true);
                      historyButton1.setVisible(false);
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
                        historyPopOut1.setVisible(false);
                        historyTrigger1.setVisible(false);
                        historyButton1.setVisible(true);
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
                        historyPopOut1.setVisible(false);
                        historyTrigger1.setVisible(false);
                        historyButton1.setVisible(true);
                      }
                    });
                historyPopOutTransition.play();
              } else if (newValue) {
                historyPopOutTransition.stop();
                historyPopOut.setTranslateX(180);
              }
            });

    historyTrigger1
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
                        historyPopOut1.setVisible(false);
                        historyTrigger1.setVisible(false);
                        historyButton1.setVisible(true);
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

    helpButton1
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
                      helpPopOut1.setVisible(true);
                      helpTrigger1.setVisible(true);
                      helpButton1.setVisible(false);
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
                        helpPopOut1.setVisible(false);
                        helpTrigger1.setVisible(false);
                        helpButton1.setVisible(true);
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
                        helpPopOut1.setVisible(false);
                        helpTrigger1.setVisible(false);
                        helpButton1.setVisible(true);
                      }
                    });
                helpPopOutTransition.play();
              } else if (newValue) {
                helpPopOutTransition.stop();
                helpPopOut.setTranslateX(180);
              }
            });

    helpTrigger1
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
                        helpPopOut1.setVisible(false);
                        helpTrigger1.setVisible(false);
                        helpButton1.setVisible(true);
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

    logoutButton1
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
                      logoutPopOut1.setVisible(true);
                      logoutTrigger1.setVisible(true);
                      logoutButton1.setVisible(false);
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
                        logoutPopOut1.setVisible(false);
                        logoutTrigger1.setVisible(false);
                        logoutButton1.setVisible(true);
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
                        logoutPopOut1.setVisible(false);
                        logoutTrigger1.setVisible(false);
                        logoutButton1.setVisible(true);
                      }
                    });
                logoutPopOutTransition.play();
              } else if (newValue) {
                logoutPopOutTransition.stop();
                logoutPopOut.setTranslateX(180);
              }
            });

    logoutTrigger1
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
                        logoutPopOut1.setVisible(false);
                        logoutTrigger1.setVisible(false);
                        logoutButton1.setVisible(true);
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

    exitButton1
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
                      exitPopOut1.setVisible(true);
                      exitTrigger1.setVisible(true);
                      exitButton1.setVisible(false);
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
                        exitPopOut1.setVisible(false);
                        exitTrigger1.setVisible(false);
                        exitButton1.setVisible(true);
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
                        exitPopOut1.setVisible(false);
                        exitTrigger1.setVisible(false);
                        exitButton1.setVisible(true);
                      }
                    });
                exitPopOutTransition.play();
              } else if (newValue) {
                exitPopOutTransition.stop();
                exitPopOut.setTranslateX(180);
              }
            });

    exitTrigger1
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
                        exitPopOut1.setVisible(false);
                        exitTrigger1.setVisible(false);
                        exitButton1.setVisible(true);
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
