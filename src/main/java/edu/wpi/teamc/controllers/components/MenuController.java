package edu.wpi.teamc.controllers.components;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.displays.Alert;
import edu.wpi.teamc.languageHelpers.TranslatorAPI;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.ToggleSwitch;

public class MenuController {

  @FXML public MFXButton Admin_menu_movetable;

  @FXML private Pane menuPane1;
  @FXML private ImageView homeButton;
  @FXML private ImageView serviceRequestButton;

  @FXML private ImageView navigationButton;

  @FXML private ImageView settingsButton;

  @FXML private ImageView helpButton;
  @FXML private ImageView exitButton;
  @FXML private ImageView logoutButton;
  @FXML private ImageView historyButton;
  @FXML private ImageView warningButton;
  @FXML private Pane homeTrigger;
  @FXML private Pane serviceRequestTrigger;
  @FXML private Pane navigationTrigger;
  @FXML private Pane settingsTrigger;
  @FXML private Pane helpTrigger;
  @FXML private Pane historyTrigger;

  @FXML private Pane exitTrigger;
  @FXML private Pane logoutTrigger;
  @FXML private Pane warningTrigger;
  @FXML private Pane serviceRequestPopOut;
  @FXML private Pane navigationPopOut;
  @FXML private Pane settingsPopOut;
  @FXML private Pane helpPopOut;
  @FXML private Pane historyPopOut;
  @FXML private Pane exitPopOut;
  @FXML private Pane logoutPopOut;
  @FXML private Pane warningPopOut;
  @FXML private Pane homePopOut;
  @FXML private AnchorPane basePane;
  @FXML private Pane settingsPane;

  // _______________________________________________________________________________________//

  @FXML private AnchorPane menuPane;
  @FXML private AnchorPane homeButton1;
  @FXML private AnchorPane serviceRequestButton1;

  @FXML private AnchorPane navigationButton1;

  @FXML private AnchorPane settingsButton1;

  @FXML private AnchorPane helpButton1;
  @FXML private AnchorPane exitButton1;
  @FXML private AnchorPane logoutButton1;
  @FXML private AnchorPane historyButton1;
  @FXML private AnchorPane warningButton1;

  @FXML private AnchorPane homeTrigger1;
  @FXML private AnchorPane serviceRequestTrigger1;
  @FXML private AnchorPane navigationTrigger1;
  @FXML private AnchorPane settingsTrigger1;
  @FXML private AnchorPane helpTrigger1;
  @FXML private AnchorPane historyTrigger1;
  @FXML private AnchorPane warningTrigger1;
  @FXML private AnchorPane exitTrigger1;
  @FXML private AnchorPane logoutTrigger1;
  @FXML private AnchorPane serviceRequestPopOut1;
  @FXML private AnchorPane navigationPopOut1;
  @FXML private AnchorPane settingsPopOut1;
  @FXML private AnchorPane helpPopOut1;
  @FXML private AnchorPane historyPopOut1;
  @FXML private AnchorPane warningPopOut1;
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

  @FXML private MFXButton Admin_menu_giftbasket_delivery;
  @FXML private MFXButton Admin_menu_room_reservation;
  // @FXML private MFXButton Admin_menu_giftbasket_delivery;
  @FXML private MFXButton Admin_menu_employee_table;
  @FXML private MFXButton Admin_menu_patient_table;

  @FXML private MFXButton Admin_menu_home_button;
  @FXML private MFXButton Admin_menu_about_button;
  @FXML private MFXButton Admin_menu_credits;
  @FXML private MFXButton meetTheTeam;
  @FXML private Pane aboutPopOut;
  @FXML private Pane creditsPopOut;
  @FXML private Pane aboutPopOut1;
  @FXML private AnchorPane aboutPopOut2;
  @FXML private AnchorPane aboutPopOut3;
  @FXML private AnchorPane creditsPopOut1;
  @FXML private StackPane Randy;
  @FXML private StackPane Eddie;
  @FXML private StackPane Nick;
  @FXML private StackPane Aaron;
  @FXML private StackPane Sebastian;
  @FXML private StackPane Brandon;
  @FXML private StackPane Taya;
  @FXML private StackPane Chase;
  @FXML private StackPane Joey;
  @FXML private StackPane Chao;
  @FXML private AnchorPane aboutPopOut4;
  @FXML private AnchorPane aboutRandyPopOut;
  @FXML private AnchorPane aboutClosePane;

  @FXML private AnchorPane aboutEddiePopOut;
  @FXML private AnchorPane aboutNickPopOut;
  @FXML private AnchorPane aboutAaronPopOut;
  @FXML private AnchorPane aboutSebastianPopOut;
  @FXML private AnchorPane aboutBrandonPopOut;
  @FXML private AnchorPane aboutTayaPopOut;
  @FXML private AnchorPane aboutChasePopOut;
  @FXML private AnchorPane aboutJoeyPopOut;
  @FXML private AnchorPane aboutChaoPopOut;

  @FXML private Label menuClock;

  // LANGUAGE TEXT//

  // ALERT TEXT//
  @FXML private TextField alert1;
  @FXML private TextField alert2;
  @FXML private TextField alert3;

  @FXML
  void getFlowerDeliveryPage(ActionEvent event) {
    CApp.currScreen = Screen.FLOWER;
    Navigation.navigate(Screen.FLOWER);
  }

