package edu.wpi.teamc.controllers.pages.requests;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

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

public class FurnitureController {

  @FXML private SearchableComboBox roomMenu;
  @FXML DatePicker startTime;
  // Meal Menu
  @FXML private MenuButton serviceMenu;
  @FXML private MenuItem servicechoice1;
  @FXML private MenuItem servicechoice2;
  @FXML private MenuItem servicechoice3;
  @FXML private MenuItem servicechoice4;
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
    serviceMenu.setText(servicechoice1.getText());
    furnitureHolder = 1;
    try {
      getFurnitureInfo(1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getFurnitureChoice2() throws Exception {
    serviceMenu.setText(servicechoice2.getText());
    furnitureHolder = 2;
    try {
      getFurnitureInfo(2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getFurnitureChoice3() throws Exception {
    serviceMenu.setText(servicechoice3.getText());
    furnitureHolder = 3;
    try {
      getFurnitureInfo(3);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getFurnitureChoice4() throws Exception {
    serviceMenu.setText(servicechoice4.getText());
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
    if (language_choice == 0) {

      switch (furnitureChoice) {
        case 1:
          furnitureImage.setImage(
              new Image(Main.class.getResource("views/images/Furniture/chair.png").openStream()));
          Dimensions.setText(("Width: 30 in, Length: 27 in, Height: 60 in"));
          weightInfo.setText(("25 lbs each"));
          break;
        case 2:
          furnitureImage.setImage(
              new Image(
                  Main.class.getResource("views/images/Furniture/doradesk.png").openStream()));
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
    } else {
      switch (furnitureChoice) {
        case 1:
          furnitureImage.setImage(
              new Image(Main.class.getResource("views/images/Furniture/chair.png").openStream()));
          Dimensions.setText(LanguageSet("Width: 30 in, Length: 27 in, Height: 60 in"));
          weightInfo.setText(LanguageSet("25 lbs each"));
          break;
        case 2:
          furnitureImage.setImage(
              new Image(
                  Main.class.getResource("views/images/Furniture/doradesk.png").openStream()));
          Dimensions.setText(LanguageSet("Width: Backpack, Length: Boots, Height: Dora"));
          weightInfo.setText(LanguageSet("5 Swipers each"));
          break;
        case 3:
          furnitureImage.setImage(
              new Image(Main.class.getResource("views/images/Furniture/stool.png").openStream()));
          Dimensions.setText(LanguageSet("Width: 15 in, Length: 15 in, Height: 30 in"));
          weightInfo.setText(LanguageSet("10 lbs each"));
          break;
        case 4:
          furnitureImage.setImage(
              new Image(Main.class.getResource("views/images/Furniture/couch.png").openStream()));
          Dimensions.setText(LanguageSet("Width: 350 in, Length: 40 in, Height: 15 in"));
          weightInfo.setText(LanguageSet("500 lbs each"));
          break;
        default:
          furnitureImage.setImage(
              new Image(Main.class.getResource("views/images/Furniture/kys.png").openStream()));
          Dimensions.setText("");
          weightInfo.setText("");
          break;
      }
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

    setLanguage();
  }

  public TranslatorAPI translatorAPI = new TranslatorAPI();

  @FXML
  String LanguageSet(String text) throws Exception {
    if (language_choice == 0) { // 0 is english
      text = translatorAPI.translateToEn(text);
    } else if (language_choice == 1) { // 1 is spanish
      text = translatorAPI.translateToSp(text);
    } else if (language_choice == 2) { // 2 is Chinese
      text = translatorAPI.translateToZh(text);
    }
    return text;
  }

  public List<String> holder = new ArrayList<String>();

  @FXML
  void setLanguage() throws Exception {
    if (language_choice == 0) {
      holder = CApp.Furniture_English_list;
    } else if (language_choice == 1) {
      // holder = CApp.Home_Spanish_list;
    } else if (language_choice == 2) {
      holder = CApp.Furniture_Chinese_list;
    }

    Title.setText(holder.get(0));
    Box1.setText(holder.get(1));
    Box2.setText(holder.get(2));
    Box3.setText(holder.get(3));
    Box4.setText(holder.get(4));
    Box5.setText(holder.get(5));
    nameBox.setPromptText(holder.get(6));
    roomMenu.setPromptText(holder.get(7));
    serviceMenu.setText(holder.get(8));
    amountMenu.setText(holder.get(9));
    specialRequest.setPromptText(holder.get(10));
    startTime.setPromptText(holder.get(11));
    employeeName.setPromptText(holder.get(12));
    Furn_dimensions.setText(holder.get(13));
    Furn_weight.setText(holder.get(14));
    Submit.setText(holder.get(15));
    Clear.setText(holder.get(16));
    Cancel.setText(holder.get(17));

    servicechoice1.setText(LanguageSet(servicechoice1.getText()));
    servicechoice2.setText(LanguageSet(servicechoice2.getText()));
    servicechoice3.setText(LanguageSet(servicechoice3.getText()));
    servicechoice4.setText(LanguageSet(servicechoice4.getText()));
  }

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
