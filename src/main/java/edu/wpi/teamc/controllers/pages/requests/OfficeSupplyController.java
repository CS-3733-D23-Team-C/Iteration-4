package edu.wpi.teamc.controllers.pages.requests;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.requests.OfficeSuppliesRequest;
import edu.wpi.teamc.dao.requests.OfficeSuppliesRequestDAO;
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

  int officeSelection = 0;

  public void getGoHome() {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getServicechoice1() throws IOException {
    serviceMenu.setText(servicechoice1.getText());
    officeSelection = 1;
    image.setImage(
        new Image(Main.class.getResource("views/images/Office_Supply/notebook.png").openStream()));
  }

  @FXML
  void getServicechoice2() throws IOException {
    serviceMenu.setText(servicechoice2.getText());
    officeSelection = 2;
    image.setImage(
        new Image(Main.class.getResource("views/images/Office_Supply/pen.png").openStream()));
  }

  @FXML
  void getServicechoice3() throws IOException {
    serviceMenu.setText(servicechoice3.getText());
    officeSelection = 3;
    image.setImage(
        new Image(Main.class.getResource("views/images/Office_Supply/pencil.png").openStream()));
  }

  @FXML
  void getServicechoice4() throws IOException {
    serviceMenu.setText(servicechoice4.getText());
    officeSelection = 4;
    image.setImage(
        new Image(Main.class.getResource("views/images/Office_Supply/staple.png").openStream()));
  }

  String MealSelector(int selection) {
    switch (selection) {
      case 1:
        return "Notebook";
      case 2:
        return "Pen";
      case 3:
        return "Pencil";
      case 4:
        return "Staple";
      default:
        return "";
    }
  }

  @FXML
  void getSubmit() {
    String notes = specialRequest.getText();
    String name = nameBox.getText();
    String room = roomMenu.getValue().toString();
    String menuSelection = MealSelector(officeSelection);
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

    setLanguage();
  }

  public List<String> holder = new ArrayList<String>();

  @FXML
  void setLanguage() throws Exception {
    if (language_choice == 0) {
      holder = CApp.Office_Supply_English_list;
    } else if (language_choice == 1) {
      // holder = CApp.Home_Spanish_list;
    } else if (language_choice == 2) {
      holder = CApp.Office_Supply_Chinese_list;
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
    supplyAmount.setPromptText(holder.get(9));
    specialRequest.setPromptText(holder.get(10));
    startTime.setPromptText(holder.get(11));
    employeeName.setPromptText(holder.get(12));
    Submit.setText(holder.get(13));
    Clear.setText(holder.get(14));
    Cancel.setText(holder.get(15));

    servicechoice1.setText(LanguageSet(servicechoice1.getText()));
    servicechoice2.setText(LanguageSet(servicechoice2.getText()));
    servicechoice3.setText(LanguageSet(servicechoice3.getText()));
    servicechoice4.setText(LanguageSet(servicechoice4.getText()));

    //        notEnglish = true;
  }

  @FXML private Text Title;
  @FXML private TextField Box1;
  @FXML private TextField Box2;
  @FXML private TextField Box3;
  @FXML private TextField Box4;
  @FXML private TextField Box5;

  @FXML private MFXButton Submit;
  @FXML private MFXButton Clear;
  @FXML private MFXButton Cancel;

  public TranslatorAPI translatorAPI = new TranslatorAPI();

  @FXML
  String LanguageSet(String text) throws Exception {
    if (text == null) {
      return "";
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
