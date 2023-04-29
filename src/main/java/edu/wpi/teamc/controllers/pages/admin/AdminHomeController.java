package edu.wpi.teamc.controllers.pages.admin;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.displays.Alert;
import edu.wpi.teamc.languageHelpers.TranslatorAPI;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javax.swing.text.html.ImageView;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;

public class AdminHomeController {

  private String filePath;
  private Desktop desktop = Desktop.getDesktop();
  // @FXML private AdminMenuController adminMenuController;

  public TranslatorAPI translatorAPI = new TranslatorAPI();

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

  SVGImage img = SVGLoader.load("http://www.w3.org/2000/svg");

  public int shiftlines(String s) {
    int count = s.length();
    int lines = count / 56;
    return lines;
  }

  public void addNotification(String notification, String type) throws IOException {

    HBox hBox = new HBox();
    Text text = new Text(notification);

    hBox.setMaxHeight(shiftlines(text.getText()) * 45);
    hBox.setAlignment(Pos.CENTER_LEFT);
    hBox.setSpacing(50);
    hBox.setStyle(
        "-fx-background-color: #ffffff;-fx-border-width: 1; -fx-max-width:650; -fx-padding: 10;"
            + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");

    text.setFont(Font.font("Arial", FontWeight.BOLD, 25));
    text.setText(notification);
    Image img = choosePNG(type);
    javafx.scene.image.ImageView imgView = new javafx.scene.image.ImageView(img);
    hBox.getChildren().add(imgView);
    text.setWrappingWidth(550);
    hBox.getChildren().add(text);
    BorderColor(type, hBox);

    notificationVBox.getChildren().add(0, hBox);
  }

  @FXML
  public void BorderColor(String type, HBox hBox) {
    switch (type) {
      case "Weather":
        hBox.setStyle(
            "-fx-background-color: #ffffff;-fx-border-color: blue;-fx-border-width: 2; -fx-max-width:650; -fx-padding: 10;"
                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
        break;
      case "Construction":
        hBox.setStyle(
            "-fx-background-color: #ffffff;-fx-border-color: orange;-fx-border-width: 2; -fx-max-width:650; -fx-padding: 10;"
                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
        break;
      case "Emergency":
        hBox.setStyle(
            "-fx-background-color: #ffffff;-fx-border-color: red;-fx-border-width: 2; -fx-max-width:650; -fx-padding: 10;"
                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
        break;
      case "Car Crash":
        hBox.setStyle(
            "-fx-background-color: #ffffff;-fx-border-color: green;-fx-border-width: 2; -fx-max-width:650; -fx-padding: 10;"
                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
        break;
      case "Closures":
        hBox.setStyle(
            "-fx-background-color: #ffffff;-fx-border-color: purple;-fx-border-width: 2; -fx-max-width:650; -fx-padding: 10;"
                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
        break;
      case "Other":
        hBox.setStyle(
            "-fx-background-color: #ffffff;-fx-border-color: black;-fx-border-width: 2; -fx-max-width:650; -fx-padding: 10;"
                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
        break;
    }
  }

  @FXML
  public void initialize() throws Exception {
    setLanguage(language_choice);
    notificationVBox.setAlignment(Pos.TOP_CENTER);
    notificationVBox.setSpacing(20);
    notificationVBox.setMinWidth(notificationBox.getWidth());

    java.util.List<Alert> alertList = (List<Alert>) HospitalSystem.fetchAllObjects(new Alert());
    for (Alert alert : alertList) {
      if (language_choice == 0) { // English
        if (alert.getDescription() == null) {
          addNotification(alert.getTitle(), alert.getType());
        } else {
          addNotification(
              alert.getTitle() + " \nDescription: " + alert.getDescription(), alert.getType());
        }
      } else if (language_choice == 1) { // Spanish
        if (alert.getDescription() == null) {
          addNotification(LanguageSet(alert.getTitle()), alert.getType());
        } else {
          addNotification(
              LanguageSet(alert.getTitle() + " \nDescription: " + alert.getDescription()),
              alert.getType());
        }
      }
    }
    if (!CApp.getAdminLoginCheck()) {
      AdminHome_Title.setText("Staff Home Page");
    }
  }

  // SVG Function for Notification//
  @FXML
  public javafx.scene.image.Image choosePNG(String type) throws IOException {
    Image holder;
    switch (type) {
      case "Weather":
        holder =
            (new javafx.scene.image.Image(
                Main.class.getResource("views/images/AlertIcons/img_5.png").openStream()));
        break;
      case "Construction":
        holder =
            (new javafx.scene.image.Image(
                Main.class.getResource("views/images/AlertIcons/img_2.png").openStream()));
        break;
      case "Car Crash":
        holder =
            (new javafx.scene.image.Image(
                Main.class.getResource("views/images/AlertIcons/img_1.png").openStream()));
        break;
      case "Closures":
        holder =
            (new javafx.scene.image.Image(
                Main.class.getResource("views/images/AlertIcons/img.png").openStream()));
        break;
      case "Emergency":
        holder =
            (new javafx.scene.image.Image(
                Main.class.getResource("views/images/AlertIcons/img_4.png").openStream()));
        break;
      case "Other":
        holder =
            (new javafx.scene.image.Image(
                Main.class.getResource("views/images/AlertIcons/img_3.png").openStream()));
        break;
      default:
        holder = (new javafx.scene.image.Image(Main.class.getResource("").openStream()));
    }
    return holder;
  }

  // LANGUAGE//
  @FXML
  void english() throws Exception {
    language_choice = 0;
    setLanguage(language_choice);
  }

  @FXML
  void spanish() throws Exception {
    language_choice = 1;
    setLanguage(language_choice);
  }

  @FXML
  void setLanguage(int language) throws Exception {
    // this.language_choice = language;
    if (language == 0) { // 0 is english

      AdminHome_Title.setText(AdminHome_Title.getText());

      weather_title.setText(weather_title.getText());
      notifications_title.setText(notifications_title.getText());

    } else if (language == 1) { // 1 is spanish
      AdminHome_Title.setText(
          LanguageSet(AdminHome_Title.getText())); // "\u00E1" is a in spanish for UTF-8
      weather_title.setText(LanguageSet(weather_title.getText()));
      notifications_title.setText(LanguageSet(notifications_title.getText()));
    }
  }

  @FXML
  String LanguageSet(String text) throws Exception {
    if (language_choice == 0) { // 0 is english
      text = translatorAPI.translateToEn(text);
    } else if (language_choice == 1) { // 1 is spanish
      text = translatorAPI.translateToSp(text);
    } else if (language_choice == 2) { // 2 is Chinese
      text = translatorAPI.translateToZh(text);
    }
    return text;
  }
}
