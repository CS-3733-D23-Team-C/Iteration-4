package edu.wpi.teamc.controllers.pages;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.users.PERMISSIONS;
import edu.wpi.teamc.dao.users.login.Login;
import edu.wpi.teamc.dao.users.login.LoginDao;
import edu.wpi.teamc.languageHelpers.TranslatorAPI;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.controlsfx.control.ToggleSwitch;

public class HomeController {

  @FXML private MFXButton GuestPage;

  @FXML private MFXButton AdminPage;

  @FXML private MFXButton clear;

  @FXML private AnchorPane mainSignin;
  @FXML private Rectangle HOME_sqr;

  @FXML private ImageView English_flag;
  @FXML private ImageView Spanish_flag;
  @FXML private Text HOME_SignInText;
  @FXML private Text HOME_motto;
  @FXML private MFXTextField HOME_username;
  @FXML private MFXPasswordField HOME_password;
  @FXML private MFXButton HOME_login;
  @FXML private Hyperlink HOME_forgot;
  @FXML private Hyperlink HOME_create;
  @FXML private Text HOME_or;
  @FXML private Text wrongPass;
  @FXML private MFXButton HOME_guest;
  @FXML private MFXButton HOME_exit;
  @FXML private MFXButton HOME_next;
  @FXML private MFXButton HOME_back;
  @FXML private MFXTextField HOME_code;
  @FXML private AnchorPane aPane;
  @FXML ToggleSwitch dbToggleHome;
  @FXML private TextArea Warning_translation;

  boolean wrongNextLogin = true;
  Login currentLogin;

  public HomeController() throws IOException {}

