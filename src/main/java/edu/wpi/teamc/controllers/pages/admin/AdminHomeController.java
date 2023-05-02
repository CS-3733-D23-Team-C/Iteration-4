package edu.wpi.teamc.controllers.pages.admin;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.displays.Alert;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.languageHelpers.TranslatorAPI;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

public class AdminHomeController {

  private String filePath;
  private Desktop desktop = Desktop.getDesktop();
  // @FXML private AdminMenuController adminMenuController;

  @FXML private AnchorPane Admin_Home_AnchorPane;
  @FXML private HTMLEditor guestWeather;
  @FXML private ImageView English_flag;
  @FXML private ImageView Spanish_flag;
  // ALL TEXT//
  @FXML private Text AdminHome_Title;
  @FXML private VBox notificationVBox;
  @FXML private VBox TODOVBox;
  @FXML private MFXScrollPane notificationBox;
  @FXML private MFXScrollPane TODOBox;

  @FXML private TextField weather_title;
  @FXML private TextField notifications_title;
  // public void initialize() {
  //  LoginDao loginDao = new LoginDao();
  //    try {
  //      login = loginDao.;
  //    }

  RequestSystem requestSystem = null;
  SVGImage img = SVGLoader.load("http://www.w3.org/2000/svg");

  public int shiftlines(String s) {
    int count = s.length();
    int lines = count / 56;
    return lines;
  }

