package edu.wpi.teamc.controllers.english;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;
import edu.wpi.teamc.CApp;

import java.awt.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.*;
import javafx.scene.web.HTMLEditor;
import javax.swing.text.html.ImageView;

public class AdminHomeController {

  private String filePath;
  private Desktop desktop = Desktop.getDesktop();
  // @FXML private AdminMenuController adminMenuController;

  @FXML private HTMLEditor guestWeather;
  @FXML private ImageView English_flag;
  @FXML private ImageView Spanish_flag;
  // ALL TEXT//
  @FXML private Text AdminHome_Title;

  @FXML private TextField weather_title;
  @FXML private TextField notifications_title;

  //  Login login;

  // public void initialize() {
  //  LoginDao loginDao = new LoginDao();
  //    try {
  //      login = loginDao.;
  //    }

  @FXML
  public void initialize() {
    if (!CApp.getAdminLoginCheck()) {
      AdminHome_Title.setText("Staff Home Page");
      }
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

  @FXML
  void setLanguage(int language) {
    // this.language_choice = language;
    if (language == 0) { // 0 is english
      if (!CApp.getAdminLoginCheck()) {
      AdminHome_Title.setText("Staff Home Page");
      }
      else{
      AdminHome_Title.setText("Admin Home Page");
      }
      weather_title.setText("Current Weather Forcast");
      notifications_title.setText("Notifications");

    } else if (language == 1) { // 1 is spanish
      AdminHome_Title.setText(
          "P" + "\u00E1" + "gina de inicio de Admin"); // "\u00E1" is a in spanish for UTF-8
      weather_title.setText("Pron" + "\u00F3" + "stico del tiempo actual");
      notifications_title.setText("Notificaciones");
    }
  }
}
