package edu.wpi.teamc.controllers.pages;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CongratsController {
  @FXML private Button mainMenuButton;
  @FXML private Text Congrats;
  @FXML private Text Entered;

  @FXML
  public void getMainMenu() {
    mainMenuButton.setOnMouseClicked(event -> Navigation.navigate(Screen.ADMIN_HOME));
  }

  @FXML
  public void initialize() {
    setlanguage();
  }

  public List<String> holder = new ArrayList<String>();

  void setlanguage() {
    if (language_choice == 0) {
      holder = CApp.Congrats_English_list;
    } else if (language_choice == 1) {
      // holder = CApp.Home_Spanish_list;
    } else if (language_choice == 2) {
      holder = CApp.Congrats_Chinese_list;
    }

    Congrats.setText(holder.get(0));
    Entered.setText(holder.get(1));
    mainMenuButton.setText(holder.get(2));
  }
}
