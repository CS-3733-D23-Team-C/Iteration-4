package edu.wpi.teamc.controllers.pages.admin;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.displays.Alert;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import java.awt.*;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.text.Font;
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
  @FXML private VBox notificationVBox;
  @FXML private MFXScrollPane notificationBox;

  @FXML private TextField weather_title;
  @FXML private TextField notifications_title;

  //  Login login;

  // public void initialize() {
  //  LoginDao loginDao = new LoginDao();
  //    try {
  //      login = loginDao.;
  //    }

  public int shiftlines(String s) {
    int count = s.length();
    int lines = count / 56;
    return lines;
  }

  public void addNotification(String notification) {
    //    HBox hBox = new HBox();
    //    hBox.setMinHeight(0);
    //    hBox.setMinWidth(notificationBox.getWidth());
    //    hBox.setAlignment(Pos.TOP_CENTER);
    //    TextArea text = new TextArea(notification);
    //    //    text.minWidth(notificationVBox.getWidth());
    //    text.setEditable(false);
    //    text.setWrapText(true);
    //    text.setFont(Font.font("Arial", FontWeight.BOLD, 25));
    //    text.setMinWidth(notificationBox.getWidth());
    //    text.setText("• " + notification);
    //    hBox.getChildren().add(text);
    //    notificationVBox.getChildren().add(hBox);
    HBox hBox = new HBox();

    Text text = new Text(notification);
    hBox.setMaxHeight(shiftlines(text.getText()) * 45);
    hBox.setAlignment(Pos.TOP_LEFT);
    hBox.setStyle(
        "-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1; -fx-max-width:1000; -fx-padding: 10;"
            + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
    // hBox.prefHeightProperty().bind(text.heightProperty());
    // text.maxHeight(shiftlines(text.getText()) * 60);

    text.setFont(Font.font("Arial", FontWeight.BOLD, 25));
    //    text.setMinWidth(notificationBox.getWidth());
    text.setText(notification);

    hBox.getChildren().add(text);
    notificationVBox.getChildren().add(hBox);
  }

  @FXML
  public void initialize() {
    setLanguage(language_choice);
    notificationVBox.setAlignment(Pos.TOP_CENTER);
    notificationVBox.setSpacing(20);
    notificationVBox.setMinWidth(notificationBox.getWidth());

    java.util.List<Alert> alertList = (List<Alert>) HospitalSystem.fetchAllObjects(new Alert());
    for (Alert alert : alertList) {
      if (alert.getDescription() == null) {
        addNotification(alert.getTitle() + " \nType: " + alert.getType());
      } else {
        addNotification(
            alert.getTitle()
                + " \nType: "
                + alert.getType()
                + " \nDescription: "
                + alert.getDescription());
      }
    }
    if (!CApp.getAdminLoginCheck()) {
      AdminHome_Title.setText("Staff Home Page");
    }
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
      } else {
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
