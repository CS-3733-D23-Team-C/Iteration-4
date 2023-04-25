package edu.wpi.teamc.controllers.pages.requests;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.requests.FlowerDeliveryRequest;
import edu.wpi.teamc.dao.requests.FlowerDeliveryRequestDAO;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.dao.users.PatientUser;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.SearchableComboBox;

public class FlowerController {

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

  // Special for Flower
  @FXML private TextArea giftCard;
  @FXML AnchorPane assignEmployeeAnchor;

  public void getGoHome() {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getServicechoice1() {
    serviceMenu.setText(servicechoice1.getText());
    try {
      getImage(1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getServicechoice2() {
    serviceMenu.setText(servicechoice2.getText());
    try {
      getImage(2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getServicechoice3() {
    serviceMenu.setText(servicechoice3.getText());
    try {
      getImage(3);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getServicechoice4() {
    serviceMenu.setText(servicechoice4.getText());
    try {
      getImage(4);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getImage(int choice) throws IOException {
    switch (choice) {
      case 1:
        image.setImage(
            new Image(Main.class.getResource("views/images/Flower/roses.png").openStream()));
        break;
      case 2:
        image.setImage(
            new Image(Main.class.getResource("views/images/Flower/lilies.png").openStream()));
        break;
      case 3:
        image.setImage(
            new Image(Main.class.getResource("views/images/Flower/daisies.png").openStream()));
        break;
      case 4:
        image.setImage(
            new Image(Main.class.getResource("views/images/Flower/sunflowers.png").openStream()));
        break;
    }
  }

  @FXML
  void getSubmit() {

    String notes = "Gift Card Message:" + giftCard.getText() + specialRequest.getText();
    String name = nameBox.getText();
    String room = roomMenu.getValue().toString();
    String menuSelection = serviceMenu.getText();
    FlowerDeliveryRequest req =
        new FlowerDeliveryRequest(new PatientUser(name), room, menuSelection, notes);
    IDao<FlowerDeliveryRequest, Integer> dao = new FlowerDeliveryRequestDAO();
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
    Navigation.navigate(Screen.FLOWER);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {
    if (!CApp.getAdminLoginCheck()) {
      assignEmployeeAnchor.setMouseTransparent(true);
      assignEmployeeAnchor.setOpacity(0);
    }

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
  }
}
