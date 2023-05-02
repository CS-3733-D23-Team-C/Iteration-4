package edu.wpi.teamc.controllers.pages;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CongratsSignUpController {
  @FXML private Button mainMenuButton;

  @FXML



  public void getMainMenu() {
    mainMenuButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
