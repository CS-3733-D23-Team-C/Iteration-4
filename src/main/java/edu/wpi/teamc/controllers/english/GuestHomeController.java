package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.web.HTMLEditor;
import javax.swing.text.html.ImageView;

public class GuestHomeController {

  @FXML private HTMLEditor weather;
  @FXML
  public void getLogout(ActionEvent actionEvent) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  public void getExit(ActionEvent actionEvent) {
    Navigation.navigate(Screen.EXIT_PAGE);
  }

  @FXML
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  public void initialize() {}
}
