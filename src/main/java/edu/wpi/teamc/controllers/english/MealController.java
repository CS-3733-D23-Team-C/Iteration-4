package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class MealController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  @FXML private MFXButton clear;

  @FXML private MenuItem choice1;

  @FXML private MenuItem choice2;

  @FXML private MenuItem choice3;

  @FXML private MenuItem choice4;
  @FXML private MenuButton roomID;
  @FXML private TextField nameBox;

  @FXML private MenuButton menuButton;

  @FXML private TextField specialNotes;

  public void getGoHome() {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getChoice0() {
    menuButton.setText("Halal Shack Bowl with Zucchini Noodles and Chicken Breast");
  }

  @FXML
  void getChoice1() {
    menuButton.setText("Cheese Burger");
  }

  @FXML
  void getChoice2() {
    menuButton.setText("Hotdog");
  }

  @FXML
  void getChoice3() {
    menuButton.setText("Grease Bowl");
  }

  @FXML
  void getChoice4() {
    menuButton.setText("Reverse Osmosis Water");
  }

  @FXML
  void getRoom1() {
    roomID.setText("Room1");
  }

  @FXML
  void getRoom2() {
    roomID.setText("Room2");
  }

  @FXML
  void getSubmit(ActionEvent event) {
    String notes = specialNotes.getText();
    String name = nameBox.getText();
    String room = roomID.getText();
    String menuSelection = menuButton.getText();
    MealRequest req =
        new MealRequest(0, new Requester(0, name), room, notes, new Meal(menuSelection, ""));
    req.setEta("Test o clock");
    IDao<MealRequest, Integer> dao = new MealRequestDAO();
    dao.addRow(req);
    Navigation.navigate(Screen.CONGRATS_PAGE);
  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.MEAL);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {}

  public void getHelpPage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.HELP);
  }

  public void getLogOut(ActionEvent actionEvent) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void getFlowerDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FLOWER);
  }

  @FXML
  void getFurnitureDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FURNITURE);
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
  void getGiftBasketRequestPage(ActionEvent event) {
    Navigation.navigate(Screen.GIFT_BASKET);
  }

  @FXML
  void getSignagePage(ActionEvent event) {
    Navigation.navigate(Screen.SIGNAGE);
  }

  @FXML
  void getHistory(ActionEvent event) {
    Navigation.navigate(Screen.MEAL_HISTORY);
  }

  @FXML
  void getPathfindingPage(ActionEvent event) {
    Navigation.navigate(Screen.PATHFINDING_PAGE);
  }

  @FXML
  void getExit(ActionEvent event) {
    Navigation.navigate(Screen.EXIT_PAGE);
  }

  @FXML
  void getEditMap(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getMapHistory(ActionEvent event) {
    Navigation.navigate(Screen.MAP_HISTORY_PAGE);
  }

  @FXML
  void getMealHistory(ActionEvent event) {
    Navigation.navigate(Screen.MEAL_HISTORY);
  }

  //  @FXML
  //  void getMapPage(ActionEvent event) {
  //    Navigation.navigate(Screen.FLOOR_PLAN);
  //  }

  @FXML
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }
}
