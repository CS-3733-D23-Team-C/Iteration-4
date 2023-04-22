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

  @FXML
  void initialize() {
    //      setLanguage(language_choice);
    if (firstTime == false) {
      FirstWarning();
      firstTime = true;
    } else if (firstTime == true) {
      // moveWarning();
    }
  }

  @FXML
  void getWarning(MouseEvent event) {
    if (WarningOut == false) {
      moveWarning();
      WarningOut = true;
    } else if (WarningOut == true) {
      killWarning();
      WarningOut = false;
    }
  }

  @FXML
  public void moveWarning() {
    TranslateTransition tranOut = new TranslateTransition();
    tranOut.setNode(warningPopOut);
    //    Timeline tOut =
    //        new Timeline(
    //            new KeyFrame(
    //                Duration.millis(10),
    //                ae -> {
    // warningPopOut.toFront();
    tranOut.setByY(250);
    tranOut.play();
    //                }),
    //            new KeyFrame(
    //                Duration.millis(2010),
    //                ae -> {
    //                  // WarningOut = true;
    //                }));
    //    tOut.setCycleCount(1);
    //    tOut.play();
    // WarningOut = true;
  }

  @FXML
  public void killWarning() {
    TranslateTransition tranBack = new TranslateTransition();
    tranBack.setNode(warningPopOut);
    //    Timeline tBack =
    //        new Timeline(
    //            new KeyFrame(
    //                Duration.millis(10),
    //                ae -> {
    tranBack.setByY(-250);
    tranBack.play();
    //                }),
    //            new KeyFrame(
    //                Duration.millis(2010),
    //                ae -> {
    // warningPopOut.toBack();
    //                  // WarningOut = false;
    //                }));
    //
    //    tBack.setCycleCount(1);
    //    tBack.play();
    // WarningOut = false;
  }

  @FXML
  public void FirstWarning() {
    TranslateTransition tranFirst = new TranslateTransition();
    tranFirst.setNode(warningPopOut);
    Timeline T1st =
        new Timeline(
            new KeyFrame(
                Duration.millis(10),
                ae -> {
                  // warningPopOut.toFront();
                  tranFirst.setByY(250);
                  tranFirst.play();
                  // WarningOut = true;
                }),
            new KeyFrame(
                Duration.millis(3000),
                ae -> {
                  tranFirst.setByY(-250);
                  tranFirst.play();
                }),
            new KeyFrame(
                Duration.millis(3800),
                ae -> {
                  // warningPopOut.toBack();
                  // WarningOut = false;
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
