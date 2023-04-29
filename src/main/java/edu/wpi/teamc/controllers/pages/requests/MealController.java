package edu.wpi.teamc.controllers.pages.requests;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.requests.Meal;
import edu.wpi.teamc.dao.requests.MealRequest;
import edu.wpi.teamc.dao.requests.MealRequestDAO;
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

public class MealController {

  @FXML private SearchableComboBox roomMenu;
  // Meal Menu
  @FXML private MenuButton serviceMenu;
  @FXML private MenuItem servicechoice1;
  @FXML private MenuItem servicechoice2;
  @FXML private MenuItem servicechoice3;
  @FXML private MenuItem servicechoice4;

  @FXML private TextField nameBox;
  @FXML private TextArea specialRequest;
  @FXML private SearchableComboBox employeeName;
  @FXML DatePicker startTime;

  // Special for Meal
  // Drink Menu
  @FXML private MenuButton drinkMenu;
  @FXML private MenuItem drinkchoice1;
  @FXML private MenuItem drinkchoice2;
  @FXML private MenuItem drinkchoice3;
  @FXML private MenuItem drinkchoice4;

  // Image and Food Information
  @FXML private ImageView mealImage;
  @FXML private TextArea ingredients;
  @FXML private TextArea allergyInfo;
  @FXML AnchorPane assignEmployeeAnchor;

  @FXML
  void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }
  // These 4 choices(1-4) are for the meal menu
  @FXML
  void getServicechoice1() {
    serviceMenu.setText(servicechoice1.getText());
    try {
      getMealInfo(1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getServicechoice2() {
    serviceMenu.setText(servicechoice2.getText());
    try {
      getMealInfo(2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getServicechoice3() {
    serviceMenu.setText(servicechoice3.getText());
    try {
      getMealInfo(3);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getServicechoice4() {
    serviceMenu.setText(servicechoice4.getText());
    try {
      getMealInfo(4);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  // These 4 choices(1-4) are for the drink menu
  @FXML
  void getDrinkChoice1() {
    drinkMenu.setText(drinkchoice1.getText());
  }

  @FXML
  void getDrinkChoice2() {
    drinkMenu.setText(drinkchoice2.getText());
  }

  @FXML
  void getDrinkChoice3() {
    drinkMenu.setText(drinkchoice3.getText());
  }

  @FXML
  void getDrinkChoice4() {
    drinkMenu.setText(drinkchoice4.getText());
  }

  @FXML
  void getMealInfo(int mealChoice) throws IOException {
    switch (mealChoice) {
      case 1:
        mealImage.setImage(
            new Image(Main.class.getResource("views/images/Meal/spaghetti.png").openStream()));
        ingredients.setText("Ground Beef, Spaghetti, Tomato Sauce, Cheese");
        allergyInfo.setText("Contains: Milk, Wheat");
        break;
      case 2:
        mealImage.setImage(
            new Image(Main.class.getResource("views/images/Meal/garlic_steak.png").openStream()));
        ingredients.setText("Beef, Garlic, Butter");
        allergyInfo.setText("Contains: Milk");
        break;
      case 3:
        mealImage.setImage(
            new Image(Main.class.getResource("views/images/Meal/grill_chicken.png").openStream()));
        ingredients.setText("Chicken, Broccoli, Honey, Soy Sauce");
        allergyInfo.setText("Contains: Soy, Honey");
        break;
      case 4:
        mealImage.setImage(
            new Image(Main.class.getResource("views/images/Meal/fried_rice.png").openStream()));
        ingredients.setText("Chicken, Rice, Egg, Soy Sauce");
        allergyInfo.setText("Contains: Egg, Soy");
        break;
      default:
        mealImage.setImage(new Image("file:src/main/resources/images/meal1.png"));
        ingredients.setText("");
        allergyInfo.setText("Contains:");
        break;
    }
  }

  @FXML
  void getSubmit(ActionEvent event) {
    String name = nameBox.getText();
    String room = roomMenu.getValue().toString();
    String notes = specialRequest.getText();
    Meal meal = new Meal(serviceMenu.getText(), "");
    String eta = startTime.getValue().toString();
    MealRequest req = new MealRequest(new PatientUser(name), room, notes, meal, eta);

    IDao<MealRequest, Integer> dao = new MealRequestDAO();

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
    Navigation.navigate(Screen.CONFERENCE);
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
    ingredients.setWrapText(true);
    allergyInfo.setWrapText(true);
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
