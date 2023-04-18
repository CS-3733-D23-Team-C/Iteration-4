package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class OfficeSupplyController {
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
  // Supply Choice
  @FXML private MenuItem servicechoice1;
  @FXML private MenuItem servicechoice2;
  @FXML private MenuItem servicechoice3;
  @FXML private MenuItem servicechoice4;
  @FXML private MenuButton roomMenu;
  @FXML private MenuButton serviceMenu;
  @FXML private TextField nameBox;
  @FXML private TextArea specialRequest;
  @FXML private MenuButton employeeName;

  // special for Office Supply
  @FXML private TextField supplyAmount;

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
  }

  @FXML
  void getServicechoice2() {
    serviceMenu.setText(servicechoice2.getText());
  }

  @FXML
  void getServicechoice3() {
    serviceMenu.setText(servicechoice3.getText());
  }

  @FXML
  void getServicechoice4() {
    serviceMenu.setText(servicechoice4.getText());
  }

  @FXML
  void getSubmit() {
    String notes = specialRequest.getText();
    String name = nameBox.getText();
    String room = roomMenu.getText();
    String menuSelection = serviceMenu.getText();
    String quantity = supplyAmount.getText();
    OfficeSuppliesRequest req =
        new OfficeSuppliesRequest(new Requester(0, name), room, menuSelection, notes, quantity);
    IDao<OfficeSuppliesRequest, Integer> dao = new OfficeSuppliesRequestDAO();
    dao.addRow(req);
    Navigation.navigate(Screen.CONGRATS_PAGE);
  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.OFFICE_SUPPLY);
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
  void getGiftBasketRequestPage(ActionEvent event) {
    Navigation.navigate(Screen.GIFT_BASKET);
  }

  @FXML
  void getRoomReservationPage(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
  }

  @FXML
  void getSignagePage(ActionEvent event) {
    Navigation.navigate(Screen.SIGNAGE);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {}

  @FXML
  void getEditMap(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void getExit(ActionEvent event) {
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

  public void getHistory(ActionEvent event) {
    Navigation.navigate(Screen.OFFICE_SUPPLY_HISTORY);
  }

  @FXML
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }
}