  @FXML
  void getAdminHome(ActionEvent event) {
    CApp.currScreen = Screen.ADMIN_HOME;
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getAdminAbout(ActionEvent event) {
    CApp.currScreen = Screen.ABOUT;
    Navigation.navigate(Screen.ABOUT);
  }

  @FXML
  void getFurnitureDeliveryPage(ActionEvent event) {
    CApp.currScreen = Screen.FURNITURE;
    Navigation.navigate(Screen.FURNITURE);
  }

  @FXML
  void getHelpPage(ActionEvent event) {
    CApp.currScreen = Screen.HELP;
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  void getMealDeliveryPage(ActionEvent event) {
    CApp.currScreen = Screen.MEAL;
    Navigation.navigate(Screen.MEAL);
  }

  @FXML
  void getOfficeSuppliesPage(ActionEvent event) {
    CApp.currScreen = Screen.OFFICE_SUPPLY;
    Navigation.navigate(Screen.OFFICE_SUPPLY);
  }

  @FXML
  void getRoomReservationPage(ActionEvent event) {
    CApp.currScreen = Screen.CONFERENCE;
    Navigation.navigate(Screen.CONFERENCE);
  }

  @FXML
  void getSignagePage(ActionEvent event) {
    CApp.currScreen = Screen.SIGNAGE;
    Navigation.navigate(Screen.SIGNAGE);
  }

  @FXML
  void getPathfindingPage(ActionEvent event) {
    CApp.currScreen = Screen.PATHFINDING_PAGE;
    Navigation.navigate(Screen.PATHFINDING_PAGE);
  }

  @FXML
  void getGiftBasketPage(ActionEvent event) {
    CApp.currScreen = Screen.GIFT_BASKET;
    Navigation.navigate(Screen.GIFT_BASKET);
  }

  @FXML
  void getExit(ActionEvent event) {
    CApp.currScreen = Screen.EXIT_PAGE;
    Navigation.navigate(Screen.EXIT_PAGE);
  }

  /** Method run when controller is initialized */
  @FXML
  void getEditMap(ActionEvent event) {
    CApp.currScreen = Screen.EDIT_MAP;
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getMapHistory(ActionEvent event) {
    CApp.currScreen = Screen.MAP_HISTORY_PAGE;
    Navigation.navigate(Screen.MAP_HISTORY_PAGE);
  }

  @FXML
  void getMoveTable(ActionEvent event) {
    CApp.currScreen = Screen.MOVE_TABLE;
    Navigation.navigate(Screen.MOVE_TABLE);
  }

  @FXML
  void getMealHistory(ActionEvent event) {
    //      Navigation.navigate(Screen.MEAL_HISTORY);
  }

  @FXML
  void getFlowerHistory(ActionEvent event) {
    //      Navigation.navigate(Screen.FLOWER_HISTORY);
  }

  @FXML
  void getSignageEdit(ActionEvent event) {
    CApp.currScreen = Screen.SIGNAGE_EDIT;
    Navigation.navigate(Screen.SIGNAGE_EDIT);
  }

  @FXML
  void getRequestHistory(ActionEvent event) {
    CApp.currScreen = Screen.REQUEST_HISTORY;
    Navigation.navigate(Screen.REQUEST_HISTORY);
  }

  @FXML
  void getOfficeSupplyHistory(ActionEvent event) {
    //      Navigation.navigate(Screen.OFFICE_SUPPLY_HISTORY);
  }

  @FXML
  void getAlertRequest(ActionEvent event) {
    CApp.currScreen = Screen.ALERT_REQUEST;
    Navigation.navigate(Screen.ALERT_REQUEST);
  }

  public void getEmployeeTablePage(ActionEvent actionEvent) {
    CApp.currScreen = Screen.EMPLOYEETABLE_PAGE;
    Navigation.navigate(Screen.EMPLOYEETABLE_PAGE);
  }

  public void getPatientTablePage(ActionEvent actionEvent) {
    CApp.currScreen = Screen.PATIENTTABLE_PAGE;
    Navigation.navigate(Screen.PATIENTTABLE_PAGE);
  }

  public void getLoginTablePage(ActionEvent actionEvent) {
    CApp.currScreen = Screen.LOGIN_TABLE;
    Navigation.navigate(Screen.LOGIN_TABLE);
  }

  public void getImportExportPage(ActionEvent actionEvent) {
    CApp.currScreen = Screen.IMPORT_EXPORT_PAGE;
    Navigation.navigate(Screen.IMPORT_EXPORT_PAGE);
  }

  @FXML ImageView appIcon;
  @FXML ImageView bellIcon;
  @FXML ImageView navIcon;
  @FXML ImageView histIcon;
  @FXML ImageView settingsIcon;
  @FXML ImageView questionIcon;
  @FXML ImageView logoutIcon;
  @FXML ImageView cancelIcon;
  @FXML Line menuLine1;
  @FXML Line menuLine2;
  @FXML ToggleSwitch dbToggle;

  //  Login login = new Login();
  //  @FXML MFXButton moveButton;

  @FXML
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  void receiveWarning() {}

  @FXML
  public void setlanguage() throws Exception {
    if (language_choice == 0) {

    } else {
      Admin_menu_home.setText(LanguageSet(Admin_menu_home.getText()));
      Admin_menu_about_button.setText(LanguageSet(Admin_menu_about_button.getText()));
      Admin_menu_credits.setText(LanguageSet(Admin_menu_credits.getText()));
      Admin_menu_home_button.setText(LanguageSet(Admin_menu_home_button.getText()));

      Admin_menu_logout.setText(LanguageSet(Admin_menu_logout.getText()));
      Admin_menu_exit.setText(LanguageSet(Admin_menu_exit.getText()));
      Admin_menu_helpmenu.setText(LanguageSet(Admin_menu_helpmenu.getText()));
      Admin_menu_settings.setText(LanguageSet(Admin_menu_settings.getText()));
      // HISTORY TEXT//

      Admin_menu_history.setText(LanguageSet(Admin_menu_history.getText()));
      Admin_menu_mapchange_history.setText(LanguageSet(Admin_menu_mapchange_history.getText()));

      // NAVIGATION TEXT//
      Admin_menu_navigation.setText(LanguageSet(Admin_menu_navigation.getText()));
      Admin_menu_directions.setText(LanguageSet(Admin_menu_directions.getText()));
      Admin_menu_editmap.setText(LanguageSet(Admin_menu_editmap.getText()));
      Admin_menu_movetable.setText(LanguageSet(Admin_menu_movetable.getText()));
      Admin_menu_signage.setText(LanguageSet(Admin_menu_signage.getText()));
      // SERVICE REQUEST TEXT//
      Admin_menu_servicerequests.setText(LanguageSet(Admin_menu_servicerequests.getText()));
      Admin_menu_flower_delivery.setText(LanguageSet(Admin_menu_flower_delivery.getText()));
      Admin_menu_furniture_delivery.setText(LanguageSet(Admin_menu_furniture_delivery.getText()));
      Admin_menu_meal_delivery.setText(LanguageSet(Admin_menu_meal_delivery.getText()));
      Admin_menu_stationary_delivery.setText(LanguageSet(Admin_menu_stationary_delivery.getText()));
      Admin_menu_room_reservation.setText(LanguageSet(Admin_menu_room_reservation.getText()));
      Admin_menu_giftbasket_delivery.setText(LanguageSet(Admin_menu_giftbasket_delivery.getText()));
      Admin_menu_employee_table.setText(LanguageSet(Admin_menu_employee_table.getText()));
    }

    if (language_choice == 1) {
      serviceRequestPopOut.setMinSize(
          serviceRequestPopOut.getPrefWidth(), serviceRequestPopOut.getPrefHeight() + 10);
    }
  }

  @FXML
  public void initialize() throws Exception {
    Thread thread =
        new Thread(
            () -> {
              java.util.List<Alert> alertList =
                  (java.util.List<Alert>) HospitalSystem.fetchAllObjects(new Alert());
              int alertListSize = alertList.size();
              int recentAlert1 = alertListSize - 1;
              int recentAlert2 = alertListSize - 2;
              int recentAlert3 = alertListSize - 3;
              Platform.runLater(
                  () -> {
                    alert1.setText(
                        alertList.get(recentAlert1).getType()
                            + ": "
                            + alertList.get(recentAlert1).getTitle());
                    alert2.setText(
                        alertList.get(recentAlert2).getType()
                            + ": "
                            + alertList.get(recentAlert2).getTitle());
                    alert3.setText(
                        alertList.get(recentAlert3).getType()
                            + ": "
                            + alertList.get(recentAlert3).getTitle());
                  });
            });
    thread.start();

    setlanguage();
    homeTrigger1.setVisible(false);
    serviceRequestTrigger1.setVisible(false);
    navigationTrigger1.setVisible(false);
    settingsTrigger1.setVisible(false);
    helpTrigger1.setVisible(false);
    historyTrigger1.setVisible(false);
    exitTrigger1.setVisible(false);
    logoutTrigger1.setVisible(false);
    warningTrigger1.setVisible(false);
    serviceRequestPopOut1.setVisible(false);
    navigationPopOut1.setVisible(false);
    settingsPopOut1.setVisible(false);
    helpPopOut1.setVisible(false);
    exitPopOut1.setVisible(false);
    logoutPopOut1.setVisible(false);
    homePopOut1.setVisible(false);
    warningPopOut1.setVisible(false);
    aboutPopOut1.setVisible(false);
    aboutPopOut.setVisible(true);
    aboutPopOut3.setVisible(true);
    aboutPopOut3.setVisible(true);
    aboutPopOut4.setVisible(false);
    aboutRandyPopOut.setVisible(false);
    aboutClosePane.setVisible(false);

    // Set styleClasses
    menuPane.getStyleClass().add("menuBackground");
    appIcon.getStyleClass().add("menuIcon");
    bellIcon.getStyleClass().add("menuIcon");
    navIcon.getStyleClass().add("menuIcon");
    histIcon.getStyleClass().add("menuIcon");
    settingsIcon.getStyleClass().add("menuIcon");
    questionIcon.getStyleClass().add("menuIcon");
    logoutIcon.getStyleClass().add("menuIcon");
    cancelIcon.getStyleClass().add("menuIcon");
    menuLine1.getStyleClass().add("menuLine");
    menuLine2.getStyleClass().add("menuLine");
    Admin_menu_movetable.getStyleClass().add("hiddenButton");

    basePane
        .getStylesheets()
        .add(Main.class.getResource("views/components/Menu_Dark.css").toString());

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
    TranslateTransition warningPopOutTransition = new TranslateTransition();
    TranslateTransition aboutPopOutTransition = new TranslateTransition();
    TranslateTransition aboutPopOutTransition1 = new TranslateTransition();
    TranslateTransition creditPopOutTransition = new TranslateTransition();
    TranslateTransition aboutRandyPopOutTransition = new TranslateTransition();
    TranslateTransition aboutEddiePopOutTransition = new TranslateTransition();
    TranslateTransition aboutNickPopOutTransition = new TranslateTransition();
    TranslateTransition aboutAaronPopOutTransition = new TranslateTransition();
    TranslateTransition aboutSebastianPopOutTransition = new TranslateTransition();
    TranslateTransition aboutBrandonPopOutTransition = new TranslateTransition();
    TranslateTransition aboutTayaPopOutTransition = new TranslateTransition();
    TranslateTransition aboutChasePopOutTransition = new TranslateTransition();
    TranslateTransition aboutChaoPopOutTransition = new TranslateTransition();
    TranslateTransition aboutJoeyPopOutTransition = new TranslateTransition();

    PauseTransition waitTransition = new PauseTransition(Duration.millis(0));

    AtomicBoolean isHovering = new AtomicBoolean(false);

    // Define a PauseTransition with a delay of 200 milliseconds
    PauseTransition delay = new PauseTransition(Duration.millis(200));

    helpTrigger1.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          CApp.currScreen = Screen.HELP;
          Navigation.navigate(Screen.HELP);
        });
    homeTrigger1.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          CApp.currScreen = Screen.ADMIN_HOME;
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
          CApp.logoutPopUp();
        });
    exitTrigger1.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          //          Navigation.clearCache();
          Navigation.navigate(Screen.EXIT_PAGE);
          Navigation.setMenuType(Navigation.MenuType.DISABLED);
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
                        // homePopOut1.setVisible(false);
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
                        // homePopOut1.setVisible(false);
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
          aboutPopOut2.setVisible(true);
          aboutPopOut.setVisible(true);

