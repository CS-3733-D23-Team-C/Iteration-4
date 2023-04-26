package edu.wpi.teamc;

import edu.wpi.teamc.dao.map.*;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CApp extends Application {

  @Setter @Getter private static Stage primaryStage;
  @Setter @Getter private static BorderPane rootPane;

  @Getter @Setter private static Boolean adminLoginCheck = false;
  //  List<Node> Floor1 = new ArrayList<Node>();
  //  List<Node> Floor2 = new ArrayList<Node>();
  //  List<Node> Floor3 = new ArrayList<Node>();
  //  List<Node> FloorG = new ArrayList<Node>();
  //  List<Node> FloorL1 = new ArrayList<Node>();
  //  List<Node> FloorL2 = new ArrayList<Node>();

  @Override
  public void init() {
    log.info("Starting Up");
  }

  public static void timeOut() {
    PauseTransition pause = new PauseTransition(Duration.millis(30000));
    pause.setOnFinished(
        (event -> {
          try {
            timerPopUp();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }));
    PauseTransition startPause = new PauseTransition(Duration.millis(6000));
    startPause.setOnFinished(
        (event -> {
          try {
            timerPopUp();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }));
    startPause.play();
  }

  public static void timerPopUp() throws InterruptedException {
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();

    Text headerText = new Text("Are you still there?");
    Text building = new Text("");
    int seconds = 60;

    //    for (int i = 60; i >= 0; i--) {
    //      building.setText(i + " seconds");
    //      PauseTransition pause = new PauseTransition(Duration.seconds(60));
    //      pause.setDuration(Duration.seconds(60));
    //      pause.setDelay(Duration.seconds(60));
    //      pause.setDelay(Duration.valueOf("60s"));
    //      pause.play();
    //      System.out.print("playing");
    //    }
    //    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    //    pause.setOnFinished(
    //        (event -> {
    //          Navigation.navigate(Screen.SCREENSAVER);
    //          Navigation.setMenuType(Navigation.MenuType.DISABLED);
    //        }));
    MFXButton addButton = new MFXButton("Continue");
    vBox.getChildren().addAll(headerText, building, addButton);

    // set styles
    headerText.getStyleClass().add("Header");
    building.getStyleClass().add("Text");
    addButton.getStyleClass().add("MFXbutton");
    borderPane.getStyleClass().add("scenePane");

    // set object locations
    int lay_x = 50;
    int lay_y = 40;
    headerText.setLayoutX(lay_x);
    headerText.setLayoutY(lay_y);
    building.setLayoutX(lay_x);
    building.setLayoutY(lay_y + 35);

    addButton.setLayoutX(lay_x);
    addButton.setLayoutY(lay_y + 95);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(headerText, building, addButton);
    //    Insets insets = new Insets(0, 0, 0, 200);
    //    aPane.setPadding(insets);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 410, 225);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Add Node Window");
    stage.setAlwaysOnTop(true);
    stage.show();

    long currTime = System.currentTimeMillis();
    long tempTime = 0;

    while (System.currentTimeMillis() - currTime < 20000) {
      //      System.out.println("waiting");
      if (tempTime == 0) {
        tempTime = currTime;
      }
      if (System.currentTimeMillis() - tempTime > 1000) {
        int i = 60;
        building.setText(i + " seconds");
        i--;
        tempTime = 0;
        System.out.println("one second");
      }
    }
    //    for (int i = 60; i >= 0; i--) {
    //      building.setText(i + " seconds");
    //      PauseTransition pause = new PauseTransition(Duration.millis(60000));
    //      System.console().wait(60000);
    //      System.currentTimeMillis();
    //      pause.setDuration(Duration.seconds(60));
    //      pause.setDelay(Duration.seconds(60));
    //      pause.setDelay(Duration.valueOf("60s"));
    //      pause.play();
    //      System.out.print("playing");
    //    }
    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    pause.setOnFinished(
        (event -> {
          Navigation.navigate(Screen.SCREENSAVER);
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

    timeOut();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }

  public void navigateAway() {
    Navigation.navigate(Screen.ADMIN_HOME);
  }
}
