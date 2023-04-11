package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.controllers.AbsAdminMenuController;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class AdminMenuTestController extends AbsAdminMenuController {

  @FXML private MFXButton serviceRequest;
  @FXML private AnchorPane navList;

  @FXML
  public void getService(ActionEvent event) {}

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

  /*private void prepareSlideMenuAnimation() {
    TranslateTransition openNav = new TranslateTransition(new Duration(350), navList);
    openNav.setToX(0);
    TranslateTransition closeNav = new TranslateTransition(new Duration(350), navList);
    serviceRequest.setOnAction(
        (ActionEvent evt) -> {
          if (navList.getTranslateX() != 0) {
            openNav.play();
          } else {
            closeNav.setToX(-(navList.getWidth()));
            closeNav.play();
          }
        });
  }*/
}
