package edu.wpi.teamc.controllers.english;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import edu.wpi.teamc.dao.users.Login;
import edu.wpi.teamc.dao.users.LoginDao;
import edu.wpi.teamc.dao.users.PERMISSIONS;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeController {

  @FXML private MFXButton GuestPage;

  @FXML private MFXButton AdminPage;

  @FXML private MFXButton clear;

  @FXML private AnchorPane mainSignin;
  @FXML private Rectangle HOME_sqr;

  @FXML private ImageView English_flag;
  @FXML private ImageView Spanish_flag;
  @FXML private Text HOME_SignInText;
  @FXML private MFXTextField HOME_username;
  @FXML private MFXTextField HOME_password;
  @FXML private MFXButton HOME_login;
  @FXML private Hyperlink HOME_forgot;
  @FXML private Hyperlink HOME_create;
  @FXML private Text HOME_or;
  @FXML private Text wrongPass;
  @FXML private MFXButton HOME_guest;
  @FXML private MFXButton HOME_exit;
  @FXML private MFXButton HOME_next;
  @FXML private MFXButton HOME_back;

  boolean wrongNextLogin = true;
  Login currentLogin;

  @FXML
  void getAdminNext(ActionEvent event) {
    String username = HOME_username.getText();
    HOME_password.setVisible(true);
    HOME_login.setVisible(true);
    HOME_next.setVisible(false);
    HOME_login.setVisible(true);
    HOME_username.setEditable(false);
    HOME_back.setVisible(true);

    LoginDao loginDao = new LoginDao();
    try {
      currentLogin = loginDao.fetchObject(username);
      wrongNextLogin = false; // if the username is correct
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void editUsername(ActionEvent event) {
    HOME_username.setEditable(true);
    HOME_password.setVisible(false);
    HOME_back.setVisible(false);
    HOME_login.setVisible(false);
    HOME_next.setVisible(true);
    wrongPass.setVisible(false);
    HOME_password.setText("");
  }

  @FXML
  void getAdmin(ActionEvent event) {
    String password = HOME_password.getText();
    boolean login = false;
    if (wrongNextLogin == false) {
      try {
        login = currentLogin.checkPassword(password);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (login) {
      if (currentLogin.getPermissions().equals(PERMISSIONS.ADMIN)) {
        Navigation.navigate(Screen.MENU);
      } else {

      }

    } else {
      // Show Error Message
      wrongPass.setVisible(true);
    }
  }

  @FXML
  void getGuest(ActionEvent event) {
    Navigation.navigate(Screen.GUEST_HOME);
  }

  @FXML
  public void getExit(ActionEvent actionEvent) {
    Navigation.navigate(Screen.EXIT_PAGE);
  }

  @FXML
  public void initialize(Stage primaryStage) throws Exception {
    //    try {
    setLanguage(language_choice);
    //      AnchorPane root = mainSignin;
    //      Scene scene = new Scene(root, mainSignin.getPrefWidth(), mainSignin.getPrefHeight());
    //      primaryStage.setScene(scene);
    //      primaryStage.show();
    //      primaryStage.setResizable(true);
    //    } catch (Exception e) {
    //      e.printStackTrace();
    //    }
  }

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

  void setLanguage(int language) {
    // this.language_choice = language;
    if (language == 0) { // 0 is english
      HOME_SignInText.setText("Sign In");
      HOME_username.setPromptText("Username");
      HOME_password.setPromptText("Password");
      HOME_login.setText("Login");
      HOME_forgot.setText("Forgot Username or Password?");
      HOME_create.setText("Create an Account");
      HOME_or.setText("OR");
      HOME_guest.setText("Continue as Guest");
      HOME_exit.setText("Exit");
    } else if (language == 1) { // 1 is spanish
      HOME_SignInText.setText("Iniciar Sesi" + "\u00F3" + "n"); // \u00F3 is the spanish o
      HOME_username.setPromptText("Nombre de usuario");
      HOME_password.setPromptText("Contrase" + "\u00F1" + "a"); // \u00F1 is the spanish n
      HOME_login.setText("Iniciar");
      HOME_forgot.setText(
          "Olvid"
              + "\u00F3"
              + " su nombre de usuario o contrase"
              + "\u00F1"
              + "a?"); // \u00F1 is the spanish n
      HOME_create.setText("Crear una cuenta");
      HOME_or.setText("O");
      HOME_guest.setText("Continuar como invitado");
      HOME_exit.setText("Salir");
    }
  }

  @FXML
  void fancy_exit(ActionEvent event) {

    TranslateTransition tran = new TranslateTransition();
    FadeTransition fade = new FadeTransition();
    ScaleTransition scale = new ScaleTransition();
    scale.setNode(HOME_sqr);
    fade.setNode(HOME_sqr);
    tran.setNode(HOME_sqr);

    Timeline t1 =
        new Timeline(
            new KeyFrame(
                Duration.millis(80),
                ae -> {
                  HOME_sqr.setVisible(true);
                  HOME_sqr.setOpacity(1);
                  scale.setToX(600);
                  scale.setToY(400);
                  scale.setDuration(Duration.millis(1500));
                  scale.play();
                  tran.setByX(-277);
                  tran.setByY(-204);
                  tran.setDuration(Duration.millis(1500));
                  tran.play();
                }),
            new KeyFrame(
                Duration.millis(1500),
                ae -> {
                  Navigation.navigate((Screen.EXIT_PAGE));
                }));
    t1.setCycleCount(1);
    t1.play();
  }
}
