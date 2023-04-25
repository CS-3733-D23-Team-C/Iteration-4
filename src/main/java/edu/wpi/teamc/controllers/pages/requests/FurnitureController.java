package edu.wpi.teamc.controllers.pages.requests;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.requests.FurnitureDeliveryRequest;
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

public class FurnitureController {

  @FXML private SearchableComboBox roomMenu;
  @FXML DatePicker startTime;
  // Meal Menu
  @FXML private MenuButton furnitureMenu;
  @FXML private MenuItem furniturechoice1;
  @FXML private MenuItem furniturechoice2;
  @FXML private MenuItem furniturechoice3;
  @FXML private MenuItem furniturechoice4;
  // Drink Menu
  @FXML private MenuButton amountMenu;
  @FXML private MenuItem amountchoice1;
  @FXML private MenuItem amountchoice2;
  @FXML private MenuItem amountchoice3;
  @FXML private MenuItem amountchoice4;

  @FXML private TextField nameBox;
  @FXML private TextArea specialRequest;
  @FXML private SearchableComboBox employeeName;

  // Image and Food Information
  @FXML private ImageView furnitureImage;
  @FXML private TextArea Dimensions;
  @FXML private TextArea weightInfo;
  @FXML AnchorPane assignEmployeeAnchor;

  @FXML
  void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getFurnitureChoice1() {
    furnitureMenu.setText(furniturechoice1.getText());
    try {
      getFurnitureInfo(1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getFurnitureChoice2() {
    furnitureMenu.setText(furniturechoice2.getText());
    try {
      getFurnitureInfo(2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getFurnitureChoice3() {
    furnitureMenu.setText(furniturechoice3.getText());
    try {
      getFurnitureInfo(3);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getFurnitureChoice4() {
    furnitureMenu.setText(furniturechoice4.getText());
    try {
      getFurnitureInfo(4);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  // These 4 choices(1-4) are for the drink menu
  @FXML
  void getAmountChoice1() {
    amountMenu.setText(amountchoice1.getText());
  }

  @FXML
  void getAmountChoice2() {
    amountMenu.setText(amountchoice2.getText());
  }

  @FXML
  void getAmountChoice3() {
    amountMenu.setText(amountchoice3.getText());
  }

  @FXML
  void getAmountChoice4() {
    amountMenu.setText(amountchoice4.getText());
  }

  @FXML
  void getFurnitureInfo(int furnitureChoice) throws IOException {
    switch (furnitureChoice) {
      case 1:
        furnitureImage.setImage(
            new Image(Main.class.getResource("views/images/Furniture/chair.png").openStream()));
        Dimensions.setText("Width: 30 in, Length: 27 in, Height: 60 in");
        weightInfo.setText("25 lbs each");
        break;
      case 2:
        furnitureImage.setImage(
            new Image(Main.class.getResource("views/images/Furniture/doradesk.png").openStream()));
        Dimensions.setText("Width: Backpack, Length: Boots, Height: Dora");
        weightInfo.setText("5 Swipers each");
        break;
      case 3:
        furnitureImage.setImage(
            new Image(Main.class.getResource("views/images/Furniture/stool.png").openStream()));
        Dimensions.setText("Width: 15 in, Length: 15 in, Height: 30 in");
        weightInfo.setText("10 lbs each");
        break;
      case 4:
        furnitureImage.setImage(
            new Image(Main.class.getResource("views/images/Furniture/couch.png").openStream()));
        Dimensions.setText("Width: 350 in, Length: 40 in, Height: 15 in");
        weightInfo.setText("500 lbs each");
        break;
      default:
        furnitureImage.setImage(
            new Image(Main.class.getResource("views/images/Furniture/kys.png").openStream()));
        Dimensions.setText("");
        weightInfo.setText("");
        break;
    }
  }

  @FXML
  void getSubmit(ActionEvent event) {
    String name = nameBox.getText();
    String room = roomMenu.getValue().toString();
    String notes = specialRequest.getText();
    String furnituretype = furnitureMenu.getText();
    String eta = startTime.getValue().toString();
    FurnitureDeliveryRequest req =
        new FurnitureDeliveryRequest(new PatientUser(name), room, notes, furnituretype, eta);

    if (!(employeeName == null)) {
      try {
        req.setAssignedto(employeeName.getValue().toString());
      } catch (Exception e) {
        System.out.println("No employee selected");
        req.setAssignedto(null);
      }
    }
    HospitalSystem.addRow(req);

    Navigation.navigate(Screen.CONGRATS_PAGE);
  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.FURNITURE);
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
    if (Dimensions == null) {
      Dimensions = new TextArea();
    }
    if (weightInfo == null) {
      weightInfo = new TextArea();
    }
    Dimensions.setWrapText(true);
    weightInfo.setWrapText(true);
  }
}
