package edu.wpi.teamc.controllers.pages.requests;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.map.LocationNameDao;
import edu.wpi.teamc.dao.requests.GiftBasketRequest;
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
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.controlsfx.control.SearchableComboBox;

public class GiftBasketRequestController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  @FXML private AnchorPane assignEmployeeAnchor;
  @FXML private TextArea basketItems;
  @FXML private DatePicker deliveryTime;
  @FXML private SearchableComboBox<EmployeeUser> employeeName;
  @FXML private ImageView image;
  @FXML private TextField nameBox;
  @FXML private SearchableComboBox<LocationName> roomMenu;
  @FXML private MenuButton serviceMenu;
  @FXML private MenuItem servicechoice1;
  @FXML private MenuItem servicechoice2;
  @FXML private MenuItem servicechoice3;
  @FXML private MenuItem servicechoice4;
  @FXML private TextArea specialRequest;
  int BasketSelection = 0;

  @FXML
  void getServicechoice1() {
    serviceMenu.setText(servicechoice1.getText());
    BasketSelection = 1;
    // getImage(1);
  }

  @FXML
  void getServicechoice2() {
    serviceMenu.setText(servicechoice2.getText());
    BasketSelection = 2;
    // getImage(2);
  }

  @FXML
  void getServicechoice3() {
    serviceMenu.setText(servicechoice3.getText());
    BasketSelection = 3;
    // getImage(3);
  }

  @FXML
  void getServicechoice4() {
    serviceMenu.setText(servicechoice4.getText());
    BasketSelection = 4;
    // getImage(4);
  }

  @FXML
  String getBasketSelection(int BasketSelection) {
    switch (BasketSelection) {
      case 1:
        return "Small";
      case 2:
        return "Medium";
      case 3:
        return "Large";
      case 4:
        return "Extra Large";
      default:
        return "No Basket Selected";
    }
  }

  @FXML
  public void getGoHome() {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getClear() {
    Navigation.navigate(Screen.GIFT_BASKET);
  }

  @FXML
  void getSubmit() {
    String name = nameBox.getText();
    String room = roomMenu.getValue().toString();
    String special = specialRequest.getText();
    String giftbasket = getBasketSelection(BasketSelection);
    String eta = deliveryTime.getValue().toString();
    GiftBasketRequest req =
        new GiftBasketRequest(new PatientUser(name), special, giftbasket, eta, room);
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
  void getImage(int choice) throws IOException {
    switch (choice) {
      case 1:
        image.setImage(
            new Image(
                Main.class
                    .getResource("views/images/GiftBasket/small-gift-basket.png")
                    .openStream()));
        break;
      case 2:
        image.setImage(
            new Image(
                Main.class
                    .getResource("views/images/GiftBasket/medium-gift-basket.png")
                    .openStream()));
        break;
      case 3:
        image.setImage(
            new Image(
                Main.class
                    .getResource("views/images/GiftBasket/large-gift-basket.png")
                    .openStream()));
        break;
      case 4:
        image.setImage(
            new Image(
                Main.class
                    .getResource("views/images/GiftBasket/extra-large-gift-basket.png")
                    .openStream()));
        break;
    }
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() throws Exception {
    LocationNameDao locationNameDao = new LocationNameDao();
    List<LocationName> locationNames = (List<LocationName>) locationNameDao.fetchAllObjects();
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

    setLanguage();
  }

  //  @FXML
  //  void getMapPage(ActionEvent event) {
  //    Navigation.navigate(Screen.FLOOR_PLAN);
  //  }

  public List<String> holder = new ArrayList<String>();

  @FXML
  void setLanguage() throws Exception {
    if (language_choice == 0) {
      holder = CApp.Gift_Basket_English_list;
    } else if (language_choice == 1) {
      // holder = CApp.Home_Spanish_list;
    } else if (language_choice == 2) {
      holder = CApp.Gift_Basket_Chinese_list;
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
    basketItems.setPromptText(holder.get(9));
    specialRequest.setPromptText(holder.get(10));
    deliveryTime.setPromptText(holder.get(11));
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
