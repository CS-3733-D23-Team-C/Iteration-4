package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginController {

  @FXML private Button GuestPage;

  @FXML private Button AdminPage;

  @FXML private Button clear;

  @FXML
  void getAdminPage(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void getGuestPage(ActionEvent event) {
    Navigation.navigate(Screen.GUEST_HOME);
  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.LOGIN);
  }

  @FXML
  public void getExit(ActionEvent actionEvent) {
    Navigation.navigate(Screen.EXIT_PAGE);
  }
}
