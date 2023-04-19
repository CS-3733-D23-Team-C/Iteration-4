package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.dao.users.PatientUser;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.SearchableComboBox;

import java.util.List;

public class FurnitureController {
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

  @FXML private SearchableComboBox roomMenu;
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

  //  @FXML
  //  void getChoice0() {
  //    roomMenu.setText("--Please Conference Room--");
  //  }

  // These 4 choices(1-4) are for the conference room
//  @FXML
//  void getChoice1() {
//    roomMenu.setText("Conference A1");
//  }
//
//  @FXML
//  void getChoice2() {
//    roomMenu.setText("Conference A2");
//  }
//
//  @FXML
//  void getChoice3() {
//    roomMenu.setText("Conference A3");
//  }
//
//  @FXML
//  void getChoice4() {
//    roomMenu.setText("Conference A4");
//  }

  // These 4 choices(5-8) are for the employee name
//  @FXML
//  void getChoice5() {
//    employeeName.setText(choice5.getText());
//  }
//
//  @FXML
//  void getChoice6() {
//    employeeName.setText(choice6.getText());
//  }
//
//  @FXML
//  void getChoice7() {
//    employeeName.setText(choice7.getText());
//  }
//
//  @FXML
//  void getChoice8() {
//    employeeName.setText(choice8.getText());
//  }

  // These 4 choices(1-4) are for the meal menu
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
            new Image(Main.class.getResource("views/Images/Furniture/chair.png").openStream()));
        Dimensions.setText("Width: 30 in, Length: 27 in, Height: 60 in");
        weightInfo.setText("25 lbs each");
        break;
      case 2:
        furnitureImage.setImage(
            new Image(Main.class.getResource("views/Images/Furniture/doradesk.png").openStream()));
        Dimensions.setText("Width: Backpack, Length: Boots, Height: Dora");
        weightInfo.setText("5 Swipers each");
        break;
      case 3:
        furnitureImage.setImage(
            new Image(Main.class.getResource("views/Images/Furniture/stool.png").openStream()));
        Dimensions.setText("Width: 15 in, Length: 15 in, Height: 30 in");
        weightInfo.setText("10 lbs each");
        break;
      case 4:
        furnitureImage.setImage(
            new Image(Main.class.getResource("views/Images/Furniture/couch.png").openStream()));
        Dimensions.setText("Width: 350 in, Length: 40 in, Height: 15 in");
        weightInfo.setText("500 lbs each");
        break;
      default:
        furnitureImage.setImage(
            new Image(Main.class.getResource("views/Images/Furniture/kys.png").openStream()));
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
    Meal meal = new Meal(furnitureMenu.getText(), "");
    MealRequest req = new MealRequest(new PatientUser(name), room, notes, meal);

    IDao<MealRequest, Integer> dao = new MealRequestDAO();

    if(!(employeeName.getValue().toString() == null)) {
      req.setAssignedto(employeeName.getValue().toString());
    }
    dao.addRow(req);

    Navigation.navigate(Screen.CONGRATS_PAGE);
  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.FURNITURE);
  }

  @FXML
  void getMenuButton() {}

  @FXML
  void getHistory(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE_HISTORY);
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

  @FXML
  void getConferenceHistory(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE_HISTORY);
  }

  @FXML
  void getGiftBasketRequestPage(ActionEvent event) {
    Navigation.navigate(Screen.GIFT_BASKET);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {
    List<LocationName> locationNames =
            (List<LocationName>) HospitalSystem.fetchAllObjects(new LocationName());
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

  @FXML
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }
}
