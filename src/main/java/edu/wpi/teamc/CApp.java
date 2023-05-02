package edu.wpi.teamc;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CApp extends Application {

  @Setter @Getter private static Stage primaryStage;
  @Setter @Getter private static BorderPane rootPane;

  @Getter @Setter private static Boolean adminLoginCheck = false;
  @Getter @Setter public static Boolean wpiDB = true;
  @Getter @Setter public static Screen currScreen = Screen.HOME;
  @Getter @Setter public static String currLogin = "";

  @Override
  public void init() {
    log.info("Starting Up");
  }

  public static void timeOut() {
    // 480000
    Thread thread =
        new Thread(
            () -> {
              try {
                Thread.sleep(480000);
                Platform.runLater(
                    () -> {
                      CApp.logoutPopUp();
                    });
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
    thread.start();
  }

  static volatile boolean logoutOpen = false;

  public static void logoutPopUp() {
    if (logoutOpen || currScreen.equals(Screen.SCREENSAVER)) return;
    if (currScreen.equals(Screen.HOME)) {
      Navigation.navigate(Screen.SCREENSAVER);
      return;
    }
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();
    Text headerText = new Text("Logging out...");
    Text building = new Text("Counting down...");
    MFXButton cancel = new MFXButton("Cancel");
    MFXButton logoutButton = new MFXButton("Logout");
    vBox.getChildren().addAll(headerText, building, cancel, logoutButton);

    // set styles
    headerText.getStyleClass().add("Header");
    building.getStyleClass().add("Text");
    cancel.getStyleClass().add("MFXbutton");
    logoutButton.getStyleClass().add("MFXbutton");
    borderPane.getStyleClass().add("scenePane");

    // set object locations
    int lay_x = 50;
    int lay_y = 40;
    headerText.setLayoutX(lay_x);
    headerText.setLayoutY(lay_y);
    building.setLayoutX(lay_x);
    building.setLayoutY(lay_y + 35);

    cancel.setLayoutX(lay_x);
    cancel.setLayoutY(lay_y + 55);
    logoutButton.setLayoutX(lay_x + 125);
    logoutButton.setLayoutY(lay_y + 55);
    cancel.setMaxWidth(100);
    logoutButton.setMaxWidth(100);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(headerText, building, cancel, logoutButton);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 330, 165);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Log Out");
    stage.setAlwaysOnTop(true);
    logoutOpen = true;
    stage.show();
    Thread thread =
        new Thread() {
          @Override
          public void run() {
            for (int i = 10; i >= 0; i--) {
              try {
                building.setText(i + " seconds");
                Thread.sleep(1000);
                if (i == 0) {
                  building.setText("Logging out...");
                }
                if (Thread.currentThread().isInterrupted()) {
                  break;
                }
              } catch (InterruptedException e) {
              }
            }
            if (Thread.currentThread().isInterrupted()) {
              return;
            }
            Platform.runLater(
                new Runnable() {
                  @Override
                  public void run() {
                    if (!stage.isShowing()) {
                      stage.close();
                      CApp.timeOut();
                      return;
                    } else {
                      stage.close();
                      logoutOpen = false;
                      CApp.setAdminLoginCheck(false);
                      CApp.currScreen = Screen.SCREENSAVER;
                      //            Navigation.clearCache();
                      Navigation.navigate(Screen.SCREENSAVER);
                      Navigation.setMenuType(Navigation.MenuType.DISABLED);
                    }
                  }
                });
          }
        };
    thread.start();
    cancel.setOnAction(
        (event -> {
          thread.interrupt();
          stage.close();
          logoutOpen = false;
        }));

    logoutButton.setOnAction(
        (event -> {
          thread.interrupt();
          stage.close();
          logoutOpen = false;
          CApp.setAdminLoginCheck(false);
          CApp.currScreen = Screen.HOME;
          //          Navigation.clearCache();
          Navigation.navigate(Screen.HOME);
          Navigation.setMenuType(Navigation.MenuType.DISABLED);
        }));
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    /* primaryStage is generally only used if one of your components require the stage to display */
    CApp.primaryStage = primaryStage;

    final FXMLLoader loader = new FXMLLoader(CApp.class.getResource("views/Root.fxml"));
    final BorderPane root = loader.load();

    CApp.rootPane = root;

    final Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
    Navigation.navigate(Screen.HOME);

    CApp.timeOut();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