  @FXML
  public void BorderColor(String type, HBox hBox) {
    switch (type) {
      case "Weather":
        hBox.setStyle(
            "-fx-background-color: #ffffff;-fx-border-color: blue;-fx-border-width: 4; -fx-max-width:650; -fx-padding: 10;"
                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
        break;
      case "Construction":
        hBox.setStyle(
            "-fx-background-color: #ffffff;-fx-border-color: orange;-fx-border-width: 4; -fx-max-width:650; -fx-padding: 10;"
                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
        break;
      case "Emergency":
        hBox.setStyle(
            "-fx-background-color: #ffffff;-fx-border-color: red;-fx-border-width: 4; -fx-max-width:650; -fx-padding: 10;"
                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
        break;
      case "Car Crash":
        hBox.setStyle(
            "-fx-background-color: #ffffff;-fx-border-color: green;-fx-border-width: 4; -fx-max-width:650; -fx-padding: 10;"
                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
        break;
      case "Closures":
        hBox.setStyle(
            "-fx-background-color: #ffffff;-fx-border-color: purple;-fx-border-width: 4; -fx-max-width:650; -fx-padding: 10;"
                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
        break;
      case "Other":
        hBox.setStyle(
            "-fx-background-color: #ffffff;-fx-border-color: black;-fx-border-width: 4; -fx-max-width:650; -fx-padding: 10;"
                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");
        break;
    }
  }

  @FXML
  public void initialize() throws Exception {
    this.initNotification();
    this.initTodo();
    if (!CApp.getAdminLoginCheck()) {
      AdminHome_Title.setText("Staff Home Page");
    }
    setLanguage();
  }

  private void initTodo() {
    TODOVBox.setAlignment(Pos.TOP_CENTER);
    TODOVBox.setSpacing(20);
    TODOVBox.setMinWidth(TODOBox.getWidth());
    TODOVBox.setStyle("-fx-padding: 20");
    final List<AbsServiceRequest>[] filteredList = new ArrayList[1];

    Thread thread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                try {
                  requestSystem = new RequestSystem();
                  filteredList[0] =
                      requestSystem.filterRequest(CApp.getCurrLogin(), STATUS.PENDING);
                } catch (Exception e) {
                  e.printStackTrace();
                }
                for (AbsServiceRequest request : filteredList[0]) {
                  this.addTask(request);
                }
              }

              private void addTask(AbsServiceRequest request) {

                Platform.runLater(
                    new Runnable() {
                      @Override
                      public void run() {
                        // for each request in the list, create a box with the title and description
                        Text title = new Text("");
                        if (request instanceof ConferenceRoomRequest) {
                          title = new Text("Conference Room Request");
                        } else if (request instanceof FlowerDeliveryRequest) {
                          title = new Text("Flower Delivery Request");
                        } else if (request instanceof FurnitureDeliveryRequest) {
                          title = new Text("Furniture Delivery Request");
                        } else if (request instanceof GiftBasketRequest) {
                          title = new Text("Gift Basket Request");
                        } else if (request instanceof MealRequest) {
                          title = new Text("Meal Request");
                        } else if (request instanceof OfficeSuppliesRequest) {
                          title = new Text("Office Supplies Request");
                        }
                        Text desc =
                            new Text(
                                request.getRequestID() + " | " + request.getRequester().getName());

                        HBox hBox = new HBox();
                        HBox container = new HBox();
                        VBox vBox = new VBox();

                        hBox.setMaxHeight(shiftlines(title.getText()) * 45);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.setSpacing(20);
                        container.setSpacing(20);
                        hBox.setStyle(
                            "-fx-background-color: #ffffff;-fx-border-width: 1; -fx-max-width:650; -fx-padding: 10;"
                                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");

                        title.setFont(Font.font("Arial", FontWeight.BOLD, 25));
                        desc.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
                        //      Image img = choosePNG(type);
                        //      javafx.scene.image.ImageView imgView = new
                        // javafx.scene.image.ImageView(img);
                        //      hBox.getChildren().add(imgView);
                        vBox.getChildren().add(title);
                        vBox.getChildren().add(desc);
                        hBox.getChildren().add(vBox);
                        container.getChildren().add(hBox);
                        BorderColor("Other", container);
                        TODOVBox.getChildren().add(0, container);
                      }
                    });
              }
            });
    thread.start();
  }

  @FXML
  public void initNotification() {
    notificationVBox.setAlignment(Pos.TOP_CENTER);
    notificationVBox.setSpacing(20);
    notificationVBox.setMinWidth(notificationBox.getWidth());
    final List<Alert>[] alertList = new ArrayList[1];
    Thread thread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                try {
                  alertList[0] = (List<Alert>) HospitalSystem.fetchAllObjects(new Alert());
                } catch (Exception e) {
                  e.printStackTrace();
                }

                for (Alert alert : alertList[0]) {
                  if (language_choice == 0) { // English
                    try {
                      this.addNotification(
                          alert.getTitle(), alert.getDescription(), alert.getType());
                    } catch (IOException e) {
                      throw new RuntimeException(e);
                    }
                  } else {
                    try {
                      this.addNotification(
                          LanguageSet(alert.getTitle()),
                          LanguageSet(alert.getDescription()),
                          alert.getType());
                    } catch (Exception e) {
                      throw new RuntimeException(e);
                    }
                  }
                }
              }

              public void addNotification(String notification, String description, String type)
                  throws IOException {

                Platform.runLater(
                    new Runnable() {
                      @Override
                      public void run() {
                        HBox hBox = new HBox();
                        HBox container = new HBox();
                        VBox vBox = new VBox();
                        Text title = new Text(notification);
                        Text desc = new Text(description);

                        hBox.setMaxHeight(shiftlines(title.getText()) * 45);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.setSpacing(20);
                        container.setSpacing(20);
                        hBox.setStyle(
                            "-fx-background-color: #ffffff;-fx-border-width: 1; -fx-max-width:650; -fx-padding: 5 10 5 10;"
                                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-background-insets: 0, 1; -fx-border-insets: 0, 1; ");

                        title.setFont(Font.font("Arial", FontWeight.BOLD, 25));
                        desc.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
                        Image img = null;
                        try {
                          img = choosePNG(type);
                        } catch (IOException e) {
                          throw new RuntimeException(e);
                        }
                        javafx.scene.image.ImageView imgView =
                            new javafx.scene.image.ImageView(img);
                        hBox.getChildren().add(imgView);
                        title.setWrappingWidth(550);
                        desc.setWrappingWidth(550);
                        vBox.getChildren().add(title);
                        vBox.getChildren().add(desc);
                        hBox.getChildren().add(vBox);
                        container.getChildren().add(hBox);
                        BorderColor(type, container);
                        notificationVBox.getChildren().add(0, container);
                      }
                    });
              }
            });
    thread.start();
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
  void setLanguage() throws Exception {
    if (language_choice == 0) { // 0 is english
    } else {
      AdminHome_Title.setText(LanguageSet(AdminHome_Title.getText()));
      weather_title.setText(LanguageSet(weather_title.getText()));
      notifications_title.setText(LanguageSet(notifications_title.getText()));
    }
  }

  // TRANSLATOR//
  public TranslatorAPI translatorAPI = new TranslatorAPI();

  @FXML
  String LanguageSet(String text) throws Exception {
    if (text == null) {
      return null;
    }
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
