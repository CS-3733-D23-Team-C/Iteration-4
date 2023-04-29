package edu.wpi.teamc.controllers.pages.guest;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.displays.Alert;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javax.swing.text.html.ImageView;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;

public class GuestHomeController {

  private String filePath;
  private Desktop desktop = Desktop.getDesktop();
  // @FXML private AdminMenuController adminMenuController;

  @FXML private HTMLEditor guestWeather;
  @FXML private ImageView English_flag;
  @FXML private ImageView Spanish_flag;
  @FXML private Text GuestHome_Title;
  @FXML private javafx.scene.control.Button logoutButton;
  @FXML private Button exitButton;
  @FXML private MenuButton ServiceRequest;
  @FXML private MenuButton NavigationButton;
  @FXML private MenuButton Settings;
  @FXML private MenuButton Help;
  @FXML private VBox notificationVBox;
  @FXML private MFXScrollPane notificationBox;

  @FXML private javafx.scene.control.TextField weather_title;
  @FXML private javafx.scene.control.TextField notifications_title;
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
        "-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1; -fx-max-width:1000; -fx-padding: 10;"
            + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
    // hBox.prefHeightProperty().bind(text.heightProperty());
    // text.maxHeight(shiftlines(text.getText()) * 60);

    text.setFont(Font.font("Arial", FontWeight.BOLD, 25));
    //    text.setMinWidth(notificationBox.getWidth());
    text.setText(notification);
    javafx.scene.image.Image img = choosePNG(type);
    javafx.scene.image.ImageView imgView = new javafx.scene.image.ImageView(img);
    hBox.getChildren().add(imgView);
    hBox.getChildren().add(text);
    notificationVBox.getChildren().add(hBox);
  }

  @FXML
  public void initialize() throws IOException {
    setLanguage(language_choice);
    notificationVBox.setAlignment(Pos.TOP_CENTER);
    notificationVBox.setSpacing(20);
    notificationVBox.setMinWidth(notificationBox.getWidth());

    java.util.List<Alert> alertList = (List<Alert>) HospitalSystem.fetchAllObjects(new Alert());
    for (Alert alert : alertList) {
      if (alert.getDescription() == null) {
        addNotification(alert.getTitle(), alert.getType());
      } else {
        addNotification(
            alert.getTitle() + " \nDescription: " + alert.getDescription(), alert.getType());
      }
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
