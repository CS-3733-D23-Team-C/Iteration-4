package edu.wpi.teamc.controllers.pages.map;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

/** */
public class SignageController {
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  //  public void backAction(ActionEvent actionEvent) {}
  //  // to use this way, have "backAction" under onAction in .fxml file
}