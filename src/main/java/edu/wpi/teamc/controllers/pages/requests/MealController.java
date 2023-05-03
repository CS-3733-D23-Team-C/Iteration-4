package edu.wpi.teamc.controllers.pages.requests;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

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
import edu.wpi.teamc.languageHelpers.TranslatorAPI;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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

  int mealHolder = 0;

  @FXML
  void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }
  // These 4 choices(1-4) are for the meal menu
  @FXML
  void getServicechoice1() throws Exception {
    serviceMenu.setText(servicechoice1.getText());
    mealHolder = 1;
    try {
      getMealInfo(1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getServicechoice2() throws Exception {
    serviceMenu.setText(servicechoice2.getText());
    mealHolder = 2;
    try {
      getMealInfo(2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getServicechoice3() throws Exception {
    serviceMenu.setText(servicechoice3.getText());
    mealHolder = 3;
    try {
      getMealInfo(3);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getServicechoice4() throws Exception {
    serviceMenu.setText(servicechoice4.getText());
    mealHolder = 4;
    try {
      getMealInfo(4);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  String mealSelecter(int holder) {
    switch (holder) {
      case 0:
        return "No Meal Selected";
      case 1:
        return "Spaghetti";
      case 2:
        return "Garlic Steak";
      case 3:
        return "Grill Chicken";
      case 4:
        return "Fried Rice";
      default:
        return "No Meal Selected";
    }
  }

  // These 4 choices(1-4) are for the drink menu
  int drinkHolder = 0;

  @FXML
  void getDrinkChoice1() {
    drinkMenu.setText(drinkchoice1.getText());
    drinkHolder = 1;
  }

  @FXML
  void getDrinkChoice2() {
    drinkMenu.setText(drinkchoice2.getText());
    drinkHolder = 2;
  }

  @FXML
  void getDrinkChoice3() {
    drinkMenu.setText(drinkchoice3.getText());
    drinkHolder = 3;
  }

  @FXML
  void getDrinkChoice4() {
    drinkMenu.setText(drinkchoice4.getText());
    drinkHolder = 4;
  }

  String drinkSelecter(int holder) {
    switch (holder) {
      case 0:
        return "No Drink Selected";
      case 1:
        return "Water";
      case 2:
        return "Lemonade";
      case 3:
        return "Coffee";
      case 4:
        return "Tea";
      default:
        return "No Drink Selected";
    }
  }

  @FXML
  void getMealInfo(int mealChoice) throws Exception {
    if (language_choice == 0) {
      switch (mealChoice) {
        case 1:
          mealImage.setImage(
              new Image(Main.class.getResource("views/images/Meal/spaghetti.png").openStream()));
          ingredients.setText(("Ground Beef, Spaghetti, Tomato Sauce, Cheese"));
          allergyInfo.setText(("Contains: Milk, Wheat"));
          break;
        case 2:
          mealImage.setImage(
              new Image(Main.class.getResource("views/images/Meal/garlic_steak.png").openStream()));
          ingredients.setText(("Beef, Garlic, Butter"));
          allergyInfo.setText(("Contains: Milk"));
          break;
        case 3:
          mealImage.setImage(
              new Image(
                  Main.class.getResource("views/images/Meal/grill_chicken.png").openStream()));
          ingredients.setText(("Chicken, Broccoli, Honey, Soy Sauce"));
          allergyInfo.setText(("Contains: Soy, Honey"));
          break;
        case 4:
          mealImage.setImage(
              new Image(Main.class.getResource("views/images/Meal/fried_rice.png").openStream()));
          ingredients.setText(("Chicken, Rice, Egg, Soy Sauce"));
          allergyInfo.setText(("Contains: Egg, Soy"));
          break;
        default:
          mealImage.setImage(new Image("file:src/main/resources/images/meal1.png"));
          ingredients.setText((""));
          allergyInfo.setText(("Contains:"));
          break;
      }
    } else {
      switch (mealChoice) {
        case 1:
          mealImage.setImage(
              new Image(Main.class.getResource("views/images/Meal/spaghetti.png").openStream()));
          ingredients.setText(LanguageSet("Ground Beef, Spaghetti, Tomato Sauce, Cheese"));
          allergyInfo.setText(LanguageSet("Contains: Milk, Wheat"));
          break;
        case 2:
          mealImage.setImage(
              new Image(Main.class.getResource("views/images/Meal/garlic_steak.png").openStream()));
          ingredients.setText(LanguageSet("Beef, Garlic, Butter"));
          allergyInfo.setText(LanguageSet("Contains: Milk"));
          break;
        case 3:
          mealImage.setImage(
              new Image(
                  Main.class.getResource("views/images/Meal/grill_chicken.png").openStream()));
          ingredients.setText(LanguageSet("Chicken, Broccoli, Honey, Soy Sauce"));
          allergyInfo.setText(LanguageSet("Contains: Soy, Honey"));
          break;
        case 4:
          mealImage.setImage(
              new Image(Main.class.getResource("views/images/Meal/fried_rice.png").openStream()));
          ingredients.setText(LanguageSet("Chicken, Rice, Egg, Soy Sauce"));
          allergyInfo.setText(LanguageSet("Contains: Egg, Soy"));
          break;
        default:
          mealImage.setImage(new Image("file:src/main/resources/images/meal1.png"));
          ingredients.setText(LanguageSet(""));
          allergyInfo.setText(LanguageSet("Contains:"));
          break;
      }
    }
  }

  @FXML
  void getSubmit(ActionEvent event) {
    String name = nameBox.getText();
    String room = roomMenu.getValue().toString();
    String notes = "Drink: " + drinkSelecter(drinkHolder) + "; " + specialRequest.getText();
    Meal meal = new Meal(mealSelecter(mealHolder), "");
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
    Navigation.navigate(Screen.MEAL);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() throws Exception {
    Thread thread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
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
            });
    thread.start();

    if (!CApp.getAdminLoginCheck()) {
      assignEmployeeAnchor.setMouseTransparent(true);
      assignEmployeeAnchor.setOpacity(0);
    }
    ingredients.setWrapText(true);
    allergyInfo.setWrapText(true);

    setLanguage();
  }

  public List<String> holder = new ArrayList<String>();

  @FXML
  void setLanguage() throws Exception {

    if (language_choice == 0) {
      holder = CApp.Meal_English_list;
    } else if (language_choice == 1) {
      // holder = CApp.Home_Spanish_list;
    } else if (language_choice == 2) {
      holder = CApp.Meal_Chinese_list;
    }

    Meal_Box1.setText(holder.get(0));
    Meal_Box2.setText(holder.get(1));
    Meal_Box3.setText(holder.get(2));
    Meal_Box4.setText(holder.get(3));
    Meal_Box5.setText(holder.get(4));

    nameBox.setPromptText(holder.get(5));
    roomMenu.setPromptText(holder.get(6));
    serviceMenu.setText(holder.get(7));
    drinkMenu.setText(holder.get(8));
    specialRequest.setPromptText(holder.get(9));
    startTime.setPromptText(holder.get(10));
    employeeName.setPromptText(holder.get(11));
    Meal_IngredientsBox.setText(holder.get(12));
    Meal_AllergyBox.setText(holder.get(13));
    Meal_Submit.setText(holder.get(14));
    Meal_Clear.setText(holder.get(15));
    Meal_Cancel.setText(holder.get(16));

    //    ingredients.setText(LanguageSet(ingredients.getText()));
    //    allergyInfo.setText(LanguageSet(allergyInfo.getText()));

    servicechoice1.setText(LanguageSet(servicechoice1.getText()));
    servicechoice2.setText(LanguageSet(servicechoice2.getText()));
    servicechoice3.setText(LanguageSet(servicechoice3.getText()));
    servicechoice4.setText(LanguageSet(servicechoice4.getText()));
    drinkchoice1.setText(LanguageSet(drinkchoice1.getText()));
    drinkchoice2.setText(LanguageSet(drinkchoice2.getText()));
    drinkchoice3.setText(LanguageSet(drinkchoice3.getText()));
    drinkchoice4.setText(LanguageSet(drinkchoice4.getText()));
    Meal_Title.setText(holder.get(17));
  }

  // TEXT need to be Translated
  @FXML private Text Meal_Title;

  @FXML private TextField Meal_Box1;
  @FXML private TextField Meal_Box2;
  @FXML private TextField Meal_Box3;
  @FXML private TextField Meal_Box4;
  @FXML private TextField Meal_Box5;
  @FXML private TextField Meal_IngredientsBox;
  @FXML private TextField Meal_AllergyBox;
  @FXML private MFXButton Meal_Submit;
  @FXML private MFXButton Meal_Clear;
  @FXML private MFXButton Meal_Cancel;

  public TranslatorAPI translatorAPI = new TranslatorAPI();

  @FXML
  String LanguageSet(String text) throws Exception {
    if (text == null) {
      return null;
    }
    if (language_choice == 0) { // 0 is english
      text = translatorAPI.translateToEn(text);
    } else if (language_choice == 1) { // 1 is spanish
      text = translatorAPI.translateToSp(text);
    } else if (language_choice == 2) { // 2 is Chinese
      text = translatorAPI.translateToZh(text);
    }
    return text;
  }
}