  @FXML
  void getLoginNext(ActionEvent event) {
    Thread thread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                LoginDao loginDao = new LoginDao();
                wrongNextLogin = true;
                String username = HOME_username.getText();
                currentLogin = loginDao.fetchObject(username);
                Platform.runLater(
                    new Runnable() {
                      @Override
                      public void run() {
                        try {
                          if (currentLogin == null) {
                            wrongNextLogin = true;
                            wrongPass.setVisible(true);
                          } else {
                            wrongNextLogin = false;
                            wrongPass.setVisible(false);
                            if (currentLogin.checkPassword(HOME_password.getText())) {
                              if (currentLogin.isOTPEnabled()) {
                                HOME_username.setVisible(false);
                                HOME_password.setVisible(false);
                                HOME_next.setVisible(false);
                                HOME_back.setVisible(true);
                                HOME_login.setVisible(true);
                                HOME_code.setVisible(true);
                              } else {
                                getLogin(event);
                              }
                            } else {
                              wrongPass.setVisible(true);
                              wrongNextLogin = true;
                            }
                          }
                        } catch (Exception e) {
                          e.printStackTrace();
                        }
                      }
                    });
              }
            });
    thread.start();
  }

  @FXML
  void backToLogin(ActionEvent event) {
    HOME_login.setVisible(true);
    HOME_username.setVisible(true);
    HOME_password.setVisible(true);
    HOME_next.setVisible(true);
    HOME_code.setVisible(false);
    HOME_back.setVisible(false);
    HOME_code.setText("");
    HOME_password.setText("");
    wrongPass.setVisible(false);
  }

  @FXML
  void getLogin(ActionEvent event) {
    if (wrongNextLogin == false) {
      try {
        if ((currentLogin.checkOTP(HOME_code.getText())
            || !currentLogin.isOTPEnabled())) { // if the passwords are correct
          if (currentLogin.getPermissions().equals(PERMISSIONS.ADMIN) // if the user is an admin
              || currentLogin.getPermissions().equals(PERMISSIONS.STAFF)) { // or staff
            if (currentLogin
                .getPermissions()
                .equals(PERMISSIONS.ADMIN)) { // if the user is an admin
              CApp.setAdminLoginCheck(true);
            } else { // if the user is staff
              CApp.setAdminLoginCheck(false);
            }
            Navigation.navigate(Screen.ADMIN_HOME);
            Navigation.setMenuType(Navigation.MenuType.ADMIN);
            CApp.currScreen = Screen.ADMIN_HOME;
            CApp.setCurrLogin(currentLogin.getUsername());
          } else {
            // Show Error Message
            wrongPass.setVisible(true);
          }
        } else {
          // Show Error Message
          wrongPass.setVisible(true);
        }
      } catch (Exception e) {
        wrongPass.setVisible(true);
        e.printStackTrace();
      }
    }
  }

  @FXML
  void getGuest(ActionEvent event) {
    Navigation.navigate(Screen.GUEST_HOME);
    Navigation.setMenuType(Navigation.MenuType.GUEST);
    CApp.currScreen = Screen.GUEST_HOME;
  }

  @FXML
  void getSignUp(ActionEvent event) {
    Navigation.navigate(Screen.SIGNUP_PAGE);
  }

  public void getExit(ActionEvent actionEvent) {
    Navigation.navigate(Screen.EXIT_PAGE);
  }

  @FXML
  public void initialize(Stage primaryStage) throws Exception {
    CApp.setCurrLogin(null);

    //    try {
    language_choice = 0;
    setLanguage();
    //    aPane.setOnKeyPressed(event -> {
    //      event.ent
    //              aPane.setOnKeyPressed(EventHandler.create())
    //    HOME_next.setOnAction( ///////////////////
    //        event -> {
    //          getLoginNext(event);
    //        });
    //    ActionEvent event = new ActionEvent();
    //    HOME_next.setOnAction(getLoginNext2(event));

    //    HOME_next.setOnKeyPressed(
    //        e -> {
    //          if (e.getCode().equals(KeyCode.ENTER)) {
    //            System.out.println("firing continue button");
    //            //            ActionEvent event = (ActionEvent) HOME_next.getOnAction();
    //            //            HOME_next.fireEvent(event);
    //            HOME_next.fire();
    //          }
    //        });
    //    HOME_next.addKeyListener(new java.awt.event.KeyAdapter() {
    //      public void keyPressed(java.awt.event.KeyEvent evt) {
    //        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
    //          System.out.print("Your function call or code can go here");
    //        }
    //      }
    //    });
    //        aPane.addEventHandler(KeyEvent.KEY_PRESSED, [ev |
    //          if (ev.getSource() == KeyCode.ENTER) {
    //            ActionEvent actionEvent = new ActionEvent();
    //            actionEvent = (ActionEvent) ev;
    //            getLogin(actionEvent);
    //          }
    //        });
    aPane.addEventFilter(
        KeyEvent.ANY,
        keyEvent -> {
          System.out.println(keyEvent);
          if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            HOME_next.fire();
          }
        });
    //    aPane.setOnKeyPressed(
    //        event -> {
    //          System.out.println("supposed to fire button");
    //          if (event.getCode().equals(KeyCode.ENTER)) {
    //            HOME_next.fire();
    //          }
    //        });

    //    })
    //      AnchorPane root = mainSignin;
    //      Scene scene = new Scene(root, mainSignin.getPrefWidth(), mainSignin.getPrefHeight());
    //      primaryStage.setScene(scene);
    //      primaryStage.show();
    //      primaryStage.setResizable(true);
    //    } catch (Exception e) {
    //      e.printStackTrace();
    //    }
    // get wpidb boolean and set toggle accordingly
    dbToggleHome.setSelected(!CApp.getWpiDB());
    dbToggleHome.setOnMouseClicked(
        event -> {
          // Toggled sets boolean wpiDB false for AWS
          CApp.wpiDB = !dbToggleHome.selectedProperty().get();
          dbToggleHome.setSelected(!CApp.wpiDB);
          //          dbToggle.fire();
          System.out.println("DB: " + CApp.wpiDB);
          Navigation.navigate(CApp.currScreen);
        });
  }

  @FXML
  void english() throws Exception {
    if (language_choice != 0) {
      Warning_translation.setVisible(false);
    }
    language_choice = 0;
    //    Warning_translation.setPrefWidth(260);
    setLanguage();
  }

  @FXML
  void spanish() throws Exception {
    language_choice = 1;
    //    Warning_translation.setVisible(true);
    //    Warning_translation.setPrefWidth(380); // Spanish translation is too long!
    setLanguage();
  }

  @FXML
  void chinese() throws Exception {
    language_choice = 2;
    Warning_translation.setVisible(true);
    //    Warning_translation.setPrefWidth(260);
    setLanguage();
  }

  //  public List<String> allTexts = new List<String>();
  //  public List<String> allTextsTranslated = new List<String>();

  //  public void getAllTexts() {
  //    allTexts.add(HOME_SignInText.getText());
  //    allTexts.add(HOME_username.getPromptText());
  //    allTexts.add(HOME_password.getPromptText());
  //    allTexts.add(HOME_login.getText());
  //    allTexts.add(HOME_next.getText());
  //    allTexts.add(HOME_back.getText());
  //    allTexts.add(HOME_forgot.getText());
  //    allTexts.add(HOME_create.getText());
  //    allTexts.add(HOME_or.getText());
  //    allTexts.add(HOME_guest.getText());
  //    allTexts.add(HOME_exit.getText());
  //    allTexts.add(HOME_motto.getText());
  //    allTexts.add(Warning_translation.getText());
  //  }

  public List<String> holder = new ArrayList<String>();

  void setLanguage() {

    if (language_choice == 0) {
      holder = CApp.Home_English_list;
    } else if (language_choice == 1) {
      // holder = CApp.Home_Spanish_list;
    } else if (language_choice == 2) {
      holder = CApp.Home_Chinese_list;
    }
    HOME_SignInText.setText(holder.get(0));
    HOME_username.setPromptText(holder.get(1));
    HOME_password.setPromptText(holder.get(2));
    HOME_next.setText(holder.get(3));
    HOME_forgot.setText(holder.get(4));
    HOME_create.setText(holder.get(5));
    HOME_or.setText(holder.get(6));
    HOME_guest.setText(holder.get(7));
    HOME_exit.setText(holder.get(8));
    HOME_motto.setText(holder.get(9));
    Warning_translation.setText(holder.get(10));
  }

  public TranslatorAPI translatorAPI = new TranslatorAPI();

  @FXML
  void LanguageSet(String text) throws Exception {
    if (language_choice == 0) { // 0 is english
      text = translatorAPI.translateToEn(text);
    } else if (language_choice == 1) { // 1 is spanish
      text = translatorAPI.translateToSp(text);
    } else if (language_choice == 2) { // 2 is Chinese
      text = translatorAPI.translateToZh(text);
    }
  }

  @FXML
  public void resetPassword(ActionEvent event) {
    BorderPane borderPane = new BorderPane();

    Text prompt = new Text("Reset Password");
    Label usernameLabel = new Label("Username");
    TextField username = new TextField();
    TextField password = new TextField();
    MFXButton confirmButton = new MFXButton("Submit");
    MFXButton cancelButton = new MFXButton("Cancel");
    VBox vbox = new VBox();

    vbox.setSpacing(10);
    confirmButton.getStyleClass().add("MFXbutton");
    cancelButton.getStyleClass().add("MFXbutton");
    prompt.getStyleClass().add("Header");
    borderPane.getStyleClass().add("scenePane");
    usernameLabel.setLabelFor(username);
    usernameLabel.getStyleClass().add("whiteLabel");

    cancelButton.setOnAction(
        e -> {
          Stage stage = (Stage) cancelButton.getScene().getWindow();
          stage.close();
        });
    confirmButton.setOnAction(
        e -> {
          try (CloseableHttpClient client = HttpClients.createDefault()) {
            if (username.getText().isEmpty()) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Error");
              alert.setHeaderText("Error");
              alert.setContentText("Please enter a username.");
              alert.showAndWait();
              return;
            }
            // define website
            HttpPost httpPost = new HttpPost(new URI("http://localhost:3000/api/resetpassword"));
            String json = String.format("{\"username\":\"%s\"}", username.getText());
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            HttpResponse response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
              String responseBody = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
              System.out.println(responseBody);
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Password Reset");
              alert.setHeaderText("Password Reset");
              alert.setContentText(
                  "Success! Check your email associated with your account to reset your password.");
              alert.showAndWait();
              Stage stage = (Stage) confirmButton.getScene().getWindow();
              stage.close();
            }
          } catch (Exception ee) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Reset");
            alert.setHeaderText("Password Reset");
            alert.setContentText("Server error");
            alert.showAndWait();
          }
        });

    // set object locations
    int lay_x = 45;
    int lay_y = 40;
    vbox.setLayoutX(lay_x);
    vbox.setLayoutY(lay_y);

    vbox.getChildren().addAll(prompt, usernameLabel, username, confirmButton, cancelButton);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(vbox);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 300, 270);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Forgot Password");
    stage.show();
  }

  @FXML
  void fancy_exit(ActionEvent event) {
    Navigation.navigate((Screen.EXIT_PAGE));
  }
}
