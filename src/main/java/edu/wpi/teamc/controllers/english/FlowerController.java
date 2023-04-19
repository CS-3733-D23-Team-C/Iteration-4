package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.PatientUser;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class FlowerController {

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

  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  @FXML private MFXButton clear;

  @FXML private MenuItem choice1;

  @FXML private MenuItem choice2;

  @FXML private MenuItem choice3;

  @FXML private MenuItem choice4;
  @FXML private MenuItem choice5;

  @FXML private MenuItem choice6;

  @FXML private MenuItem choice7;

  @FXML private MenuItem choice8;
  // Super Choice
  @FXML private MenuItem servicechoice1;
  @FXML private MenuItem servicechoice2;
  @FXML private MenuItem servicechoice3;
  @FXML private MenuItem servicechoice4;
  @FXML private MenuButton roomMenu;
  @FXML private MenuButton serviceMenu;
  @FXML private TextField nameBox;
  @FXML private TextArea specialRequest;
  @FXML private MenuButton employeeName;
  @FXML private ImageView image;

  // Special for Flower
  @FXML private TextArea giftCard;
  @FXML AnchorPane assignEmployeeAnchor;

  public void getGoHome() {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  // These 4 choices(1-4) are for the room name
  @FXML
  void getChoice1() {
    roomMenu.setText("Conference A1");
  }

  @FXML
  void getChoice2() {
    roomMenu.setText("Conference A2");
  }

  @FXML
  void getChoice3() {
    roomMenu.setText("Conference A3");
  }

  @FXML
  void getChoice4() {
    roomMenu.setText("Conference A4");
  }
  // These 4 choices(5-8) are for the employee name
  @FXML
  void getChoice5() {
    employeeName.setText(choice5.getText());
  }

  @FXML
  void getChoice6() {
    employeeName.setText(choice6.getText());
  }

  @FXML
  void getChoice7() {
    employeeName.setText(choice7.getText());
  }

  @FXML
  void getChoice8() {
    employeeName.setText(choice8.getText());
  }

  @FXML
  void getServicechoice1() {
    serviceMenu.setText(servicechoice1.getText());
    getImage(1);
  }

  @FXML
  void getServicechoice2() {
    serviceMenu.setText(servicechoice2.getText());
    getImage(2);
  }

  @FXML
  void getServicechoice3() {
    serviceMenu.setText(servicechoice3.getText());
    getImage(3);
  }

  @FXML
  void getServicechoice4() {
    serviceMenu.setText(servicechoice4.getText());
    getImage(4);
  }

  @FXML
  void getImage(int choice) {
    switch (choice) {
      case 1:
        image.setImage(
            new Image("file:src/main/resources/edu/wpi/teamc/views/Images/Flower/roses.png"));
        break;
      case 2:
        image.setImage(
            new Image("file:src/main/resources/edu/wpi/teamc/views/Images/Flower/lilies.png"));
        break;
      case 3:
        image.setImage(
            new Image("file:src/main/resources/edu/wpi/teamc/views/Images/Flower/daisies.png"));
        break;
      case 4:
        image.setImage(
            new Image("file:src/main/resources/edu/wpi/teamc/views/Images/Flower/sunflowers.png"));
        break;
    }
  }

  @FXML
  void getSubmit() {
    String notes = "Gift Card Message:" + giftCard.getText() + specialRequest.getText();
    String name = nameBox.getText();
    String room = roomMenu.getText();
    String menuSelection = serviceMenu.getText();
    FlowerDeliveryRequest req =
        new FlowerDeliveryRequest(new PatientUser(name), room, menuSelection, notes);
    IDao<FlowerDeliveryRequest, Integer> dao = new FlowerDeliveryRequestDAO();
    dao.addRow(req);
    Navigation.navigate(Screen.CONGRATS_PAGE);
  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.FLOWER);
  }

  @FXML
  void getFlowerDeliveryPage(KeyEvent event) {
    Navigation.navigate(Screen.FLOWER);
  }

  @FXML
  void getFurnitureDeliveryPage(KeyEvent event) {
    Navigation.navigate(Screen.FURNITURE);
  }

  @FXML
  void getHelpPage(KeyEvent event) {
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  void getMealDeliveryPage(KeyEvent event) {
    Navigation.navigate(Screen.MEAL);
  }

  @FXML
  void getOfficeSuppliesPage(KeyEvent event) {
    Navigation.navigate(Screen.OFFICE_SUPPLY);
  }

  @FXML
  void getRoomReservationPage(KeyEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
  }

  @FXML
  void getSignagePage(KeyEvent event) {
    Navigation.navigate(Screen.SIGNAGE);
  }

  @FXML
  void getGiftBasketRequestPage(KeyEvent event) {
    Navigation.navigate(Screen.GIFT_BASKET);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {
    if (!CApp.getAdminLoginCheck()) {
      assignEmployeeAnchor.setMouseTransparent(true);
      assignEmployeeAnchor.setOpacity(0);
    }
  }

  @FXML
  void getEditMap(KeyEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getLogOut(KeyEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void getExit(KeyEvent event) {
    Navigation.navigate(Screen.EXIT_PAGE);
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
  void getPathfindingPage(ActionEvent event) {
    Navigation.navigate(Screen.PATHFINDING_PAGE);
  }

  @FXML
  public void getHistory(ActionEvent event) {
    Navigation.navigate(Screen.FLOWER_HISTORY);
  }

  @FXML
  void getHelpage(KeyEvent event) {
    Navigation.navigate(Screen.HELP);
  }
}
