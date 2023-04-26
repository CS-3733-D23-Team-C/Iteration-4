package edu.wpi.teamc.controllers.pages.patient;

import static edu.wpi.teamc.alertHelpers.alertHelper.firstTime;

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

  private boolean WarningOut;
  private boolean FirstOut;

  @FXML
  void initialize() {
    //      setLanguage(language_choice);
    if (firstTime == false) {
      FirstWarning();
      firstTime = true;
    } else if (firstTime == true) {

    }
  }

  @FXML
  void getWarning(MouseEvent event) {
    if (FirstOut == true) {
      killWarning();
      FirstOut = false;
    } else if (FirstOut == false) {

      if (WarningOut == false) {
        moveWarning();
        WarningOut = true;
      } else if (WarningOut == true) {
        killWarning();
        WarningOut = false;
      }
    }
  }

  @FXML
  public void moveWarning() {
    TranslateTransition tranOut = new TranslateTransition();
    tranOut.setNode(warningPopOut);
    tranOut.setByY(326);
    tranOut.play();
  }

  @FXML
  public void killWarning() {
    TranslateTransition tranBack = new TranslateTransition();
    tranBack.setNode(warningPopOut);
    tranBack.setByY(-326);
    tranBack.play();
  }

  @FXML
  public void FirstWarning() {
    TranslateTransition tranFirst = new TranslateTransition();
    tranFirst.setNode(warningPopOut);
    Timeline T1st =
        new Timeline(
            new KeyFrame(
                Duration.millis(0),
                ae -> {
                  moveWarning();
                  FirstOut = true;
                }),
            new KeyFrame(
                Duration.millis(3000),
                ae -> {
                  if (FirstOut == true) {
                    killWarning();
                    FirstOut = false;
                  }
                }));

    T1st.setCycleCount(1);
    T1st.play();
  }

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
