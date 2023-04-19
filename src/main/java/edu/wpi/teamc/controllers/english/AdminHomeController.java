package edu.wpi.teamc.controllers.english;

import java.awt.*;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.text.*;
import javax.swing.text.html.ImageView;

public class AdminHomeController {

  private String filePath;
  private Desktop desktop = Desktop.getDesktop();
  // @FXML private AdminMenuController adminMenuController;

  @FXML private ImageView English_flag;
  @FXML private ImageView Spanish_flag;
  @FXML private Text AdminHome_Title;
  @FXML private javafx.scene.control.Button logoutButton;
  @FXML private Button exitButton;
  @FXML private MenuButton ServiceRequest;
  @FXML private MenuButton NavigationButton;
  @FXML private MenuButton Settings;
  @FXML private MenuButton Help;
  //  Login login;

  // public void initialize() {
  //  LoginDao loginDao = new LoginDao();
  //    try {
  //      login = loginDao.;
  //    }

  @FXML
  public void initialize() {
    //      setLanguage(language_choice);
  }

  // LANGUAGE//
  @FXML
  void english() {
    //      language_choice = 0;
    //      setLanguage(language_choice);
  }

  @FXML
  void spanish() {
    //      language_choice = 1;
    //      setLanguage(language_choice);
  }

  @FXML
  void setLanguage(int language) {
    //      // this.language_choice = language;
    //      if (language == 0) { // 0 is english
    //        AdminHome_Title.setText("Admin Home Page");
    //        ServiceRequest.setText("Service Request");
    //        NavigationButton.setText("Navigation");
    //        Settings.setText("Settings");
    //        Help.setText("Help");
    //        logoutButton.setText("Log Out");
    //        exitButton.setText("Exit");
    //      } else if (language == 1) { // 1 is spanish
    //        AdminHome_Title.setText(
    //            "P" + "\u00E1" + "gina de inicio de Admin"); // "\u00E1" is a in spanish for UTF-8
    // //
    //        ServiceRequest.setText("Servicio");
    //        NavigationButton.setText("Navegaci" + "\u00F3" + "n"); // "\u00F3" is o in spanish for
    //   UTF-8
    //        Settings.setText("Configuraci" + "\u00F3" + "n"); // "\u00F3" is o in spanish for
    // UTF-8
    //        Help.setText("Ayuda");
    //        logoutButton.setText("Cerrar Sesi" + "\u00F3" + "n"); // "\u00F3" is o in spanish for
    //   UTF-8
    //        exitButton.setText("Salir");
    //      }
  }
}
