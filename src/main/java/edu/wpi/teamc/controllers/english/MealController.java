package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MealController {
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

  @FXML private MenuButton roomMenu;
  // Meal Menu
  @FXML private MenuButton serviceMenu;
  @FXML private MenuItem servicechoice1;
  @FXML private MenuItem servicechoice2;
  @FXML private MenuItem servicechoice3;
  @FXML private MenuItem servicechoice4;

  @FXML private TextField nameBox;
  @FXML private TextArea specialRequest;
  @FXML private MenuButton employeeName;

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

  //  @FXML
  //  void getChoice0() {
  //    roomMenu.setText("--Please Conference Room--");
  //  }

  // These 4 choices(1-4) are for the conference room
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

  // These 4 choices(1-4) are for the meal menu
  @FXML
  void getServicechoice1() {
    serviceMenu.setText(servicechoice1.getText());
    getMealInfo(1);
  }

  @FXML
  void getServicechoice2() {
    serviceMenu.setText(servicechoice2.getText());
    getMealInfo(2);
  }

  @FXML
  void getServicechoice3() {
    serviceMenu.setText(servicechoice3.getText());
    getMealInfo(3);
  }

  @FXML
  void getServicechoice4() {
    serviceMenu.setText(servicechoice4.getText());
    getMealInfo(4);
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
  void getMealInfo(int mealChoice) {
    switch (mealChoice) {
      case 1:
        mealImage.setImage(
            new Image("file:src/main/resources/edu/wpi/teamc/views/Images/Meal/spaghetti.png"));
        ingredients.setText("Ground Beef, Spaghetti, Tomato Sauce, Cheese");
        allergyInfo.setText("Contains: Milk, Wheat");
        break;
      case 2:
        mealImage.setImage(
            new Image("file:src/main/resources/edu/wpi/teamc/views/Images/Meal/garlic_steak.png"));
        ingredients.setText("Beef, Garlic, Butter");
        allergyInfo.setText("Contains: Milk");
        break;
      case 3:
        mealImage.setImage(
            new Image("file:src/main/resources/edu/wpi/teamc/views/Images/Meal/grill_chicken.png"));
        ingredients.setText("Chicken, Broccoli, Honey, Soy Sauce");
        allergyInfo.setText("Contains: Soy, Honey");
        break;
      case 4:
        mealImage.setImage(
            new Image("file:src/main/resources/edu/wpi/teamc/views/Images/Meal/fried_rice.png"));
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
    String room = roomMenu.getText();
    String notes = specialRequest.getText();
    Meal meal = new Meal(serviceMenu.getText(), "");
    MealRequest req = new MealRequest(new Requester(0, name), room, notes, meal);

    IDao<MealRequest, Integer> dao = new MealRequestDAO();

    dao.addRow(req);

    Navigation.navigate(Screen.CONGRATS_PAGE);
  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
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
