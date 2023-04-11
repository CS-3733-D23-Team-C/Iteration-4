package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GuestHomeController {

  @FXML
  public void getLogout(ActionEvent actionEvent) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  public void getExit(ActionEvent actionEvent) {
    System.exit(0);
  }

  @FXML
  public void initialize() {}
}
