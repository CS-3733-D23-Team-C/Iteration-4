package edu.wpi.teamc.controllers.pages;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignUpController {

  @FXML private Button backButton;

  @FXML private TextField name;

  @FXML private TextField phoneNumber;

  @FXML private Button submitButton;

  @FXML
  void getBack(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void getSubmit(ActionEvent event) {}
}