          aboutPopOutTransition.setDuration(Duration.millis(300));
          aboutPopOutTransition.setNode(aboutPopOut2);
          aboutPopOutTransition.setToX(290);
          aboutPopOutTransition.play();
          aboutClosePane.setVisible(true);
        });

    aboutClosePane
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                aboutPopOutTransition.stop();
                aboutPopOutTransition1.stop();

                aboutPopOutTransition.setDuration(Duration.millis(300));
                aboutPopOutTransition.setNode(aboutPopOut2);
                aboutPopOutTransition.setToX(0);
                aboutPopOutTransition.play();
                aboutClosePane.setVisible(false);
                aboutPopOut4.setVisible(false);
                aboutPopOutTransition1.setDuration(Duration.millis(300));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();
                aboutRandyPopOutTransition.setDuration(Duration.millis(300));
                aboutRandyPopOutTransition.setNode(aboutRandyPopOut);
                aboutRandyPopOutTransition.setToX(0);
                aboutRandyPopOutTransition.play();
                aboutEddiePopOutTransition.setDuration(Duration.millis(300));
                aboutEddiePopOutTransition.setNode(aboutEddiePopOut);
                aboutEddiePopOutTransition.setToX(0);
                aboutEddiePopOutTransition.play();
                aboutNickPopOutTransition.setDuration(Duration.millis(300));
                aboutNickPopOutTransition.setNode(aboutNickPopOut);
                aboutNickPopOutTransition.setToX(0);
                aboutNickPopOutTransition.play();
                aboutAaronPopOutTransition.setDuration(Duration.millis(300));
                aboutAaronPopOutTransition.setNode(aboutAaronPopOut);
                aboutAaronPopOutTransition.setToX(0);
                aboutAaronPopOutTransition.play();
                aboutSebastianPopOutTransition.setDuration(Duration.millis(300));
                aboutSebastianPopOutTransition.setNode(aboutSebastianPopOut);
                aboutSebastianPopOutTransition.setToX(0);
                aboutSebastianPopOutTransition.play();
                aboutBrandonPopOutTransition.setDuration(Duration.millis(300));
                aboutBrandonPopOutTransition.setNode(aboutBrandonPopOut);
                aboutBrandonPopOutTransition.setToX(0);
                aboutBrandonPopOutTransition.play();
                aboutTayaPopOutTransition.setDuration(Duration.millis(300));
                aboutTayaPopOutTransition.setNode(aboutTayaPopOut);
                aboutTayaPopOutTransition.setToX(0);
                aboutTayaPopOutTransition.play();
                aboutChasePopOutTransition.setDuration(Duration.millis(300));
                aboutChasePopOutTransition.setNode(aboutChasePopOut);
                aboutChasePopOutTransition.setToX(0);
                aboutChasePopOutTransition.play();
                aboutChaoPopOutTransition.setDuration(Duration.millis(300));
                aboutChaoPopOutTransition.setNode(aboutChaoPopOut);
                aboutChaoPopOutTransition.setToX(0);
                aboutChaoPopOutTransition.play();
                aboutJoeyPopOutTransition.setDuration(Duration.millis(300));
                aboutJoeyPopOutTransition.setNode(aboutJoeyPopOut);
                aboutJoeyPopOutTransition.setToX(0);
                aboutJoeyPopOutTransition.play();
              }
            });

    aboutPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue
                  && !aboutPopOut.isHover()
                  && !homePopOut.isHover()
                  && !aboutPopOut3.isHover()
                  && !aboutPopOut4.isHover()) {
                aboutPopOutTransition.stop();
                aboutPopOutTransition.setDuration(Duration.millis(300));
                aboutPopOutTransition.setNode(aboutPopOut2);
                aboutPopOutTransition.setToX(0);
                //                aboutPopOutTransition1.setDuration(Duration.millis(300));
                //                aboutPopOutTransition1.setNode(aboutPopOut3);
                //                aboutPopOutTransition1.setToX(0);
                waitTransition.setOnFinished(
                    e -> {
                      if (!aboutPopOut.isHover() && !homePopOut.isHover()) {
                        // aboutPopOut.setVisible(false);
                        homeTrigger1.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });
                aboutPopOutTransition.play();
                aboutPopOut4.setVisible(false);
                aboutPopOutTransition1.setOnFinished(
                    e -> {
                      if (!meetTheTeam.isHover() && !aboutPopOut1.isHover() && !newValue) {
                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

                aboutPopOutTransition1.play();
              } else if (newValue) {
                aboutClosePane.setVisible(true);
                aboutPopOutTransition.stop();
                aboutPopOutTransition.setDuration(Duration.millis(300));
                aboutPopOutTransition.setNode(aboutPopOut2);
                aboutPopOutTransition.setToX(290);
                aboutPopOutTransition.play();
                aboutPopOutTransition1.setDuration(Duration.millis(300));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    Admin_menu_credits.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          creditsPopOut1.setVisible(true);
          creditsPopOut.setVisible(true);
          creditPopOutTransition.setDuration(Duration.millis(300));
          creditPopOutTransition.setNode(creditsPopOut1);
          creditPopOutTransition.setToX(290);
          creditPopOutTransition.play();
        });
    creditsPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !creditsPopOut.isHover() && !homePopOut.isHover()) {
                creditPopOutTransition.stop();
                creditPopOutTransition.setDuration(Duration.millis(300));
                creditPopOutTransition.setNode(creditsPopOut1);
                creditPopOutTransition.setToX(0);
                creditPopOutTransition.setOnFinished(
                    e -> {
                      if (!creditsPopOut.isHover() && !homePopOut.isHover()) {
                        creditsPopOut.setVisible(false);
                        homeTrigger1.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });
                creditPopOutTransition.play();
              } else if (newValue) {
                creditPopOutTransition.setDuration(Duration.millis(300));
                creditPopOutTransition.setNode(creditsPopOut1);
                creditPopOutTransition.setToX(290);
                creditPopOutTransition.play();
              }
            });
    // get wpidb boolean and set toggle accordingly

    dbToggle.setSelected(!CApp.getWpiDB());
    dbToggle.setOnMouseClicked(
        event -> {
          // Toggled sets boolean wpiDB false for AWS
          CApp.wpiDB = !dbToggle.selectedProperty().get();
          dbToggle.setSelected(!CApp.wpiDB);
          //          dbToggle.fire();
          System.out.println("DB: " + CApp.wpiDB);
          //          Navigation.clearCache();
          Navigation.navigate(CApp.currScreen);
        });

    meetTheTeam
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !meetTheTeam.isHover() && !aboutPopOut3.isHover()) {
                aboutPopOutTransition1.stop();
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!meetTheTeam.isHover() && !aboutPopOut3.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });
                aboutPopOut4.setVisible(false);

              } else if (newValue) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(585);
                aboutPopOutTransition1.play();
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(true);
              }
            });

    aboutPopOut3
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !meetTheTeam.isHover() && !aboutPopOut3.isHover()) {
                aboutPopOutTransition1.stop();
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();
                aboutPopOut4.setVisible(false);

                waitTransition.setOnFinished(
                    event -> {
                      if (!meetTheTeam.isHover() && !aboutPopOut3.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue) {
                aboutPopOutTransition.stop();
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(585);
                aboutPopOutTransition1.play();

                aboutRandyPopOutTransition.stop();
                aboutRandyPopOutTransition.setDuration(Duration.millis(200));
                aboutRandyPopOutTransition.setNode(aboutRandyPopOut);
                aboutRandyPopOutTransition.setToX(0);
                aboutRandyPopOutTransition.play();

                aboutEddiePopOutTransition.stop();
                aboutEddiePopOutTransition.setDuration(Duration.millis(200));
                aboutEddiePopOutTransition.setNode(aboutEddiePopOut);
                aboutEddiePopOutTransition.setToX(0);
                aboutEddiePopOutTransition.play();

                aboutNickPopOutTransition.stop();
                aboutNickPopOutTransition.setDuration(Duration.millis(200));
                aboutNickPopOutTransition.setNode(aboutNickPopOut);
                aboutNickPopOutTransition.setToX(0);
                aboutNickPopOutTransition.play();

                aboutAaronPopOutTransition.stop();
                aboutAaronPopOutTransition.setDuration(Duration.millis(200));
                aboutAaronPopOutTransition.setNode(aboutAaronPopOut);
                aboutAaronPopOutTransition.setToX(0);
                aboutAaronPopOutTransition.play();

                aboutSebastianPopOutTransition.stop();
                aboutSebastianPopOutTransition.setDuration(Duration.millis(200));
                aboutSebastianPopOutTransition.setNode(aboutSebastianPopOut);
                aboutSebastianPopOutTransition.setToX(0);
                aboutSebastianPopOutTransition.play();

                aboutBrandonPopOutTransition.stop();
                aboutBrandonPopOutTransition.setDuration(Duration.millis(200));
                aboutBrandonPopOutTransition.setNode(aboutBrandonPopOut);
                aboutBrandonPopOutTransition.setToX(0);
                aboutBrandonPopOutTransition.play();

                aboutTayaPopOutTransition.stop();
                aboutTayaPopOutTransition.setDuration(Duration.millis(200));
                aboutTayaPopOutTransition.setNode(aboutTayaPopOut);
                aboutTayaPopOutTransition.setToX(0);
                aboutTayaPopOutTransition.play();

                aboutChasePopOutTransition.stop();
                aboutChasePopOutTransition.setDuration(Duration.millis(200));
                aboutChasePopOutTransition.setNode(aboutChasePopOut);
                aboutChasePopOutTransition.setToX(0);
                aboutChasePopOutTransition.play();

                aboutChaoPopOutTransition.stop();
                aboutChaoPopOutTransition.setDuration(Duration.millis(200));
                aboutChaoPopOutTransition.setNode(aboutChaoPopOut);
                aboutChaoPopOutTransition.setToX(0);
                aboutChaoPopOutTransition.play();

                aboutJoeyPopOutTransition.stop();
                aboutJoeyPopOutTransition.setDuration(Duration.millis(200));
                aboutJoeyPopOutTransition.setNode(aboutJoeyPopOut);
                aboutJoeyPopOutTransition.setToX(0);
                aboutJoeyPopOutTransition.play();

                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    aboutPopOut4
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !aboutPopOut4.isHover()) {
                aboutPopOutTransition.setDuration(Duration.millis(200));
                aboutPopOutTransition.setNode(aboutPopOut2);
                aboutPopOutTransition.setToX(290);

                aboutPopOutTransition.play();
                aboutPopOut4.setVisible(false);

              } else if (newValue) {
                aboutPopOutTransition1.stop();
                aboutPopOutTransition.stop();
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(585);
                aboutPopOutTransition1.play();

                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(true);
              }
            });

    Randy.hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Randy.isHover()) {
                aboutRandyPopOutTransition.stop();
                aboutRandyPopOutTransition.setDuration(Duration.millis(200));
                aboutRandyPopOutTransition.setNode(aboutRandyPopOut);
                aboutRandyPopOutTransition.setToX(0);
                aboutRandyPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Randy.isHover() && !aboutPopOut3.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(585);
                aboutPopOutTransition1.play();
                aboutRandyPopOutTransition.setDuration(Duration.millis(300));
                aboutRandyPopOutTransition.setNode(aboutRandyPopOut);
                aboutRandyPopOutTransition.setToX(290);
                aboutRandyPopOutTransition.play();
                aboutRandyPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    aboutRandyPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Randy.isHover()) {
                aboutRandyPopOutTransition.stop();
                aboutRandyPopOutTransition.setDuration(Duration.millis(200));
                aboutRandyPopOutTransition.setNode(aboutRandyPopOut);
                aboutRandyPopOutTransition.setToX(0);
                aboutRandyPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Randy.isHover() && !aboutRandyPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();
                //                aboutRandyPopOutTransition.setDuration(Duration.millis(300));
                //                aboutRandyPopOutTransition.setNode(aboutRandyPopOut);
                aboutRandyPopOutTransition.setToX(290);
                aboutRandyPopOutTransition.play();
                aboutRandyPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });

    Eddie.hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Eddie.isHover()) {
                aboutEddiePopOutTransition.stop();
                aboutEddiePopOutTransition.setDuration(Duration.millis(200));
                aboutEddiePopOutTransition.setNode(aboutEddiePopOut);
                aboutEddiePopOutTransition.setToX(0);
                aboutEddiePopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Eddie.isHover() && !aboutEddiePopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(585);
                aboutPopOutTransition1.play();
                aboutEddiePopOutTransition.setDuration(Duration.millis(300));
                aboutEddiePopOutTransition.setNode(aboutEddiePopOut);
                aboutEddiePopOutTransition.setToX(290);
                aboutEddiePopOutTransition.play();
                aboutEddiePopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });

    aboutEddiePopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Eddie.isHover()) {
                aboutEddiePopOutTransition.stop();
                aboutEddiePopOutTransition.setDuration(Duration.millis(200));
                aboutEddiePopOutTransition.setNode(aboutEddiePopOut);
                aboutEddiePopOutTransition.setToX(0);
                aboutEddiePopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Eddie.isHover() && !aboutEddiePopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue || aboutEddiePopOut.isHover()) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();
                aboutEddiePopOutTransition.setDuration(Duration.millis(300));
                aboutEddiePopOutTransition.setNode(aboutEddiePopOut);
                aboutEddiePopOutTransition.setToX(290);
                aboutEddiePopOutTransition.play();
                aboutEddiePopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });

    Sebastian.hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Sebastian.isHover()) {
                aboutSebastianPopOutTransition.stop();
                aboutSebastianPopOutTransition.setDuration(Duration.millis(200));
                aboutSebastianPopOutTransition.setNode(aboutSebastianPopOut);
                aboutSebastianPopOutTransition.setToX(0);
                aboutSebastianPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Sebastian.isHover() && !aboutSebastianPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(585);
                aboutPopOutTransition1.play();
                aboutSebastianPopOutTransition.setDuration(Duration.millis(300));
                aboutSebastianPopOutTransition.setNode(aboutSebastianPopOut);
                aboutSebastianPopOutTransition.setToX(290);
                aboutSebastianPopOutTransition.play();
                aboutSebastianPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });

    aboutSebastianPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Sebastian.isHover()) {
                aboutSebastianPopOutTransition.stop();
                aboutSebastianPopOutTransition.setDuration(Duration.millis(200));
                aboutSebastianPopOutTransition.setNode(aboutSebastianPopOut);
                aboutSebastianPopOutTransition.setToX(0);
                aboutSebastianPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Sebastian.isHover() && !aboutSebastianPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue || aboutSebastianPopOut.isHover()) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();
                aboutSebastianPopOutTransition.setDuration(Duration.millis(300));
                aboutSebastianPopOutTransition.setNode(aboutSebastianPopOut);
                aboutSebastianPopOutTransition.setToX(290);
                aboutSebastianPopOutTransition.play();
                aboutSebastianPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    Nick.hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Nick.isHover()) {
                aboutNickPopOutTransition.stop();
                aboutNickPopOutTransition.setDuration(Duration.millis(200));
                aboutNickPopOutTransition.setNode(aboutNickPopOut);
                aboutNickPopOutTransition.setToX(0);
                aboutNickPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Nick.isHover() && !aboutNickPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(585);
                aboutPopOutTransition1.play();
                aboutNickPopOutTransition.setDuration(Duration.millis(300));
                aboutNickPopOutTransition.setNode(aboutNickPopOut);
                aboutNickPopOutTransition.setToX(290);
                aboutNickPopOutTransition.play();
                aboutNickPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    aboutNickPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Nick.isHover()) {
                aboutNickPopOutTransition.stop();
                aboutNickPopOutTransition.setDuration(Duration.millis(200));
                aboutNickPopOutTransition.setNode(aboutNickPopOut);
                aboutNickPopOutTransition.setToX(0);
                aboutNickPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Nick.isHover() && !aboutNickPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue || aboutNickPopOut.isHover()) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();
                aboutNickPopOutTransition.setDuration(Duration.millis(300));
                aboutNickPopOutTransition.setNode(aboutNickPopOut);
                aboutNickPopOutTransition.setToX(290);
                aboutNickPopOutTransition.play();
                aboutNickPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    Aaron.hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Aaron.isHover()) {
                aboutAaronPopOutTransition.stop();
                aboutAaronPopOutTransition.setDuration(Duration.millis(200));
                aboutAaronPopOutTransition.setNode(aboutAaronPopOut);
                aboutAaronPopOutTransition.setToX(0);
                aboutAaronPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Aaron.isHover() && !aboutAaronPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(585);
                aboutPopOutTransition1.play();
                aboutAaronPopOutTransition.setDuration(Duration.millis(300));
                aboutAaronPopOutTransition.setNode(aboutAaronPopOut);
                aboutAaronPopOutTransition.setToX(290);
                aboutAaronPopOutTransition.play();
                aboutAaronPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    aboutAaronPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Aaron.isHover()) {
                aboutAaronPopOutTransition.stop();
                aboutAaronPopOutTransition.setDuration(Duration.millis(200));
                aboutAaronPopOutTransition.setNode(aboutAaronPopOut);
                aboutAaronPopOutTransition.setToX(0);
                aboutAaronPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Aaron.isHover() && !aboutAaronPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue || aboutAaronPopOut.isHover()) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();
                aboutAaronPopOutTransition.setDuration(Duration.millis(300));
                aboutAaronPopOutTransition.setNode(aboutAaronPopOut);
                aboutAaronPopOutTransition.setToX(290);
                aboutAaronPopOutTransition.play();
                aboutAaronPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    Brandon.hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Brandon.isHover()) {
                aboutBrandonPopOutTransition.stop();
                aboutBrandonPopOutTransition.setDuration(Duration.millis(200));
                aboutBrandonPopOutTransition.setNode(aboutBrandonPopOut);
                aboutBrandonPopOutTransition.setToX(0);
                aboutBrandonPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Brandon.isHover() && !aboutBrandonPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(585);
                aboutPopOutTransition1.play();
                aboutBrandonPopOutTransition.setDuration(Duration.millis(300));
                aboutBrandonPopOutTransition.setNode(aboutBrandonPopOut);
                aboutBrandonPopOutTransition.setToX(290);
                aboutBrandonPopOutTransition.play();
                aboutBrandonPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    aboutBrandonPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Brandon.isHover()) {
                aboutBrandonPopOutTransition.stop();
                aboutBrandonPopOutTransition.setDuration(Duration.millis(200));
                aboutBrandonPopOutTransition.setNode(aboutBrandonPopOut);
                aboutBrandonPopOutTransition.setToX(0);
                aboutBrandonPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Brandon.isHover() && !aboutBrandonPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue || aboutBrandonPopOut.isHover()) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();
                aboutBrandonPopOutTransition.setDuration(Duration.millis(300));
                aboutBrandonPopOutTransition.setNode(aboutBrandonPopOut);
                aboutBrandonPopOutTransition.setToX(290);
                aboutBrandonPopOutTransition.play();
                aboutBrandonPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    Taya.hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Taya.isHover()) {
                aboutTayaPopOutTransition.stop();
                aboutTayaPopOutTransition.setDuration(Duration.millis(200));
                aboutTayaPopOutTransition.setNode(aboutTayaPopOut);
                aboutTayaPopOutTransition.setToX(0);
                aboutTayaPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Taya.isHover() && !aboutTayaPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(585);
                aboutPopOutTransition1.play();
                aboutTayaPopOutTransition.setDuration(Duration.millis(300));
                aboutTayaPopOutTransition.setNode(aboutTayaPopOut);
                aboutTayaPopOutTransition.setToX(290);
                aboutTayaPopOutTransition.play();
                aboutTayaPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    aboutTayaPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Taya.isHover()) {
                aboutTayaPopOutTransition.stop();
                aboutTayaPopOutTransition.setDuration(Duration.millis(200));
                aboutTayaPopOutTransition.setNode(aboutTayaPopOut);
                aboutTayaPopOutTransition.setToX(0);
                aboutTayaPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Taya.isHover() && !aboutTayaPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue || aboutTayaPopOut.isHover()) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();
                aboutTayaPopOutTransition.setDuration(Duration.millis(300));
                aboutTayaPopOutTransition.setNode(aboutTayaPopOut);
                aboutTayaPopOutTransition.setToX(290);
                aboutTayaPopOutTransition.play();
                aboutTayaPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    Chase.hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Chase.isHover()) {
                aboutChasePopOutTransition.stop();
                aboutChasePopOutTransition.setDuration(Duration.millis(200));
                aboutChasePopOutTransition.setNode(aboutChasePopOut);
                aboutChasePopOutTransition.setToX(0);
                aboutChasePopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Chase.isHover() && !aboutChasePopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(585);
                aboutPopOutTransition1.play();
                aboutChasePopOutTransition.setDuration(Duration.millis(300));
                aboutChasePopOutTransition.setNode(aboutChasePopOut);
                aboutChasePopOutTransition.setToX(290);
                aboutChasePopOutTransition.play();
                aboutChasePopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    aboutChasePopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Chase.isHover()) {
                aboutChasePopOutTransition.stop();
                aboutChasePopOutTransition.setDuration(Duration.millis(200));
                aboutChasePopOutTransition.setNode(aboutChasePopOut);
                aboutChasePopOutTransition.setToX(0);
                aboutChasePopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Chase.isHover() && !aboutChasePopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue || aboutChasePopOut.isHover()) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();
                aboutChasePopOutTransition.setDuration(Duration.millis(300));
                aboutChasePopOutTransition.setNode(aboutChasePopOut);
                aboutChasePopOutTransition.setToX(290);
                aboutChasePopOutTransition.play();
                aboutChasePopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    Chao.hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Chao.isHover()) {
                aboutChaoPopOutTransition.stop();
                aboutChaoPopOutTransition.setDuration(Duration.millis(200));
                aboutChaoPopOutTransition.setNode(aboutChaoPopOut);
                aboutChaoPopOutTransition.setToX(0);
                aboutChaoPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Chao.isHover() && !aboutChaoPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(585);
                aboutPopOutTransition1.play();
                aboutChaoPopOutTransition.setDuration(Duration.millis(300));
                aboutChaoPopOutTransition.setNode(aboutChaoPopOut);
                aboutChaoPopOutTransition.setToX(290);
                aboutChaoPopOutTransition.play();
                aboutChaoPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    aboutChaoPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Chao.isHover()) {
                aboutChaoPopOutTransition.stop();
                aboutChaoPopOutTransition.setDuration(Duration.millis(200));
                aboutChaoPopOutTransition.setNode(aboutChaoPopOut);
                aboutChaoPopOutTransition.setToX(0);
                aboutChaoPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Chao.isHover() && !aboutChaoPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue || aboutChaoPopOut.isHover()) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();
                aboutChaoPopOutTransition.setDuration(Duration.millis(300));
                aboutChaoPopOutTransition.setNode(aboutChaoPopOut);
                aboutChaoPopOutTransition.setToX(290);
                aboutChaoPopOutTransition.play();
                aboutChaoPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    Joey.hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Joey.isHover()) {
                aboutJoeyPopOutTransition.stop();
                aboutJoeyPopOutTransition.setDuration(Duration.millis(200));
                aboutJoeyPopOutTransition.setNode(aboutJoeyPopOut);
                aboutJoeyPopOutTransition.setToX(0);
                aboutJoeyPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Joey.isHover() && !aboutJoeyPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(585);
                aboutPopOutTransition1.play();
                aboutJoeyPopOutTransition.setDuration(Duration.millis(300));
                aboutJoeyPopOutTransition.setNode(aboutJoeyPopOut);
                aboutJoeyPopOutTransition.setToX(290);
                aboutJoeyPopOutTransition.play();
                aboutJoeyPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
              }
            });
    aboutJoeyPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !Joey.isHover()) {
                aboutJoeyPopOutTransition.stop();
                aboutJoeyPopOutTransition.setDuration(Duration.millis(200));
                aboutJoeyPopOutTransition.setNode(aboutJoeyPopOut);
                aboutJoeyPopOutTransition.setToX(0);
                aboutJoeyPopOutTransition.play();

                waitTransition.setOnFinished(
                    event -> {
                      if (!Joey.isHover() && !aboutJoeyPopOut.isHover()) {

                        // aboutPopOut1.setVisible(false);
                        homeTrigger1.setVisible(false);
                        aboutPopOut4.setVisible(false);
                        homeButton1.setVisible(true);
                      }
                    });

              } else if (newValue || aboutJoeyPopOut.isHover()) {
                aboutPopOutTransition1.setDuration(Duration.millis(200));
                aboutPopOutTransition1.setNode(aboutPopOut3);
                aboutPopOutTransition1.setToX(0);
                aboutPopOutTransition1.play();
                aboutJoeyPopOutTransition.setDuration(Duration.millis(300));
                aboutJoeyPopOutTransition.setNode(aboutJoeyPopOut);
                aboutJoeyPopOutTransition.setToX(290);
                aboutJoeyPopOutTransition.play();
                aboutJoeyPopOut.setVisible(true);
                aboutPopOut.setVisible(true);
                aboutPopOut3.setVisible(true);
                aboutPopOut1.setVisible(true);
                aboutPopOut4.setVisible(false);
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

    warningTrigger1
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !warningButton.isHover() && !warningPopOut.isHover()) {
                warningPopOutTransition.setDuration(Duration.millis(200));
                warningPopOutTransition.setNode(warningPopOut);
                warningPopOutTransition.setToX(0);
                warningPopOutTransition.setOnFinished(
                    e -> {
                      if (!warningPopOut.isHover() && !warningTrigger.isHover()) {
                        warningPopOut1.setVisible(false);
                        warningTrigger1.setVisible(false);
                        warningButton1.setVisible(true);
                      }
                    });
                warningPopOutTransition.play();
              } else if (newValue) {
                warningPopOutTransition.setDuration(Duration.millis(200));
                warningPopOutTransition.setNode(warningPopOut);
                warningPopOut.setTranslateX(250);
                warningPopOutTransition.play();
              }
            });
    warningPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !warningButton.isHover() && !warningTrigger.isHover()) {
                warningPopOutTransition.setDuration(Duration.millis(200));
                warningPopOutTransition.setNode(warningPopOut);
                warningPopOutTransition.setToX(0);
                warningPopOutTransition.setOnFinished(
                    e -> {
                      if (!warningPopOut.isHover() && !warningTrigger.isHover()) {
                        warningPopOut1.setVisible(false);
                        warningTrigger1.setVisible(false);
                        warningButton1.setVisible(true);
                      }
                    });
                warningPopOutTransition.play();
              } else if (newValue) {
                warningPopOutTransition.stop();
                warningPopOut.setTranslateX(250);
              }
            });
    warningButton1
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      warningPopOutTransition.setDuration(Duration.millis(200));
                      warningPopOutTransition.setNode(warningPopOut);
                      warningPopOutTransition.setToX(250);
                      warningPopOutTransition.play();
                      warningPopOut1.setVisible(true);
                      warningTrigger1.setVisible(true);
                      warningButton1.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                warningPopOutTransition.setDuration(Duration.millis(200));
                warningPopOutTransition.setNode(warningPopOut);
                warningPopOutTransition.setToX(0);
                warningPopOutTransition.setOnFinished(
                    e -> {
                      if (!warningPopOut.isHover() && !warningTrigger.isHover()) {
                        warningPopOut1.setVisible(false);
                        warningTrigger1.setVisible(false);
                        warningButton1.setVisible(true);
                      }
                    });
                warningPopOutTransition.play();
              }
            });

    Timeline clock =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                e ->
                    menuClock.setText(
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm\na")))),
            new KeyFrame(Duration.seconds(1)));
    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();
  }

  // TRANSLATOR//
  public TranslatorAPI translatorAPI = new TranslatorAPI();

  @FXML
  String LanguageSet(String text) throws Exception {
    if (language_choice == 0) { // 0 is english
      text = translatorAPI.translateToEn(text);
    } else if (language_choice == 1) { // 1 is spanish
      text = translatorAPI.translateToSp(text);
    } else if (language_choice == 2) { // 2 is Chinese
      text = translatorAPI.translateToZh(text);
    }
    return text;
  }
}
