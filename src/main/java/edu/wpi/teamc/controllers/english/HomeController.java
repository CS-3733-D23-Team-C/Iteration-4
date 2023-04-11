package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;

public class HomeController {

  private String filePath;
  private Desktop desktop = Desktop.getDesktop();

  @FXML MFXButton signageButton2;

  @FXML private Button flowerDeliveryPage;

  @FXML private Button furnitureDeliveryPage;

  @FXML private Button helpPage;

  @FXML private Button mealDeliveryPage;

  @FXML private Button officeSuppliesPage;

  @FXML private Button roomReservationPage;

  @FXML private Button signagePage;

  @FXML private Button logOut;
  @FXML private Button editMap;

  @FXML private Button mapHistory;

  @FXML private Button mapPage;

  @FXML private MenuButton importMenu;

  @FXML private Label testText;

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
  void getExit(ActionEvent event) {
    System.exit(0);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {}

  @FXML
  void getEditMap(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getImportNodes(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getImportEdges(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getImportLocationNames(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getImportMove(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getExportNodes(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getExportEdges(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getExportLocationNames(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getExportMove(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.LOGIN);
  }

  @FXML
  void getMapHistory(ActionEvent event) {
    Navigation.navigate(Screen.MAP_HISTORY_PAGE);
  }

  @FXML
  void getMapPage(ActionEvent event) {
    Navigation.navigate(Screen.FLOOR_PLAN);
  }

  @FXML
  void getExportButton(ActionEvent event) {}
}
