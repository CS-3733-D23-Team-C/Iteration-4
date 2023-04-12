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

public class FurnitureController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  @FXML private MFXButton clear;
  @FXML private MenuItem choice1;
  @FXML private MenuItem choice2;
  @FXML private MenuButton menuButton;
  @FXML private TextField nameBox;
  @FXML private MenuButton roomID;
  @FXML private TextField specialNotes;

  @FXML
  public void getGoHome() {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getChoice0() {
    menuButton.setText("--Please Select Furniture Option--");
  }

  @FXML
  void getChoice1() {
    menuButton.setText("Chair");
  }

  @FXML
  void getChoice2() {
    menuButton.setText("Couch");
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
  void getSubmit() {
    String notes = specialNotes.getText();
    String name = nameBox.getText();
    String room = roomID.getText();
    String menuSelection = menuButton.getText();
    FurnitureDeliveryRequest req =
        new FurnitureDeliveryRequest(0, new Requester(0, name), room, notes, menuSelection);

    IDao<FurnitureDeliveryRequest> dao = new FurnitureDeliveryRequestDAO();
    dao.addRow(req);
    Navigation.navigate(Screen.CONGRATS_PAGE);
  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.FURNITURE);
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
  void getEditMap(ActionEvent event) {}

  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void getExit(ActionEvent event) {
    System.exit(0);
  }

  @FXML
  void getMapHistory(ActionEvent event) {}

  @FXML
  void getMapPage(ActionEvent event) {}

  @FXML
  void getPathfindingPage(ActionEvent event) {}

  public void getHistory(ActionEvent event) {
    Navigation.navigate(Screen.FURNITURE_HISTORY);
  }
}
