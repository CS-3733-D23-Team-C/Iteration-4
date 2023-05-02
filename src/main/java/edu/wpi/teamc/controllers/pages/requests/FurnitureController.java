package edu.wpi.teamc.controllers.pages.requests;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.requests.FurnitureDeliveryRequest;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.dao.users.PatientUser;
import edu.wpi.teamc.languageHelpers.TranslatorAPI;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
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

  int furnitureHolder = 0;

  @FXML
  void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getFurnitureChoice1() throws Exception {
    furnitureMenu.setText(furniturechoice1.getText());
    furnitureHolder = 1;
    try {
      getFurnitureInfo(1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getFurnitureChoice2() throws Exception {
    furnitureMenu.setText(furniturechoice2.getText());
    furnitureHolder = 2;
    try {
      getFurnitureInfo(2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getFurnitureChoice3() throws Exception {
    furnitureMenu.setText(furniturechoice3.getText());
    furnitureHolder = 3;
    try {
      getFurnitureInfo(3);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getFurnitureChoice4() throws Exception {
    furnitureMenu.setText(furniturechoice4.getText());
    furnitureHolder = 4;
    try {
      getFurnitureInfo(4);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  String furnitureSelecter(int holder) {
    switch (holder) {
      case 0:
        return "No Furniture Selected";
      case 1:
        return "Chair";
      case 2:
        return "Desk";
      case 3:
        return "Stool";
      case 4:
        return "Couch";
      default:
        return "No Furniture Selected";
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
  void getFurnitureInfo(int furnitureChoice) throws Exception {
    switch (furnitureChoice) {
      case 1:
        furnitureImage.setImage(
            new Image(Main.class.getResource("views/images/Furniture/chair.png").openStream()));
        Dimensions.setText(("Width: 30 in, Length: 27 in, Height: 60 in"));
        weightInfo.setText(("25 lbs each"));
        break;
      case 2:
        furnitureImage.setImage(
            new Image(Main.class.getResource("views/images/Furniture/doradesk.png").openStream()));
        Dimensions.setText(("Width: Backpack, Length: Boots, Height: Dora"));
        weightInfo.setText(("5 Swipers each"));
        break;
      case 3:
        furnitureImage.setImage(
            new Image(Main.class.getResource("views/images/Furniture/stool.png").openStream()));
        Dimensions.setText(("Width: 15 in, Length: 15 in, Height: 30 in"));
        weightInfo.setText(("10 lbs each"));
        break;
      case 4:
        furnitureImage.setImage(
            new Image(Main.class.getResource("views/images/Furniture/couch.png").openStream()));
        Dimensions.setText(("Width: 350 in, Length: 40 in, Height: 15 in"));
        weightInfo.setText(("500 lbs each"));
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
    String furnituretype = furnitureSelecter(furnitureHolder);
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
  public void initialize() throws Exception {
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

    // setLanguage();
  }

  public TranslatorAPI translatorAPI = new TranslatorAPI();

  //  @FXML
  //  String LanguageSet(String text) throws Exception {
  //    if (language_choice == 0) { // 0 is english
  //      text = translatorAPI.translateToEn(text);
  //    } else if (language_choice == 1) { // 1 is spanish
  //      text = translatorAPI.translateToSp(text);
  //    } else if (language_choice == 2) { // 2 is Chinese
  //      text = translatorAPI.translateToZh(text);
  //    }
  //    return text;
  //  }
  //
  //  @FXML
  //  void setLanguage() throws Exception {
  //    if (language_choice == 0 && notEnglish == false) { // 0 is english
  //    } else {
  //      Box1.setText(LanguageSet(Box1.getText()));
  //      Box2.setText(LanguageSet(Box2.getText()));
  //      Box3.setText(LanguageSet(Box3.getText()));
  //      Box4.setText(LanguageSet(Box4.getText()));
  //      Box5.setText(LanguageSet(Box5.getText()));
  //      nameBox.setPromptText(LanguageSet(nameBox.getPromptText()));
  //      roomMenu.setPromptText(LanguageSet(roomMenu.getPromptText()));
  //      furnitureMenu.setText(LanguageSet(furnitureMenu.getText()));
  //      amountMenu.setText(LanguageSet(amountMenu.getText()));
  //      specialRequest.setPromptText(LanguageSet(specialRequest.getPromptText()));
  //      startTime.setPromptText(LanguageSet(startTime.getPromptText()));
  //      employeeName.setPromptText(LanguageSet(employeeName.getPromptText()));
  //      Furn_dimensions.setText(LanguageSet(Furn_dimensions.getText()));
  //      Furn_weight.setText(LanguageSet(Furn_weight.getText()));
  //      Submit.setText(LanguageSet(Submit.getText()));
  //      Clear.setText(LanguageSet(Clear.getText()));
  //      Cancel.setText(LanguageSet(Cancel.getText()));
  //      //      ingredients.setText(LanguageSet(ingredients.getText()));
  //      //      allergyInfo.setText(LanguageSet(allergyInfo.getText()));
  //
  //      furniturechoice1.setText(LanguageSet(furniturechoice1.getText()));
  //      furniturechoice2.setText(LanguageSet(furniturechoice2.getText()));
  //      furniturechoice3.setText(LanguageSet(furniturechoice3.getText()));
  //      furniturechoice4.setText(LanguageSet(furniturechoice4.getText()));
  //      Title.setText(LanguageSet(Title.getText()));
  //      notEnglish = true;
  //    }
  //  }

  @FXML private Text Title;
  @FXML private MFXButton Submit;
  @FXML private MFXButton Clear;
  @FXML private MFXButton Cancel;
  @FXML private TextField Box1;
  @FXML private TextField Box2;
  @FXML private TextField Box3;
  @FXML private TextField Box4;
  @FXML private TextField Box5;
  @FXML private TextField Furn_dimensions;
  @FXML private TextField Furn_weight;
}
