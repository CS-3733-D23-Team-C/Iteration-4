package edu.wpi.teamc.controllers.pages;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.dao.users.PERMISSIONS;
import edu.wpi.teamc.dao.users.login.Login;
import edu.wpi.teamc.dao.users.login.LoginDao;
import edu.wpi.teamc.languageHelpers.TranslatorAPI;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
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

  boolean wrongNextLogin = true;
  Login currentLogin;

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
    language_choice = 0;
    setLanguage();
  }

  @FXML
  void spanish() throws Exception {
    language_choice = 1;
    setLanguage();
  }

  @FXML
  void chinese() throws Exception {
    language_choice = 2;
    setLanguage();
  }

  void setLanguage() throws Exception {
    HOME_SignInText.setText(LanguageSet(HOME_SignInText.getText()));
    HOME_username.setPromptText(LanguageSet(HOME_username.getPromptText()));
    HOME_password.setPromptText(LanguageSet(HOME_password.getPromptText()));
    HOME_login.setText(LanguageSet(HOME_login.getText()));
    HOME_next.setText(LanguageSet(HOME_next.getText()));
    HOME_back.setText(LanguageSet(HOME_back.getText()));
    HOME_forgot.setText(LanguageSet(HOME_forgot.getText()));
    HOME_create.setText(LanguageSet(HOME_create.getText()));
    HOME_or.setText(LanguageSet(HOME_or.getText()));
    HOME_guest.setText(LanguageSet(HOME_guest.getText()));
    HOME_exit.setText(LanguageSet(HOME_exit.getText()));
    HOME_motto.setText(LanguageSet(HOME_motto.getText()));
  }

  public TranslatorAPI translatorAPI = new TranslatorAPI();

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

  @FXML
  public void resetPassword(ActionEvent event) {
    try (CloseableHttpClient client = HttpClients.createDefault()) {
      // define website
      HttpPost httpPost = new HttpPost(new URI("https://teamc.blui.co/api/resetpassword"));
      String username = HOME_username.getText();
      //      String username = "blui";
      // format and set json
      String json = String.format("{\"username\":\"%s\"}", username);
      StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
      httpPost.setEntity(entity);

      HttpResponse response = client.execute(httpPost);
      HttpEntity responseEntity = response.getEntity();
      if (responseEntity != null) {
        String responseBody = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
        System.out.println(responseBody);
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  @FXML
  void fancy_exit(ActionEvent event) {
    Navigation.navigate((Screen.EXIT_PAGE));
  }
}
