package edu.wpi.teamc.controllers.pages.requests;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.requests.OfficeSuppliesRequest;
import edu.wpi.teamc.dao.requests.OfficeSuppliesRequestDAO;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.dao.users.PatientUser;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.SearchableComboBox;

public class OfficeSupplyController {
  // Supply Choice
  @FXML private MenuItem servicechoice1;
  @FXML private MenuItem servicechoice2;
  @FXML private MenuItem servicechoice3;
  @FXML private MenuItem servicechoice4;
  @FXML private SearchableComboBox roomMenu;
  @FXML private MenuButton serviceMenu;
  @FXML private TextField nameBox;
  @FXML private TextArea specialRequest;
  @FXML private SearchableComboBox employeeName;
  @FXML private ImageView image;

  // special for Office Supply
  @FXML private TextField supplyAmount;
  @FXML AnchorPane assignEmployeeAnchor;

  @FXML DatePicker startTime;

  public void getGoHome() {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getServicechoice1() throws IOException {
    serviceMenu.setText(servicechoice1.getText());
    image.setImage(
        new Image(Main.class.getResource("views/images/Office_Supply/notebook.png").openStream()));
  }

  @FXML
  void getServicechoice2() throws IOException {
    serviceMenu.setText(servicechoice2.getText());
    image.setImage(
        new Image(Main.class.getResource("views/images/Office_Supply/pen.png").openStream()));
  }

  @FXML
  void getServicechoice3() throws IOException {
    serviceMenu.setText(servicechoice3.getText());
    image.setImage(
        new Image(Main.class.getResource("views/images/Office_Supply/pencil.png").openStream()));
  }

  @FXML
  void getServicechoice4() throws IOException {
    serviceMenu.setText(servicechoice4.getText());
    image.setImage(
        new Image(Main.class.getResource("views/images/Office_Supply/staple.png").openStream()));
  }

  @FXML
  void getSubmit() {
    String notes = specialRequest.getText();
    String name = nameBox.getText();
    String room = roomMenu.getValue().toString();
    String menuSelection = serviceMenu.getText();
    String startTime = this.startTime.getValue().toString();
    OfficeSuppliesRequest req =
        new OfficeSuppliesRequest(new PatientUser(name), room, menuSelection, notes, startTime);
    IDao<OfficeSuppliesRequest, Integer> dao = new OfficeSuppliesRequestDAO();
    if (!(employeeName == null)) {
      try {
        req.setAssignedto(employeeName.getValue().toString());
      } catch (Exception e) {
        System.out.println("No employee selected");
        req.setAssignedto(null);
      }
    }
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
  public void initialize() {
    List<LocationName> locationNames =
        (List<LocationName>) HospitalSystem.fetchAllObjects(new LocationName());
    // remove halls, elevators, stairs and bathrooms from list
    locationNames.removeIf(locationName -> locationName.getNodeType().equals("HALL"));
    locationNames.removeIf(locationName -> locationName.getNodeType().equals("ELEV"));
    locationNames.removeIf(locationName -> locationName.getNodeType().equals("BATH"));
    locationNames.removeIf(locationName -> locationName.getNodeType().equals("STAI"));
    locationNames.removeIf(locationName -> locationName.getNodeType().equals("REST"));
    roomMenu.setItems(FXCollections.observableArrayList(locationNames));

    List<EmployeeUser> employeeUsers =
        (List<EmployeeUser>) HospitalSystem.fetchAllObjects(new EmployeeUser());
    employeeName.setItems(FXCollections.observableArrayList(employeeUsers));

    if (!CApp.getAdminLoginCheck()) {
      assignEmployeeAnchor.setMouseTransparent(true);
      assignEmployeeAnchor.setOpacity(0);
    }
  }
}
