package edu.wpi.teamc.controllers.pages.patient;

import java.awt.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.util.Duration;

public class PatientHomeController {

  private String filePath;
  private Desktop desktop = Desktop.getDesktop();
  // @FXML private AdminMenuController adminMenuController;

  @FXML private HTMLEditor patientWeather;
  @FXML private ImageView English_flag;
  @FXML private ImageView Spanish_flag;
  @FXML private Text PatientHome_Title;
  @FXML private javafx.scene.control.Button logoutButton;
  @FXML private Button exitButton;
  @FXML private MenuButton ServiceRequest;
  @FXML private MenuButton NavigationButton;
  @FXML private MenuButton Settings;
  @FXML private MenuButton Help;
  @FXML private javafx.scene.image.ImageView guest_warning;
  @FXML private Pane warningPopOut;
  @FXML private Text warning_words;

  @FXML
  void getWarning(MouseEvent event) {
    // PatientHome_Title.setText("Warning");
    moveWarning();
    // PatientHome_Title.setText("Warning");
  }

  @FXML
  public void moveWarning() {
    TranslateTransition tran = new TranslateTransition();
    tran.setNode(warningPopOut);
    Timeline t1 =
        new Timeline(
            new KeyFrame(
                Duration.millis(10),
                ae -> {
                  warningPopOut.toFront();
                  tran.setByY(250);
                  tran.play();
                }),
            new KeyFrame(
                Duration.millis(3000),
                ae -> {
                  tran.setByY(-250);
                  tran.play();
                }),
            new KeyFrame(
                Duration.millis(3800),
                ae -> {
                  warningPopOut.toBack();
                }));

    t1.setCycleCount(1);
    t1.play();
  }

  // @FXML
  //  void recieveTextMessages(){
  //    String name = HOME_username.getText();
  //    String number = HOME_phonenumber.getText();
  //    if(validPatient(name, number)){
  //      //send text message
  //    }
  //  }

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
}
