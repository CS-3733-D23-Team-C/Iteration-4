package edu.wpi.teamc.controllers.english;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;

public class AdminHomeController {

  private String filePath;
  private Desktop desktop = Desktop.getDesktop();

  @FXML MFXButton signageButton2;

  @FXML private Button flowerDeliveryPage;

  @FXML private Button furnitureDeliveryPage;

  @FXML private Button helpPage;

  @FXML private Button mealDeliveryPage;

  @FXML private Button officeSuppliesPage;

  @FXML private Button roomReservationPage;

  @FXML private Button signagePage;

  @FXML private Button logOut;
  @FXML private Button editMap;

  @FXML private Button mapHistory;

  @FXML private Button mapPage;

  @FXML private MenuButton importMenu;

  @FXML private Label testText;

  @FXML private ImageView English_flag;
  @FXML private ImageView Spanish_flag;
  @FXML private Text AdminHome_Title;
  @FXML private Button logoutButton;
  @FXML private Button exitButton;
  @FXML private MenuButton ServiceRequest;
  @FXML private MenuButton NavigationButton;
  @FXML private MenuButton Settings;
  @FXML private MenuButton Help;

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
  void getPathfindingPage(ActionEvent event) {
    Navigation.navigate(Screen.PATHFINDING_PAGE);
  }

  @FXML
  void getGiftBasketRequestPage(ActionEvent event) {
    Navigation.navigate(Screen.GIFT_BASKET);
  }

  @FXML
  void getExit(ActionEvent event) {
    Navigation.navigate(Screen.EXIT_PAGE);
  }

  /** Method run when controller is initialized */
  @FXML
  void getEditMap(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
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
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  public void initialize() {
    setLanguage(language_choice);
  }
  // LANGUAGE//
  @FXML
  void english() {
    language_choice = 0;
    setLanguage(language_choice);
  }

  @FXML
  void spanish() {
    language_choice = 1;
    setLanguage(language_choice);
  }

  void setLanguage(int language) {
    // this.language_choice = language;
    if (language == 0) { // 0 is english
      AdminHome_Title.setText("Admin Home Page");
      ServiceRequest.setText("Service Request");
      NavigationButton.setText("Navigation");
      Settings.setText("Settings");
      Help.setText("Help");
      logoutButton.setText("Log Out");
      exitButton.setText("Exit");
    } else if (language == 1) { // 1 is spanish
      AdminHome_Title.setText(
          "P" + "\u00E1" + "gina de inicio de Admin"); // "\u00E1" is a in spanish for UTF-8 //
      ServiceRequest.setText("Servicio");
      NavigationButton.setText("Navegaci" + "\u00F3" + "n"); // "\u00F3" is o in spanish for UTF-8
      Settings.setText("Configuraci" + "\u00F3" + "n"); // "\u00F3" is o in spanish for UTF-8
      Help.setText("Ayuda");
      logoutButton.setText("Cerrar Sesi" + "\u00F3" + "n"); // "\u00F3" is o in spanish for UTF-8
      exitButton.setText("Salir");
    }
  }
}
